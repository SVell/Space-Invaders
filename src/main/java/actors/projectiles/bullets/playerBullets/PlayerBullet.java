package actors.projectiles.bullets.playerBullets;

import actors.Actor;
import actors.enemies.Enemy;
import actors.projectiles.bullets.Bullet;
import game.Stage;

public abstract class PlayerBullet extends Bullet {

    public PlayerBullet(Stage stage) {
        super(stage);
    }


    @Override
    public void collision(Actor a) {
        if (a instanceof Enemy)
            setMarkedForRemoval(true);
    }
}
