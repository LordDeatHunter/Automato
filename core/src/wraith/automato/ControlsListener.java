package wraith.automato;

public interface ControlsListener {

    default void onKeyDown (int keycode){}
    default void onKeyUp (int keycode){}
    default void onKeyTyped (char character){}
    default void onTouchDown (int screenX, int screenY, int pointer, int button){}
    default void onTouchUp (int screenX, int screenY, int pointer, int button){}
    default void onTouchDragged (int screenX, int screenY, int pointer){}
    default void onMouseMoved (int screenX, int screenY){}
    default void onScrolled (float amountX, float amountY){}

}
