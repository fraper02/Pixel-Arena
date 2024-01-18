import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import application.entities.Character;

public class LifeRegenTest {
    private Character mainCharacter;

    @Before
    public void setUp(){
        mainCharacter = Mockito.spy(Character.class);
    }

    @Test
    public void healingTest(){
        mainCharacter.setHealthPoints(42);
        mainCharacter.doHeal(0);
        Assert.assertEquals(52, mainCharacter.getHealthPoints());
    }

    @Test
    public void healingTestMaxHp(){
        mainCharacter.setMaxHealthPoints(100);
        mainCharacter.setHealthPoints(mainCharacter.getMaxHealthPoints());
        mainCharacter.doHeal(0);
        Assert.assertEquals(mainCharacter.getMaxHealthPoints(), mainCharacter.getHealthPoints());
    }
}
