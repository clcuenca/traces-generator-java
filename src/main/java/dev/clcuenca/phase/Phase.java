package dev.clcuenca.phase;

import dev.clcuenca.utilities.SourceFile;

import java.util.ArrayList;
import java.util.List;

import static dev.clcuenca.utilities.Reflection.NewInstanceOf;

/**
 * <p>Class that encapsulates a {@link Phase} to execute upon a {@link SourceFile} to provide
 * proper error reporting & handling between the {@link Phase} and generator. Allows for loosely-coupled dependencies
 * between the compiler's {@link Phase}s.</p></p>
 * @author Carlos L. Cuenca
 * @see SourceFile
 * @version 1.0.0
 * @since 0.1.0
 */
public abstract class Phase {

    /**
     * <p>{@link Listener} instance that receives any {@link Phase.Message} instances from the {@link Phase}.</p>
     * @since 1.0.0
     * @see Listener
     * @see Phase.Message
     */
    private final Listener listener;

    /**
     * <p>The {@link SourceFile} instance that is associated with this {@link Phase}. This field is updated
     * for each {@link Phase#execute(SourceFile)} invocation.</p>
     * @since 1.0.0
     * @see SourceFile
     */
    private SourceFile sourceFile;

    /**
     * <p>Initializes the {@link Phase} to its' default state with the specified {@link Listener}.</p>
     * @param listener The {@link Listener} to bind to the {@link Phase}.
     * @since 0.1.0
     * @see Listener
     */
    public Phase(final Listener listener) {

        this.listener = listener;
        this.sourceFile = null;

    }

    /**
     * <p>Method that is invoked within {@link Phase#execute(SourceFile)}. All Phase-dependent
     * procedures should be executed here.</p>
     * @see Phase.Error
     * @since 0.1.0
     */
    abstract protected void executePhase();

    /**
     * <p>Returns the {@link Listener} instance bound to the {@link Phase}.</p>
     * @return {@link Listener} instance bound to the {@link Phase}.
     * @since 0.1.0
     */
    protected final Listener getListener() {

        return this.listener;

    }

    /**
     * <p>Returns the {@link SourceFile} instance bound to the {@link Phase}.</p>
     * @return {@link SourceFile} instance bound to the {@link Phase}.
     * @since 0.1.1
     */
    protected final SourceFile getSourceFile() {

        return this.sourceFile;

    }

    /**
     * <p>Executes the {@link Phase}. Invokes the {@link Phase}'s specific implementation.</p>
     * @throws Phase.Error If a null value was specified for the {@link SourceFile} or {@link Listener}.
     * @since 0.1.0
     */
    public final void execute(final SourceFile sourceFile) throws Phase.Error {

        // If a null value was specified for the Listener, emit the error
        if(this.listener == null)
            FatalAssert.NullListener.Assert(this);

            // If a null value was specified for the ProcessJ source file
        else if(sourceFile == null)
            FatalAssert.NullSourceFile.Assert(this);

            // If the file has not been completed by this
        else if(!sourceFile.hasBeenCompletedBy(this)) {

            // Otherwise, update the ProcessJSourceFile
            this.sourceFile = sourceFile;

            // Execute the phase
            this.executePhase();

            // Mark the file as completed by this Phase
            sourceFile.setCompletedPhase(this);

            // Clear
            this.sourceFile = null;

        }

    }

    /**
     * <p>Base class that is used to emit a message to the user.</p>
     * @author Carlos L. Cuenca
     * @since 0.1.0
     * @see Exception
     */
    public static class Message extends Exception {

        /**
         * <p>The {@link Phase} where the {@link Message} originated from.</p>
         * @since 0.1.0
         * @see Phase
         */
        private final Phase phase;

        /**
         * <p>Initializes the {@link Message} to its' default state with the specified {@link Phase}.</p>
         * @param phase The {@link Phase} where the {@link Message} originated from.
         */
        protected Message(final Phase phase) {

            // Fail if the Message was given an invalid Phase
            assert phase != null;

            this.phase = phase;

        }

        /**
         * <p>Returns the {@link Phase} where the {@link Message} originated from.</p>
         * @return {@link Phase} where the {@link Message} originated from.
         * @since 0.1.0
         * @see Phase
         */
        protected final Phase getPhase() {

            return this.phase;

        }

        /**
         * <p>Returns the {@link SourceFile} that was being processed at the time the {@link Message} was emitted.</p>
         * @return {@link SourceFile} that was being processed at the time the {@link Message} was emitted.
         * @since 0.1.0
         * @see Phase
         */
        protected final SourceFile getSourceFile() {

            return this.phase.getSourceFile();

        }

    }

    /**
     * <p>{@link Message} that represents a {@link Phase.Info} when executing a {@link Phase}.</p>
     * @author Carlos L. Cuenca
     * @since 0.1.0
     * @version 1.0.0
     * @see Message
     * @see Phase
     */
    public static class Info extends Message {

        /**
         * <p>Constructs an {@link Phase.Info} from the specified class object, {@link Phase}, & variadic parameters.
         * Once the {@link Phase.Info} is instantiated, this method will notify the specified {@link Phase}'s
         * {@link Phase.Listener} of the {@link Phase.Info}</p>
         * @param messageType The class object corresponding to the {@link Phase.Info} to instantiate.
         * @param phase The {@link Phase} where the assertion was raised.
         * @param parameters Any parameters that pertain to the {@link Phase.Info}.
         * @param <MessageType> Parameteric Type of the {@link Phase.Info}.
         */
        protected static <MessageType extends Info> void Assert(final Class<MessageType> messageType,
                                                                final Phase phase,
                                                                final Object... parameters) {

            // Notify the listener
            phase.getListener().notify(NewInstanceOf(messageType, phase, parameters));

        }

        /**
         * <p>Initializes the {@link Phase.Info} to its' default state with the specified {@link Phase}.</p>
         * @param phase The {@link Phase} corresponding to the {@link Phase.Info}.
         * @since 0.1.0
         */
        protected Info(final Phase phase) {
            super(phase);
        }

    }

    /**
     * <p>{@link Message} that represents a {@link Phase.Warning} when executing a {@link Phase}.</p>
     * @author Carlos L. Cuenca
     * @since 0.1.0
     * @version 1.0.0
     * @see Message
     * @see Phase
     */
    public static class Warning extends Message {

        /**
         * <p>Constructs an {@link Phase.Warning} from the specified class object, {@link Phase}, & variadic parameters.
         * Once the {@link Phase.Warning} is instantiated, this method will notify the specified {@link Phase}'s
         * {@link Phase.Listener} of the {@link Phase.Warning}</p>
         * @param messageType The class object corresponding to the {@link Phase.Warning} to instantiate.
         * @param phase The {@link Phase} where the assertion was raised.
         * @param parameters Any parameters that pertain to the {@link Phase.Warning}.
         * @param <MessageType> Parameteric Type of the {@link Phase.Warning}.
         */
        protected static <MessageType extends Info> void Assert(final Class<MessageType> messageType,
                                                                final Phase phase,
                                                                final Object... parameters) {

            // Notify the listener
            phase.getListener().notify(NewInstanceOf(messageType, phase, parameters));

        }

        /**
         * <p>Initializes the {@link Phase.Warning} to its' default state with the specified {@link Phase}.</p>
         * @param phase The {@link Phase} corresponding to the {@link Phase.Warning}.
         * @since 0.1.0
         */
        protected Warning(final Phase phase) {
            super(phase);
        }

    }

    /**
     * <p>{@link Message} that represents a {@link Phase.Error} when executing a {@link Phase}.</p>
     * @author Carlos L. Cuenca
     * @since 0.1.0
     * @version 1.0.0
     * @see Message
     * @see Phase
     */
    public static class Error extends Message {

        /**
         * <p>Constructs an {@link Phase.Error} from the specified class object, {@link Phase}, & variadic parameters.
         * Once the {@link Phase.Error} is instantiated, this method will notify the specified {@link Phase}'s
         * {@link Phase.Listener} of the {@link Phase.Error}</p>
         * @param messageType The class object corresponding to the {@link Phase.Error} to instantiate.
         * @param phase The {@link Phase} where the assertion was raised.
         * @param parameters Any parameters that pertain to the {@link Phase.Error}.
         * @param <MessageType> Parameteric Type of the {@link Phase.Error}.
         */
        protected static <MessageType extends Error> void Assert(final Class<MessageType> messageType,
                                                                 final Phase phase,
                                                                 final Object... parameters) {

            // Notify the listener
            phase.getListener().notify(NewInstanceOf(messageType, phase, parameters));

        }

        /**
         * <p>Initializes the {@link Phase.Error} to its' default state with the specified {@link Phase}.</p>
         * @param phase The {@link Phase} corresponding to the {@link Phase.Error}.
         * @since 0.1.0
         */
        protected Error(final Phase phase) {
            super(phase);
        }

    }

    /**
     * <p>Listener class that handles a {@link Phase}'s informative, warning, & error messages appropriately.</p>
     * @see Phase
     * @see Phase.Info
     * @see Phase.Warning
     * @see Phase.Error
     * @author Carlos L. Cuenca
     * @version 1.0.0
     * @since 0.1.0
     */
    public abstract static class Listener {

        /**
         * <p>The info logging method that handles informative messages.</p>
         * @since 0.1.0
         */
        public static LogInfo Info = null;

        /**
         * <p>The warning logging method that handles warning messages.</p>
         * @since 0.1.0
         */
        public static LogWarning Warning = null;

        /**
         * <p>The error logging method that handles error messages.</p>
         * @since 0.1.0
         */
        public static LogError Error = null;

        /**
         * <p>{@link List} containing all of the {@link Phase}'s informative messages.</p>
         * @since 0.1.0
         */
        private final List<Phase.Info> infoList;

        /**
         * <p>{@link List} containing all of the {@link Phase}'s warning messages.</p>
         * @since 0.1.0
         */
        private final List<Phase.Warning> warningList;

        /**
         * <p>{@link List} containing all of the {@link Phase}'s error messages.</p>
         * @since 0.1.0
         */
        private final List<Phase.Error> errorList;

        /**
         * <p>Initializes the {@link Phase.Listener} to its' default state.</p>
         * @since 0.1.0
         */
        public Listener() {

            this.infoList = new ArrayList<>();
            this.warningList = new ArrayList<>();
            this.errorList = new ArrayList<>();

        }

        /**
         * <p>Aggregates the specified {@link Phase.Info} to the corresponding list.</p>
         * @param phaseInfo The {@link Phase.Info} to aggregate.
         * @since 0.1.0
         */
        private void push(final Phase.Info phaseInfo) {

            if(phaseInfo != null)
                this.infoList.add(phaseInfo);

        }

        /**
         * <p>Aggregates the specified {@link Phase.Warning} to the corresponding list.</p>
         * @param phaseWarning The {@link Phase.Warning} to aggregate.
         * @since 0.1.0
         */
        private void push(final Phase.Warning phaseWarning) {

            if(phaseWarning != null)
                this.warningList.add(phaseWarning);

        }

        /**
         * <p>Aggregates the specified {@link Phase.Error} to the corresponding list.</p>
         * @param phaseError The {@link Phase.Error} to aggregate.
         * @since 0.1.0
         */
        private void push(final Phase.Error phaseError) {

            if(phaseError != null)
                this.errorList.add(phaseError);

        }

        /**
         * <p>Callback that's invoked when the {@link Phase} emits an informative {@link Phase.Message}</p>
         * @param phaseInfo The {@link Phase.Info} message to handle.
         * @see Phase.Info
         * @since 0.1.0
         */
        protected final void notify(final Phase.Info phaseInfo) {

            // Simply log the info
            Info.Log(phaseInfo.getSourceFile().getPath() + ": " + phaseInfo.getMessage());

            // Push the info
            this.push(phaseInfo);

        }

        /**
         * <p>Callback that's invoked when the {@link Phase} emits a warning {@link Phase.Message}</p>
         * @param phaseWarning The {@link Phase.Warning} message to handle.
         * @see Phase.Warning
         * @since 0.1.0
         */
        protected final void notify(final Phase.Warning phaseWarning) {

            // Simply log the warning
            Warning.Log(phaseWarning.getSourceFile() + ": " + phaseWarning.getMessage());

            // Push the warning
            this.push(phaseWarning);

        }

        /**
         * <p>Callback that's invoked when the {@link Phase} emits an error {@link Phase.Message}</p>
         * @param phaseError The {@link Phase.Error} message to handle.
         * @see Phase.Error
         * @since 0.1.0
         */
        protected final void notify(final Phase.Error phaseError)  {

            // Log the message
            Error.Log(phaseError.getSourceFile() + ": " + phaseError.getMessage());

            // Push the error
            this.push(phaseError);

        }

        /**
         * <p>Retrieves the {@link Listener}'s collection of {@link Phase.Info} messages.</p>
         * @since 0.1.0
         */
        public final List<Phase.Info> getInfoList() {

            return this.infoList;

        }

        /**
         * <p>Retrieves the {@link Listener}'s collection of {@link Phase.Warning} messages.</p>
         * @since 0.1.0
         */
        public final List<Phase.Warning> getWarningList() {

            return this.warningList;

        }

        /**
         * <p>Retrieves the {@link Listener}'s collection of {@link Phase.Error} messages.</p>
         * @since 0.1.0
         */
        public final List<Phase.Error> getErrorList() {

            return this.errorList;

        }

        /**
         * <p>Defines the {@link Phase.Listener} info logging method signature for the stream responsible for outputting
         * informative messages.</p>
         * @author Carlos L. Cuenca
         * @since 0.1.0
         */
        @FunctionalInterface
        public interface LogInfo {

            void Log(final String message);

        }

        /**
         * <p>Defines the {@link Phase.Listener} warning logging method signature for the stream responsible for
         * outputting warning messages.</p>
         * @author Carlos L. Cuenca
         * @since 0.1.0
         */
        @FunctionalInterface
        public interface LogWarning {

            void Log(final String message);

        }

        /**
         * <p>Defines the {@link Phase.Listener} error logging method signature for the stream responsible for
         * outputting error messages.</p>
         * @author Carlos L. Cuenca
         * @since 0.1.0
         */
        @FunctionalInterface
        public interface LogError {

            void Log(final String message);

        }

    }

    /**
     * <p>Set of {@link Message}s that can be emitted during execution of a {@link Phase} that has failed.</p>
     * @author Carlos L. Cuenca
     * @since 0.1.0
     * @see Message
     * @see Phase
     */
    private static class FatalAssert {

        /**
         * <p>{@link Phase.Error} to be emitted if the {@link Phase}'s {@link Listener} is not specified.</p>
         * @see Phase
         * @see Phase.Error
         * @version 1.0.0
         * @since 0.1.0
         */
        private static class NullListener extends Error {

            /**
             * <p>Emits the {@link NullListener}.</p>
             * @param phase The invoking {@link Phase}.
             */
            protected static void Assert(final Phase phase) {

                Error.Assert(NullListener.class, phase);

            }

            /**
             * <p>Constructs the {@link NullListener} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Error
             * @since 0.1.0
             */
            protected NullListener(final Phase culprit) {
                super(culprit);
            }

        }

        /**
         * <p>{@link Phase.Error} to be emitted if the {@link Phase}'s corresponding {@link SourceFile}
         * is not specified.</p>
         * @see Phase
         * @see Phase.Error
         * @version 1.0.0
         * @since 0.1.0
         */
        private static class NullSourceFile extends Error {

            /**
             * <p>Emits the {@link NullSourceFile} to its' specified {@link Listener}.</p>
             * @param phase The invoking {@link Phase}.
             */
            protected static void Assert(final Phase phase) {

                Error.Assert(NullSourceFile.class, phase);

            }

            /**
             * <p>Constructs the {@link NullListener} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Error
             * @since 0.1.0
             */
            protected NullSourceFile(final Phase culprit) {
                super(culprit);
            }

        }

    }

    /**
     * <p>Set of parsing {@link Message}s that can be emitted during execution of a {@link Phase}.</p>
     * @author Carlos L. Cuenca
     * @since 0.1.0
     * @see Message
     * @see Phase
     */
    protected static class ParserAssert {

        /**
         * <p>{@link Phase.Info} to be emitted when a {@link SourceFile} is about to be parsed.</p>
         * @see Phase
         * @see Phase.Info
         * @version 1.0.0
         * @since 0.1.0
         */
        protected static class ParsingFile extends Info {

            /**
             * <p>Emits the {@link ParserAssert.ParsingFile}.</p>
             * @param phase The invoking {@link Phase}.
             * @since 0.1.0
             * @see Phase
             */
            protected static void Assert(final Phase phase, final SourceFile sourceFile) {

                phase.getListener().notify(NewInstanceOf(ParserAssert.ParsingFile.class, phase, sourceFile));

            }

            /**
             * <p>Invalid file.</p>
             * @since 0.1.0
             * @see SourceFile
             */
            private final SourceFile invalidFile;

            /**
             * <p>Constructs the {@link ParsingFile} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Info
             * @since 0.1.0
             */
            protected ParsingFile(final Phase culprit, final Object sourceFile) {
                super(culprit);

                this.invalidFile = (SourceFile) sourceFile;

            }

            @Override
            public final String getMessage() {

                return "Parsing";

            }

        }

        /**
         * <p>{@link Phase.Error} to be emitted if a {@link SourceFile} failed to open during
         * execution of a {@link Phase}.</p>
         * @see Phase
         * @see Phase.Error
         * @version 1.0.0
         * @since 0.1.0
         */
        protected static class FileOpenFailure extends Error {

            /**
             * <p>Emits the {@link ParserAssert.FileOpenFailure}.</p>
             * @param phase The invoking {@link Phase}.
             * @since 0.1.0
             * @see Phase
             */
            protected static void Assert(final Phase phase, final SourceFile sourceFile) {

                Error.Assert(FatalAssert.NullListener.class, phase, sourceFile);

            }

            /**
             * <p>Invalid file.</p>
             * @since 0.1.0
             * @see SourceFile
             */
            private final SourceFile invalidFile;

            /**
             * <p>Constructs the {@link FileOpenFailure} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Error
             * @since 0.1.0
             */
            protected FileOpenFailure(final Phase culprit, final SourceFile sourceFile) {
                super(culprit);

                this.invalidFile = sourceFile;

            }

        }

        /**
         * <p>{@link Phase.Error} to be emitted if a {@link SourceFile} failed to parse.</p>
         * @see Phase
         * @see Phase.Error
         * @version 1.0.0
         * @since 0.1.0
         */
        protected static class ParserFailure extends Error {

            /**
             * <p>Emits the {@link ParserFailure}.</p>
             * @param phase The invoking {@link Phase}.
             * @since 0.1.0
             * @see Phase
             */
            protected static void Assert(final Phase phase, final SourceFile sourceFile) {

                Error.Assert(ParserFailure.class, phase, sourceFile);

            }

            /**
             * <p>Invalid file.</p>
             * @since 0.1.0
             * @see SourceFile
             */
            private final SourceFile invalidFile;

            /**
             * <p>Constructs the {@link ParserFailure} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Error
             * @since 0.1.0
             */
            protected ParserFailure(final Phase culprit, final SourceFile sourceFile) {
                super(culprit);

                this.invalidFile = sourceFile;

            }

        }

    }

    /**
     * <p>Set of combination generation {@link Message}s that can be emitted during execution of a {@link Phase}.</p>
     * @author Carlos L. Cuenca
     * @since 0.1.0
     * @see Message
     * @see Phase
     */
    protected static class GeneratorAssert {

        /**
         * <p>{@link Phase.Info} to be emitted when a {@link SourceFile} is about to be parsed.</p>
         * @see Phase
         * @see Phase.Info
         * @version 1.0.0
         * @since 0.1.0
         */
        protected static class GeneratingCombinations extends Info {

            /**
             * <p>Emits the {@link GeneratorAssert.GeneratingCombinations}.</p>
             * @param phase The invoking {@link Phase}.
             * @since 0.1.0
             * @see Phase
             */
            protected static void Assert(final Phase phase, final SourceFile sourceFile) {

                phase.getListener().notify(NewInstanceOf(GeneratorAssert.GeneratingCombinations.class, phase, sourceFile));

            }

            /**
             * <p>Invalid file.</p>
             * @since 0.1.0
             * @see SourceFile
             */
            private final SourceFile invalidFile;

            /**
             * <p>Constructs the {@link GeneratorAssert.GeneratingCombinations} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Info
             * @since 0.1.0
             */
            protected GeneratingCombinations(final Phase culprit, final Object sourceFile) {
                super(culprit);

                this.invalidFile = (SourceFile) sourceFile;

            }

            /**
             * <p>Returns the {@link String} message to be emitted to the user.</p>
             * @return {@link String} message to be emitted to the user.
             * @since 0.1.0
             * @see String
             * @see Exception
             */
            @Override
            public final String getMessage() {

                return "Generating Trace Combinations";

            }

        }

        /**
         * <p>{@link Phase.Info} to be emitted when a {@link SourceFile} generated a unique trace combination.</p>
         * @see Phase
         * @see Phase.Info
         * @version 1.0.0
         * @since 0.1.0
         */
        protected static class Generated extends Info {

            /**
             * <p>Emits the {@link GeneratorAssert.Generated}.</p>
             * @param phase The invoking {@link Phase}.
             * @since 0.1.0
             * @see Phase
             */
            protected static void Assert(final Phase phase, final SourceFile sourceFile, final String trace) {

                phase.getListener().notify(NewInstanceOf(GeneratorAssert.Generated.class, phase, sourceFile, trace));

            }

            /**
             * <p>Invalid file.</p>
             * @since 0.1.0
             * @see SourceFile
             */
            private final SourceFile invalidFile;

            /**
             * <p>Generated {@link String} trace.</p>
             * @since 0.1.0
             * @see String
             */
            private final String trace;

            /**
             * <p>Constructs the {@link GeneratorAssert.Generated} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Info
             * @since 0.1.0
             */
            protected Generated(final Phase culprit, final SourceFile sourceFile, final String trace) {
                super(culprit);

                this.invalidFile = sourceFile;
                this.trace = trace;

            }

            /**
             * <p>Returns the {@link String} message to be emitted to the user.</p>
             * @return {@link String} message to be emitted to the user.
             * @since 0.1.0
             * @see String
             * @see Exception
             */
            @Override
            public final String getMessage() {

                return String.format("Generated: %s", this.trace);

            }

        }

        /**
         * <p>{@link Phase.Info} to be emitted when a {@link SourceFile} generated a unique trace combination.</p>
         * @see Phase
         * @see Phase.Info
         * @version 1.0.0
         * @since 0.1.0
         */
        protected static class GeneratedTotal extends Info {

            /**
             * <p>Emits the {@link GeneratorAssert.GeneratedTotal}.</p>
             * @param phase The invoking {@link Phase}.
             * @since 0.1.0
             * @see Phase
             */
            protected static void Assert(final Phase phase, final SourceFile sourceFile, final String amount) {

                phase.getListener().notify(NewInstanceOf(
                        GeneratorAssert.GeneratedTotal.class, phase, sourceFile, amount));

            }

            /**
             * <p>Invalid file.</p>
             * @since 0.1.0
             * @see SourceFile
             */
            private final SourceFile sourceFile;

            /**
             * <p>{@link String} value of the amount of generated traces.</p>
             * @since 0.1.0
             * @see String
             */
            private final String amount;

            /**
             * <p>Constructs the {@link GeneratorAssert.GeneratedTotal} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Info
             * @since 0.1.0
             */
            protected GeneratedTotal(final Phase culprit, final SourceFile sourceFile, final String amount) {
                super(culprit);

                this.sourceFile = sourceFile;
                this.amount = amount;

            }

            /**
             * <p>Returns the {@link String} message to be emitted to the user.</p>
             * @return {@link String} message to be emitted to the user.
             * @since 0.1.0
             * @see String
             * @see Exception
             */
            @Override
            public final String getMessage() {

                return String.format("Generated %s unique traces", this.amount);

            }

        }

        /**
         * <p>{@link Phase.Error} to be emitted if a {@link SourceFile} failed to open during
         * execution of a {@link Phase}.</p>
         * @see Phase
         * @see Phase.Error
         * @version 1.0.0
         * @since 0.1.0
         */
        protected static class InvalidDepth extends Error {

            /**
             * <p>Emits the {@link GeneratorAssert.InvalidDepth}.</p>
             * @param phase The invoking {@link Phase}.
             * @param sourceFile The {@link SourceFile} that emitted the {@link Phase.Error}.
             * @since 0.1.0
             */
            protected static void Assert(final Phase phase, final SourceFile sourceFile) {

                Error.Assert(GeneratorAssert.InvalidDepth.class, phase, sourceFile);

            }

            /**
             * <p>The invalid file.</p>
             * @since 0.1.0
             * @see SourceFile
             */
            private final SourceFile invalidFile;

            /**
             * <p>Constructs the {@link GeneratorAssert.InvalidDepth} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Error
             * @since 0.1.0
             */
            protected InvalidDepth(final Phase culprit, final SourceFile sourceFile) {
                super(culprit);

                this.invalidFile = sourceFile;

            }

        }

        /**
         * <p>{@link Phase.Error} to be emitted if a {@link SourceFile} failed to write during
         * execution of a {@link Phase}.</p>
         * @see Phase
         * @see Phase.Error
         * @version 1.0.0
         * @since 0.1.0
         */
        protected static class FileWriteFailed extends Error {

            /**
             * <p>Emits the {@link GeneratorAssert.FileWriteFailed}.</p>
             * @param phase The invoking {@link Phase}.
             * @param sourceFile The {@link SourceFile} that emitted the {@link Phase.Error}.
             * @since 0.1.0
             */
            protected static void Assert(final Phase phase, final SourceFile sourceFile) {

                Error.Assert(GeneratorAssert.FileWriteFailed.class, phase, sourceFile);

            }

            /**
             * <p>The invalid file.</p>
             * @since 0.1.0
             * @see SourceFile
             */
            private final SourceFile invalidFile;

            /**
             * <p>Constructs the {@link GeneratorAssert.FileWriteFailed} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Error
             * @since 0.1.0
             */
            protected FileWriteFailed(final Phase culprit, final SourceFile sourceFile) {
                super(culprit);

                this.invalidFile = sourceFile;

            }

        }

    }

}
