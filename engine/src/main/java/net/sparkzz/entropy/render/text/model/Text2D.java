package net.sparkzz.entropy.render.text.model;

import net.sparkzz.entropy.render.text.RenderableText;
import net.sparkzz.entropy.render.text.util.Font;
import org.joml.Vector4f;

/**
 * A renderable text object in 2D orthographic space.
 * Wraps a string of text with a bitmap font, screen-space position, scale, and color tint
 * for submission to {@link net.sparkzz.entropy.render.text.batch.RenderBatchText}.
 *
 * <p>Example usage:
 * <pre>{@code
 * Text2D label = new Text2D("Score: 0", font, 10f, 10f, 1f, new Vector4f(1f, 1f, 1f, 1f));
 * }</pre>
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-28
 * @see RenderableText
 */
public class Text2D extends RenderableText {

    /**
     * Constructs a {@code Text2D} with full control over all rendering properties.
     *
     * @param text  The text content to render. Must not be null.
     * @param font  The bitmap font to use. Must not be null.
     * @param x     The horizontal position of the text origin in screen space.
     * @param y     The vertical position of the text origin in screen space.
     * @param scale The uniform scale factor applied to each glyph.
     * @param color The RGBA color tint. Must not be null.
     */
    public Text2D(String text, Font font, float x, float y, float scale, Vector4f color) {
        super(text, font, x, y, scale, color);
    }

    /**
     * Constructs a {@code Text2D} with default scale (1.0) and opaque white color.
     *
     * @param text The text content to render. Must not be null.
     * @param font The bitmap font to use. Must not be null.
     * @param x    The horizontal position of the text origin in screen space.
     * @param y    The vertical position of the text origin in screen space.
     */
    public Text2D(String text, Font font, float x, float y) {
        super(text, font, x, y, 1f, new Vector4f(1f, 1f, 1f, 1f));
    }
}
