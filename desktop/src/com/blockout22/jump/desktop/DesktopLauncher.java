package com.blockout22.jump.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.blockout22.jump.cleanup.Jump;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 480;
//		config.resizable = false;
		new LwjglApplication(new Jump(), config);
//		new LwjglApplication(new Test3D(), config);
	}
}
