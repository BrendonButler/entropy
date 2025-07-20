package net.sparkzz.entropy.render.orthographic.batch;

import net.sparkzz.entropy.render.orthographic.Renderable2D;
import net.sparkzz.entropy.render.orthographic.camera.Camera2D;
import net.sparkzz.entropy.render.orthographic.shader.Shader2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for rendering batches of 2D renderable objects.
 *
 * @param <T> Type of Renderable2D
 */
public abstract class RenderBatch2D<T extends Renderable2D> implements IRenderBatch2D<T> {

    private boolean persistent = false;

    /**
     * Shader used for rendering the batch
     */
    protected Shader2D shader;
    /**
     * List of items to be rendered in the batch
     */
    protected List<T> batchItems = new ArrayList<>();

    /**
     * Constructor for AbstractRenderBatch2D.
     *
     * @param shader The Shader2D to be used for rendering
     */
    public RenderBatch2D(Shader2D shader) {
        this.shader = shader;
    }

    /**
     * Begins the batch rendering process by binding the shader,
     * setting the projection matrix, and clearing the batch if not persistent.
     *
     * @param camera The Camera2D to be used for rendering
     */
    @Override
    public void begin(Camera2D camera) {
        shader.bind();
        shader.setProjectionMatrix(camera.getProjectionMatrix());
        if(!persistent) batchItems.clear();
    }

    /**
     * Submits a renderable entity to the batch.
     *
     * @param entity The Renderable2D entity to be submitted
     */
    @Override
    public void submit(T entity) {
        batchItems.add(entity);
    }

    /**
     * Ends the batch rendering process, flushing any remaining entities.
     */
    @Override
    public void end() {
        flush();
        shader.unbind();
    }

    /**
     * Clears all entities from the batch.
     * Used for cleaning up persistent and non-persistent batches.
     */
    public void clear() {
        batchItems.clear();
    }

    /**
     * Sets whether the batch should persist between frames.
     *
     * @param persistent True if the batch should persist, false otherwise
     */
    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    /**
     * Checks if the batch is persistent.
     *
     * @return True if the batch is persistent, false otherwise
     */
    public boolean isPersistent() {
        return persistent;
    }

    /**
     * Flushes the batch, rendering all submitted entities.
     * This method must be implemented by subclasses to define specific rendering behavior.
     */
    protected abstract void flush();
}
