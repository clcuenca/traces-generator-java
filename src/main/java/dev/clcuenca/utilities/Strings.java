package dev.clcuenca.utilities;

import java.nio.CharBuffer;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Collection of {@link String} utility functions.</p>
 * @author Carlos L. Cuenca
 * @since 0.1.0
 */
public class Strings {

    /**
     * <p>Returns a {@link String} value containing the specified amount of spaces.</p>
     * @param length The integer value corresponding to the length of the blank {@link String}.
     * @return blank {@link String}
     * @since 0.1.0
     */
    public static String SpacesOf(final int length) {

        return CharBuffer.allocate(length).toString().replace('\0', ' ');

    }

}
