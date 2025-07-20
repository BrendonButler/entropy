package net.sparkzz.entropy.render.orthographic.batch;

import net.sparkzz.entropy.render.orthographic.model.UIElement;
import net.sparkzz.entropy.render.orthographic.shader.Shader2D;
import net.sparkzz.entropy.render.orthographic.util.QuadMesh;

import java.util.Comparator;

/**
 * A render batch specifically for rendering UI elements.
 * It sorts the elements by their layer before rendering to ensure correct draw order.
 */
public class UIRenderBatch extends RenderBatch2D<UIElement> {

    /**
     * Constructs a UIRenderBatch with the specified shader.
     *
     * @param shader The shader to be used for rendering UI elements.
     */
    public UIRenderBatch(Shader2D shader) {
        super(shader);
    }

    /**
     * Flushes the batch by rendering all submitted UI elements.
     * Sorts the elements by their layer, binds the quad mesh,
     * sets the shader color and texture for each element,
     * and draws the quad mesh. Non-persistent elements are removed after rendering.
     */
    @Override
    protected void flush() {
        batchItems.sort(Comparator.comparingInt(UIElement::getLayer));

        QuadMesh.bind();

        for (UIElement element : batchItems) {
            shader.setColor(element.getColor());
            element.getTexture().bind();

            QuadMesh.draw();
        }

        QuadMesh.unbind();

        batchItems.removeIf(element -> !element.isPersistent());
    }
}
