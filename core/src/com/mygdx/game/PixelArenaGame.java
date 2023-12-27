package com.mygdx.game;

import com.badlogic.gdx.Game;

import application.entities.Knight;
import presentation.screens.LevelScreen;

public class PixelArenaGame extends Game {
	@Override
	public void create () {
		setScreen(new LevelScreen(this, new Knight(0, 0)));
	}
}
