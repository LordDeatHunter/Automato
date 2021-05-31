package wraith.automato.world;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import wraith.automato.Automato;
import wraith.automato.Renderer;

public class Tile {

    public String textureName;
    public int textureNum;
    private TextureAtlas.AtlasRegion texture;
    private final int x;
    private final int y;

    public Tile(int x, int y, String textureName, int textureNum) {
        this.textureName = textureName;
        this.textureNum = textureNum;
        this.texture = Renderer.atlas.findRegions(textureName).get(textureNum);
        this.x = x;
        this.y = y;
    }

    public void render() {
        int tileSize = Automato.INSTANCE.getCamera().getTileSize();
        Renderer.BATCH.draw(texture, x * tileSize - Automato.INSTANCE.getCamera().x, y * tileSize - Automato.INSTANCE.getCamera().y, tileSize, tileSize);
    }

}
