package net.sparkzz.entropy.render.text.batch;

import net.sparkzz.entropy.render.text.RenderableText;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the rendering of 2D text objects in batches.
 * This class collects text objects and renders them in a single pass.
 * It is designed to optimize the rendering process by minimizing state changes.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-28
 */
public class RenderBatchText {

    private final List<RenderableText> textBatch = new ArrayList<>();

    /**
     * Submits a RenderableText object to the batch for rendering.
     * The text will be drawn in the order it was submitted.
     *
     * @param text The RenderableText object to be rendered.
     */
    public void submit(RenderableText text) {
        textBatch.add(text);
    }

    /**
     * Begins the rendering process for the batch.
     * This method should be called before any text is submitted.
     * It prepares the rendering context, such as binding shaders or textures if necessary.
     */
    public void render() {
        for (RenderableText text : textBatch) {
            text.draw();
        }

        textBatch.clear();
    }
}
