package dev.clcuenca.phase;

import dev.clcuenca.utilities.DirectedGraph;
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
    abstract protected void executePhase() throws Phase.Error;

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
     * <p>Returns a valid {@link DirectedGraph} instance from the {@link SourceFile}. This method successfully
     * returns if the {@link SourceFile} contains a valid {@link DirectedGraph}.</p>
     * @return {@link DirectedGraph} corresponding to the {@link SourceFile}.
     * @since 0.1.0
     */
    protected final DirectedGraph retrieveValidDirectedGraph() {

        // Retrieve the ProcessJ Source File
        final SourceFile sourceFile = this.getSourceFile();

        // If a null value was specified for the ProcessJ source file
        if(sourceFile == null)
            FatalAssert.NullProcessJSourceFile.Assert(this);

            // If the processJ source file does not contain a DirectedGraph
        else if(sourceFile.isNotBoundedToDirectedGraph())
            FatalAssert.NullDirectedGraph.Assert(this);

        // Return the compilation
        return sourceFile.getDirectedGraph();

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
            FatalAssert.NullProcessJSourceFile.Assert(this);

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

    public static class Message extends Exception {

        private final Phase phase;

        protected Message(final Phase phase) {

            // Fail if the Message was given an invalid Phase
            assert phase != null;

            this.phase = phase;

        }

        protected final Phase getPhase() {

            return this.phase;

        }

        protected final SourceFile getSourceFile() {

            return this.phase.getSourceFile();

        }

    }

    public static class Info extends Message {

        /**
         * <p>Constructs an {@link Phase.Error} from the specified class object, {@link Phase}, & variadic parameters.
         * Once the {@link Phase.Error} is instantiated, this method will notify the specified {@link Phase}'s
         * {@link Phase.Listener} of the {@link Phase.Error}</p>
         * @param messageType The class object corresponding to the {@link Phase.Error} to instantiate.
         * @param phase The {@link Phase} where the assertion was raised.
         * @param parameters Any parameters that pertain to the {@link Phase.Error}.
         * @param <MessageType> Parameteric Type of the {@link Phase.Error}.
         */
        protected static <MessageType extends Info> void Assert(final Class<MessageType> messageType,
                                                                final Phase phase,
                                                                final Object... parameters) {

            // Notify the listener
            phase.getListener().notify(NewInstanceOf(messageType, phase, parameters));

        }

        protected Info(final Phase phase) {
            super(phase);
        }

    }

    public static class Warning extends Message {

        protected Warning(final Phase phase) {
            super(phase);
        }

    }

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

        protected Error(final Phase phase) {
            super(phase);
        }

        public final Error commit() {

            this.getPhase().getListener().notify(this);

            return this;

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
         */
        public static LogInfo Info = null;

        /**
         * <p>The warning logging method that handles warning messages.</p>
         */
        public static LogWarning Warning = null;

        /**
         * <p>The error logging method that handles error messages.</p>
         */
        public static LogError Error = null;

        /**
         * <p>{@link List} containing all of the {@link Phase}'s informative messages.</p>
         */
        private final List<Phase.Info> infoList;

        /**
         * <p>{@link List} containing all of the {@link Phase}'s warning messages.</p>
         */
        private final List<Phase.Warning> warningList;

        /**
         * <p>{@link List} containing all of the {@link Phase}'s error messages.</p>
         */
        private final List<Phase.Error> errorList;

        /**
         * <p>Initializes the {@link Phase.Listener} to its' default state.</p>
         * @since 0.1.0
         */
        public Listener() {

            this.infoList       = new ArrayList<>();
            this.warningList    = new ArrayList<>();
            this.errorList      = new ArrayList<>();

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
            Info.Log(phaseInfo.getSourceFile() + ": " + phaseInfo.getMessage());

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
        protected final List<Phase.Info> getInfoList() {

            return this.infoList;

        }

        /**
         * <p>Retrieves the {@link Listener}'s collection of {@link Phase.Warning} messages.</p>
         * @since 0.1.0
         */
        protected final List<Phase.Warning> getWarningList() {

            return this.warningList;

        }

        /**
         * <p>Retrieves the {@link Listener}'s collection of {@link Phase.Error} messages.</p>
         * @since 0.1.0
         */
        protected final List<Phase.Error> getErrorList() {

            return this.errorList;

        }

        /**
         * <p>Defines the {@link Phase.Listener} info logging method signature for the stream responsible for outputting
         * informative messages.</p>
         */
        @FunctionalInterface
        public interface LogInfo {

            void Log(final String message);

        }

        /**
         * <p>Defines the {@link Phase.Listener} warning logging method signature for the stream responsible for
         * outputting warning messages.</p>
         */
        @FunctionalInterface
        public interface LogWarning {

            void Log(final String message);

        }

        /**
         * <p>Defines the {@link Phase.Listener} error logging method signature for the stream responsible for
         * outputting error messages.</p>
         */
        @FunctionalInterface
        public interface LogError {

            void Log(final String message);

        }

    }

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
        private static class NullProcessJSourceFile extends Error {

            /**
             * <p>Emits the {@link NullProcessJSourceFile} to its' specified {@link Listener}.</p>
             * @param phase The invoking {@link Phase}.
             */
            protected static void Assert(final Phase phase) {

                Error.Assert(NullProcessJSourceFile.class, phase);

            }

            /**
             * <p>Constructs the {@link NullListener} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Error
             * @since 0.1.0
             */
            protected NullProcessJSourceFile(final Phase culprit) {
                super(culprit);
            }

        }

        /**
         * <p>{@link Phase.Error} to be emitted if the {@link Phase}'s corresponding {@link DirectedGraph}
         * is not specified.</p>
         * @see Phase
         * @see Phase.Error
         * @version 1.0.0
         * @since 0.1.0
         */
        private static class NullDirectedGraph extends Error {

            /// ------------------------
            /// Protected Static Methods

            /**
             * <p>Emits the {@link NullDirectedGraph} to its' specified {@link Listener}.</p>
             * @param phase The invoking {@link Phase}.
             */
            protected static void Assert(final Phase phase) {

                Error.Assert(NullDirectedGraph.class, phase);

            }

            /// ------------
            /// Constructors

            /**
             * <p>Constructs the {@link NullDirectedGraph} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Error
             * @since 0.1.0
             */
            protected NullDirectedGraph(final Phase culprit) {
                super(culprit);
            }

        }

    }

    protected static class ParserAssert {

        /**
         * <p>{@link Phase.Error} to be emitted if a {@link SourceFile} failed to open during
         * execution of a {@link Phase}.</p>
         * @see Phase
         * @see Phase.Error
         * @version 1.0.0
         * @since 0.1.0
         */
        protected static class FileOpenFailure extends Error {

            /// ------------------------
            /// Protected Static Methods

            /**
             * <p>Emits the {@link FatalAssert.NullListener}.</p>
             * @param phase The invoking {@link Phase}.
             */
            protected static void Assert(final Phase phase, final SourceFile sourceFile) {

                Error.Assert(FatalAssert.NullListener.class, phase);

            }

            /// --------------
            /// Private Fields

            /**
             * <p>Invalid file.</p>
             */
            private final SourceFile invalidFile;

            /// ------------
            /// Constructors

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

            /// ------------------------
            /// Protected Static Methods

            /**
             * <p>Emits the {@link ParserFailure}.</p>
             * @param phase The invoking {@link Phase}.
             */
            protected static void Assert(final Phase phase, final SourceFile sourceFile) {

                Error.Assert(ParserFailure.class, phase);

            }

            /// --------------
            /// Private Fields

            /**
             * <p>Invalid file.</p>
             */
            private final SourceFile invalidFile;

            /// ------------
            /// Constructors

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

        /**
         * <p>{@link Phase.Error} to be emitted if a {@link SourceFile}'s Parser encountered an
         * unexpected end-of-file.</p>
         * @see Phase
         * @see Phase.Error
         * @version 1.0.0
         * @since 0.1.0
         */
        protected static class UnexpectedEndOfFile extends Error {

            /// ------------------------
            /// Protected Static Methods

            /**
             * <p>Emits the {@link UnexpectedEndOfFile}.</p>
             * @param phase The invoking {@link Phase}.
             */
            protected static void Assert(final Phase phase, final int line) {

                Error.Assert(UnexpectedEndOfFile.class, phase, line);

            }

            /// --------------
            /// Private Fields

            /**
             * <p>Invalid file.</p>
             */
            private final int line;

            /// ------------
            /// Constructors

            /**
             * <p>Constructs the {@link UnexpectedEndOfFile} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Error
             * @since 0.1.0
             */
            protected UnexpectedEndOfFile(final Phase culprit, final int line) {
                super(culprit);

                this.line = line;

            }

        }

        /**
         * <p>{@link Phase.Error} to be emitted if a {@link SourceFile} contained invalid syntax.</p>
         * @see Phase
         * @see Phase.Error
         * @version 1.0.0
         * @since 0.1.0
         */
        protected static class SyntaxErrorException extends Error {

            /// ------------------------
            /// Protected Static Methods

            /**
             * <p>Emits the {@link SyntaxErrorException}.</p>
             * @param phase The invoking {@link Phase}.
             */
            protected static void Assert(final Phase phase, final int line, final int lineCount, final int length) {

                Error.Assert(SyntaxErrorException.class, phase, line, lineCount, length);

            }

            /// --------------
            /// Private Fields

            final int line      ;
            final int lineCount ;
            final int length    ;

            /// ------------
            /// Constructors

            /**
             * <p>Constructs the {@link SyntaxErrorException} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Error
             * @since 0.1.0
             */
            protected SyntaxErrorException(final Phase culprit, final int line, final int lineCount, final int length) {
                super(culprit);
                this.line       = line      ;
                this.lineCount  = lineCount ;
                this.length     = length    ;
            }

        }

        /**
         * <p>{@link Phase.Error} to be emitted if a {@link SourceFile} encountered an illegal cast expression.</p>
         * @see Phase
         * @see Phase.Error
         * @version 1.0.0
         * @since 0.1.0
         */
        protected static class IllegalCastExpression extends Error {

            /// ------------------------
            /// Protected Static Methods

            /**
             * <p>Emits the {@link IllegalCastExpression}.</p>
             * @param phase The invoking {@link Phase}.
             */
            protected static void Assert(final Phase phase, final int line, final int lineCount, final int length) {

                Error.Assert(IllegalCastExpression.class, phase, line, lineCount, length);

            }

            /// --------------
            /// Private Fields

            final int line      ;
            final int lineCount ;
            final int length    ;

            /// ------------
            /// Constructors

            /**
             * <p>Constructs the {@link IllegalCastExpression} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Error
             * @since 0.1.0
             */
            protected IllegalCastExpression(final Phase culprit, final int line, final int lineCount, final int length) {
                super(culprit);
                this.line       = line      ;
                this.lineCount  = lineCount ;
                this.length     = length    ;
            }

        }

        /**
         * <p>{@link Phase.Error} to be emitted if a {@link SourceFile} encoded a malformed Package Access.</p>
         * @see Phase
         * @see Phase.Error
         * @version 1.0.0
         * @since 0.1.0
         */
        protected static class MalformedPackageAccess extends Error {

            /// ------------------------
            /// Protected Static Methods

            /**
             * <p>Emits the {@link MalformedPackageAccess}.</p>
             * @param phase The invoking {@link Phase}.
             */
            protected static void Assert(final Phase phase, final int line, final int lineCount, final int length) {

                Error.Assert(MalformedPackageAccess.class, phase, line, lineCount, length);

            }

            /// --------------
            /// Private Fields

            final int line      ;
            final int lineCount ;
            final int length    ;

            /// ------------
            /// Constructors

            /**
             * <p>Constructs the {@link MalformedPackageAccess} to its default state.</p>
             * @param culprit The {@link Phase} instance that raised the error.
             * @see Phase
             * @see Error
             * @since 0.1.0
             */
            protected MalformedPackageAccess(final Phase culprit, final int line, final int lineCount, final int length) {
                super(culprit);
                this.line       = line      ;
                this.lineCount  = lineCount ;
                this.length     = length    ;
            }

        }

    }



}
