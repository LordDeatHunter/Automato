package wraith.automato;

import com.badlogic.gdx.Input;

public class Controls {

    public Controls() {
        InputHandler.INSTANCE.registerListener(new ControlsListener() {
            @Override
            public void onKeyDown(int keycode) {
                if(keycode == Input.Keys.F3) {
                    Automato.INSTANCE.getDebugMenu().toggleVisibility();
                }
            }
        });
    }

}
