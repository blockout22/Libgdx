package com.blockout22.jump;

import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {

	private int health;
	private int maxhealth;
	private boolean onGround = false;

	public Player() {
		super(new Vector2(150, 300), new Vector2((25), (50)));
		this.health = 1;
		maxhealth = 1;
	}

	public void update() {

	}

	public void reset() {
		health = maxhealth;
	}

	public int getHealth() {
		return health;
	}

	public int getMaxhealth() {
		return maxhealth;
	}

	public void setMaxhealth(int maxhealth) {
		this.maxhealth = maxhealth;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean jump() {
		if (isOnGround()) {
			setOnGround(false);
			applyForce(0, 8);
			return true;
		}
		return false;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
	public boolean isDead()
	{
		return getHealth() <= 0;
	}

	@Override
	public void onResize() {
	}
}
