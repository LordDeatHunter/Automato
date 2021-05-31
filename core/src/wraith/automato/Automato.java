package wraith.automato;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import wraith.automato.utils.UniqueArray;
import wraith.automato.utils.Utils;
import wraith.automato.world.WorldMap;

import java.io.File;

public class Automato extends ApplicationAdapter {

	public static Automato INSTANCE;
	private WorldMap world;
	private DebugMenu debugMenu;
	private Thread updateThread;
	private final UniqueArray<Updatable> updatables = new UniqueArray<>();
	private Player player;
	private Camera camera;
	private Controls controls;
	private boolean isRunning = true;

	public Automato() {
		INSTANCE = this;
	}

	@Override
	public void create () {
		Gdx.graphics.setForegroundFPS(120);

		Renderer.initialize();

		FileHandle file = Gdx.files.local("saves/world" + File.separator + "world_data.wdt");
		if (file.exists()) {
			Utils.initialize(Integer.parseInt(file.readString().split(" ")[1]));
		} else {
			Utils.initialize();
			file.writeString("seed: " + Utils.getSeed(), false);
		}

		Gdx.input.setInputProcessor(InputHandler.INSTANCE);
		this.camera = new Camera();
		this.controls = new Controls();

		this.debugMenu = new DebugMenu();
		this.debugMenu.lines.add(() -> "FPS: " + Gdx.graphics.getFramesPerSecond());
		this.debugMenu.lines.add(() -> "Pos: " + this.camera.x + " " + this.camera.y);
		this.debugMenu.lines.add(() -> "Absolute Mouse Pos: " + InputHandler.INSTANCE.getMouseX() + " " + InputHandler.INSTANCE.getMouseY());
		this.debugMenu.lines.add(() -> "Relative Mouse Pos: " + (this.camera.x + InputHandler.INSTANCE.getMouseX()) + " " + (this.camera.y + InputHandler.INSTANCE.getMouseY()));

		this.player = new Player();
		this.world = new WorldMap();
		this.player.setCurrentWorld(this.world);

		this.updateThread = new Thread(this::update);
		this.updateThread.start();
	}

	public DebugMenu getDebugMenu() {
		return debugMenu;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Renderer.resize();
	}

	private void update() {
		while (this.isRunning) {
			this.updatables.forEach(Updatable::update);
		}
	}

	public void addUpdatable(Updatable updatable) {
		this.updatables.add(updatable);
	}

	@Override
	public void render () {
    	Renderer.clearScreen();
		this.camera.update();
        this.world.render();
		this.debugMenu.render();
	}

	public Camera getCamera() {
		return this.camera;
	}

	@Override
	public void dispose () {
		this.isRunning = false;
    	Renderer.dispose();
		try {
			updateThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
