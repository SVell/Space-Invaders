package actors.projectiles;

import actors.Actor;
import actors.Invader;
import game.Stage;

public class InvaderShot extends Shot {

	public InvaderShot(Stage stage) {
		super(stage);
		super.up = false;
		bulletSpeed = 3;
	}

	@Override
	public void collision(Actor a) {
		if (a instanceof Invader || a instanceof HpBuff)
			return;
		setMarkedForRemoval(true);
	}
	
}
