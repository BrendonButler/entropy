package net.sparkzz.entropy.render.orthographic.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;

/**
 * Shader program for 2D rendering.
 * Manages uniforms for projection, model transformation, and color.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-17
 */
public class Shader2D extends ShaderProgram {

    /**
     * Constructs a Shader2D instance with specified vertex and fragment shader source code.
     * Initializes the necessary uniforms for 2D rendering.
     *
     * @param vertexSource   The source code of the vertex shader.
     * @param fragmentSource The source code of the fragment shader.
     */
    public Shader2D(String vertexSource, String fragmentSource) {
        super(vertexSource, fragmentSource);
        createUniform("uProjection");
        createUniform("uModel");
        createUniform("uColor");
    }

    /**
     * Sets the projection matrix uniform.
     *
     * @param projection The projection matrix to set.
     */
    public void setProjectionMatrix(Matrix4f projection) {
        setUniformMatrix4f("uProjection", projection);
    }

    /**
     * Sets the model transformation matrix uniform.
     *
     * @param model The model transformation matrix to set.
     */
    public void setModel(Matrix4f model) {
        setUniformMatrix4f("uModel", model);
    }

    /**
     * Sets the color uniform.
     *
     * @param color The color vector (RGBA) to set.
     */
    public void setColor(Vector4f color) {
        setUniform4f("uColor", color);
    }
}
