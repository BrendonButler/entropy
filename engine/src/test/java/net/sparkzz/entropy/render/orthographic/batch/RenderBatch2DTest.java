package net.sparkzz.entropy.render.orthographic.batch;

import net.sparkzz.entropy.render.orthographic.Renderable2D;
import net.sparkzz.entropy.render.orthographic.camera.Camera2D;
import net.sparkzz.entropy.render.orthographic.shader.Shader2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RenderBatch2DTest {

    private RenderBatch2D<Renderable2D> renderBatch;
    private Camera2D camera;
    private Shader2D shader;

    @BeforeEach
    void setUp() {
        camera = mock(Camera2D.class);
        shader = mock(Shader2D.class);
        renderBatch = new RenderBatch2D<>(shader) {
            @Override
            protected void flush() {}
        };
    }

    @Test
    void testSetPersistent() {
        renderBatch.setPersistent(true);
        assertTrue(renderBatch.isPersistent());

        renderBatch.setPersistent(false);
        assertFalse(renderBatch.isPersistent());
    }

    @Test
    @Order(20)
    void testBeginClearsBatchWhenNotPersistent() {
        Renderable2D item = mock(Renderable2D.class);

        renderBatch.submit(item);
        renderBatch.setPersistent(false);
        renderBatch.begin(camera);

        assertEquals(0, renderBatch.getSize());
    }

    @Test
    @Order(30)
    void testBeginDoesNotClearBatchWhenPersistent() {
        Renderable2D item = mock(Renderable2D.class);

        renderBatch.submit(item);
        renderBatch.setPersistent(true);
        renderBatch.begin(camera);

        assertEquals(1, renderBatch.getSize());
    }

    @Test
    @Order(10)
    void testSubmitAddsItemsToBatch() {
        Renderable2D item = mock(Renderable2D.class);

        assertEquals(0, renderBatch.getSize());

        renderBatch.submit(item);
        assertEquals(1, renderBatch.getSize());
    }

    @Test
    void testClearRemovesAllItems() {
        Renderable2D item1 = mock(Renderable2D.class);
        Renderable2D item2 = mock(Renderable2D.class);

        renderBatch.submit(item1);
        renderBatch.submit(item2);
        assertEquals(2, renderBatch.getSize());

        renderBatch.clear();
        assertEquals(0, renderBatch.getSize());
    }
}
