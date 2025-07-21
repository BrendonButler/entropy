package net.sparkzz.entropy.render.orthographic.batch;

import net.sparkzz.entropy.render.orthographic.Renderable2D;
import net.sparkzz.entropy.render.orthographic.camera.Camera2D;

/**
 * An interface defining a batch renderer for 2D renderable objects.
 *
 * @param <T> The type of renderable object that this batch can handle, extending Renderable2D.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-17
 */
public interface IRenderBatch2D<T extends Renderable2D> {

    /**
     * Initializes the batch rendering process with the given camera.
     *
     * @param camera The Camera2D instance to be used for rendering.
     */
    void begin(Camera2D camera);

    /**
     * Submits a renderable object to the batch for rendering.
     *
     * @param renderable The renderable object to be submitted.
     */
    void submit(T renderable);

    /**
     * Ends the batch rendering process, flushing any remaining renderables to the screen.
     */
    void end();
}
