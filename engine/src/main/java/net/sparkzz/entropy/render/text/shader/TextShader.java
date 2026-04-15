package net.sparkzz.entropy.render.text.shader;

import net.sparkzz.entropy.render.orthographic.shader.ShaderProgram;
import org.joml.Matrix4f;
import org.joml.Vector4f;

/**
 * Shader program for bitmap font text rendering.
 * Manages uniforms for projection, per-glyph model transformation, atlas UV region, and color tint.
 *
 * <p>Each glyph is rendered as a unit quad. The atlas UV region is specified per draw call via
 * {@link #setAtlasRegion(float, float, float, float)}, allowing the same quad geometry to be
 * reused for every glyph with no per-glyph vertex buffer updates.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-28
 * @see net.sparkzz.entropy.render.text.batch.RenderBatchText
 */
public class TextShader extends ShaderProgram {

    /**
     * Constructs a {@code TextShader} from the given vertex and fragment shader source.
     *
     * @param vertexSource   The GLSL source of the vertex shader.
     * @param fragmentSource The GLSL source of the fragment shader.
     */
    public TextShader(String vertexSource, String fragmentSource) {
        super(vertexSource, fragmentSource);
        createUniform("uProjection");
        createUniform("uModel");
        createUniform("uColor");
        createUniform("uAtlasOffset");
        createUniform("uAtlasSize");
        createUniform("uAtlas");
    }

    /**
     * Sets the orthographic projection matrix.
     *
     * @param projection The projection matrix from {@link net.sparkzz.entropy.render.orthographic.camera.Camera2D}.
     */
    public void setProjectionMatrix(Matrix4f projection) {
        setUniformMatrix4f("uProjection", projection);
    }

    /**
     * Sets the model transformation matrix for the current glyph quad.
     *
     * @param model The model matrix encoding the glyph's screen position and size.
     */
    public void setModel(Matrix4f model) {
        setUniformMatrix4f("uModel", model);
    }

    /**
     * Sets the RGBA color tint applied to the rendered glyphs.
     *
     * @param color The color vector (RGBA).
     */
    public void setColor(Vector4f color) {
        setUniform4f("uColor", color);
    }

    /**
     * Sets the UV region within the font atlas for the current glyph.
     * Coordinates are normalized to the range [0.0, 1.0].
     *
     * @param u      Normalized x offset of the glyph in the atlas.
     * @param v      Normalized y offset of the glyph in the atlas.
     * @param width  Normalized width of the glyph in the atlas.
     * @param height Normalized height of the glyph in the atlas.
     */
    public void setAtlasRegion(float u, float v, float width, float height) {
        setUniform2f("uAtlasOffset", u, v);
        setUniform2f("uAtlasSize", width, height);
    }

    /**
     * Binds the font atlas texture to the given texture unit.
     *
     * @param textureUnit The OpenGL texture unit index (e.g., 0 for {@code GL_TEXTURE0}).
     */
    public void setAtlas(int textureUnit) {
        setUniform1i("uAtlas", textureUnit);
    }
}
