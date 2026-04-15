package net.sparkzz.entropy.render;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * Represents a texture loaded from an image file.
 * Handles loading, binding, and cleanup of the texture.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-17
 */
public class Texture {

    private static final Logger logger = LoggerFactory.getLogger(Texture.class);

    private final int id;
    private final int width;
    private final int height;

    private Texture(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a 1x1 solid-color texture from the given RGBA components.
     * Useful for rendering untextured quads or as a placeholder texture.
     *
     * @param r Red component (0.0–1.0).
     * @param g Green component (0.0–1.0).
     * @param b Blue component (0.0–1.0).
     * @param a Alpha component (0.0–1.0).
     * @return A new Texture backed by a 1x1 RGBA pixel.
     */
    public static Texture ofColor(float r, float g, float b, float a) {
        int id = glGenTextures();
        ByteBuffer pixel = MemoryUtil.memAlloc(4);

        try {
            pixel.put((byte) (int) (Math.clamp(r, 0f, 1f) * 255))
                 .put((byte) (int) (Math.clamp(g, 0f, 1f) * 255))
                 .put((byte) (int) (Math.clamp(b, 0f, 1f) * 255))
                 .put((byte) (int) (Math.clamp(a, 0f, 1f) * 255))
                 .flip();

            glBindTexture(GL_TEXTURE_2D, id);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 1, 1, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixel);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            glBindTexture(GL_TEXTURE_2D, 0);
        } finally {
            MemoryUtil.memFree(pixel);
        }

        return new Texture(id, 1, 1);
    }

    /**
     * Loads a texture from the specified file path.
     *
     * @param path The file path to the image.
     */
    public Texture(String path) {
        logger.info("Loading texture from path: {}", path);

        ByteBuffer imageBuffer;
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null)
                throw new RuntimeException("Texture resource not found: " + path);
            byte[] bytes = is.readAllBytes();
            imageBuffer = org.lwjgl.system.MemoryUtil.memAlloc(bytes.length);
            imageBuffer.put(bytes).flip();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load texture resource: " + path, e);
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer components = stack.mallocInt(1);

            STBImage.stbi_set_flip_vertically_on_load(true);

            ByteBuffer data = STBImage.stbi_load_from_memory(imageBuffer, width, height, components, 4);

            if (data == null)
                throw new RuntimeException("Failed to load texture file: " + path
                        + System.lineSeparator() + "Reason: " + STBImage.stbi_failure_reason());

            this.width = width.get(0);
            this.height = height.get(0);
            this.id = glGenTextures();

            try {
                bind();
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
                glGenerateMipmap(GL_TEXTURE_2D);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            } catch (RuntimeException e) {
                glDeleteTextures(this.id);
                throw e;
            } finally {
                STBImage.stbi_image_free(data);
                unbind();
            }
        } finally {
            MemoryUtil.memFree(imageBuffer);
        }
    }

    /**
     * Binds the texture for rendering.
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    /**
     * Unbinds the texture.
     */
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    /**
     * Cleans up the texture resources.
     */
    public void cleanup() {
        glDeleteTextures(id);
    }

    /**
     * Gets the OpenGL texture ID.
     *
     * @return The texture ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the width of the texture.
     *
     * @return The texture width in pixels.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the texture.
     *
     * @return The texture height in pixels.
     */
    public int getHeight() {
        return height;
    }
}
