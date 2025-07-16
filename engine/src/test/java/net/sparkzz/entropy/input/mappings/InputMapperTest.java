package net.sparkzz.entropy.input.mappings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static net.sparkzz.entropy.input.mappings.InputAction.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

class InputMapperTest {

    private static final InputMapper<InputAction> inputMapper = new InputMapper<>(InputAction.class);

    @BeforeEach
    void setUp() {
        inputMapper.clearAllBindings();
    }

    @Test
    void testDefaultBindings() {
        assertEquals(GLFW_KEY_W, MOVE_FORWARD.getDefaultKey());
        assertNotEquals(GLFW_KEY_W, MOVE_BACKWARD.getDefaultKey());
        assertEquals(GLFW_KEY_ESCAPE, TOGGLE_PAUSE_MENU.getDefaultKey());
    }

    @Test
    void testCustomBindings() {
        assertEquals(MOVE_FORWARD.getDefaultKey(), inputMapper.getBinding(MOVE_FORWARD));

        inputMapper.setBinding(MOVE_FORWARD, GLFW_KEY_ESCAPE);
        assertEquals(GLFW_KEY_ESCAPE, inputMapper.getBinding(MOVE_FORWARD));
        assertNotEquals(GLFW_KEY_W, inputMapper.getBinding(MOVE_FORWARD));
    }

    @Test
    void testDefaultBinding_WhenCustomSet() {
        inputMapper.setBinding(MOVE_FORWARD, GLFW_KEY_ESCAPE);
        assertEquals(MOVE_FORWARD.getDefaultKey(), inputMapper.getDefaultBinding(MOVE_FORWARD));
        assertEquals(GLFW_KEY_ESCAPE, inputMapper.getBinding(MOVE_FORWARD));
    }

    @Test
    void testClearBindings() {
        inputMapper.setBinding(MOVE_FORWARD, GLFW_KEY_ESCAPE);
        assertEquals(GLFW_KEY_ESCAPE, inputMapper.getBinding(MOVE_FORWARD));

        inputMapper.clearBinding(MOVE_FORWARD);
        assertEquals(GLFW_KEY_W, inputMapper.getBinding(MOVE_FORWARD));
    }

    @Test
    void testInvalidBindings() {
        assertThrows(IllegalArgumentException.class, () -> inputMapper.setBinding(null, GLFW_KEY_W));
        assertThrows(IllegalArgumentException.class, () -> inputMapper.setBinding(MOVE_FORWARD, -1));
        assertThrows(IllegalArgumentException.class, () -> inputMapper.getBinding(null));
        assertThrows(IllegalArgumentException.class, () -> inputMapper.getDefaultBinding(null));
    }
}
