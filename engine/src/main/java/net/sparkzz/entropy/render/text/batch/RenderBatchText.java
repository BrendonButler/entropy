package net.sparkzz.entropy.render.text.batch;

import net.sparkzz.entropy.render.Texture;
import net.sparkzz.entropy.render.orthographic.camera.Camera2D;
import net.sparkzz.entropy.render.orthographic.util.QuadMesh;
import net.sparkzz.entropy.render.text.RenderableText;
import net.sparkzz.entropy.render.text.model.Glyph;
import net.sparkzz.entropy.render.text.shader.TextShader;
import net.sparkzz.entropy.render.text.util.Font;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * Manages the rendering of text objects in batches.
 *
 * <p>Collects {@link RenderableText} instances and renders them glyph-by-glyph using the
 * {@link TextShader} and a shared {@link QuadMesh} unit quad. For each character in a submitted
 * text object the batch looks up the corresponding {@link Glyph}, computes a per-glyph model
 * matrix from its screen position and dimensions, normalises its pixel coordinates to UV space,
 * and issues a single quad draw call. Whitespace and unknown characters advance the cursor
 * without producing a draw call.
 *
 * <p>Typical usage per frame:
 * <pre>{@code
 * batch.begin(camera);     // bind shader, set projection, clear non-persistent items
 * batch.submit(text2d);    // enqueue a text object
 * batch.end();             // flush all queued text, unbind shader
 * }</pre>
 *
 * <p>Persistent mode keeps submitted items alive across frames so callers can call
 * {@link #submit(RenderableText)} once and skip it on subsequent frames:
 * <pre>{@code
 * batch.setPersistent(true);
 * batch.submit(label);
 * // subsequent frames — begin/end only, label is re-rendered automatically
 * }</pre>
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-28
 * @see TextShader
 * @see net.sparkzz.entropy.render.text.util.FontLoader
 */
public class RenderBatchText {

    private final TextShader shader;
    private final List<RenderableText> textBatch = new ArrayList<>();
    private final Matrix4f modelMatrix = new Matrix4f();
    private boolean persistent = false;

    /**
     * Constructs a {@code RenderBatchText} that uses the given shader for all draw calls.
     *
     * @param shader The {@link TextShader} to use for rendering. Must not be null.
     * @throws NullPointerException If {@code shader} is null.
     */
    public RenderBatchText(TextShader shader) {
        this.shader = Objects.requireNonNull(shader, "shader cannot be null");
    }

    /**
     * Begins a new render pass.
     *
     * <p>Binds the shader and uploads the camera's projection matrix. If the batch is not
     * persistent, all previously submitted text objects are discarded so the caller can
     * re-submit only what is visible this frame.
     *
     * @param camera The {@link Camera2D} whose projection matrix is applied to the shader.
     *               Must not be null.
     */
    public void begin(Camera2D camera) {
        shader.bind();
        shader.setProjectionMatrix(camera.getProjectionMatrix());

        if (!persistent)
            textBatch.clear();
    }

    /**
     * Submits a {@link RenderableText} object to the batch for rendering.
     *
     * @param text The text object to render. Must not be null.
     * @throws NullPointerException If {@code text} is null.
     */
    public void submit(RenderableText text) {
        textBatch.add(Objects.requireNonNull(text, "text cannot be null"));
    }

    /**
     * Ends the render pass by flushing all queued text objects to the screen and unbinding
     * the shader.
     */
    public void end() {
        flush();
        shader.unbind();
    }

    /**
     * Renders all queued text objects glyph-by-glyph and then releases them.
     *
     * <p>For each submitted {@link RenderableText}:
     * <ol>
     *   <li>The font atlas is bound to texture unit 0.</li>
     *   <li>Each character's {@link Glyph} is looked up; unknown characters are skipped.</li>
     *   <li>A model matrix is built from the glyph's screen position (cursor + offset × scale)
     *       and size (pixels × scale).</li>
     *   <li>The glyph's pixel coordinates are normalised to UV space and uploaded as atlas
     *       region uniforms.</li>
     *   <li>The shared {@link QuadMesh} unit quad is drawn.</li>
     *   <li>The horizontal cursor advances by {@code xAdvance × scale}.</li>
     * </ol>
     * Glyphs with zero width or height (e.g., space) advance the cursor without a draw call.
     */
    private void flush() {
        QuadMesh.bind();

        for (RenderableText renderable : textBatch) {
            Font font = renderable.getFont();
            Texture atlas = font.atlas();
            float atlasW = atlas.getWidth();
            float atlasH = atlas.getHeight();
            float scale = renderable.getScale();

            shader.setColor(renderable.getColor());

            glActiveTexture(GL_TEXTURE0);
            atlas.bind();
            shader.setAtlas(0);

            float cursorX = renderable.getX();

            for (char c : renderable.getText().toCharArray()) {
                Glyph glyph = font.getGlyph(c);

                if (glyph == null)
                    continue;

                cursorX += glyph.getXAdvance() * scale;

                if (glyph.getWidth() <= 0 || glyph.getHeight() <= 0)
                    continue;

                float drawX = (cursorX - glyph.getXAdvance() * scale) + glyph.getXOffset() * scale;
                float drawY = renderable.getY() + glyph.getYOffset() * scale;
                float drawW = glyph.getWidth() * scale;
                float drawH = glyph.getHeight() * scale;

                modelMatrix.identity()
                        .translate(drawX, drawY, 0f)
                        .scale(drawW, drawH, 1f);
                shader.setModel(modelMatrix);

                float u  = glyph.getX() / atlasW;
                float v  = glyph.getY() / atlasH;
                float uw = glyph.getWidth() / atlasW;
                float vh = glyph.getHeight() / atlasH;
                shader.setAtlasRegion(u, v, uw, vh);

                QuadMesh.draw();
            }

            atlas.unbind();
        }

        QuadMesh.unbind();
    }

    /**
     * Clears all queued text objects from the batch immediately, regardless of the persistent flag.
     */
    public void clear() {
        textBatch.clear();
    }

    /**
     * Sets whether the batch retains submitted text objects across frames.
     *
     * <p>When {@code true}, {@link #begin(Camera2D)} does not clear the batch, so callers need
     * only submit once. When {@code false} (the default), items are discarded at the start of
     * each frame.
     *
     * @param persistent {@code true} to enable persistent mode, {@code false} to disable it.
     */
    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    /**
     * Returns whether the batch is currently in persistent mode.
     *
     * @return {@code true} if the batch is persistent, {@code false} otherwise.
     */
    public boolean isPersistent() {
        return persistent;
    }

    /**
     * Returns the number of text objects currently queued in the batch.
     *
     * @return The current batch size.
     */
    public int getSize() {
        return textBatch.size();
    }
}
