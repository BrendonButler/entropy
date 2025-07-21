package net.sparkzz.entropy.io.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Utility class for loading resources from the classpath.
 */
public class ResourceLoader {

    /**
     * Loads a resource file as a string.
     *
     * @param filePath The path to the resource file.
     * @return The contents of the resource file as a string.
     * @throws IOException If an I/O error occurs when reading from the file or a malformed or unmappable byte sequence is read.
     */
    public static String loadResourceAsString(String filePath) throws IOException {
        try (var inputStream = Objects.requireNonNull(
                ResourceLoader.class.getResourceAsStream(filePath),
                "Resource not found: " + filePath)) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
