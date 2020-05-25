package actors.projectiles;

import actors.Actor;
import actors.enemies.Invader;
import game.Stage;

public class InvaderShot extends Shot {

	public InvaderShot(Stage stage) {
		super(stage);
		super.up = false;
		bulletSpeed = 1;
	}

	@Override
	public void collision(Actor a) {
		if (a instanceof Invader)
			return;
		setMarkedForRemoval(true);
	}
	
}