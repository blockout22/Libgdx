package com.blockout22.jump;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BoxTest {

	private Vector2 position;
	private Vector2 size;

	private Shape shape;
	private Body body;

	public BoxTest(World world, float x, float y) {
		position = new Vector2(x, y);
		size = new Vector2(50, 50);

		BodyDef bodydef = new BodyDef();
		bodydef.type = BodyDef.BodyType.DynamicBody;
		bodydef.position.set(position);
		bodydef.fixedRotation = true;
		body = world.createBody(bodydef);

		shape = new PolygonShape();

		((PolygonShape) shape).setAsBox(size.x / 2, size.y / 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;

		Fixture fixture = body.createFixture(fixtureDef);
	}

	public void draw(ShapeRenderer shape) {
		position.set(body.getPosition());
		shape.begin(ShapeType.Filled);
		shape.setColor(Color.YELLOW);
		shape.rect(position.x - size.x / 2, position.y - size.y / 2, size.x, size.y);
		shape.end();
	}

	public void dispose() {
		shape.dispose();
	}
	
	

}
