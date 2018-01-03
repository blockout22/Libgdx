package com.blockout22.jump.cleanup;

import com.badlogic.gdx.Game;

public class Jump extends Game{
	
	private MenuScreen menuScreen;
	private InGameScreen ingameScreen;
	private LoadingScreen loadingScreen;
	
	public static final int MENU = 0;
	public static final int INGAME = 1;
	public static final int LOADING = 3;
	
	@Override
	public void create() {
		loadingScreen = new LoadingScreen(this);
		this.setScreen(loadingScreen);
	}

	public void changeScreen(int screen) {
		switch(screen) {
		case MENU:
			if(menuScreen == null){
				menuScreen = new MenuScreen(this);
			}
			this.setScreen(menuScreen);
			break;
		case INGAME:
			if(ingameScreen == null) {
				ingameScreen = new InGameScreen(this);
			}
			this.setScreen(ingameScreen);
			break;
		}
	}
}
