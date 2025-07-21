package net.sparkzz.entropy.render.orthographic;

import net.sparkzz.entropy.render.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

/**
 * Base class representing a 2D renderable object.
 * It defines the necessary methods to retrieve properties
 * such as texture, position, size, color, and rotation.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-17
 */
public abstract class Renderable2D {

    private boolean persistent = false;
    private int zIndex;

    /**
     * Retrieves the texture associated with this renderable object.
     *
     * @return The texture of the renderable object.
     */
    public abstract Texture getTexture();

    /**
     * Retrieves the position of the renderable object in 2D space.
     *
     * @return A Vector2f representing the position.
     */
    public abstract Vector2f getPosition();

    /**
     * Retrieves the size of the renderable object.
     *
     * @return A Vector2f representing the width and height.
     */
    public abstract Vector2f getSize();

    /**
     * Retrieves the color of the renderable object.
     *
     * @return A Vector4f (immutable) representing the RGBA color.
     */
    public abstract Vector4f getColor();

    /**
     * Retrieves the rotation of the renderable object in degrees.
     *
     * @return The rotation angle in degrees.
     */
    public abstract float getRotation();

    /**
     * Checks if the renderable object is persistent across scenes or states.
     *
     * @return True if the object is persistent, false otherwise.
     */
    public boolean isPersistent() {
        return persistent;
    }

    /**
     * Sets whether the renderable object is persistent across scenes or states.
     *
     * @param persistent True to make the object persistent, false otherwise.
     */
    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    /**
     * Gets the z-index of the renderable object for layering purposes.
     *
     * @return The z-index value.
     */
    public int getZIndex() {
        return zIndex;
    }

    /**
     * Sets the z-index of the renderable object for layering purposes.
     *
     * @param zIndex The z-index value to set.
     */
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }
}
