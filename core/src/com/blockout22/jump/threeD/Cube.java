package com.blockout22.jump.threeD;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Cube {
	
	public static Model build(float width, float height, float depth, Material material)
	{
		ModelBuilder modelBuilder = new ModelBuilder();
		Model model = modelBuilder.createBox(width, height, depth, material, Usage.Position | Usage.Normal);
		return model;
	}

}
