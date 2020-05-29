package actors.projectiles.bullets.playerBullets;

import game.Stage;

public class BasicBullet extends PlayerBullet {
    private final static int SPEED = -3;

    public BasicBullet(Stage stage) {
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
