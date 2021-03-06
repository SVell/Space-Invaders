package actors;

import game.ResourceLoader;
import game.Stage;

import java.awt.*;


public class Actor {



	private static final int POINT_VALUE = 0;
	private int vx;
	private int vy;
	private int posX;
	private int posY;
	protected int height;
	protected int width;
	protected int frame;
	protected int frameSpeed;
	protected int actorSpeed;
	protected int time;
	protected int lives = 1;

	protected boolean gotShoot = false;
	protected int speed = 0;

	private boolean markedForRemoval = false;
	protected String[] sprites = null; 
	protected Stage stage = null;

	public Actor(Stage canvas) {
		this.stage = canvas;
		frame = 0;
		frameSpeed = 1;
		actorSpeed = 10;
		time = 0;
	}
	
	public void act() {
		updateFrame();
	}
	
	protected void updateFrame() {
		time++;
		if (time % frameSpeed == 0) {
			time = 0;
			frame = (frame + 1) % sprites.length;
		}
	}
	
	public void playSound(final String name) {
		new Thread(new Runnable() {
			public void run() {
				ResourceLoader.getInstance().getSound(name).play();
			}
		}).start();
	}

			
	public void paint(Graphics g) {
		g.drawImage(ResourceLoader.getInstance().getSprite(sprites[frame]), posX, posY, stage);
	}
	
	public void setX(int posX) {
		this.posX = posX;
	}
	
	public void setY(int posY) {
		this.posY = posY;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
	protected void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	protected void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}
	
	public void setVx(int vx) {
		this.vx = vx;
	}

	public void getShot(){
		this.gotShoot = true;
	}
	
	// checks if actor was shot by bullet
	public boolean isGotShot(){
		return this.gotShoot;
	}

	public void decLives(){
		this.lives--;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getVx() {
		return vx;
	}
	
	public void setVy(int vy) {
		this.vy = vy;
	}

	public void move(Point vector){
		posX += vector.x;
		posY += vector.y;
	}

	public void move(int x, int y){
		move(new Point(x, y));
	}

	public void moveX(int dx){
		move(new Point(dx, 0));
	}

	public void moveY(int dy){
		move(new Point(0, dy));
	}

	public int getVy() {
		return vy;
	}

	public Rectangle getBounds() {
		return new Rectangle(posX,posY,width, height);
	}
	
	public void collision(Actor a) {		
	}
	
	public void setMarkedForRemoval(boolean markedForRemoval) {
		this.markedForRemoval = markedForRemoval;
	}

	public boolean isMarkedForRemoval() {
		return markedForRemoval;
	}
	
	public int getPointValue() {
		return Actor.POINT_VALUE;
	}
}
