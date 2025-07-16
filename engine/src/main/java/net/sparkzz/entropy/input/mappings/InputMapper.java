package net.sparkzz.entropy.input.mappings;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Maps InputActions to GLFW key codes, allowing for custom key bindings.
 * Provides methods to set, clear, and retrieve key bindings for actions.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-15
 */
public class InputMapper {

    private static final Map<InputAction, Integer> defaultBindings = Stream.of(InputAction.values()).collect(Collectors.toMap(
            Function.identity(),
            InputAction::getDefaultKey,
            (a, b) -> a,
            () -> new EnumMap<>(InputAction.class)
    ));

    private static final Map<InputAction, Integer> userBindings = new EnumMap<>(InputAction.class);

    /**
     * Sets a custom key binding for the specified InputAction.
     *
     * @param action the InputAction to bind
     * @param key    the GLFW key code to bind to the action
     */
    public static void setBinding(InputAction action, int key) {
        if (action == null) throw new IllegalArgumentException("InputAction cannot be null");
        if (key < 0) throw new IllegalArgumentException("Key must be a valid GLFW key code");

        userBindings.put(action, key);
    }

    /**
     * Clears the custom key binding for the specified InputAction.
     * If no custom binding exists, it will not affect the default binding.
     *
     * @param action the InputAction to clear the binding for
     */
    public static void clearBinding(InputAction action) {
        if (action == null) throw new IllegalArgumentException("InputAction cannot be null");

        userBindings.remove(action);
    }

    /**
     * Retrieves the key binding for the specified InputAction.
     * If a custom binding exists, it returns that; otherwise, it returns the default binding.
     *
     * @param action the InputAction to retrieve the binding for
     * @return the GLFW key code bound to the action
     */
    public static int getBinding(InputAction action) {
        if (action == null) throw new IllegalArgumentException("InputAction cannot be null");

        return userBindings.getOrDefault(action, defaultBindings.get(action));
    }

    /**
     * Retrieves the default key binding for the specified InputAction.
     *
     * @param action the InputAction to retrieve the default binding for
     * @return the default GLFW key code bound to the action
     */
    public static int getDefaultBinding(InputAction action) {
        if (action == null) throw new IllegalArgumentException("InputAction cannot be null");

        return defaultBindings.get(action);
    }
}
