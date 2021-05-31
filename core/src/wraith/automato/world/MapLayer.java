package wraith.automato.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import wraith.automato.Automato;
import wraith.automato.Renderer;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class MapLayer {

    private static final HashMap<String, String> layerTextures = new HashMap<>();

    private final HashMap<Vector2, WorldRegion> worldRegions = new HashMap<>();
    private final String layerName;

    public MapLayer(String layerName) {
        this.layerName = layerName;
    }

    public void generate() {
        int tileSize = Automato.INSTANCE.getCamera().getTileSize();
        int xOffset = WorldRegion.MAX_X * tileSize;
        int yOffset = WorldRegion.MAX_Y * tileSize;
        int xRange = (Automato.INSTANCE.getCamera().x + Gdx.graphics.getWidth()) / xOffset + 2;
        int yRange = (Automato.INSTANCE.getCamera().y + Gdx.graphics.getHeight()) / yOffset + 2;
        int xStart = Automato.INSTANCE.getCamera().x / xOffset - 2;
        int yStart = Automato.INSTANCE.getCamera().y / yOffset - 2;

        HashSet<Vector2> unloadedRegions = new HashSet<>();
        for (Vector2 pos : this.worldRegions.keySet()) {
            if (pos.x < xStart || pos.x > xRange || pos.y < yStart || pos.y > yRange) {
                unloadedRegions.add(pos);
            }
        }
        unloadedRegions.forEach(v -> {
            WorldRegion region = this.worldRegions.get(v);
            region.save();
            this.worldRegions.remove(v);
        });

        for (int x = xStart; x <= xRange; ++x) {
            for (int y = yStart; y <= yRange; ++y) {
                Vector2 coordinates = new Vector2(x, y);
                if (this.worldRegions.containsKey(coordinates)) {
                    continue;
                }

                WorldRegion worldRegion = new WorldRegion(this, x, y);
                this.worldRegions.put(new Vector2(x, y), worldRegion);

                FileHandle file = Gdx.files.local("saves/world" + File.separator + layerName + File.separator + "r" + x + "_" + y + ".wrg");
                if (file.exists()) {
                    worldRegion.load(file);
                } else {
                    worldRegion.generate();
                }
            }
        }
    }

    public void render() {
        int tileSize = Automato.INSTANCE.getCamera().getTileSize();
        int xOffset = WorldRegion.MAX_X * tileSize;
        int yOffset = WorldRegion.MAX_Y * tileSize;
        int xRange = (Automato.INSTANCE.getCamera().x + Gdx.graphics.getWidth()) / xOffset;
        int yRange = (Automato.INSTANCE.getCamera().y + Gdx.graphics.getHeight()) / yOffset;
        for (int x = Automato.INSTANCE.getCamera().x / xOffset - 1; x <= xRange + 1; ++x) {
            for (int y = Automato.INSTANCE.getCamera().y / yOffset - 1; y <= yRange + 1; ++y) {
                WorldRegion region = this.worldRegions.getOrDefault(new Vector2(x, y), null);
                Renderer.FONT.getData().setScale(0.2F);
                Renderer.drawText(x + " " + y, x * WorldRegion.MAX_X * tileSize - Automato.INSTANCE.getCamera().x, y * WorldRegion.MAX_Y * tileSize - Automato.INSTANCE.getCamera().y);
                if (region != null) {
                    region.render();
                }
            }
        }
    }

    public int regionCount() {
        return this.worldRegions.size();
    }

    public String getName() {
        return layerName;
    }

    public WorldRegion getRegion(int x, int y) {
        return worldRegions.getOrDefault(new Vector2(x, y), null);
    }

}
