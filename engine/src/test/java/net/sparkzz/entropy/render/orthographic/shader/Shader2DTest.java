package net.sparkzz.entropy.render.orthographic.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import java.io.IOException;

import static net.sparkzz.entropy.io.util.ResourceLoader.loadResourceAsString;
import static org.mockito.Mockito.*;

public class Shader2DTest {

    private long window;
    private String vertexShaderSource, fragmentShaderSource;

    @BeforeEach
    void setUp() {
        if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        window = GLFW.glfwCreateWindow(800, 600, "Shader2D Test", 0L, 0L);

        if (window == 0L) throw new RuntimeException("Failed to create the GLFW window");

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        try {
            vertexShaderSource = loadResourceAsString("/shaders/test_vertex_shader.glsl");
            fragmentShaderSource = loadResourceAsString("/shaders/test_fragment_shader.glsl");
        } catch (IOException exception) {
            throw new RuntimeException("Failed to load shader sources for Shader2D tests", exception);
        }
    }

    @AfterEach
    void tearDown() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    @Test
    void testSetProjectionMatrix() {
        Shader2D shader = spy(new Shader2D(vertexShaderSource, fragmentShaderSource));
        Matrix4f matrix = new Matrix4f();

        shader.setProjectionMatrix(matrix);

        verify(shader).setUniformMatrix4f("uProjection", matrix);
    }

    @Test
    void testSetColor() {
        Shader2D shader = spy(new Shader2D(vertexShaderSource, fragmentShaderSource));
        Vector4f color = new Vector4f(1f, 0f, 0f, 1f);

        shader.setColor(color);

        verify(shader).setUniform4f("uColor", color);
    }
}
