package net.sparkzz.entropy.render.orthographic.model;

import net.sparkzz.entropy.render.Texture;
import net.sparkzz.entropy.render.orthographic.Renderable2D;
import org.joml.Vector2f;
import org.joml.Vector4f;

/**
 * A simple UI element that extends Renderable2D.
 * This class represents a 2D UI component with position, size, color, rotation, and texture.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-17
 */
public class UIElement extends Renderable2D {

    private final Texture texture;
    private final Vector2f position;
    private final Vector2f size;
    private final Vector4f color;
    private final float rotation;
    private final int layer;

    /**
     * Constructs a UIElement with the specified properties.
     *
     * @param texture  The texture to be applied to the UI element.
     * @param position The position of the UI element in 2D space.
     * @param size     The size (width and height) of the UI element.
     * @param color    The color tint of the UI element (RGBA).
     * @param rotation The rotation angle of the UI element in degrees.
     * @param layer    The layer index for rendering order (lower values render first).
     */
    public UIElement(Texture texture, Vector2f position, Vector2f size, Vector4f color, float rotation, int layer) {
        this.texture = texture;
        this.position = position;
        this.size = size;
        this.color = color;
        this.rotation = rotation;
        this.layer = layer;
    }

    /**
     * Gets the texture of the UI element.
     *
     * @return The texture applied to the UI element.
     */
    @Override
    public Texture getTexture() {
        return texture;
    }

    /**
     * Gets the position of the UI element.
     *
     * @return The position vector of the UI element.
     */
    @Override
    public Vector2f getPosition() {
        return position;
    }

    /**
     * Gets the size of the UI element.
     *
     * @return The size vector (width and height) of the UI element.
     */
    @Override
    public Vector2f getSize() {
        return size;
    }

    /**
     * Gets the color tint of the UI element.
     *
     * @return The color vector (RGBA) of the UI element.
     */
    @Override
    public Vector4f getColor() {
        return color;
    }

    /**
     * Gets the rotation angle of the UI element.
     *
     * @return The rotation angle in degrees.
     */
    @Override
    public float getRotation() {
        return rotation;
    }

    /**
     * Gets the layer of the UI element for rendering order.
     * Lower layer values are rendered first (in the background), higher values are rendered later (in the foreground).
     *
     * @return The layer index.
     */
    public int getLayer() {
        return layer;
    }
}
