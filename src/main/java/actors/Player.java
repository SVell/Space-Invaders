package actors;

import actors.enemies.Enemy;
import actors.projectiles.InvaderShot;
import actors.projectiles.Shot;
import game.Stage;

import java.awt.event.KeyEvent;

public class Player extends Actor {
	
	private boolean up,down,left,right;
	private int score = 0;
	
	public Player(Stage stage) {
		super(stage);

		sprites = new String[]{"player.gif"};
		frame = 0;
		frameSpeed = 35;
		actorSpeed = 10;
		setWidth(32);
		setHeight(20);
		setX(Stage.WIDTH/2);
		setY(Stage.HEIGHT/2);
	}

	@Override
	public void act() {
		super.act();		
	}

	protected void updateSpeed() {
		//setVx(0);
		//setVy(0);
		if (down)
			setVy(actorSpeed);
		if (up)
			setVy(-actorSpeed);
		if (left)
			setVx(-actorSpeed);
		if (right)
			setVx(actorSpeed);
		
		//don't allow scrolling off the edge of the screen		
		if (getX() - getWidth()/2 > 0 && getVx() < 0)
			moveX(getVx());
		else if (getX() + getWidth()*1.5 < Stage.WIDTH && getVx() > 0)
			moveX(getVx());
		else if (getY() - getHeight()/2 > 0 && getVy() < 0)
			moveY(getVy());
		else if (getY() + getHeight()*1.5 < Stage.HEIGHT && getVy() > 0)
			moveY(getVy());
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		}
		updateSpeed();
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		///*
		case KeyEvent.VK_UP:
			up = true;
			break;
		//*/
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		///*
		case KeyEvent.VK_DOWN:
			down = true;
			break;
	    //*/
		case KeyEvent.VK_SPACE: 
			fire(); 
			break;

		}
		updateSpeed();
	}

	@Override
	public void collision(Actor a) {
		if(a instanceof InvaderShot || a instanceof Enemy)
			stage.endGame();
	}

	private void fire() {
		Actor shot = new Shot(stage);
		shot.setX(getX());
		shot.setY(getY() - shot.getHeight());
		stage.actors.add(shot);
		playSound("photon.wav");
	}

	public void updateScore(int score) {
		this.score += score;
	}

	public int getScore() {
		return score;
	}
}
