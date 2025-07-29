package net.sparkzz.entropy.render.text;

import net.sparkzz.entropy.render.Texture;
import net.sparkzz.entropy.render.text.model.Glyph;
import net.sparkzz.entropy.render.text.util.Font;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class RenderableTextTest {

    static class DummyText extends RenderableText {
        DummyText(String text, Font font, float x, float y) { super(text, font, x, y); }
        @Override public void draw() {}
    }

    @Test
    void testSettersAndGetters() {
        Texture atlas = mock(Texture.class);

        Map<Integer, Glyph> glyphs = Map.of(
            1, new Glyph(1, 0, 0, 10, 10, 5, 5, 5),
            2, new Glyph(2, 10, 0, 20, 10, 5, 5, 5)
        );

        Font font = new Font(atlas, glyphs, 12);
        DummyText text = new DummyText("abc", font, 1f, 2f);

        text.setText("def");
        text.setX(3f);
        text.setY(4f);

        assertEquals("def", text.getText());
        assertEquals(3f, text.getX());
        assertEquals(4f, text.getY());
    }
}