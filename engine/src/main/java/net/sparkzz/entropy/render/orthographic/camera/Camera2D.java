package net.sparkzz.entropy.render.orthographic.camera;

import org.joml.Matrix4f;

/**
 * A simple 2D camera that provides an orthographic projection matrix.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-17
 */
public class Camera2D {

    private final Matrix4f projectionMatrix = new Matrix4f();

    /**
     * Constructs a Camera2D instance with an initial projection matrix.
     *
     * @param width  The width of the viewport.
     * @param height The height of the viewport.
     */
    public void resize(int width, int height) {
        projectionMatrix.identity().ortho2D(0, width, height, 0);
    }

    /**
     * Retrieves the current projection matrix of the camera.
     *
     * @return The orthographic projection matrix.
     */
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
