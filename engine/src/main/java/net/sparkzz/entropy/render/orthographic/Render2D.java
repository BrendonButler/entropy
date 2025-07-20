package net.sparkzz.entropy.render.orthographic;

import net.sparkzz.entropy.render.orthographic.batch.BatchType;
import net.sparkzz.entropy.render.orthographic.batch.IRenderBatch2D;
import net.sparkzz.entropy.render.orthographic.batch.SpriteRenderBatch;
import net.sparkzz.entropy.render.orthographic.batch.UIRenderBatch;
import net.sparkzz.entropy.render.orthographic.camera.Camera2D;
import net.sparkzz.entropy.render.orthographic.shader.Shader2D;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import static net.sparkzz.entropy.render.orthographic.batch.BatchType.SPRITE;
import static net.sparkzz.entropy.render.orthographic.batch.BatchType.UI_ELEMENT;

/**
 * A 2D renderer that manages different render batches for various types of renderable objects.
 * It uses a camera for view transformations and supports rendering UI elements and sprites.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-17
 */
public class Render2D {

    private final Camera2D camera;
    private final Map<BatchType, IRenderBatch2D<?>> batches = Collections.synchronizedMap(new EnumMap<>(BatchType.class));

    /**
     * Constructs a Render2D instance with the specified camera and shaders for UI and sprites.
     * Initializes render batches for UI elements and sprites.
     *
     * @param camera       The Camera2D instance to be used for rendering.
     * @param uiShader     The Shader2D instance for rendering UI elements.
     * @param spriteShader The Shader2D instance for rendering sprites.
     */
    public Render2D(Camera2D camera, Shader2D uiShader, Shader2D spriteShader) {
        this.camera = camera;

        batches.put(UI_ELEMENT, new UIRenderBatch(uiShader));
        batches.put(SPRITE, new SpriteRenderBatch(spriteShader));
    }

    /**
     * Renders a collection of renderable objects of the specified type.
     *
     * @param type  The class type of the renderable objects to be rendered.
     * @param items The collection of renderable objects to be rendered.
     * @param <T>   The type of renderable object, extending Renderable2D.
     */
    public synchronized <T extends Renderable2D> void render(BatchType type, Collection<T> items) {
        IRenderBatch2D<T> batch = (IRenderBatch2D<T>) batches.get(type);

        batch.begin(camera);
        items.forEach(batch::submit);
        batch.end();
    }
}
