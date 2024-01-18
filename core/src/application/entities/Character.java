package application.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import application.ai.Node;
import application.ai.TilesGraph;

public abstract class Character {
    private int maxHealthPoints;
    private int healthPoints;
    private int attackPower;
    private float standardSpeed;
    private float runningSpeed;
    private boolean attacking;
    protected String tipo;
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
    public enum Directions{
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
    private Node nearNode;
    private TilesGraph tilesGraph;
    private boolean alive;
    private boolean healing = false;
    private float startHealing = 0;
    private int numGemme = 0;

    public Character(){}
    /**
     * Inizializza un personaggio
     * @param maxHealthPoints indica il valore massimo dei punti vita
     * @param attackPower indica quanto danno fa un attacco del personaggio
     * @param standardSpeed indica la velocità di camminata del personaggio
     * @param x indica la posizione sull'asse delle x
     * @param y indica la posizione sull'asse delle y
     */
    public Character(int maxHealthPoints, int attackPower, float standardSpeed, float x, float y) {
        this.maxHealthPoints = maxHealthPoints;
        this.healthPoints = maxHealthPoints;
        this.attackPower = attackPower;
        this.standardSpeed = standardSpeed;
        this.runningSpeed = standardSpeed * 1.75f;
        this.attacking = false;
        this.x = x;
        this.y = y;
        this.previousX = x;
        this.previousY = y;
        this.movementBox = new Rectangle( x + 22,y + 15,22,17);
        this.hitBox = new Rectangle(x + 22,y +15,22,30);
        this.direction = Directions.SOUTH;
        this.running = false;
        this.alive = true;
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

    /**
     * Imposta la velocità di camminata al valore passato e la velocità di corsa il 50% più veloce di questa
     * @param standardSpeed is the walking speed of the character
     */
    public void setSpeed(float standardSpeed) {
        this.standardSpeed = standardSpeed ;
        this.runningSpeed = standardSpeed * 1.5f;
    }

    public float getRunningSpeed() {
        return runningSpeed;
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

    public Rectangle getHitBox() {
        return hitBox;
    }

    public String getTipo() {
        return tipo;
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

    public int getNumGemme() {
        return numGemme;
    }

    public void setNumGemme(int numGemme) {
        this.numGemme = numGemme;
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
     * Aggiorna la posizione di un personaggio e le sue box
     * @param x posizione sull'asse delle x
     * @param y posizione sull'asse delle y
     */
    public void setPosition(float x, float y){
        if(standardSpeed >= 0) {
            this.setPreviousPosition(this.x, this.y);
            this.x = x;
            this.y = y;
            if (this.movementBox != null) {
                this.movementBox.setPosition(x + 22, y + 15);
                this.hitBox.setPosition(x + 22, y + 15);
                this.attackBoxDown.setPosition(x + 10, y);
                this.attackBoxLeft.setPosition(x + 2, y + 6);
                this.attackBoxUp.setPosition(x + 10, y + 34);
                this.attackBoxRight.setPosition(x + 35, y + 6);
            }
            if (this instanceof Villain) {
                ((Villain) this).actionArea.setPosition(x + 8, y + 8);
            }
        }
    }

    /**
     * Cambia le ultime coordinate del personaggio
     * @param x posizione sull'asse delle x
     * @param y posizione sull'asse delle y
     */
    public void setPreviousPosition(float x, float y){
        this.previousX = x;
        this.previousY = y;
    }
    /**
     * Diminuisce la vita del personaggio di un valore passato
     * @param amount amount of healthPoint lost
     *               @Pre: amount deve essere > 0
     */
    public void decreaseHealth(int amount){
        if(amount > 0){
            this.healthPoints -= amount;
        }
        if(this.healthPoints <= 0) {
            this.healthPoints = 0;
            this.setAlive(false);
        }
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    /**
     * Imposta l'animazione del personaggio ad idle nella direzione
     * in cui sta gurdando
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
     * Imposta l'animazione del personaggio all'attacco
     * nella direzione in cui sta guardando
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
     * Imposta l'animazione del personaggio a walk nella direzione
     * in cui sta guardando
     */
    public void doWalk(){
        switch(this.direction){
            case SOUTH:{
                this.currentAnimation = walkDown;
                this.setPreviousPosition(this.getX(), this.getY());
                this.setPosition(this.getX(), this.getY() - this.getStandardSpeed());
            }break;
            case WEST:{
                this.currentAnimation = walkLeft;
                this.setPreviousPosition(this.getX(), this.getY());
                this.setPosition(this.getX() - this.getStandardSpeed(), this.getY());
            }break;
            case NORTH:{
                this.currentAnimation = walkUp;
                this.setPreviousPosition(this.getX(), this.getY());
                this.setPosition(this.getX(), this.getY() + this.getStandardSpeed());
            }break;
            case EAST:{
                this.currentAnimation = walkRight;
                this.setPreviousPosition(this.getX(), this.getY());
                this.setPosition(this.getX() + this.getRunningSpeed(), this.getY());
            }break;
        }
    }

    public void doWalk(String direction){
        if(direction.equalsIgnoreCase("LEFT")){
            this.currentAnimation = walkLeft;
            this.setPreviousPosition(this.getX(), this.getY());
            this.setPosition(this.getX() - this.getStandardSpeed(), this.getY());
        }
        else if(direction.equalsIgnoreCase("RIGHT")){
            this.currentAnimation = walkRight;
            this.setPreviousPosition(this.getX(), this.getY());
            this.setPosition(this.getX() + this.getStandardSpeed(), this.getY());
        }
        else if(direction.equalsIgnoreCase("UP")){
            this.currentAnimation = walkUp;
            this.setPreviousPosition(this.getX(), this.getY());
            this.setPosition(this.getX(), this.getY() + this.getStandardSpeed());
        }
        else if(direction.equalsIgnoreCase("DOWN")){
            this.currentAnimation = walkDown;
            this.setPreviousPosition(this.getX(), this.getY());
            this.setPosition(this.getX(), this.getY()  - this.getStandardSpeed());
        }
    }

    /**
     * Imposta l'animazione del personaggio a corsa nella direzione
     * in cui sta guradando
     */
    public void doRun(){
        switch(this.direction){
            case SOUTH:{
                this.currentAnimation = runDown;
                this.setPreviousPosition(this.getX(), this.getY());
                this.setPosition(this.getX(), this.getY()  - this.getRunningSpeed());
            }break;
            case WEST:{
                this.currentAnimation = runLeft;
                this.setPreviousPosition(this.getX(), this.getY());
                this.setPosition(this.getX() - this.getRunningSpeed(), this.getY());
            }break;
            case NORTH:{
                this.currentAnimation = runUp;
                this.setPreviousPosition(this.getX(), this.getY());
                this.setPosition(this.getX(), this.getY() + this.getRunningSpeed());
            }break;
            case EAST:{
                this.currentAnimation = runRight;
                this.setPreviousPosition(this.getX(), this.getY());
                this.setPosition(this.getX() + this.getRunningSpeed(), this.getY());
            }break;
        }
    }

    public void doRun(String direction){
        if(direction.equalsIgnoreCase("LEFT")){
            this.currentAnimation = runLeft;
            this.setPreviousPosition(this.getX(), this.getY());
            this.setPosition(this.getX() - this.getRunningSpeed(), this.getY());
        }
        else if(direction.equalsIgnoreCase("RIGHT")){
            this.currentAnimation = runRight;
            this.setPreviousPosition(this.getX(), this.getY());
            this.setPosition(this.getX() + this.getRunningSpeed(), this.getY());
        }
        else if(direction.equalsIgnoreCase("UP")){
            this.currentAnimation = runUp;
            this.setPreviousPosition(this.getX(), this.getY());
            this.setPosition(this.getX(), this.getY() + this.getRunningSpeed());
        }
        else if(direction.equalsIgnoreCase("DOWN")){
            this.currentAnimation = runDown;
            this.setPreviousPosition(this.getX(), this.getY());
            this.setPosition(this.getX(), this.getY()  - this.getRunningSpeed());
        }
    }

    /**
     * Controlla se c'è stata una collisione tra 2 personaggi e la risolve
     * @param v il personaggio con cui vogliamo controllare la collisione
     */
    public void collisionCheck(Character v) {
        if(this.getMovementBox().overlaps(v.getMovementBox())){
            this.setPosition(previousX, previousY);
            v.setPosition(v.previousX, v.previousY);
        }
    }
    public Animation<TextureRegion> getCurrentAnimation() {
        return currentAnimation;
    }

    public void setNearNode(Node node){
        this.nearNode = node;
    }

    public Node getNearNode(){ return this.nearNode; }

    public TilesGraph getTilesGraph() {
        return tilesGraph;
    }

    public void setTilesGraph(TilesGraph tilesGraph) {
        this.tilesGraph = tilesGraph;
    }

    public boolean isAlive(){ return this.alive; }

    /**
     * Cura il personaggio di 10 punti vita se questo non è a vita massima
     * @param stateTime valore usato per effettuare la cura solo 1 volta al secondo
     */
    public void doHeal(float stateTime){
        if(!healing){
            startHealing = stateTime;
            healing = true;
            if(this.healthPoints != this.maxHealthPoints) {
                this.setHealthPoints(this.healthPoints + 10);
            }
        }
        if(stateTime - startHealing >= 1f){
            healing = false;
        }
    }

    /**
     * Controlla se il personaggio attaccato è sato colpito oppure no
     * @param attacked il personaggio che è stato attaccato
     * @return Ritorna true se il colpo è andato a segno e false altrimenti
     */
    public boolean checkAttack(Character attacked){
        switch(direction){
            case EAST:{
                if(this.attackBoxRight.overlaps(attacked.getHitBox())){
                    attacked.decreaseHealth(this.attackPower);
                    return true;
                }
            }break;
            case WEST:{
                if(this.attackBoxLeft.overlaps(attacked.getHitBox())){
                    attacked.decreaseHealth(this.attackPower);
                    return true;
                }
            }break;
            case NORTH:{
                if(this.attackBoxUp.overlaps(attacked.getHitBox())){
                    attacked.decreaseHealth(this.attackPower);
                    return true;
                }
            }break;
            case SOUTH:{
                if(this.attackBoxDown.overlaps(attacked.getHitBox())){
                    attacked.decreaseHealth(this.attackPower);
                    return true;
                }
            }break;
        }
        return false;
    }
}
