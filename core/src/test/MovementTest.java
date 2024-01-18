import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import application.entities.Character;
import application.entities.Knight;

public class MovementTest {
    private Character mainCharacter;

    @Before
    public void setUp(){
        mainCharacter = Mockito.spy(Character.class);
    }

    @Test
    public void testWalkUp(){
        mainCharacter.setPosition(0,0);
        mainCharacter.setSpeed(1f);
        mainCharacter.setDirection(Character.Directions.NORTH);
        mainCharacter.doWalk();
        Assert.assertEquals(0, mainCharacter.getX(), 0 - mainCharacter.getX());
        Assert.assertEquals(mainCharacter.getY() + mainCharacter.getStandardSpeed(), mainCharacter.getY(), mainCharacter.getStandardSpeed() );
    }

    @Test
    public void testNegativeSpeedWalkUp(){
        mainCharacter.setPosition(0,0);
        mainCharacter.setSpeed(-1f);
        mainCharacter.setDirection(Character.Directions.NORTH);
        mainCharacter.doWalk();
        Assert.assertEquals(0, mainCharacter.getX(), 0 - mainCharacter.getX());
        Assert.assertEquals(0, mainCharacter.getY(), mainCharacter.getStandardSpeed() );
    }
}
