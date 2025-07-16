package net.sparkzz.entropy.input.mappings;

/**
 * Interface for input actions that provide a default key binding.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-16
 */
public interface InputActionProvider {

    /**
     * Gets the default key code for this input action.
     *
     * @return the default key code (GLFW key code)
     */
    int getDefaultKey();
}
