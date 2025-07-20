package net.sparkzz.entropy.render.orthographic.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * Abstract class representing a shader program.
 * Handles shader compilation, linking, and uniform management.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-17
 */
public abstract class ShaderProgram {

    private final int programId;
    private final Map<String, Integer> uniforms = new HashMap<>();

    /**
     * Constructs a ShaderProgram with given vertex and fragment shader source code.
     *
     * @param vertexCode   The source code for the vertex shader.
     * @param fragmentCode The source code for the fragment shader.
     */
    protected ShaderProgram(String vertexCode, String fragmentCode) {
        programId = glCreateProgram();

        if (programId == 0) throw new RuntimeException("Could not create Shader");

        int vertexShaderId = compile(vertexCode, GL_VERTEX_SHADER);
        int fragmentShaderId = compile(fragmentCode, GL_VERTEX_SHADER);

        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        glLinkProgram(programId);

        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE)
            throw new RuntimeException("Could not link shader program: " + glGetProgramInfoLog(programId));

        // shaders can be detached and deleted after linking
        glDetachShader(programId, vertexShaderId);
        glDetachShader(programId, fragmentShaderId);
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
    }

    private int compile(String code, int type) {
        int id = glCreateShader(type);

        glShaderSource(id, code);
        glCompileShader(id);

        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE)
            throw new RuntimeException("Could not compile shader: " + glGetShaderInfoLog(id));

        return id;
    }

    /**
     * Binds the shader program for use.
     * <p>
     * Call once per uniform name to cache its location.
     *
     * @param name The name of the uniform variable in the shader.
     */
    protected void createUniform(String name) {
        int location = glGetUniformLocation(programId, name);

        if (location < 0) throw new RuntimeException("Could not find uniform: " + name);

        uniforms.put(name, location);
    }

    /**
     * Sets a 4x4 matrix uniform in the shader program.
     *
     * @param name   The name of the uniform variable in the shader.
     * @param matrix The Matrix4f to set.
     */
    protected void setUniformMatrix4f(String name, Matrix4f matrix) {
        try (var stack = stackPush()) {
            FloatBuffer floatBuffer = stack.mallocFloat(16);

            matrix.get(floatBuffer);
            glUniformMatrix4fv(uniforms.get(name), false, floatBuffer);
        }
    }

    /**
     * Sets a Vector4f uniform in the shader program.
     *
     * @param name   The name of the uniform variable in the shader.
     * @param vector The Vector4f to set.
     */
    protected void setUniform4f(String name, Vector4f vector) {
        glUniform4f(uniforms.get(name), vector.x, vector.y, vector.z, vector.w);
    }

    /**
     * Binds the shader program.
     */
    public void bind() {
        glUseProgram(programId);
    }

    /**
     * Unbinds the shader program.
     */
    public void unbind() {
        glUseProgram(0);
    }

    /**
     * Cleans up the shader program resources.
     * Call this when the shader program is no longer needed.
     */
    public void cleanup() {
        unbind();
        glDeleteProgram(programId);
    }
}
