package actors;

import game.Stage;

public class Shot extends Actor {

	protected int bulletSpeed = 2;  
	protected boolean up = true;
	
	public Shot(Stage stage) {
		super(stage);
		width = 10;
		height = 15;
		sprites = new String[]{"Bullet.png","Bullet.png"};
	}

	public void act() {
		super.act();
		if (up)
			posY -= bulletSpeed;
		else
			posY += bulletSpeed;
		
		if (posY < 0)
			setMarkedForRemoval(true);
	}
	
	public void collision(Actor a) {
		if(!(a instanceof Player || a instanceof Shot)) {
			setMarkedForRemoval(true);
		}
	}
}
