package com.blockout22.jump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {

	public Vector2 position;
	public Vector2 size;
	public Vector2 velocity;
	public Vector2 acceleration;

	private Color color = Color.YELLOW;
	
	protected GameObject(Vector2 pos, Vector2 size) {
		position = pos;
		this.size = size;
		velocity = new Vector2().setZero();
		acceleration = new Vector2().setZero();
	}

	public abstract void update();

	public abstract void onResize();

	public void applyForce(float x, float y) {
		acceleration.add(x, y);
	}

	public void applyForce(Vector2 vector) {
		acceleration.add(vector);
	}

	public boolean intersects(GameObject ob) {
		return (this.position.x < ob.position.x + ob.size.x && this.position.x + this.size.x > ob.position.x
				&& this.position.y < ob.position.y + ob.size.y && this.position.y + this.size.y > ob.position.y);
	}

	public static Vector2 toOpenGLcoords(float x, float y) {
		
		return new Vector2(x * (2f / Gdx.graphics.getWidth()) - 1f, -(y * (2f / Gdx.graphics.getHeight()) - 1));
	}

	public static Vector2 fromOpenGLcoords(float x, float y) {
		return new Vector2(((x + 1f) / 2.0f) * Gdx.graphics.getWidth(), ((1f - y) / 2.0f) * Gdx.graphics.getHeight());
	}

	public void clearForces() {
		this.velocity.x *= 0;
		this.velocity.y *= 0;
		this.acceleration.x *= 0;
		this.acceleration.y *= 0;
	}
	
	public void updatePhysics()
	{
		this.velocity.add(this.acceleration);
		this.position.add(this.velocity);
		this.acceleration.set(0, 0);
	}

	public void render(Camera camera, ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setColor(color);
			sr.rect(position.x, position.y, size.x, size.y);
		sr.end();
	}

	public void resize() {
		onResize();
	}

	public void setColor(Color col) {
		this.color = col;
	}

	public void dispose() {
	}

	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getSize() {
		return size;
	}
}
