package application.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Character {
    private int maxHealthPoints;
    private int healthPoints;
    private int attackPower;
    private float standardSpeed;
    private float runningSpeed;
    private boolean attacking;
    private Rectangle movementBox;
    private Rectangle hitBox;
    protected Rectangle attackBoxDown;
    protected Rectangle attackBoxUp;
    protected Rectangle attackBoxRight;
    protected Rectangle attackBoxLeft;
    private float previousX;
    private float previousY;
    private float x;
    private float y;
    private enum Directions{
        SOUTH ,
        WEST,
        NORTH,
        EAST
    }
    private Directions direction;
    private boolean running;
    protected Animation<TextureRegion> idleDown;
    protected Animation<TextureRegion> idleUp;
    protected Animation<TextureRegion> idleLeft;
    protected Animation<TextureRegion> idleRight;
    protected Animation<TextureRegion> walkRight;
    protected Animation<TextureRegion> walkLeft;
    protected Animation<TextureRegion> walkDown;
    protected Animation<TextureRegion> walkUp;
    protected Animation<TextureRegion> attackDown;
    protected Animation<TextureRegion> attackUp;
    protected Animation<TextureRegion> attackLeft;
    protected Animation<TextureRegion> attackRight;
    protected Animation<TextureRegion> runDown;
    protected Animation<TextureRegion> runRight;
    protected Animation<TextureRegion> runUp;
    protected Animation<TextureRegion> runLeft;
    private Animation<TextureRegion> currentAnimation;

    /**
     * Initialize a character
     * @param maxHealthPoints indicates the max health points of the character
     * @param attackPower indicates how much damage the character deals with an attack
     * @param standardSpeed indicates the walking speed of the character
     * @param x indicates position on the x axis
     * @param y indicates position on the y axis
     */
    public Character(int maxHealthPoints, int attackPower, float standardSpeed, float x, float y) {
        this.maxHealthPoints = maxHealthPoints;
        this.healthPoints = maxHealthPoints;
        this.attackPower = attackPower;
        this.standardSpeed = standardSpeed;
        this.runningSpeed = standardSpeed * 1.5f;
        this.attacking = false;
        this.x = x;
        this.y = y;
        this.previousX = x;
        this.previousY = y;
        this.movementBox = new Rectangle( x + 22,y + 15,22,17);
        this.hitBox = new Rectangle(x + 22,y +15,22,30);
        this.direction = Directions.SOUTH;
        this.running = false;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public float getStandardSpeed() {
        return standardSpeed;
    }

    public void setStandardSpeed(float standardSpeed) {
        this.standardSpeed = standardSpeed;
    }

    public float getRunningSpeed() {
        return runningSpeed;
    }

    public void setRunningSpeed(float runningSpeed) {
        this.runningSpeed = runningSpeed;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public Rectangle getMovementBox() {
        return movementBox;
    }

    public void setMovementBox(Rectangle movementBox) {
        this.movementBox = movementBox;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }
    public Rectangle getAttackBoxDown() {
        return attackBoxDown;
    }

    public void setAttackBoxDown(Rectangle attackBoxDown) {
        this.attackBoxDown = attackBoxDown;
    }

    public Rectangle getAttackBoxUp() {
        return attackBoxUp;
    }

    public void setAttackBoxUp(Rectangle attackBoxUp) {
        this.attackBoxUp = attackBoxUp;
    }

    public Rectangle getAttackBoxRight() {
        return attackBoxRight;
    }

    public void setAttackBoxRight(Rectangle attackBoxRight) {
        this.attackBoxRight = attackBoxRight;
    }

    public Rectangle getAttackBoxLeft() {
        return attackBoxLeft;
    }

    public void setAttackBoxLeft(Rectangle attackBoxLeft) {
        this.attackBoxLeft = attackBoxLeft;
    }
    public float getPreviousX() {
        return previousX;
    }

    public float getPreviousY() {
        return previousY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Updates the position of the character after a movement
     * and its boxes
     * @param x position on the x axis
     * @param y position on the y axis
     */
    public void setPosition(float x, float y){
        this.setPreviousPosition(this.x, this.y);
        this.x = x;
        this.y = y;
        this.movementBox.setPosition(x + 22,y + 15);
        this.hitBox.setPosition(x + 22,y +15);
        this.attackBoxDown.setPosition(x + 10, y);
        this.attackBoxLeft.setPosition(x + 2, y +6);
        this.attackBoxUp.setPosition(x + 10, y + 34);
        this.attackBoxRight.setPosition(x + 35, y + 6);
        this.doStopAndIdle();
    }

    /**
     * Changes the values of the previous coordinates
     * @param x position on the x axis
     * @param y position on the y axis
     */
    public void setPreviousPosition(float x, float y){
        this.previousX = x;
        this.previousY = y;
    }
    /**
     * Decreases the healthPoints of the character by the given amount
     * @param amount amount of healthPoint lost
     */
    public void decreaseHealth(int amount){
        this.healthPoints -= amount;
        if(this.healthPoints < 0) {
            this.healthPoints = 0;
        }
    }

    /**
     * Set the animation to idle in the current direction which the character is facing
     */
    public void doStopAndIdle(){
        switch(this.direction){
            case SOUTH:{
                this.currentAnimation = idleDown;
            }break;
            case WEST:{
                this.currentAnimation = idleLeft;
            }break;
            case NORTH:{
                this.currentAnimation = idleUp;
            }break;
            case EAST:{
                this.currentAnimation = idleRight;
            }break;
        }
    }

    /**
     * Set the current animation to attack in the direction which the character is facing
     */
    public void doAttack(){
        switch(this.direction){
            case SOUTH:{
                this.currentAnimation = attackDown;
            }break;
            case WEST:{
                this.currentAnimation = attackLeft;
            }break;
            case NORTH:{
                this.currentAnimation = attackUp;
            }break;
            case EAST:{
                this.currentAnimation = attackRight;
            }break;
        }
        this.attacking = true;
    }

    /**
     * Set the current animation to walk in the direction which the character is facing
     */
    public void doWalk(){
        switch(this.direction){
            case SOUTH:{
                this.currentAnimation = walkDown;
            }break;
            case WEST:{
                this.currentAnimation = walkLeft;
            }break;
            case NORTH:{
                this.currentAnimation = walkUp;
            }break;
            case EAST:{
                this.currentAnimation = walkRight;
            }break;
        }
    }

    /**
     * Set the current animation to running in the direction which the character is facing
     */
    public void doRun(){
        switch(this.direction){
            case SOUTH:{
                this.currentAnimation = runDown;
            }break;
            case WEST:{
                this.currentAnimation = runLeft;
            }break;
            case NORTH:{
                this.currentAnimation = runUp;
            }break;
            case EAST:{
                this.currentAnimation = runRight;
            }break;
        }
    }

    public Animation<TextureRegion> getCurrentAnimation() {
        return currentAnimation;
    }
}
