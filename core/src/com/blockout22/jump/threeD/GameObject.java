package com.blockout22.jump.threeD;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class GameObject {

	public Vector3 position;
	public Vector2 size;
	public Vector2 velocity;
	public Vector2 acceleration;

	private Color color = Color.YELLOW;
	
	private ModelInstance instance;

	protected GameObject(Model model, Vector2 pos, Vector2 size) {
		position = new Vector3();
		this.size = size;
		velocity = new Vector2().setZero();
		acceleration = new Vector2().setZero();
		
		instance = new ModelInstance(model);
		instance.transform.scale(size.x, size.y, 1);
		instance.transform.translate(pos.x, pos.y, 1);
	}

	public abstract void update();
	
	public void updatePhysics()
	{
		this.velocity.add(this.acceleration);
		instance.transform.translate(velocity.x, velocity.y, 0);
		this.acceleration.set(0, 0);
		
		instance.transform.getTranslation(position);
	}

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

	public void clearForces() {
		this.velocity.x *= 0;
		this.velocity.y *= 0;
		this.acceleration.x *= 0;
		this.acceleration.y *= 0;
	}
	
	public ModelInstance getInstance()
	{
		return instance;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
