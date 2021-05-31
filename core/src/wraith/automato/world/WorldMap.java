package wraith.automato.world;

import wraith.automato.Automato;
import wraith.automato.Renderer;
import wraith.automato.Updatable;
import wraith.automato.utils.UniqueArray;

import java.util.HashMap;

public class WorldMap implements Updatable {

    public static final UniqueArray<String> layerList = new UniqueArray<>();
    private static final HashMap<String, MapLayer> layers = new HashMap<>();

    public WorldMap() {
        for (String layerName : layerList) {
            MapLayer layer = new MapLayer(layerName);
            layers.put(layerName, layer);
            layer.generate();
        }
        Automato.INSTANCE.addUpdatable(this);
        Automato.INSTANCE.getDebugMenu().lines.add(() -> "Loaded regions: " + layers.values().stream().reduce(0, (s, e) -> s + e.regionCount(), Integer::sum));
    }

    public void generate() {
        for (MapLayer layer : layers.values()) {
            layer.generate();
        }
    }

    @Override
    public void update() {
        generate();
    }

    public void render() {
        Renderer.BATCH.begin();
        for (String layer : layerList) {
            layers.get(layer).render();
        }
        Renderer.BATCH.end();
    }

    public Tile getTile(String layer, int x, int y) {
        if (layers.containsKey(layer)) {
            return null;
        }
        WorldRegion region = layers.get(layer).getRegion(x / WorldRegion.MAX_X, y / WorldRegion.MAX_Y);
        if (region == null) {
            return null;
        }
        return region.getTile(x % WorldRegion.MAX_X, y % WorldRegion.MAX_X);
    }

    static {
        layerList.add("ground");
        layerList.add("ores");
    }

}
