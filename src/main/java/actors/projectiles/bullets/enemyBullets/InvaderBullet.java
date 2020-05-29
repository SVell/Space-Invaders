package actors.projectiles.bullets.enemyBullets;

import game.Stage;

public class InvaderBullet extends EnemyBullet {
    private final static int SPEED = 2;

    public InvaderBullet(Stage stage) {
		super(stage);
        width = 10;
        height = 15;
        sprites = new String[]{"Bullet.png", "Bullet.png"};
	}

	@Override
    public void act() {
        moveY(SPEED);
        super.act();
	}
}
