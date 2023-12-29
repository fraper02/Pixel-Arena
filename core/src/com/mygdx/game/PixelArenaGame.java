package com.mygdx.game;

import com.badlogic.gdx.Game;

import java.util.ArrayList;

import application.entities.Character;
import application.entities.Knight;
import presentation.screens.LevelScreen;
import application.entities.Character;


public class PixelArenaGame extends Game {

	ArrayList<Character> enemies;

	@Override
	public void create () {
		setScreen(new LevelScreen(this, new Knight(0, 0),enemies));
	}
}
