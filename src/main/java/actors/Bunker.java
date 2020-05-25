package actors;

import game.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bunker extends Actor {
	
	private Color pixels[][] = new Color[40][20];
	
	public Bunker(Stage canvas) {
		super(canvas);
		initPixels();
	}

	private void initPixels() {		
		for (int x = 0; x < 40; x++) {
			for (int y = 0; y < 20; y++) {
				pixels[x][y] = Color.green; 
			}
		}		
	}

	private BufferedImage getSprite() {
		BufferedImage sprite = new BufferedImage(40, 20, BufferedImage.TYPE_INT_ARGB);
		Graphics g = sprite.getGraphics();
		for (int x = 0; x < 40; x++) {
			for (int y = 0; y < 20; y++) {
				g.setColor(pixels[x][y]);
				g.drawRect(x, y, x+1, y+1); 
			}
		}
		return sprite;
	}

	@Override
	public void paint(Graphics g) {		
		g.drawImage(getSprite(), getX(), getY(), stage);
	}

	@Override
	public void collision(Actor a) {		
	}
}
