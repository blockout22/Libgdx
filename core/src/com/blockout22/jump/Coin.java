package com.blockout22.jump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Coin extends GameObject{

	protected Coin() {
		super(new Vector2(Gdx.graphics.getWidth(), 200), new Vector2((25), (25)));
	}

	@Override
	public void update() {
	}

	@Override
	public void onResize() {
	}

}
