package com.blockout22.jump.threeD;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector2;

public class Box extends GameObject{

	protected Box(Model model, Vector2 pos, Vector2 size) {
		super(model, pos, size);
	}

	@Override
	public void update() {
	}

	@Override
	public void onResize() {
	}

}
