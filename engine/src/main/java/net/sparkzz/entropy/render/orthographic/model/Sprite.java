package net.sparkzz.entropy.render.orthographic.model;

import net.sparkzz.entropy.render.Texture;
import net.sparkzz.entropy.render.orthographic.Renderable2D;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;

import java.util.Objects;

/**
 * A simple sprite class that extends Renderable2D.
 * This class represents a 2D sprite with position, size, color, rotation, and texture.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-17
 */
public class Sprite extends Renderable2D {

    private final Texture texture;
    private final Vector2fc position;
    private final Vector2fc size;
    private final Vector4fc color;
    private final float rotation;

    /**
     * Constructs a Sprite with the specified properties.
     *
     * @param texture  The texture to be applied to the sprite.
     * @param position The position of the sprite in 2D space.
     * @param size     The size (width and height) of the sprite.
     * @param color    The color tint of the sprite (RGBA).
     * @param rotation The rotation angle of the sprite in degrees.
     */
    public Sprite(Texture texture, Vector2f position, Vector2f size, Vector4f color, float rotation) {
        this.texture = Objects.requireNonNull(texture, "Texture cannot be null");
        this.position = new Vector2f(Objects.requireNonNull(position, "Position cannot be null"));
        this.size = new Vector2f(Objects.requireNonNull(size, "Size cannot be null"));
        this.color = new Vector4f(Objects.requireNonNull(color, "Color cannot be null"));
        this.rotation = rotation;
    }

    /**
     * Constructs a Sprite with default color (white) and no rotation.
     *
     * @param texture  The texture to be applied to the sprite.
     * @param position The position of the sprite in 2D space.
     * @param size     The size (width and height) of the sprite.
     */
    public Sprite(Texture texture, Vector2f position, Vector2f size) {
        this(texture, position, size, new Vector4f(1, 1, 1, 1), 0);
    }

    /**
     * Gets the texture of the sprite.
     *
     * @return The texture applied to the sprite.
     */
    @Override
    public Texture getTexture() {
        return texture;
    }

    /**
     * Gets the position of the sprite.
     *
     * @return The position vector of the sprite.
     */
    @Override
    public Vector2f getPosition() {
        return position.get(new Vector2f());
    }

    /**
     * Gets the size of the sprite.
     *
     * @return The size vector (width and height) of the sprite.
     */
    @Override
    public Vector2f getSize() {
        return size.get(new Vector2f());
    }

    /**
     * Gets the color tint of the sprite.
     *
     * @return The color vector (RGBA) of the sprite.
     */
    @Override
    public Vector4f getColor() {
        return color.get(new Vector4f());
    }

    /**
     * Gets the rotation angle of the sprite.
     *
     * @return The rotation angle in degrees.
     */
    @Override
    public float getRotation() {
        return rotation;
    }
}
