package actors.projectiles;

import actors.Actor;
import game.Stage;

public class Shot extends Actor {

	protected int bulletSpeed = 2;  
	protected boolean up = true;
	
	public Shot(Stage stage) {
		super(stage);
		setWidth(10);
		setHeight(15);
		sprites = new String[]{"shot1.gif","shot2.gif"};
	}

	public void act() {
		super.act();
		if (up)
			moveY(-bulletSpeed);
		else
			moveY(bulletSpeed);
		
		if (getY() < 0)
			setMarkedForRemoval(true);
	}
	
	public void collision(Actor a) {		
		setMarkedForRemoval(true);
	}
}
