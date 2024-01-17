package com.mygdx.game;

import com.badlogic.gdx.Game;

import java.util.ArrayList;

import java.util.List;

import application.entities.Character;
import application.entities.Knight;
import application.entities.Villain;
import presentation.screens.LevelScreen;
import application.entities.Character;
import presentation.screens.MainMenuScreen;


public class PixelArenaGame extends Game {
	private boolean isPaused = false;
	@Override
	public void create () {
		setScreen(new MainMenuScreen(this));
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void togglePause() {
		isPaused = !isPaused;
	}
}
