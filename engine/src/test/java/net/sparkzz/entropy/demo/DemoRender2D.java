package net.sparkzz.entropy.demo;

import net.sparkzz.entropy.EntropyEngine;
import net.sparkzz.entropy.IEntropyGame;
import net.sparkzz.entropy.render.Texture;
import net.sparkzz.entropy.render.orthographic.Render2D;
import net.sparkzz.entropy.render.orthographic.batch.BatchType;
import net.sparkzz.entropy.render.orthographic.camera.Camera2D;
import net.sparkzz.entropy.render.orthographic.model.UIElement;
import net.sparkzz.entropy.render.orthographic.shader.Shader2D;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static net.sparkzz.entropy.io.util.ResourceLoader.loadResourceAsString;

public class DemoRender2D implements IEntropyGame {

    private static final Logger log = LoggerFactory.getLogger(DemoRender2D.class);

    private Render2D renderer;
    private UIElement element;
    private boolean initialized = false;

    private void initResources() {
        String vertexShader, fragmentShader;

        try {
            vertexShader = loadResourceAsString("/shaders/default_2d_vertex.glsl");
            fragmentShader = loadResourceAsString("/shaders/default_2d_fragment.glsl");
        } catch (IOException e) {
            log.error("Failed to load shader resources", e);
            throw new RuntimeException("Shader resource loading failed", e);
        }

        renderer = new Render2D(
                new Camera2D(),
                new Shader2D(vertexShader, fragmentShader),
                new Shader2D(vertexShader, fragmentShader)
        );

        element = new UIElement(
                Texture.ofColor(0.2f, 0.6f, 1f, 1f),
                new Vector2f(0f, 0f),
                new Vector2f(0.25f, 0.25f),
                new Vector4f(1f, 1f, 1f, 1f),
                0f,
                1
        );

        initialized = true;
    }

    @Override
    public void update() {}

    @Override
    public void render() {
        if (!initialized) initResources();

        renderer.render(BatchType.UI_ELEMENT, List.of(element));
    }

    @Override
    public String getWindowTitle() {
        return "Render2D Demo";
    }

    public static void main(String[] args) {
        try {
            DemoRender2D game = new DemoRender2D();
            EntropyEngine engine = new EntropyEngine(game);

            engine.run();
        } catch (Exception exception) {
            log.error("An error occurred while running the Entropy Game", exception);
            System.exit(1);
        }
    }
}
