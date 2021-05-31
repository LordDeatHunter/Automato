package wraith.automato;

import wraith.automato.world.WorldMap;

public class Player {

    private WorldMap currentWorld;

    public void setCurrentWorld(WorldMap world) {
        this.currentWorld = world;
    }

    public WorldMap getCurrentWorld() {
        return this.currentWorld;
    }

}
