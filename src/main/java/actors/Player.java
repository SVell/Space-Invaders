package actors;

//
import actors.enemies.Ufo;
import actors.projectiles.InvaderShot;
import game.Stage;
import java.awt.event.KeyEvent;

public class Player extends Actor {
	
	private boolean up,down,left,right;
	private int score = 0;
	private int lives = 3;
	
	public Player(Stage stage) {
		super(stage);

		sprites = new String[]{"player.gif"};
		frame = 0;
		frameSpeed = 35;
		actorSpeed = 10;
		width = 32;
		height = 20;
		posX = Stage.WIDTH/2;
		posY = Stage.HEIGHT/2;
	}

	public void act() {
		super.act();		
	}
	
	protected void updateSpeed() {
		vx = 0;
		vy = 0;
		if (down)
			vy = actorSpeed;
		if (up)
			vy = -actorSpeed;
		if (left)
			vx = -actorSpeed;
		if (right)
			vx = actorSpeed;
		
		//don't allow scrolling off the edge of the screen		
		if (posX - width/2 > 0 && vx < 0)
			posX += vx;
		else if (posX + width  + (width/2)< Stage.WIDTH && vx > 0)
			posX += vx;
		else if (posY - height/2 > 0 && vy < 0)
			posY += vy;
		else if (posY + height + (height/2) < Stage.HEIGHT && vy > 0)
			posY += vy;
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

	public int getLives(){
		return this.lives;
	}

	public void decLives(){
		this.lives--;
		isPlayerDead();
	}

	public void setLives(int lifes){
		this.lives = lifes;
	}

	public void collision(Actor a) {
		if(a instanceof InvaderShot || a instanceof Invader || a instanceof Ufo){
			this.lives--;
		}
		isPlayerDead();
	}

	public void isPlayerDead(){
			if(lives == 0) stage.endGame();
	}

	private void fire() {
		Actor shot = new actors.Shot(stage);
		shot.setX(posX + 1 + getWidth()/2-shot.getWidth()/2);
		shot.setY(posY - shot.getHeight());
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
