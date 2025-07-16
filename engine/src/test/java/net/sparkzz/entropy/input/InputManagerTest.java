package net.sparkzz.entropy.input;

import org.junit.jupiter.api.Test;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.mockito.Mockito.*;

class InputManagerTest {

    @Test
    void testAddListener() {
        InputManager inputManager = new InputManager(999L);
        InputListener inputListener = mock(InputListener.class);

        inputManager.addListener(inputListener);
        inputManager.addListener(inputListener); // Adding the same listener again should not duplicate it

        // Verify that dispatching calls the listener exactly once, not multiple times
        inputManager.dispatch(listener -> listener.onKeyEvent(GLFW_KEY_W, GLFW_PRESS, 0));
        verify(inputListener, times(1)).onKeyEvent(GLFW_KEY_W, GLFW_PRESS, 0);
    }

    @Test
    void testRemoveListener() {
        InputManager inputManager = new InputManager(999L);
        InputListener inputListener = mock(InputListener.class);

        inputManager.addListener(inputListener);
        inputManager.removeListener(inputListener);

        inputManager.dispatch(listener -> listener.onKeyEvent(GLFW_KEY_W, GLFW_PRESS, 0));
        verify(inputListener, never()).onKeyEvent(anyInt(), anyInt(), anyInt());
    }
}
