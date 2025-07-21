package net.sparkzz.entropy.render.orthographic;

import net.sparkzz.entropy.render.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Renderable2DTest {

    @Test
    void testSetPersistent() {
        Renderable2D renderable = new Renderable2D() {
            @Override
            public Texture getTexture() {
                return null;
            }

            @Override
            public Vector2f getPosition() {
                return null;
            }

            @Override
            public Vector2f getSize() {
                return null;
            }

            @Override
            public Vector4f getColor() {
                return null;
            }

            @Override
            public float getRotation() {
                return 0;
            }
        };

        renderable.setPersistent(true);
        assertTrue(renderable.isPersistent());

        renderable.setPersistent(false);
        assertFalse(renderable.isPersistent());
    }
}
