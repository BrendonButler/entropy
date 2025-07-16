package net.sparkzz.entropy.input.mappings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static net.sparkzz.entropy.input.mappings.InputAction.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

class InputMapperTest {

    @BeforeEach
    void setUp() {
        InputMapper.clearBinding(MOVE_FORWARD);
    }

    @Test
    void testDefaultBindings() {
        assertEquals(GLFW_KEY_W, MOVE_FORWARD.getDefaultKey());
        assertNotEquals(GLFW_KEY_W, MOVE_BACKWARD.getDefaultKey());
        assertEquals(GLFW_KEY_ESCAPE, TOGGLE_PAUSE_MENU.getDefaultKey());
    }

    @Test
    void testCustomBindings() {
        assertEquals(MOVE_FORWARD.getDefaultKey(), InputMapper.getBinding(MOVE_FORWARD));

        InputMapper.setBinding(MOVE_FORWARD, GLFW_KEY_ESCAPE);
        assertEquals(GLFW_KEY_ESCAPE, InputMapper.getBinding(MOVE_FORWARD));
        assertNotEquals(GLFW_KEY_W, InputMapper.getBinding(MOVE_FORWARD));
    }

    @Test
    void testDefaultBinding_WhenCustomSet() {
        InputMapper.setBinding(MOVE_FORWARD, GLFW_KEY_ESCAPE);
        assertEquals(MOVE_FORWARD.getDefaultKey(), InputMapper.getDefaultBinding(MOVE_FORWARD));
        assertEquals(GLFW_KEY_ESCAPE, InputMapper.getBinding(MOVE_FORWARD));
    }

    @Test
    void testClearBindings() {
        InputMapper.setBinding(MOVE_FORWARD, GLFW_KEY_ESCAPE);
        assertEquals(GLFW_KEY_ESCAPE, InputMapper.getBinding(MOVE_FORWARD));

        InputMapper.clearBinding(MOVE_FORWARD);
        assertEquals(GLFW_KEY_W, InputMapper.getBinding(MOVE_FORWARD));
    }

    @Test
    void testInvalidBindings() {
        assertThrows(IllegalArgumentException.class, () -> InputMapper.setBinding(null, GLFW_KEY_W));
        assertThrows(IllegalArgumentException.class, () -> InputMapper.setBinding(MOVE_FORWARD, -1));
        assertThrows(IllegalArgumentException.class, () -> InputMapper.getBinding(null));
        assertThrows(IllegalArgumentException.class, () -> InputMapper.getDefaultBinding(null));
    }
}
