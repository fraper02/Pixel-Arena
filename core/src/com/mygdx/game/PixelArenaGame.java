package com.mygdx.game;

import com.badlogic.gdx.Game;

import java.util.ArrayList;
import java.util.List;

import application.entities.Character;
import application.entities.Knight;
import application.entities.Villain;
import presentation.screens.LevelScreen;

public class PixelArenaGame extends Game {
	@Override
	public void create () {
		List<Character> enemies = new ArrayList<>();
		enemies.add(new Villain(40,40));
		setScreen(new LevelScreen(this, new Knight(0, 0), enemies));
	}
}
