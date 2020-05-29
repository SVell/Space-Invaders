package actors.guns;

import actors.Actor;
import actors.projectiles.bullets.playerBullets.BasicBullet;
import game.Stage;

public class SingleShotPlayerGun extends Gun {
    private static final int FIRE_RATE = 350;

    public SingleShotPlayerGun(Stage stage, String sound) {
        super(stage, FIRE_RATE, sound);
    }

    @Override
    void fire(int x, int y) {
        Actor shot = new BasicBullet(getStage());
        shot.setX(x + shot.getWidth() + 2);
        shot.setY(y - shot.getHeight());
        getStage().actors.add(shot);
    }
}
