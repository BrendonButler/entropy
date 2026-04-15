package net.sparkzz.entropy.render.text.util;

import net.sparkzz.entropy.render.Texture;
import net.sparkzz.entropy.render.text.model.Glyph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for loading bitmap fonts from the BMFont text ({@code .fnt}) format.
 *
 * <p>The caller is responsible for loading the font atlas {@link Texture} before calling
 * {@link #load(InputStream, Texture)}. This separation keeps font parsing free of any
 * OpenGL dependency and makes the loader fully unit-testable.
 *
 * <p>Example usage:
 * <pre>{@code
 * Texture atlas = new Texture("/fonts/my_font.png");
 * Font font;
 *
 * try (InputStream fnt = getClass().getResourceAsStream("/fonts/my_font.fnt")) {
 *     font = FontLoader.load(fnt, atlas);
 * }
 * }</pre>
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-28
 * @see Font
 * @see Glyph
 */
public class FontLoader {

    private FontLoader() {}

    /**
     * Parses a BMFont {@code .fnt} text file and assembles a {@link Font} using the provided atlas.
     *
     * <p>Only the {@code common} and {@code char} lines are consumed; all other lines are ignored.
     * Glyph coordinates are stored as raw pixel values and normalized to UV space by the
     * batch renderer at draw time.
     *
     * @param fntStream An open {@link InputStream} for the {@code .fnt} file. The caller is
     *                  responsible for closing it.
     * @param atlas     The preloaded texture atlas that corresponds to this font. Must not be null.
     * @return A fully populated {@link Font} ready for use with
     *         {@link net.sparkzz.entropy.render.text.batch.RenderBatchText}.
     * @throws IOException              If an I/O error occurs while reading the stream.
     * @throws IllegalArgumentException If a required key is missing from a parsed line.
     * @throws NullPointerException     If either argument is null.
     */
    public static Font load(InputStream fntStream, Texture atlas) throws IOException {
        Objects.requireNonNull(fntStream, "fntStream cannot be null");
        Objects.requireNonNull(atlas, "atlas cannot be null");

        Map<Integer, Glyph> glyphs = new HashMap<>();
        int lineHeight = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fntStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("common ")) {
                    lineHeight = getInt(parseKeyValues(line), "lineHeight", line);
                } else if (line.startsWith("char ")) {
                    Map<String, String> kv = parseKeyValues(line);
                    int id       = getInt(kv, "id",       line);
                    float x      = getInt(kv, "x",        line);
                    float y      = getInt(kv, "y",        line);
                    float width  = getInt(kv, "width",    line);
                    float height = getInt(kv, "height",   line);
                    float xAdv   = getInt(kv, "xadvance", line);
                    float xOff   = getInt(kv, "xoffset",  line);
                    float yOff   = getInt(kv, "yoffset",  line);

                    glyphs.put(id, new Glyph(id, x, y, width, height, xAdv, xOff, yOff));
                }
            }
        }

        return new Font(atlas, glyphs, lineHeight);
    }

    /**
     * Splits a BMFont line into its key=value pairs, skipping the leading tag token.
     *
     * @param line A single line from a {@code .fnt} file (e.g., {@code "char id=65 x=0 ..."}).
     * @return A map of key → raw string value. Surrounding quotes are stripped from values.
     */
    private static Map<String, String> parseKeyValues(String line) {
        Map<String, String> map = new HashMap<>();
        String[] tokens = line.trim().split("\\s+");

        for (int i = 1; i < tokens.length; i++) {
            int eq = tokens[i].indexOf('=');

            if (eq > 0) {
                String key   = tokens[i].substring(0, eq);
                String value = tokens[i].substring(eq + 1).replace("\"", "");
                map.put(key, value);
            }
        }

        return map;
    }

    /**
     * Retrieves an integer value by key from a parsed key-value map.
     *
     * @param kv      The key-value map from {@link #parseKeyValues(String)}.
     * @param key     The key to look up.
     * @param srcLine The original line, used in the exception message for context.
     * @return The integer value associated with the key.
     * @throws IllegalArgumentException If the key is absent or its value is not a valid integer.
     */
    private static int getInt(Map<String, String> kv, String key, String srcLine) {
        String value = kv.get(key);

        if (value == null)
            throw new IllegalArgumentException("Missing key '" + key + "' in line: " + srcLine);

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "Invalid integer for key '" + key + "' (value='" + value + "') in line: " + srcLine, e);
        }
    }
}
