package net.sparkzz.entropy.render.text.batch;

import net.sparkzz.entropy.render.orthographic.camera.Camera2D;
import net.sparkzz.entropy.render.text.RenderableText;
import net.sparkzz.entropy.render.text.model.Text2D;
import net.sparkzz.entropy.render.text.shader.TextShader;
import net.sparkzz.entropy.render.text.util.Font;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link RenderBatchText}.
 *
 * <p>All tests use mocked dependencies so no OpenGL context is required.
 * The {@link TextShader} and {@link Camera2D} mocks satisfy the constructor and
 * {@link RenderBatchText#begin(Camera2D)} contracts without touching the GPU.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RenderBatchTextTest {

    private RenderBatchText batch;
    private Camera2D camera;
    private RenderableText text;

    @BeforeEach
    void setUp() {
        TextShader shader = mock(TextShader.class);
        camera = mock(Camera2D.class);
        Font font = mock(Font.class);
        text = new Text2D("Hello", font, 0f, 0f);
        batch = new RenderBatchText(shader);
    }

    @Test
    @Order(10)
    void testSubmitAddsItemToBatch() {
        assertEquals(0, batch.getSize());

        batch.submit(text);

        assertEquals(1, batch.getSize());
    }

    @Test
    @Order(20)
    void testBeginClearsBatchWhenNotPersistent() {
        batch.submit(text);
        batch.setPersistent(false);
        batch.begin(camera);

        assertEquals(0, batch.getSize());
    }

    @Test
    @Order(30)
    void testBeginDoesNotClearBatchWhenPersistent() {
        batch.submit(text);
        batch.setPersistent(true);
        batch.begin(camera);

        assertEquals(1, batch.getSize());
    }

    @Test
    void testClearRemovesAllItems() {
        Font font = mock(Font.class);
        RenderableText second = new Text2D("World", font, 10f, 10f);

        batch.submit(text);
        batch.submit(second);
        assertEquals(2, batch.getSize());

        batch.clear();

        assertEquals(0, batch.getSize());
    }

    @Test
    void testSetPersistent() {
        batch.setPersistent(true);
        assertTrue(batch.isPersistent());

        batch.setPersistent(false);
        assertFalse(batch.isPersistent());
    }

    @Test
    void testNullShaderThrows() {
        assertThrows(NullPointerException.class, () -> new RenderBatchText(null));
    }

    @Test
    void testNullTextThrows() {
        assertThrows(NullPointerException.class, () -> batch.submit(null));
    }
}
