package net.sparkzz.entropy.render.text.util;

import net.sparkzz.entropy.render.Texture;
import net.sparkzz.entropy.render.text.model.Glyph;

import java.util.Map;

/**
 * Represents a font, which consists of a texture atlas containing glyphs and their metadata.
 * This class provides access to the texture atlas, individual glyphs, and the line height of the font.
 *
 * @param atlas      The texture atlas containing the glyphs.
 * @param glyphs     A map of glyphs indexed by their unique IDs.
 * @param lineHeight The height of a line in the font, used for text layout.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-28
 */
public record Font(Texture atlas, Map<Integer, Glyph> glyphs, int lineHeight) {

    /**
     * Gets a glyph by its unique identifier.
     *
     * @param id The unique identifier of the glyph.
     * @return The Glyph object corresponding to the specified ID, or null if not found.
     */
    public Glyph getGlyph(int id) {
        return glyphs.get(id);
    }
}
