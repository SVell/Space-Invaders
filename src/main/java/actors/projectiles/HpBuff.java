package actors.projectiles;

import actors.Actor;
import actors.Invader;
import actors.Player;
import game.Stage;

import javax.swing.*;

public class HpBuff extends Actor {

    public HpBuff(Stage canvas) {
        super(canvas);

        sprites = new String[]{"MedKit.png"};

        frame = 0;
        frameSpeed = 50;
        actorSpeed = 100;
        width = 20;
        height = 20;
        setX(Stage.WIDTH/2);
        setY(Stage.HEIGHT/2);
    }

    public void act() {
        super.act();
        updateYSpeed();
    }


    private void updateYSpeed() {
        moveY(getVy());
        if (getY() >= stage.getHeight())
            setMarkedForRemoval(true);
    }

    public void collision(Actor a) {
        //playSound("explosion.wav");
        if (a instanceof Player) {
            setMarkedForRemoval(true);
        }
    }

}
