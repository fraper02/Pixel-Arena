## Authors
- [@fraper02](https://www.github.com/fraper02)
- [@Manursp0022](https://www.github.com/Manursp0022)
- [@DEEECAAAuni](https://www.github.com/DEEECAAAuni)

# Pixel Arena

Pixel Arena is an orthogonal platforming video game that will see the player delving into a map 
filled with AI-enhanced enemies who will try to stop him at any cost.

## Description

We are 3 students from the University of salerno who decided to develop a video game as a combined project for our Software Engineering and Fundamentals of Artificial Intelligence exams.
The idea came from wanting to do a different and quite complex project that could test our skills, while also giving us a chance to learn new technologies in the field of game development.
For the realization of this project we used the libGDX framework for the game engine and the Tiled program to make the map, 
the code was written in Java using the Android Studio IDE and uses gradle as the build system.

## Features

The application provides a main menu at startup that gives the player the option of starting a new game or loading an old save.
In addition, the player will have to choose the character to use through a special Menu before starting the game.
When the game starts, the player will find himself in the map where he will have to eliminate all enemies to advance to the next level,
the enemies will be equipped with intelligence and will seek out the enemy in their areas of responsibility, in turn trying to eliminate the player.
In each level, healing bases and gems will be seeded that the player can collect to later generate power-ups that will be chosen at the end of the level.

### Main Menu
<img width="1920" alt="MainMenu-min" src="https://github.com/fraper02/Pixel-Arena/assets/114728100/afe9208a-546a-4316-bf6f-d4ddc6704f5d">

### Choice of characters 
<img width="1918" alt="ChooseCharacter-min" src="https://github.com/fraper02/Pixel-Arena/assets/114728100/1284ac64-1c83-4d9c-bc22-51d827f2591b">

### Running Game
![ScreenShotMappa](https://github.com/fraper02/Pixel-Arena/assets/114185914/0ddd0e95-1844-4625-b91a-1fdd84aa182d)
An example of a map showing enemy search areas.

## Pathfinding & Attack

Enemy pathfinding is done through the indexed A* algorithm, the implementation of which is provided in the libGDX library "GDX.ai",
thanks to this the enemy will be able to reach the player when the player enters its area of action.
For attacking the agent will behave as a simple reactive agent, in fact it is provided with an actionArea that acts as a sensor, when the player enters the enemy's actionArea, the enemy will start attacking it.

![ScreenShotAttacci](https://github.com/fraper02/Pixel-Arena/assets/114185914/3693a25a-f216-4747-bfab-ff3156354242)

## Tutorial[PixelArena]

### Movement
WASD to Move: Use the W, A, S, D keys to move.
- Press W to move north.
- Press A to head west.
- Use S to head south.
- Click D to move east.
#### Running
- The player will also be able to turn the run on or off by pressing the LSHIFT key, while attacking the enemy will require pressing the K key.

### Health Recovery.
- Healing Bases: Find bases scattered around the level.
- Stand on Base: Stand on a base to recover health.
- Healing: While on a base, you will recover 10HP per second.

### Collect Gems 
- Gems in the Level: Gems are scattered throughout the level.
- Collect Gems: Walk over the gems to collect them.
  
### Enemies and Combat
- Life of Enemies: Enemies have 80HP total life.
- Attack: Both you and enemies have an attack of 10.
- Strategy: Use your skill and cunning to defeat enemies.

# Avvio da Android Studio

To start the project from Android Studio first you will need to clone the repository.
```bash
  git clone https://github.com/fraper02/Pixel-Arena.git
```
after which you will need to create a new configuration in order to run the project, instructions at this link: https://libgdx.com/wiki/start/import-and-running
