package net.sparkzz.entropy.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Manages input events such as keyboard and mouse inputs.
 * Provides methods to register listeners and handle input events.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-15
 */
public class InputManager {

    private final static Logger log = LoggerFactory.getLogger(InputManager.class);

    private final long window;
    private final Set<Integer> keysDown = new HashSet<>();
    private final Set<Integer> mouseButtonsDown = new HashSet<>();
    private final List<InputListener> listeners = new ArrayList<>();

    private double mouseX, mouseY;

    /**
     * Constructs an InputManager for the specified GLFW window.
     *
     * @param window The GLFW window handle to manage input for.
     */
    public InputManager(long window) {
        this.window = window;
        initCallbacks();
    }

    /**
     * Updates the input state by checking for gamepad events.
     * This should be called regularly to process input events.
     */
    public void update() {
        for (int joyId = GLFW_JOYSTICK_1; joyId <= GLFW_JOYSTICK_LAST; joyId++) {
            if (glfwJoystickPresent(joyId)) {
                try (GLFWGamepadState state = GLFWGamepadState.malloc()) {
                    if (glfwGetGamepadState(joyId, state)) {
                        final int joystickId = joyId;

                        dispatch(listener -> listener.onGamepadEvent(joystickId, state));
                    }
                }
            }
        }
    }

    /**
     * Checks if a specific key is currently pressed down.
     *
     * @param keyCode The GLFW key code to check.
     * @return true if the key is pressed, false otherwise.
     */
    public boolean isKeyDown(int keyCode) {
        return keysDown.contains(keyCode);
    }

    /**
     * Checks if a specific mouse button is currently pressed down.
     *
     * @param button The GLFW mouse button code to check.
     * @return true if the mouse button is pressed, false otherwise.
     */
    public boolean isMouseButtonDown(int button) {
        return mouseButtonsDown.contains(button);
    }

    /**
     * Gets the current mouse X position.
     *
     * @return The X coordinate of the mouse cursor.
     */
    public double getMouseX() {
        return mouseX;
    }

    /**
     * Gets the current mouse Y position.
     *
     * @return The Y coordinate of the mouse cursor.
     */
    public double getMouseY() {
        return mouseY;
    }

    /**
     * Adds a listener to receive input events.
     *
     * @param listener The InputListener to add.
     */
    public void addListener(InputListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Removes a listener from receiving input events.
     *
     * @param listener The InputListener to remove.
     */
    public void removeListener(InputListener listener) {
        listeners.remove(listener);
    }

    private void initCallbacks() {
        try (
                GLFWKeyCallback keyCallback = glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
                    if (action == GLFW_PRESS) keysDown.add(key);
                    if (action == GLFW_RELEASE) keysDown.remove(key);

                    dispatch(listener -> listener.onKeyEvent(key, action, mods));
                });

                GLFWMouseButtonCallback mouseButtonCallback = glfwSetMouseButtonCallback(window, (win, button, action, mods) -> {
                    if (action == GLFW_PRESS) mouseButtonsDown.add(button);
                    if (action == GLFW_RELEASE) mouseButtonsDown.remove(button);

                    dispatch(listener -> listener.onMouseButtonEvent(button, action, mods));
                });

                GLFWCursorPosCallback cursorPosCallback = glfwSetCursorPosCallback(window, (win, xpos, ypos) -> {
                    mouseX = xpos;
                    mouseY = ypos;

                    dispatch(listener -> listener.onMouseMove(xpos, ypos));
                })
        ) {
            if (keyCallback == null || mouseButtonCallback == null || cursorPosCallback == null) {
                log.error("Failed to set input callbacks");
            }
        }
    }

    void dispatch(Consumer<InputListener> consumer) {
        for (InputListener listener : listeners) {
            try {
                consumer.accept(listener);
            } catch (Exception exception) {
                log.error("Error dispatching input event to listener", exception);
            }
        }
    }
}
