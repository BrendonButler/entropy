package net.sparkzz.entropy.render.text;

import net.sparkzz.entropy.render.text.util.Font;
import org.joml.Vector4f;

import java.util.Objects;

/**
 * Abstract base class for all renderable text objects.
 * Holds the data required by the text batch renderer: content, font, position, scale, and color.
 * Subclasses represent text in a specific rendering context (e.g., {@link net.sparkzz.entropy.render.text.model.Text2D}).
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
    /** The horizontal position of the text origin. */
    protected float x;
    /** The vertical position of the text origin. */
    protected float y;
    /** The uniform scale factor applied to each glyph. */
    protected float scale;
    /** The RGBA color tint applied to the rendered glyphs. */
    protected Vector4f color;

    /**
     * Constructs a renderable text object with the given content, font, position, scale, and color.
     *
     * @param text  The text content to render. Must not be null.
     * @param font  The bitmap font to use. Must not be null.
     * @param x     The horizontal position of the text origin.
     * @param y     The vertical position of the text origin.
     * @param scale The uniform scale factor applied to each glyph.
     * @param color The RGBA color tint. Must not be null.
     */
    protected RenderableText(String text, Font font, float x, float y, float scale, Vector4f color) {
        this.text = Objects.requireNonNull(text, "Text cannot be null");
        this.font = Objects.requireNonNull(font, "Font cannot be null");
        this.color = new Vector4f(Objects.requireNonNull(color, "Color cannot be null"));
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

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
     * @param text The new text content. Must not be null.
     */
    public void setText(String text) {
        this.text = Objects.requireNonNull(text, "Text cannot be null");
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
     * @param font The new font. Must not be null.
     */
    public void setFont(Font font) {
        this.font = Objects.requireNonNull(font, "Font cannot be null");
    }

    /**
     * Gets the horizontal position of the text origin.
     *
     * @return The x position.
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the horizontal position of the text origin.
     *
     * @param x The new x position.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets the vertical position of the text origin.
     *
     * @return The y position.
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the vertical position of the text origin.
     *
     * @param y The new y position.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets the uniform scale factor applied to each glyph.
     *
     * @return The scale factor.
     */
    public float getScale() {
        return scale;
    }

    /**
     * Sets the uniform scale factor applied to each glyph.
     *
     * @param scale The new scale factor.
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * Gets a copy of the RGBA color tint applied to the rendered glyphs.
     *
     * @return A new {@link Vector4f} containing the color values.
     */
    public Vector4f getColor() {
        return new Vector4f(color);
    }

    /**
     * Sets the RGBA color tint applied to the rendered glyphs.
     *
     * @param color The new color. Must not be null.
     */
    public void setColor(Vector4f color) {
        this.color.set(Objects.requireNonNull(color, "Color cannot be null"));
    }
}
