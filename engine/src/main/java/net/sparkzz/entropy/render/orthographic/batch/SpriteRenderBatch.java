package net.sparkzz.entropy.render.orthographic.batch;

import net.sparkzz.entropy.render.orthographic.model.Sprite;
import net.sparkzz.entropy.render.orthographic.shader.Shader2D;
import net.sparkzz.entropy.render.orthographic.util.QuadMesh;
import org.joml.Matrix4f;

/**
 * A render batch specifically for rendering sprites.
 * It handles the rendering of 2D sprite objects using a specified shader.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-17
 */
public class SpriteRenderBatch extends RenderBatch2D<Sprite> {

    private final Matrix4f modelMatrix = new Matrix4f();

    /**
     * Constructs a SpriteRenderBatch with the specified shader.
     *
     * @param shader The Shader2D instance to be used for rendering sprites.
     */
    public SpriteRenderBatch(Shader2D shader) {
        super(shader);
    }

    /**
     * Flushes the batch by rendering all submitted sprites.
     * Binds the quad mesh, sets the shader color and texture for each sprite,
     * and draws the quad mesh. Non-persistent sprites are removed after rendering.
     */
    @Override
    protected void flush() {
        QuadMesh.bind();

        for (Sprite sprite : batchItems) {
            modelMatrix.identity()
                    .translate(sprite.getPosition().x, sprite.getPosition().y, 0)
                    .scale(sprite.getSize().x, sprite.getSize().y, 1)
                    .rotateZ((float) Math.toRadians(sprite.getRotation()));
            shader.setModel(modelMatrix);

            shader.setColor(sprite.getColor());
            sprite.getTexture().bind();

            QuadMesh.draw();

            sprite.getTexture().unbind();
        }

        QuadMesh.unbind();

        batchItems.removeIf(sprite -> !sprite.isPersistent());
    }
}
