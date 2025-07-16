package net.sparkzz.entropy.input;

import org.junit.jupiter.api.Test;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.mockito.Mockito.*;

class InputListenerTest {

    @Test
    void testOnKeyEvent() {
        InputListener listener = mock(InputListener.class);
        listener.onKeyEvent(GLFW_KEY_W, GLFW_PRESS, 0);
        verify(listener, times(1)).onKeyEvent(GLFW_KEY_W, GLFW_PRESS, 0);
    }

    @Test
    void testOnMouseButtonEvent() {
        InputListener listener = mock(InputListener.class);
        listener.onMouseButtonEvent(0, GLFW_PRESS, 0);
        verify(listener, times(1)).onMouseButtonEvent(0, GLFW_PRESS, 0);
    }

    @Test
    void testOnMouseMoveEvent() {
        InputListener listener = mock(InputListener.class);
        listener.onMouseMove(100.0, 200.0);
        verify(listener, times(1)).onMouseMove(100.0, 200.0);
    }

    @Test
    void testOnGamepadEvent() {
        InputListener listener = mock(InputListener.class);
        listener.onGamepadEvent(0, null);
        verify(listener, times(1)).onGamepadEvent(0, null);
    }
}
