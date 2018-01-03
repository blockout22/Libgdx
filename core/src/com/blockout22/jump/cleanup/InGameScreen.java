package com.blockout22.jump.cleanup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InGameScreen implements Screen {

	private Jump parent;

	private SpriteBatch batch;
	private Texture texture;
	private OrthographicCamera camera;
	private Viewport viewport;
	private ShapeRenderer shape;

	public InGameScreen(Jump parent) {
		this.parent = parent;
		batch = new SpriteBatch();
		texture = new Texture("badlogic.jpg");
		camera = new OrthographicCamera();
		viewport = new FitViewport(16, 9, camera);
		shape = new ShapeRenderer();
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(texture, 0, 0, 1, 1);
		batch.end();

		shape.begin(ShapeType.Filled);
		shape.rect(50, 50, 50, 50);
		shape.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		shape.dispose();
		texture.dispose();
		batch.dispose();
	}

}
