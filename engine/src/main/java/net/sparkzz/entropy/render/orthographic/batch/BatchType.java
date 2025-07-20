package net.sparkzz.entropy.render.orthographic.batch;

/**
 * Enum representing different types of render batches.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-18
 */
public enum BatchType {
    /**
     * Batch type for UI elements (HUDs, dialog boxes, etc.).
     */
    UI_ELEMENT,

    /**
     * Batch type for sprites (2D images, characters, etc.).
     */
    SPRITE,

    /**
     * Batch type fallback for any other types not explicitly defined.
     */
    OTHER
}
