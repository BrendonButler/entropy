package net.sparkzz.entropy;

public class EntropyGame implements IEntropyGame {

    @Override
    public void update() {
        // Game logic updates (e.g., physics, AI, etc.)
        System.out.println("Updating game logic...");
    }

    @Override
    public void render() {
        // Game rendering logic (e.g., drawing objects, UI, etc.)
        System.out.println("Rendering game visuals...");
    }

    public static void main(String[] args) {
        EntropyEngine engine = new EntropyEngine();
        EntropyGame game = new EntropyGame();

        engine.run(game);
    }
}