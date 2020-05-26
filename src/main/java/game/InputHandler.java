package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class InputHandler implements KeyListener{

	InputHandler(){
		bind(KeyEvent.VK_UP, Key.up);
		bind(KeyEvent.VK_LEFT, Key.left);
		bind(KeyEvent.VK_DOWN, Key.down);
		bind(KeyEvent.VK_RIGHT, Key.right);
		bind(KeyEvent.VK_SPACE, Key.space);
		bind(KeyEvent.VK_ENTER, Key.enter);
	}

	private HashMap<Integer, Key> keyBindings = new HashMap<>();

	private void bind(Integer keyCode, Key key){
		keyBindings.put(keyCode, key);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(keyBindings.get(e.getKeyCode()) != null)
			keyBindings.get(e.getKeyCode()).isDown = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(keyBindings.get(e.getKeyCode()) != null)
			keyBindings.get(e.getKeyCode()).isDown = false;
	}
}