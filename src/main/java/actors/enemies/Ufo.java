package actors.enemies;

import actors.Actor;
import actors.Player;
import actors.Shot;
import game.Stage;

public class Ufo extends Enemy {
	
	private static final int POINT_VALUE = 50;
	
	public Ufo(Stage stage) {		
		super(stage);		
		sprites = new String[]{"ufo0.gif","ufo1.gif","ufo2.gif","ufo3.gif","ufo4.gif"};
		frameSpeed = 100;
		setWidth(30);
		setHeight(17);
		setX(Stage.WIDTH/2);
		setY(Stage.HEIGHT/2);
		setVx(0);
		setVy(1);
	}

	@Override
	public void act() {
		super.act();
		updateXSpeed();
		updateYSpeed();
	}

	private void updateXSpeed() {
		/*moveX(getVx());
		if (getX() > stage.getWidth()) setMarkedForRemoval(true);*/
	}
	
	private void updateYSpeed() {
		moveY(getVy());
		if (getY() > stage.getHeight()) setMarkedForRemoval(true);
	}

	@Override
	public void collision(Actor a) {
		if (a instanceof Shot || a instanceof Player) {
			playSound("explosion.wav");
			setMarkedForRemoval(true);
		}
	}

	@Override
	public int getPointValue() {
		return Ufo.POINT_VALUE;
	}
	
}
