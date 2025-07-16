package net.sparkzz.entropy.input.mappings;

import java.util.Collections;
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
public class InputMapper<T extends Enum<T> & InputActionProvider> {

    private final Map<T, Integer> defaultBindings;
    private final Map<T, Integer> userBindings;

    /**
     * Constructs an InputMapper for the specified InputAction enum type.
     *
     * @param actionClass the class of the InputAction enum
     */
    public InputMapper(Class<T> actionClass) {
        this.defaultBindings = Collections.unmodifiableMap(
                Stream.of(actionClass.getEnumConstants()).collect(Collectors.toMap(
                        Function.identity(),
                        InputActionProvider::getDefaultKey,
                        (a, b) -> a,
                        () -> new EnumMap<>(actionClass)
                )));

        this.userBindings = Collections.synchronizedMap(new EnumMap<>(actionClass));
    }

    /**
     * Sets a custom key binding for the specified InputAction.
     *
     * @param action the InputAction to bind
     * @param key    the GLFW key code to bind to the action
     */
    public void setBinding(T action, int key) {
        if (action == null) throw new IllegalArgumentException("InputAction cannot be null");
        if (key < 0) throw new IllegalArgumentException("Key must be a valid GLFW key code");

        userBindings.put(action, key);
    }

    /**
     * Clears the custom key binding for the specified InputAction.
     *
     * @param action the InputAction to clear the binding for
     */
    public void clearBinding(T action) {
        if (action == null) throw new IllegalArgumentException("InputAction cannot be null");

        userBindings.remove(action);
    }

    /**
     * Clears all custom key bindings, reverting all actions to their default bindings.
     */
    public void clearAllBindings() {
        userBindings.clear();
    }

    /**
     * Retrieves the key binding for the specified InputAction.
     *
     * @param action the InputAction to retrieve the binding for
     * @return the GLFW key code bound to the action
     */
    public int getBinding(T action) {
        if (action == null) throw new IllegalArgumentException("InputAction cannot be null");

        return userBindings.getOrDefault(action, defaultBindings.get(action));
    }

    /**
     * Retrieves the default key binding for the specified InputAction.
     *
     * @param action the InputAction to retrieve the default binding for
     * @return the default GLFW key code bound to the action
     */
    public int getDefaultBinding(T action) {
        if (action == null) throw new IllegalArgumentException("InputAction cannot be null");

        return defaultBindings.get(action);
    }
}
