package wraith.automato;

import com.badlogic.gdx.InputProcessor;

import java.util.HashSet;

public class InputHandler implements InputProcessor {

    public static final InputHandler INSTANCE = new InputHandler();
    private final HashSet<ControlsListener> listeners = new HashSet<>();
    private int mouseX = 0;
    private int mouseY = 0;

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void registerListener(ControlsListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public boolean keyDown(int keycode) {
        this.listeners.forEach(l -> l.onKeyDown(keycode));
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        this.listeners.forEach(l -> l.onKeyUp(keycode));
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        this.listeners.forEach(l -> l.onKeyTyped(character));
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.listeners.forEach(l -> l.onTouchDown(screenX, screenY, pointer, button));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.listeners.forEach(l -> l.onTouchUp(screenX, screenY, pointer, button));
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        this.mouseX = screenX;
        this.mouseY = screenY;
        this.listeners.forEach(l -> l.onTouchDragged(screenX, screenY, pointer));
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        this.mouseX = screenX;
        this.mouseY = screenY;
        this.listeners.forEach(l -> l.onMouseMoved(screenX, screenY));
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        this.listeners.forEach(l -> l.onScrolled(amountX, amountY));
        return false;
    }

}
