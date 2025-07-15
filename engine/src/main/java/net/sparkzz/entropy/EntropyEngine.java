package net.sparkzz.entropy;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * Main class for the Entropy Engine.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-14
 */
public class EntropyEngine {

    private static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
    private static final Logger log = LoggerFactory.getLogger(EntropyEngine.class);

    private final IEntropyGame game;

    private long window;
    private String windowTitle = "Entropy Engine";

    /**
     * Constructs an Entropy Engine instance with the specified game.
     *
     * @param game The game instance to run with the engine.
     */
    public EntropyEngine(IEntropyGame game) {
        if (game == null) throw new IllegalArgumentException("Game instance cannot be null");

        this.game = game;
        this.windowTitle = game.getWindowTitle() != null ? game.getWindowTitle() : windowTitle;
    }

    /**
     * Starts the Entropy Engine with the provided game instance.
     */
    public void run() {
        if (game == null) throw new IllegalArgumentException("Game instance cannot be null");

        try {
            init();
            loop();
        } finally {
            cleanup();
        }
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, windowTitle, 0L, 0L);

        if (window == 0L) throw new RuntimeException("Failed to create the GLFW window");

        try (MemoryStack stack = stackPush()) {
            IntBuffer pw = stack.mallocInt(1), ph = stack.mallocInt(1);

            glfwGetWindowSize(window, pw, ph);

            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if (videoMode == null) throw new RuntimeException("Failed to get video mode for primary monitor");

            glfwSetWindowPos(window,
                    (videoMode.width() - pw.get(0)) / 2,
                    (videoMode.height() - ph.get(0)) / 2
            );

            glfwMakeContextCurrent(window);
            glfwSwapInterval(1); // Enable v-sync
            glfwShowWindow(window);
        }
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            try {
                game.update();
                game.render();
            } catch (Exception exception) {
                log.error("Error during game loop", exception);
            }

            glfwSwapBuffers(window);
        }
    }

    private void cleanup() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();

        try (GLFWErrorCallback errorCallback = glfwSetErrorCallback(null)) {
            if (errorCallback == null) log.warn("Error callback was not set");
        }
    }
}
