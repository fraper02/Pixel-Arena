import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import application.entities.Character;
public class AttackTest {
    private Character mainCharacter;
    private Character villain;

    @Before
    public void setUp(){
        mainCharacter = Mockito.spy(Character.class);
        villain = Mockito.spy(Character.class);
    }

    @Test
    public void attackHitTest(){
        mainCharacter.setAttackPower(20);
        villain.setMaxHealthPoints(100);
        villain.setHealthPoints(100);
        villain.decreaseHealth(mainCharacter.getAttackPower());
        Assert.assertEquals(80, villain.getHealthPoints());
    }

    @Test
    public void negativeAttackTest(){
        mainCharacter.setAttackPower(-1);
        villain.setMaxHealthPoints(100);
        villain.setHealthPoints(100);
        villain.decreaseHealth(mainCharacter.getAttackPower());
        Assert.assertEquals(villain.getMaxHealthPoints(), villain.getHealthPoints());
    }

    @Test
    public void negativeLifeAttackTest(){
        mainCharacter.setAttackPower(20);
        villain.setMaxHealthPoints(100);
        villain.setHealthPoints(-1);
        villain.decreaseHealth(mainCharacter.getAttackPower());
        Assert.assertEquals(0, villain.getHealthPoints());
    }

    @Test
    public void negativeLifeAndNegativeAttackTest(){
        mainCharacter.setAttackPower(-20);
        villain.setMaxHealthPoints(100);
        villain.setHealthPoints(-1);
        villain.decreaseHealth(mainCharacter.getAttackPower());
        Assert.assertEquals(0, villain.getHealthPoints());
    }
}
