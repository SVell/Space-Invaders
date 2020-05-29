package actors.guns;

import actors.Actor;
import actors.projectiles.bullets.enemyBullets.InvaderBullet;
import game.Stage;

public class SingleShotInvaderGun extends Gun {
    private static final int FIRE_RATE = 1000;

    public SingleShotInvaderGun(Stage stage) {
        super(stage, FIRE_RATE, null);
    }

    @Override
    void fire(int x, int y) {
        Actor shot = new InvaderBullet(getStage());
        shot.setX(x + width / 2);
        shot.setY(y + shot.getHeight());
        getStage().actors.add(shot);
    }
}
