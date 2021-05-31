package wraith.automato;

import com.badlogic.gdx.Gdx;
import wraith.automato.utils.UniqueArray;

public class DebugMenu {

    public UniqueArray<LineString> lines = new UniqueArray<>();
    public boolean debugModeEnabled = false;

    public void render() {
        if (!debugModeEnabled) {
            return;
        }
        Renderer.BATCH.begin();
        Renderer.FONT.getData().setScale(0.2F);
        for (int i = 0; i < lines.size(); ++i) {
            Renderer.drawText(lines.get(i).getLine(), 16, Gdx.graphics.getHeight() - 16 * (i + 1));
        }
        Renderer.BATCH.end();
    }

    public void toggleVisibility() {
        this.debugModeEnabled = !this.debugModeEnabled;
    }

}
