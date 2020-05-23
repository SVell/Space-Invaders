package game;

import actors.Player;

import java.awt.event.KeyEvent;

/**
 * creates a thread to process player input
 * @author ghast
 *
 */
public class InputHandler extends Thread {

	private Invaders invaders = null;
	private Player player  = null;
	public Action action;
	public KeyEvent event;

	public InputHandler(Invaders invaders, Player player) {
		this.invaders = invaders;
		this.player = player;
	}

	@Override
	public void run() {
		if (action == Action.PRESS) {
			if (KeyEvent.VK_ENTER == event.getKeyCode()) {
				if (invaders.gameOver || invaders.gameWon) {
					invaders.initWorld();
					invaders.game();
				}
			}

			else
				player.keyPressed(event);
		}
		else if (action == Action.RELEASE)
			player.keyReleased(event);
	}

	public enum Action {
		PRESS,
		RELEASE
	}
}