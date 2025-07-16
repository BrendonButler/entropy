package net.sparkzz.entropy.input.mappings;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Enum representing input actions in the Entropy Engine.
 * Each action is associated with a default GLFW key code.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-15
 */
public enum InputAction {
    // Movement
    MOVE_FORWARD(GLFW_KEY_W),
    MOVE_BACKWARD(GLFW_KEY_S),
    MOVE_LEFT(GLFW_KEY_A),
    MOVE_RIGHT(GLFW_KEY_D),

    // Controls
    INTERACT(GLFW_KEY_F),
    TOGGLE_INVENTORY(GLFW_KEY_E),

    // Generic
    TOGGLE_PAUSE_MENU(GLFW_KEY_ESCAPE);

    private final int defaultKey;

    /**
     * Constructs an InputAction with a default GLFW key code.
     *
     * @param defaultKey the default GLFW key code for this action
     */
    InputAction(int defaultKey) {
        this.defaultKey = defaultKey;
    }

    /**
     * Gets the default GLFW key code for this input action.
     *
     * @return the default key code
     */
    public int getDefaultKey() {
        return defaultKey;
    }
}
