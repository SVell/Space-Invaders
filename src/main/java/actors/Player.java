package actors;

//
import actors.enemies.Ufo;
import actors.projectiles.HpBuff;
import actors.projectiles.InvaderShot;
import actors.projectiles.Shot;
import game.Key;
import game.Stage;

import java.awt.event.KeyEvent;

public class Player extends Actor {

	private int score = 0;
	private int lives = 3;
	private long pressTime = System.currentTimeMillis();
	private long fireRate = 200000000L;
	
	public Player(Stage stage) {
		super(stage);

		sprites = new String[]{"Plane.png"};
		frame = 0;
		frameSpeed = 35;
		actorSpeed = 4;
		width = 32;
		height = 20;
		setX(Stage.WIDTH/2);
		setY(Stage.HEIGHT/2);
	}

	public void act() {
		super.act();		
	}
	
	public void updateControls() {
		if (Key.down.isDown && !Key.up.isDown)
			setVy(actorSpeed);
		else if (Key.up.isDown && !Key.down.isDown)
			setVy(-actorSpeed);
		else
			setVy(0);

		if (Key.left.isDown && !Key.right.isDown)
			setVx(-actorSpeed);
		else if (Key.right.isDown && !Key.left.isDown)
			setVx(actorSpeed);
		else
			setVx(0);

		long currentTime = System.nanoTime();
		if(Key.space.isDown && currentTime - pressTime > fireRate) {
			fire();
			pressTime = currentTime;
		}
		
		//don't allow scrolling off the edge of the screen		
		if (getX() > 0 && getVx() < 0)
			moveX(getVx());
		else if (getX() + width  + (width/2)< Stage.WIDTH && getVx() > 0)
			moveX(getVx());
		if (getY() - height/2 > 0 && getVy() < 0)
			moveY(getVy());
		else if (getY() + height + 52 < Stage.HEIGHT && getVy() > 0) 
			moveY(getVy());

	}

	public int getLives(){
		return this.lives;
	}

	public void decLives(){
		//playSound("damage.mp3");
		--this.lives;
		isPlayerDead();
	}

	public void setLives(int lives){
		this.lives = lives;
	}

	public void collision(Actor a) {
		//playSound("dam.mp3");
		if(a instanceof InvaderShot || a instanceof Invader || a instanceof Ufo){
			this.lives--;
		}
		if(a instanceof HpBuff){
			this.lives++;
		}
		isPlayerDead();
	}

	public void isPlayerDead(){
			if(lives == 0) stage.endGame();
	}

	private void fire() {

		Actor shot = new Shot(stage);
		shot.setX(getX() + shot.getWidth() + 2);
		shot.setY(getY() - shot.getHeight());
		stage.actors.add(shot);
		playSound("photon.wav");

		/*Actor shot = new Shot(stage);
		shot.setX(getX() + 1 + getWidth()/2-shot.getWidth()/2);
		shot.setY(getY() - shot.getHeight());
		stage.actors.add(shot);
		playSound("photon.wav");*/
	}

	public void updateScore(int score) {
		this.score += score;
	}

	public int getScore() {
		return score;
	}
}
