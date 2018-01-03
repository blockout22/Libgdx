package com.blockout22.jump.cleanup;

import com.badlogic.gdx.Screen;

public class LoadingScreen implements Screen {
	private Jump parent;

	public LoadingScreen(Jump parent) {
		this.parent = parent;
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		parent.changeScreen(Jump.MENU);
	}

	@Override
	public void resize(int width, int height) {
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
	}

}
