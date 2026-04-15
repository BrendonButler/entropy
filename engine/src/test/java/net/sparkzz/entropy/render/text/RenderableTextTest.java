package net.sparkzz.entropy.render.text;

import net.sparkzz.entropy.render.Texture;
import net.sparkzz.entropy.render.text.model.Glyph;
import net.sparkzz.entropy.render.text.model.Text2D;
import net.sparkzz.entropy.render.text.util.Font;
import org.joml.Vector4f;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RenderableTextTest {

    private Font font;

    @BeforeEach
    void setUp() {
        Texture atlas = mock(Texture.class);
        Map<Integer, Glyph> glyphs = Map.of(
            65, new Glyph(65, 0, 0, 8, 12, 9, 0, 0),
            66, new Glyph(66, 8, 0, 8, 12, 9, 0, 0)
        );
        font = new Font(atlas, glyphs, 14);
    }

    @Test
    void testDefaultConstructorSetsScaleAndColor() {
        Text2D text = new Text2D("AB", font, 10f, 20f);

        assertEquals(1f, text.getScale());
        assertEquals(new Vector4f(1f, 1f, 1f, 1f), text.getColor());
    }

    @Test
    void testFullConstructorProperties() {
        Vector4f color = new Vector4f(1f, 0f, 0f, 1f);
        Text2D text = new Text2D("AB", font, 10f, 20f, 2f, color);

        assertEquals("AB", text.getText());
        assertEquals(font, text.getFont());
        assertEquals(10f, text.getX());
        assertEquals(20f, text.getY());
        assertEquals(2f, text.getScale());
        assertEquals(color, text.getColor());
    }

    @Test
    void testSetters() {
        Text2D text = new Text2D("AB", font, 0f, 0f);
        Font font2 = new Font(mock(Texture.class), Map.of(), 10);

        text.setText("CD");
        text.setFont(font2);
        text.setX(5f);
        text.setY(6f);
        text.setScale(0.5f);
        text.setColor(new Vector4f(0f, 0f, 1f, 1f));

        assertEquals("CD", text.getText());
        assertEquals(font2, text.getFont());
        assertEquals(5f, text.getX());
        assertEquals(6f, text.getY());
        assertEquals(0.5f, text.getScale());
        assertEquals(new Vector4f(0f, 0f, 1f, 1f), text.getColor());
    }

    @Test
    void testGetColorReturnsCopy() {
        Text2D text = new Text2D("AB", font, 0f, 0f);
        Vector4f color = text.getColor();
        color.x = 0f;

        assertEquals(1f, text.getColor().x, "mutating the returned color must not affect the stored color");
    }

    @Test
    void testNullTextThrows() {
        assertThrows(NullPointerException.class, () -> new Text2D(null, font, 0f, 0f));
    }

    @Test
    void testNullFontThrows() {
        assertThrows(NullPointerException.class, () -> new Text2D("AB", null, 0f, 0f));
    }

    @Test
    void testSetNullTextThrows() {
        Text2D text = new Text2D("AB", font, 0f, 0f);
        assertThrows(NullPointerException.class, () -> text.setText(null));
    }

    @Test
    void testSetNullFontThrows() {
        Text2D text = new Text2D("AB", font, 0f, 0f);
        assertThrows(NullPointerException.class, () -> text.setFont(null));
    }

    @Test
    void testSetNullColorThrows() {
        Text2D text = new Text2D("AB", font, 0f, 0f);
        assertThrows(NullPointerException.class, () -> text.setColor(null));
    }
}
