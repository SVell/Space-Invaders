package actors.enemies;

import actors.Actor;
import actors.Player;
import actors.guns.Gun;
import actors.guns.SingleShotInvaderGun;
import actors.projectiles.bullets.Bullet;
import actors.projectiles.bullets.enemyBullets.InvaderBullet;
import game.Stage;

import java.util.Random;

public class Invader extends Enemy {
	
	private static final int POINT_VALUE = 10;
    private Gun gun;
    private int speed = 0;

	private boolean canShoot = true;
	
	public Invader(Stage stage) {
		super(stage);
		Random random = new Random();
        int num = random.nextInt(4);

		switch (num){
			case 0:
				sprites = new String[]{"enemy1.png"};
				speed = 1;
				canShoot = false;
				break;
			case 1:
				sprites = new String[]{"enemy2.png"};
				break;
			case 2:
				sprites = new String[]{"enemy3.png"};
				lives = 2;
				break;
			case 3:
				sprites = new String[]{"enemy4.png"};
				lives = 3;
				canShoot = false;
				break;

		}
		frame = 0;
		frameSpeed = 50;
		actorSpeed = 100;
		width = 20;
		height = 20;
		setX(Stage.WIDTH/2);
		setY(Stage.HEIGHT/2);
        gun = new SingleShotInvaderGun(stage);

	}
	
	public void act() {

		super.act();
        if (canShoot)
            gun.shoot(getX(), getY());
		
		updateXSpeed();
		updateYSpeed();
	}

	private void updateXSpeed() {
	}

	private void updateYSpeed() {
		moveY(getVy() + speed);
		if (getY() >= stage.getHeight())
			setMarkedForRemoval(true);
	}

	public int getLives(){
		return this.lives;
	}

	public void decLives(){
		//playSound("damage.mp3");
		--this.lives;
		isDead();
	}

	public void isDead(){
		if(lives == 0) setMarkedForRemoval(true);
	}

	public void collision(Actor a) {


        if (a instanceof InvaderBullet) {
			return;
		}

		playSound("explosion.wav");
        if (a instanceof Bullet) {
			getShot();
			decLives();
			//setMarkedForRemoval(true);
		}
        if(a instanceof Player){
			lives = 1;
			getShot();
			decLives();

		}
	}
	
	public int getPointValue() {
		return Invader.POINT_VALUE;
	}	
}
