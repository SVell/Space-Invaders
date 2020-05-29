package actors.projectiles.bullets.enemyBullets;

import actors.Actor;
import actors.Player;
import actors.projectiles.bullets.Bullet;
import game.Stage;

public abstract class EnemyBullet extends Bullet {

    public EnemyBullet(Stage stage) {
        super(stage);
    }

    @Override
    public void collision(Actor a) {
        if (a instanceof Player)
            setMarkedForRemoval(true);
    }
}
