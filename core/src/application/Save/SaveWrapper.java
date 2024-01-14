package application.Save;

import application.entities.Character;

public class SaveWrapper {
    private int numLevel;

    private Character mainCharacter;

    public SaveWrapper(int numLevel, Character mainCharacter) {
        this.numLevel = numLevel;
        this.mainCharacter = mainCharacter;
    }

    public int getNumLevel() {
        return numLevel;
    }

    public void setNumLevel(int numLevel) {
        this.numLevel = numLevel;
    }

    public Character getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(Character mainCharacter) {
        this.mainCharacter = mainCharacter;
    }
}
