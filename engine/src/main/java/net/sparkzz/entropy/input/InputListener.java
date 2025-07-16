package net.sparkzz.entropy.input;

import org.lwjgl.glfw.GLFWGamepadState;

/**
 * Interface for handling input events such as keyboard, mouse, and gamepad inputs.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-15
 */
public interface InputListener {

    /**
     * Called when a keyboard event occurs.
     *
     * @param key    The key code of the pressed or released key.
     * @param action The action performed (GLFW_PRESS, GLFW_RELEASE, etc.).
     * @param mods   The modifier keys (e.g., SHIFT, CTRL) that were active during the event.
     */
    void onKeyEvent(int key, int action, int mods);

    /**
     * Called when a mouse button event occurs.
     *
     * @param button The mouse button code (e.g., GLFW_MOUSE_BUTTON_LEFT).
     * @param action The action performed (GLFW_PRESS, GLFW_RELEASE, etc.).
     * @param mods   The modifier keys (e.g., SHIFT, CTRL) that were active during the event.
     */
    void onMouseButtonEvent(int button, int action, int mods);

    /**
     * Called when the mouse is moved.
     *
     * @param xPos The x-coordinate of the mouse position.
     * @param yPos The y-coordinate of the mouse position.
     */
    void onMouseMove(double xPos, double yPos);

    /**
     * Called when a gamepad event occurs.
     *
     * @param joyId The ID of the gamepad.
     * @param state The current state of the gamepad.
     */
    void onGamepadEvent(int joyId, GLFWGamepadState state);
}
