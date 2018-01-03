package com.blockout22.jump.threeD;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector2;

public class Test3D implements ApplicationListener {

	public OrthographicCamera camera;
	public ModelBatch modelBatch;
	public Model model;
	public ModelInstance instance;

	private Player player;
	private ArrayList<Box> boxes = new ArrayList<Box>();
	

	@Override
	public void create() {
		modelBatch = new ModelBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(true);
		camera.zoom = 6;
//		camera.position.set(0f, 0f, 1f);
		camera.lookAt(0, 0, 0);
//		camera.near = 1f;
//		camera.far = 300f;
		camera.update();

		model = Cube.build(1, 1, 1, new Material(ColorAttribute.createDiffuse(Color.GREEN)));
		
		player = new Player(model, new Vector2(-1, 0), new Vector2(50, 100));

		Box b = new Box(model, new Vector2(8, 0), new Vector2(50, 50));
		b.applyForce(-0.1f, 0.0001f);
		boxes.add(b);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(camera);
		modelBatch.render(player.getInstance());
		for (Box b : boxes) {
			modelBatch.render(b.getInstance());
			b.updatePhysics();
		}
		modelBatch.end();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		model.dispose();
	}

}
