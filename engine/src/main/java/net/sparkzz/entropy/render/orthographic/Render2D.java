package net.sparkzz.entropy.render.orthographic;

import net.sparkzz.entropy.render.orthographic.batch.BatchType;
import net.sparkzz.entropy.render.orthographic.batch.IRenderBatch2D;
import net.sparkzz.entropy.render.orthographic.batch.SpriteRenderBatch;
import net.sparkzz.entropy.render.orthographic.batch.UIRenderBatch;
import net.sparkzz.entropy.render.orthographic.camera.Camera2D;
import net.sparkzz.entropy.render.orthographic.shader.Shader2D;
import net.sparkzz.entropy.render.text.RenderableText;
import net.sparkzz.entropy.render.text.batch.RenderBatchText;
import net.sparkzz.entropy.render.text.shader.TextShader;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import static net.sparkzz.entropy.render.orthographic.batch.BatchType.SPRITE;
import static net.sparkzz.entropy.render.orthographic.batch.BatchType.UI_ELEMENT;

/**
 * A 2D renderer that manages render batches for sprites, UI elements, and bitmap-font text.
 *
 * <p>Each batch type uses its own dedicated shader and follows the same
 * {@code begin → submit* → end} lifecycle per frame.
 *
 * <p>Typical per-frame usage:
 * <pre>{@code
 * renderer.render(BatchType.UI_ELEMENT, uiElements);
 * renderer.renderText(textLabels);
 * }</pre>
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-17
 */
public class Render2D {

    private final Camera2D camera;
    private final Map<BatchType, IRenderBatch2D<?>> batches = Collections.synchronizedMap(new EnumMap<>(BatchType.class));
    private final RenderBatchText textBatch;

    /**
     * Constructs a {@code Render2D} with shaders for UI elements, sprites, and text.
     *
     * @param camera       The {@link Camera2D} whose projection matrix is applied each frame.
     * @param uiShader     The {@link Shader2D} used for rendering UI elements.
     * @param spriteShader The {@link Shader2D} used for rendering sprites.
     * @param textShader   The {@link TextShader} used for rendering bitmap-font text.
     */
    public Render2D(Camera2D camera, Shader2D uiShader, Shader2D spriteShader, TextShader textShader) {
        this.camera = camera;
        this.textBatch = new RenderBatchText(textShader);

        batches.put(UI_ELEMENT, new UIRenderBatch(uiShader));
        batches.put(SPRITE, new SpriteRenderBatch(spriteShader));
    }

    /**
     * Renders a collection of renderable objects of the specified batch type.
     *
     * @param type  The {@link BatchType} identifying which batch to use.
     * @param items The collection of renderable objects to render.
     * @param <T>   The concrete {@link Renderable2D} subtype.
     * @throws IllegalArgumentException If {@code type} or {@code items} is null, or no batch
     *                                  is registered for the given type.
     */
    public synchronized <T extends Renderable2D> void render(BatchType type, Collection<T> items) {
        if (type == null || items == null) {
            throw new IllegalArgumentException("BatchType and items cannot be null");
        }

        IRenderBatch2D<?> rawBatch = batches.get(type);
        if (rawBatch == null) {
            throw new IllegalArgumentException("No batch found for type: " + type);
        }

        @SuppressWarnings("unchecked")
        IRenderBatch2D<T> batch = (IRenderBatch2D<T>) rawBatch;

        batch.begin(camera);
        items.forEach(batch::submit);
        batch.end();
    }

    /**
     * Renders a collection of {@link RenderableText} objects using the text batch.
     *
     * <p>Each call follows the {@code begin → submit* → end} lifecycle: the batch is cleared
     * (unless persistent), every item is submitted, and all glyphs are flushed to the screen.
     *
     * @param items The text objects to render. Must not be null.
     * @throws IllegalArgumentException If {@code items} is null.
     */
    public synchronized void renderText(Collection<? extends RenderableText> items) {
        if (items == null)
            throw new IllegalArgumentException("items cannot be null");

        textBatch.begin(camera);
        items.forEach(textBatch::submit);
        textBatch.end();
    }
}
