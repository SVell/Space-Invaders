package actors.guns;

import actors.Actor;
import game.Stage;

public abstract class Gun extends Actor {
    private Stage stage;
    private long lastShotTime = System.currentTimeMillis();
    public long fireRate;
    private String sound;

    public Gun(Stage stage, long fireRate, String sound) {
        super(stage);
        this.stage = stage;
        this.fireRate = fireRate;
        this.sound = sound;
    }


    public void shoot(int x, int y) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime > fireRate) {
            fire(x, y);
            if (sound != null)
                playSound(sound);
            lastShotTime = currentTime;
        }
    }

    abstract void fire(int x, int y);

    public void setFireRate(long fireRate) {
        this.fireRate = fireRate;
    }

    public Stage getStage() {
        return stage;
    }
}
