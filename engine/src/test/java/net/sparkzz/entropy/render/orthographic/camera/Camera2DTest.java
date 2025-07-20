package net.sparkzz.entropy.render.orthographic.camera;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Camera2DTest {

    @Test
    void testResize() {
        Camera2D camera = new Camera2D();

        int width = 800, height = 600;

        camera.resize(width, height);

        // The scale factors for orthographic projection should be (2 / width) and (-2 / height)
        assertEquals(2f / width, camera.getProjectionMatrix().m00(), 0.0001);
        assertEquals(-2f / height, camera.getProjectionMatrix().m11(), 0.0001);
    }

    @Test
    void testProjectionMatrixNotNull() {
        Camera2D camera = new Camera2D();

        assertNotNull(camera.getProjectionMatrix());
    }
}
