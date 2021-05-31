package wraith.automato;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;

public class Camera {

    public int x;
    public int y;
    public int sensitivity;
    public int mouseDragStartX = 0;
    public int mouseDragStartY = 0;
    private boolean dragging = false;
    private int tileSize = 32;

    public Camera() {
        this.x = 0;
        this.y = 0;
        this.sensitivity = 1;
        InputHandler.INSTANCE.registerListener(new ControlsListener() {
            @Override
            public void onTouchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.RIGHT) {
                    dragging = true;
                    mouseDragStartX = screenX;
                    mouseDragStartY = screenY;
                }
            }
            @Override
            public void onTouchUp(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.RIGHT) {
                    dragging = false;
                }
            }

            @Override
            public void onScrolled(float amountX, float amountY) {
                tileSize = MathUtils.clamp(tileSize + (amountY > 0 ? -4 : 4), 16, 64);
            }
        });
    }

    public void update() {
        if (this.dragging) {
            x -= ((mouseDragStartX - InputHandler.INSTANCE.getMouseX()) / 16F) * sensitivity;
            y += ((mouseDragStartY - InputHandler.INSTANCE.getMouseY()) / 16F) * sensitivity;
        }
    }

    public int getTileSize() {
        return tileSize;
    }
}
