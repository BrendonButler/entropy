package net.sparkzz.entropy.render;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    /**
     * Loads a texture from the specified file path.
     *
     * @param path The file path to the image.
     */
    public Texture(String path) {
        logger.info("Loading texture from path: {}", path);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer components = stack.mallocInt(1);

            // Flip Y axis to match OpenGL's coordinate system
            STBImage.stbi_set_flip_vertically_on_load(true);

            ByteBuffer data = STBImage.stbi_load(path, width, height, components, 4);

            if (data == null)
                throw new RuntimeException("Failed to load texture file: " + path
                        + System.lineSeparator() + "Reason: " + STBImage.stbi_failure_reason());

            this.width = width.get(0);
            this.height = height.get(0);
            this.id = glGenTextures();

            bind();
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
            glGenerateMipmap(GL_TEXTURE_2D);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

            STBImage.stbi_image_free(data);
            unbind();
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
