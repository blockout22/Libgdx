package com.blockout22.jump;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Jump extends ApplicationAdapter implements InputProcessor {

	private OrthographicCamera camera;
	public Preferences prefs;

	private ShapeRenderer shape;
	private SpriteBatch batch;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	private BitmapFont font;
	private Sound jumpSound;
	private Sound freezeSound;

	private Ground ground;

	private Player player;
	private ArrayList<Box> boxes = new ArrayList<Box>();
	private ArrayList<Coin> coinBox = new ArrayList<Coin>();
	private ArrayList<StopBox> stopBoxes = new ArrayList<StopBox>();

	private Vector2 gravity = new Vector2(0, -0.3f);

	private float GROUND_HEIGHT;

	public State state;

	private long startTime;
	private long nextTime;
	private Random r = new Random();

	private int coins = 0;
	private int score = 0;
	private int hiscore = 0;
	private float boxSpeed = -3f;
	private long freezePlayerTime = 0;

	// physics update;
	private long curTime;
	private long lastTime;
	private long physicsTime = (long) ((1f / 60f) * 1000);
	private boolean updatePhysics = false;
	private boolean physicsFrozen = false;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 512, 512);

		prefs = Gdx.app.getPreferences("jump.data");
		hiscore = prefs.getInteger("hiscore");
		coins = prefs.getInteger("coins");

		int mxh = prefs.getInteger("maxhealth");
		if (mxh < 1) {
			mxh = 1;
			prefs.putInteger("maxhealth", 1);
			prefs.flush();
		}

		shape = new ShapeRenderer();

		batch = new SpriteBatch();

		generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Regular.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 32;
		font = generator.generateFont(parameter);
		font.setColor(Color.RED);
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.mp3"));
		freezeSound = Gdx.audio.newSound(Gdx.files.internal("freeze.wav"));

		// world = new World(new Vector2(0, -35f), true);
		ground = new Ground(camera);
		GROUND_HEIGHT = ground.getPosition().y + ground.getSize().y;
		System.out.println(ground.getPosition().y);
		player = new Player();
		player.setMaxhealth(mxh);

		Gdx.input.setInputProcessor(this);

		state = State.IN_GAME;
		startTime = TimeUtils.millis();
		nextTime = r.nextInt(1000) + 1000;
	}

	private void testState() {
	}

	@Override
	public void render() {
		updatePhysics = false;
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update(false);
		shape.setProjectionMatrix(camera.combined);
		shape.updateMatrices();

		curTime = TimeUtils.millis();
		if (lastTime + physicsTime < curTime) {
			if (!physicsFrozen) {
				updatePhysics = true;
			}
			lastTime = curTime;
		}

		if (state == State.MAIN_MENU) {

		} else if (state == State.IN_GAME) {
			renderInGame();
		} else if (state == State.RESTART) {
			renderRestart();
		} else if (state == State.TEST) {
			testState();
		}
	}

	public void renderInGame() {
		if (player.getHealth() <= 0) {
			physicsFrozen = true;
//			renderRestart();
			batch.begin();
			font.draw(batch, "You Died! \nClick to restart", camera.viewportWidth / 2, camera.viewportHeight);
			batch.end();
//			state = State.RESTART;
			
//			return;
		}

		ground.render(camera, shape);
		ground.update();

		player.render(camera, shape);
		player.update();
		if (updatePhysics) {
			player.updatePhysics();
			player.applyForce(gravity);
		}
		player.setColor(Color.YELLOW);

		// draw Health bar{
		shape.begin(ShapeType.Line);
		shape.setColor(Color.WHITE);
		shape.rect(10, camera.viewportWidth - 30, camera.viewportHeight / 4, 25);
		shape.end();

		shape.begin(ShapeType.Filled);
		shape.setColor(Color.RED);
		shape.rect(12, camera.viewportWidth - 27, camera.viewportHeight / 4 - 2, 21);
		shape.end();

		if (player.getPosition().y < GROUND_HEIGHT) {
			player.velocity.y *= 0;
			player.position.y = GROUND_HEIGHT;
			player.setOnGround(true);
		}

		if (TimeUtils.timeSinceMillis(startTime) >= nextTime) {
			if (r.nextInt(100) < 10) {
				StopBox sb = new StopBox();
				sb.setColor(Color.CYAN);
				sb.position.y = GROUND_HEIGHT;
				sb.applyForce(boxSpeed * 0.9f, 0);
				stopBoxes.add(sb);
			} else if (r.nextInt(100) < 15) {
				Coin c = new Coin();
				c.position.y = GROUND_HEIGHT;
				c.applyForce(boxSpeed * 0.3f, 0);
				coinBox.add(c);
			} else {
				Box b = new Box();
				b.setColor(Color.BROWN);
				b.position.y = GROUND_HEIGHT;
				if (r.nextInt(100) < 15) {
					b.applyForce(boxSpeed * 1.1f, 0);
				} else {
					b.applyForce(boxSpeed, 0);
				}
				boxes.add(b);
			}
			startTime = TimeUtils.millis();
			nextTime = r.nextInt(1000) + 1000;
		}

		// for (Box b : boxes) {
		for (int i = boxes.size() - 1; i >= 0; i--) {
			if (offScreen(boxes.get(i))) {
				boxes.remove(i);
				score++;
				continue;
			}
			boxes.get(i).render(camera, shape);
			boxes.get(i).update();
			if (updatePhysics) {
				boxes.get(i).updatePhysics();
			}
			if (boxes.get(i).intersects(player)) {
				player.setColor(Color.RED);
				player.setHealth(player.getHealth() - 1);
			}
		}

		for (int i = coinBox.size() - 1; i >= 0; i--) {
			if (offScreen(coinBox.get(i))) {
				coinBox.remove(i);
				continue;
			}

			coinBox.get(i).render(camera, shape);
			coinBox.get(i).update();
			if (updatePhysics) {
				coinBox.get(i).updatePhysics();
			}

			if (coinBox.get(i).intersects(player)) {
				player.setColor(Color.GREEN);
				coins++;
				coinBox.remove(i);
			}
		}

		for (int i = stopBoxes.size() - 1; i >= 0; i--) {
			if (offScreen(stopBoxes.get(i))) {
				stopBoxes.remove(i);
				continue;
			}

			stopBoxes.get(i).render(camera, shape);
			stopBoxes.get(i).update();
			if (updatePhysics) {
				stopBoxes.get(i).updatePhysics();
			}

			if (stopBoxes.get(i).intersects(player)) {
				freezeSound.play();
				freezePlayerTime = TimeUtils.millis() + 1000;
				stopBoxes.remove(i);
			}
		}

		batch.begin();
		font.draw(batch, "Score: " + score + " HiScore: " + hiscore + " Coins: " + coins, Gdx.graphics.getWidth() / 3,
				Gdx.graphics.getHeight() - 10);
		batch.end();
	}

	private boolean offScreen(GameObject object) {
		return object.getPosition().x < 0 - object.getSize().x;
	}

	private void renderRestart() {
//		batch.begin();
//		font.draw(batch, "Click to restart", camera.viewportWidth / 2, camera.viewportHeight / 2);
//		batch.end();
	}

	private void resetGame() {
		physicsFrozen = false;
		if (score > hiscore) {
			hiscore = score;
			prefs.putInteger("hiscore", hiscore);
		}
		score = 0;
		boxSpeed = -3f;
		prefs.putInteger("coins", coins);
		prefs.flush();
		boxes.clear();
		coinBox.clear();
		stopBoxes.clear();
		player.reset();
		state = State.IN_GAME;
	}

	public void healthBar() {
		shape.begin(ShapeType.Filled);
		shape.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void dispose() {
		jumpSound.dispose();
		freezeSound.dispose();
		batch.dispose();
		font.dispose();
		player.dispose();
		for (Box b : boxes) {
			b.dispose();
		}
		ground.dispose();
		shape.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// Box b = new Box();
		// b.position.y = GROUND_HEIGHT;
		// b.applyForce(-3f, 0);
		// boxes.add(b);
		// Vector2 opengl = GameObject.toOpenGLcoords(screenX, screenY);
		// Vector2 screen = GameObject.fromOpenGLcoords(opengl.x, opengl.y);
		// System.out.println(screenX + " : " + screenY + " | " + opengl + " | " +
		// screen);

		if (state == State.IN_GAME) {
			if (freezePlayerTime < TimeUtils.millis()) {
				if (player.jump() && player.getHealth() > 0) {
					jumpSound.play();
				}else if(player.getHealth() <= 0){
					resetGame();
				}
			}
		} else if (state == State.RESTART) {
			resetGame();
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
//		float openGLx = (screenX * (2f / Gdx.graphics.getWidth()) - 1f);
//		float openGLy = -(screenY * (2f / Gdx.graphics.getHeight()) - 1f);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
