package wraith.automato;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.ScreenUtils;

public final class Renderer {

    public static final SpriteBatch BATCH = new SpriteBatch();
    public static final BitmapFont FONT = new BitmapFont(Gdx.files.internal("fonts/dpcomics.fnt"), false);
    public static TextureAtlas atlas;

    public static void initialize() {
        TexturePacker.process("textures/", "textures/", "texture_atlas");
        atlas = new TextureAtlas(Gdx.files.internal("textures/texture_atlas.atlas"));
    }

    public static void dispose() {
        BATCH.dispose();
        FONT.dispose();
        atlas.dispose();
    }

    public static void resize() {
        BATCH.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public static void drawText(String text, int x, int y) {
        FONT.draw(BATCH, text, x, y);
    }


    private static final Color backgroundColor = new Color(0xABCDEF);

    public static void clearScreen() {
        ScreenUtils.clear(backgroundColor);
    }

}
