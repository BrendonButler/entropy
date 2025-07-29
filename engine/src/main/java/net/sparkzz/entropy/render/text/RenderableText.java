package net.sparkzz.entropy.render.text;

import net.sparkzz.entropy.render.text.util.Font;

/**
 * Represents a renderable text object in the rendering system.
 * This class serves as a base for all text objects that can be rendered.
 * It may include properties such as font, size, color, and other text attributes.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-28
 */
public abstract class RenderableText {

    /** The text content to be rendered. */
    protected String text;
    /** The font used for rendering the text. */
    protected Font font;
    /** The horizontal alignment of the text. */
    protected float x;
    /** The vertical alignment of the text. */
    protected float y;
    /** The scale factor for the text rendering. */
    protected float scale = 1F;

    public RenderableText(String text, Font font, float x, float y) {
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the text on the screen.
     */
    public abstract void draw();

    /**
     * Gets the text content to be rendered.
     *
     * @return The text content.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text content to be rendered.
     *
     * @param text The new text content.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the font used for rendering the text.
     *
     * @return The font.
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the font used for rendering the text.
     *
     * @param font The new font.
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Gets the scale factor for the text rendering.
     *
     * @return The scale factor.
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the horizontal alignment of the text.
     *
     * @param x The new horizontal alignment.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets the vertical alignment of the text.
     *
     * @return The vertical alignment.
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the vertical alignment of the text.
     *
     * @param y The new vertical alignment.
     */
    public void setY(float y) {
        this.y = y;
    }
}
