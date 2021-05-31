package wraith.automato.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class WorldRegion {

    public static final int MAX_X = 32;
    public static final int MAX_Y = 32;

    private final int x;
    private final int y;

    private final HashMap<Vector2, Tile> tiles = new HashMap<>();

    private final MapLayer layer;

    public WorldRegion(MapLayer layer, int x, int y) {
        this.layer = layer;
        this.x = x;
        this.y = y;
        generate();
    }

    public void generate() {
        int xOffset = this.x * MAX_X;
        int yOffset = this.y * MAX_Y;
        for (int x = 0; x <= MAX_X; ++x) {
            for (int y = 0; y < MAX_Y; y++) {
                Tile tile = TileGenerator.generateTile(layer.getName(), xOffset + x, yOffset + y);
                if (tile != null) {
                    tiles.put(new Vector2(x, y), tile);
                }
            }
        }
    }

    public void render() {
        for (Tile tile : tiles.values()) {
            tile.render();
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void save() {
        final StringBuilder sb = new StringBuilder();
        tiles.forEach((coordinate, tile) -> sb.append((int)coordinate.x).append(" ").append((int)coordinate.y).append(" ").append(tile.textureName).append(" ").append(tile.textureNum).append("\n"));
        Gdx.files.local("saves/world" + File.separator + layer.getName() + File.separator + "r" + x + "_" + y + ".wrg").writeString(sb.toString(), false);
    }

    public void load(FileHandle file) {
        int xOffset = this.x * MAX_X;
        int yOffset = this.y * MAX_Y;
        try {
            Files.lines(Path.of(file.path())).forEach(l -> {
                String[] segments = l.split(" ");
                if (segments.length != 4) {
                    return;
                }
                try {
                    int x = Integer.parseInt(segments[0]);
                    int y = Integer.parseInt(segments[1]);
                    String textureName = segments[2];
                    int textureNum = Integer.parseInt(segments[3]);
                    tiles.put(new Vector2(x, y), new Tile(xOffset + x, yOffset + y, textureName, textureNum));
                } catch (Exception ignored) {}
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Tile getTile(int x, int y) {
        return tiles.getOrDefault(new Vector2(x, y ), null);
    }

}
