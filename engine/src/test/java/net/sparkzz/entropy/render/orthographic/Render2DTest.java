package net.sparkzz.entropy.render.orthographic;

import net.sparkzz.entropy.render.orthographic.batch.BatchType;
import net.sparkzz.entropy.render.orthographic.camera.Camera2D;
import net.sparkzz.entropy.render.orthographic.shader.Shader2D;
import net.sparkzz.entropy.render.text.shader.TextShader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link Render2D}.
 *
 * <p>All shader and camera dependencies are mocked so no OpenGL context is required.
 * Tests cover argument validation for both {@link Render2D#render(BatchType, java.util.Collection)}
 * and {@link Render2D#renderText(java.util.Collection)}.
 */
class Render2DTest {

    private Render2D renderer;

    @BeforeEach
    void setUp() {
        Camera2D camera = mock(Camera2D.class);
        Shader2D uiShader = mock(Shader2D.class);
        Shader2D spriteShader = mock(Shader2D.class);
        TextShader textShader = mock(TextShader.class);

        renderer = new Render2D(camera, uiShader, spriteShader, textShader);
    }

    // --- render() ---

    @Test
    void testRenderNullTypeThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> renderer.render(null, List.of()));
    }

    @Test
    void testRenderNullItemsThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> renderer.render(BatchType.UI_ELEMENT, null));
    }

    @Test
    void testRenderUnregisteredBatchTypeThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> renderer.render(BatchType.OTHER, List.of()));
    }

    // --- renderText() ---

    @Test
    void testRenderTextNullItemsThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> renderer.renderText(null));
    }
}
