package net.sparkzz.entropy.render.orthographic.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Utility class for rendering a full-screen quad.
 * <p>
 * This class sets up a Vertex Array Object (VAO) and Vertex Buffer Object (VBO)
 * for a simple quad mesh that can be used for 2D rendering.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-17
 */
public class QuadMesh {

    // encapsulates all the vertex attribute state
    private static final int VAO;
    // stores the vertex data
    private static final int VBO;

    static {
        FloatBuffer vertices = BufferUtils.createFloatBuffer(16);
        // 4 vertices: (x, y, u, v)
        vertices.put(new float[] {
                0f, 0f, 0f, 0f,
                1f, 0f, 1f, 0f,
                0f, 1f, 0f, 1f,
                1f, 1f, 1f, 1f
        }).flip();

        // 1) Generate and bind VAO
        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        // 2) Generate, bind and fill VBO
        VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        // 3) Define vertex attributes
        int stride = 4 * Float.BYTES; // 4 floats per vert

        // a) Position attribute (location = 0, size = 2 floats, offset = 0)
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, stride, 0);

        // b) TexCoord attribute (location = 1, size = 2 floats, offset = 2 floats)
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, stride, 2 * Float.BYTES);

        // 4) Unbind to clean up
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        // Free the vertex buffer
        MemoryUtil.memFree(vertices);
    }

    /**
     * Binds the quad mesh VAO for rendering.
     */
    public static void bind() {
        glBindVertexArray(VAO);
    }

    /**
     * Unbinds the quad mesh VAO.
     */
    public static void unbind() {
        glBindVertexArray(0);
    }

    /**
     * Draws the quad mesh using triangle strip mode.
     */
    public static void draw() {
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    }
}
