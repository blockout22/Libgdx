package com.blockout22.jump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameScreenActor extends Actor {
	
	private ShapeRenderer shape;
	
	public GameScreenActor()
	{
		shape = new ShapeRenderer();
	}
	
	public void draw (Batch batch, float parentAlpha) {
		shape.begin(ShapeType.Filled);
		shape.setColor(Color.RED);
		shape.rect(Gdx.graphics.getWidth() * 0.10f, Gdx.graphics.getHeight() * 0.10f, Gdx.graphics.getWidth() * 0.10f, Gdx.graphics.getHeight() * 0.10f);
		shape.end();
	}

}
