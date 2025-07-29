package net.sparkzz.entropy.render.text.model;

/**
 * Represents a single glyph in a font.
 * A glyph is a specific visual representation of a character in a font.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-28
 */
public class Glyph {

    private final int id;
    private final float x, y, width, height;
    private final float xAdvance, xOffset, yOffset;

    /**
     * Constructs a Glyph with the specified parameters.
     *
     * @param id        The unique identifier for the glyph.
     * @param x         The x-coordinate of the glyph's top-left corner in the texture atlas.
     * @param y         The y-coordinate of the glyph's top-left corner in the texture atlas.
     * @param width     The width of the glyph in pixels.
     * @param height    The height of the glyph in pixels.
     * @param xAdvance  The amount to advance the cursor horizontally after rendering this glyph.
     * @param xOffset   The horizontal offset from the cursor position to the glyph's origin.
     * @param yOffset   The vertical offset from the cursor position to the glyph's origin.
     */
    public Glyph(int id, float x, float y, float width, float height, float xAdvance, float xOffset, float yOffset) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xAdvance = xAdvance;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /**
     * Gets the unique identifier of the glyph.
     *
     * @return The glyph's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the x-coordinate of the glyph's top-left corner in the texture atlas.
     *
     * @return The x-coordinate.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the glyph's top-left corner in the texture atlas.
     *
     * @return The y-coordinate.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the width of the glyph in pixels.
     *
     * @return The width of the glyph.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets the height of the glyph in pixels.
     *
     * @return The height of the glyph.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Gets the amount to advance the cursor horizontally after rendering this glyph.
     *
     * @return The x-advance value.
     */
    public float getXAdvance() {
        return xAdvance;
    }

    /**
     * Gets the horizontal offset from the cursor position to the glyph's origin.
     *
     * @return The x-offset value.
     */
    public float getXOffset() {
        return xOffset;
    }

    /**
     * Gets the vertical offset from the cursor position to the glyph's origin.
     *
     * @return The y-offset value.
     */
    public float getYOffset() {
        return yOffset;
    }
}
