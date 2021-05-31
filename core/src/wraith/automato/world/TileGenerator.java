package wraith.automato.world;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import wraith.automato.Renderer;
import wraith.automato.libraries.FastNoiseLite;
import wraith.automato.utils.Utils;

import java.util.HashMap;

public final class TileGenerator {

    private static final HashMap<String, FastNoiseLite> noiseMap = new HashMap<>();
    private static final HashMap<String, String> layerTextures = new HashMap<>();


    public static Tile generateTile(String layer, int x, int y) {
        if ("ores".equals(layer) && noiseMap.get("ores").GetNoise(x, y) < -0.6) {
            return null;
        }

        String textureName = layerTextures.get(layer);
        Array<TextureAtlas.AtlasRegion> regions = Renderer.atlas.findRegions(textureName);
        int textureNum = (int) (((noiseMap.get(layer).GetNoise(x, y) + 1) / 2) * regions.size);

        return new Tile(x, y, textureName, textureNum);
    }

    static {
        FastNoiseLite noise;
        noise = new FastNoiseLite(Utils.getSeed());
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        noise.SetFrequency(0.1F);
        noiseMap.put("ground", noise);

        noise = new FastNoiseLite(Utils.getSeed());
        noise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        noise.SetFrequency(0.03F);
        noise.SetFractalType(FastNoiseLite.FractalType.Ridged);
        noise.SetFractalLacunarity(1.4F);
        noise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.EuclideanSq);
        noise.SetCellularReturnType(FastNoiseLite.CellularReturnType.Distance2Mul);
        noiseMap.put("ores", noise);

        layerTextures.put("ground", "earth_dirt");
        layerTextures.put("ores", "gold_deposit");
    }

}
