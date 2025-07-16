package net.sparkzz.entropy;

/**
 * Interface representing a game that uses the Entropy Engine.
 * Provides methods for updating game logic and rendering game visuals.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-14
 */
public interface IEntropyGame {

    /**
     * Updates the game logic. This method is called on each frame to process
     * game state changes, physics calculations, and other updates. Called once
     * per frame before render().
     */
    void update();

    /**
     * Renders the game visuals. This method is called on each frame to draw
     * the game objects and handle graphical output.
     */
    void render();

    /**
     * Gets the title of the game window.
     * Override this to change the window title.
     */
    default String getWindowTitle() {
        return "Entropy Game";
    }
}
