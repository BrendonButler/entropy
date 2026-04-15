package net.sparkzz.entropy.render.text;

import net.sparkzz.entropy.render.Texture;
import net.sparkzz.entropy.render.text.model.Glyph;
import net.sparkzz.entropy.render.text.util.Font;
import net.sparkzz.entropy.render.text.util.FontLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FontLoaderTest {

    private Texture atlas;

    @BeforeEach
    void setUp() {
        atlas = mock(Texture.class);
    }

    private InputStream fntStream() {
        InputStream stream = getClass().getResourceAsStream("/fonts/test_font.fnt");
        assertNotNull(stream, "test_font.fnt must be on the test classpath");
        return stream;
    }

    @Test
    void testLoadsLineHeight() throws IOException {
        Font font = FontLoader.load(fntStream(), atlas);
        assertEquals(18, font.lineHeight());
    }

    @Test
    void testLoadsCorrectGlyphCount() throws IOException {
        Font font = FontLoader.load(fntStream(), atlas);
        assertEquals(3, font.glyphs().size());
    }

    @Test
    void testGlyphAProperties() throws IOException {
        Font font = FontLoader.load(fntStream(), atlas);
        Glyph a = font.getGlyph(65);

        assertNotNull(a, "glyph for 'A' (id=65) must be present");
        assertEquals(0f,  a.getX());
        assertEquals(0f,  a.getY());
        assertEquals(8f,  a.getWidth());
        assertEquals(12f, a.getHeight());
        assertEquals(9f,  a.getXAdvance());
        assertEquals(0f,  a.getXOffset());
        assertEquals(2f,  a.getYOffset());
    }

    @Test
    void testSpaceGlyphHasZeroDimensions() throws IOException {
        Font font = FontLoader.load(fntStream(), atlas);
        Glyph space = font.getGlyph(32);

        assertNotNull(space, "space glyph (id=32) must be present");
        assertEquals(0f, space.getWidth());
        assertEquals(0f, space.getHeight());
        assertEquals(5f, space.getXAdvance());
    }

    @Test
    void testUnknownGlyphReturnsNull() throws IOException {
        Font font = FontLoader.load(fntStream(), atlas);
        assertNull(font.getGlyph(9999));
    }

    @Test
    void testAtlasIsPreserved() throws IOException {
        Font font = FontLoader.load(fntStream(), atlas);
        assertSame(atlas, font.atlas());
    }

    @Test
    void testNullStreamThrows() {
        assertThrows(NullPointerException.class, () -> FontLoader.load(null, atlas));
    }

    @Test
    void testNullAtlasThrows() throws IOException {
        assertThrows(NullPointerException.class, () -> FontLoader.load(fntStream(), null));
    }

    @Test
    void testMissingKeyThrows() {
        String malformed = "common scaleW=64 scaleH=64\n"; // lineHeight is missing
        InputStream stream = new ByteArrayInputStream(malformed.getBytes(StandardCharsets.UTF_8));
        assertThrows(IllegalArgumentException.class, () -> FontLoader.load(stream, atlas));
    }
}
