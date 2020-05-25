package actors;

import actors.projectiles.InvaderShot;
import game.Stage;

import java.util.Random;

public class Invader extends Actor {
	
	private static final int POINT_VALUE = 10;
	protected static final double FIRING_FREQUENCY = 0.01;
	 
	private int leftWall = 0;
	private int rightWall = 0;
	private int step = 0;
	private int advanceTime = 1000;
	
	public Invader(Stage stage) {
		super(stage);
		Random random = new Random();
		int num = random.nextInt(4 - 0) + 0;

		switch (num){
			case 0:
				sprites = new String[]{"enemy1.png"};
				break;
			case 1:
				sprites = new String[]{"enemy2.png"};
				break;
			case 2:
				sprites = new String[]{"enemy3.png"};
				break;
			case 3:
				sprites = new String[]{"enemy4.png"};
				break;

		}


		frame = 0;
		frameSpeed = 50;
		actorSpeed = 100;
		width = 20;
		height = 20;
		posX = Stage.WIDTH/2;
		posY = Stage.HEIGHT/2;
	}
	
	public void fire() {
		InvaderShot shot = new InvaderShot(stage);
		shot.setX(posX + width/2);
		shot.setY(posY + shot.getHeight());
		shot.sprites = new String[]{"BulletEnemy.png", "BulletEnemy.png"};
		stage.actors.add(shot);
	}
	
	public void act() {

		super.act();
		if (Math.random() < FIRING_FREQUENCY) {
			if (Math.random() < FIRING_FREQUENCY)
				fire();
		}
		
		updateXSpeed();
		updateYSpeed();
	}
		
	public void setLeftWall(int leftWall) {
		this.leftWall = leftWall;
	}
	
	public void setRightWall(int rightWall) {
		this.rightWall = rightWall;
	}
	
	private void updateXSpeed() {
		if (time % actorSpeed == 0) {
			posX += getVx();
			if (posX < leftWall || posX > rightWall) setVx(-getVx());
		}
	}
	
	private void updateYSpeed() {
		step++;
		if (step == advanceTime) {
			posY += height;
			step = 0;
		}	

		if (posY == stage.getHeight()) 
			stage.endGame();
	}

	public void collision(Actor a) {
		if (a instanceof InvaderShot) {
			return;
		}

		playSound("explosion.wav");
		if (a instanceof actors.Shot || a instanceof Player) {
			setMarkedForRemoval(true);
		}
	}
	
	public int getPointValue() {
		return Invader.POINT_VALUE;
	}	
}
