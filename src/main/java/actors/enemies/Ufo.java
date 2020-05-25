package actors.enemies;

import actors.Actor;
import actors.projectiles.Shot;
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
		setVx(1);
		setVy(0);
	}

	@Override
	public void act() {
		super.act();
		updateXSpeed();
		updateYSpeed();
	}

	private void updateXSpeed() {
		moveX(getVx());
		if (getX() > stage.getWidth()) setMarkedForRemoval(true);
	}
	
	private void updateYSpeed() {
		
	}

	@Override
	public void collision(Actor a) {
		if (a instanceof Shot)
			setMarkedForRemoval(true);
	}

	@Override
	public int getPointValue() {
		return Ufo.POINT_VALUE;
	}
	
}
