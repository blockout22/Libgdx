package com.blockout22.jump;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Ground extends GameObject {

	public Ground(Camera camera) {
		super(new Vector2(10, camera.viewportWidth / 2 - (camera.viewportWidth / 2 - 10)),
				new Vector2((camera.viewportWidth - 20), (camera.viewportHeight / 2 - 10)));

		// super(new Vector2(-1f, 1f), new Vector2(1.9f, 0.5f));
		setColor(Color.GREEN);
	}

	@Override
	public void update() {
	}

	@Override
	public void onResize() {
		// position.set(new Vector2(10, Gdx.graphics.getHeight() / 2 -
		// (Gdx.graphics.getHeight() / 2 - 10)));
		// size.set(new Vector2(Gdx.graphics.getWidth() - 20, Gdx.graphics.getHeight() /
		// 2 - 10));
	}
}
