package net.sparkzz.entropy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for the Entropy Game, implementing the IEntropyGame interface.
 * This class contains the main game loop and handles game updates and rendering.
 *
 * @author Brendon Butler
 * @version 0.1.0-PREALPHA
 * @since 2025-07-14
 */
public class EntropyGame implements IEntropyGame {

    private static final Logger log = LoggerFactory.getLogger(EntropyGame.class);

    @Override
    public void update() {
        // Game logic updates (e.g., physics, AI, etc.)
        // TODO: Implement game logic updates
    }

    @Override
    public void render() {
        // Game rendering logic (e.g., drawing objects, UI, etc.)
        // TODO: Implement game rendering logic
    }

    @Override
    public String getWindowTitle() {
        return "Entropy";
    }

    public static void main(String[] args) {
        try {
            EntropyGame game = new EntropyGame();
            EntropyEngine engine = new EntropyEngine(game);

            engine.run();
        } catch (Exception exception) {
            log.error("An error occurred while running the Entropy Game", exception);
            System.exit(1);
        }
    }
}