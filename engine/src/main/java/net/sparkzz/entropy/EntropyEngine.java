package net.sparkzz.entropy;

import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class EntropyEngine {

    private long window;

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(800, 600, "Entropy Engine", 0, 0);

        if (window == 0) throw new RuntimeException("Failed to create the GLFW window");

        try (MemoryStack stack = stackPush()) {
            IntBuffer pw = stack.mallocInt(1), ph = stack.mallocInt(1);

            glfwGetWindowSize(window, pw, ph);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            assert vidmode != null;

            glfwSetWindowPos(window,
                    (vidmode.width() - pw.get(0)) / 2,
                    (vidmode.height() - ph.get(0)) / 2
            );

            glfwMakeContextCurrent(window);
            glfwSwapInterval(1); // Enable v-sync
            glfwShowWindow(window);
        }
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            // render/update methods

            glfwSwapBuffers(window);
        }
    }

    private void cleanup() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();

        try (GLFWErrorCallback errorCallback = glfwSetErrorCallback(null)) {
            if (errorCallback != null) errorCallback.free();
        }
    }
}
