package net.sparkzz.entropy.render.text.batch;

import net.sparkzz.entropy.render.text.RenderableText;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the rendering of text objects in batches.
 * Collects {@link RenderableText} instances and renders them in a single pass using a
 * dedicated text shader and font atlas.
 *
 * <p>Full shader-based glyph rendering will be implemented in a subsequent commit.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-28
 */
public class RenderBatchText {

    private final List<RenderableText> textBatch = new ArrayList<>();

    /**
     * Submits a {@link RenderableText} object to the batch for rendering.
     *
     * @param text The text object to render. Must not be null.
     */
    public void submit(RenderableText text) {
        textBatch.add(text);
    }

    /**
     * Renders all submitted text objects and clears the batch.
     * Full GL rendering is not yet implemented.
     */
    public void render() {
        textBatch.clear();
    }
}
