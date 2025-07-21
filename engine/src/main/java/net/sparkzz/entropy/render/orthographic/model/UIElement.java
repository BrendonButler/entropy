package net.sparkzz.entropy.render.orthographic.model;

import net.sparkzz.entropy.render.Texture;
import net.sparkzz.entropy.render.orthographic.Renderable2D;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;

import java.util.Objects;

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
    private final Vector2fc position;
    private final Vector2fc size;
    private final Vector4fc color;
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
        this.texture = Objects.requireNonNull(texture, "Texture cannot be null");
        this.position = new Vector2f(Objects.requireNonNull(position, "Position cannot be null"));
        this.size = new Vector2f(Objects.requireNonNull(size, "Size cannot be null"));
        this.color = new Vector4f(Objects.requireNonNull(color, "Color cannot be null"));
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
        return position.get(new Vector2f());
    }

    /**
     * Gets the size of the UI element.
     *
     * @return The size vector (width and height) of the UI element.
     */
    @Override
    public Vector2f getSize() {
        return size.get(new Vector2f());
    }

    /**
     * Gets the color tint of the UI element.
     *
     * @return The color vector (RGBA) of the UI element.
     */
    @Override
    public Vector4f getColor() {
        return color.get(new Vector4f());
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
