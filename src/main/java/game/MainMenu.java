package game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static game.Stage.WIDTH;
import static game.Stage.HEIGHT;

public class MainMenu  {

    private Rectangle playButton = new Rectangle(WIDTH/2 - 150 , 200, 300, 150);
    private Rectangle exitButton = new Rectangle(WIDTH/2 - 150, 500, 300, 150);




    public void render(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        Font myFont = new Font("Serif", Font.ITALIC, 48);
        g.setFont(myFont);
        g.setColor(Color.WHITE);
        g.drawString("SPACE INVADERS", (WIDTH/4) + 10, 100);

        g.setColor(Color.ORANGE);

        Font buttonFont = new Font("arial", Font.BOLD, 70);

        g.setFont(buttonFont);
        g.drawString("Play", playButton.x + 80, playButton.y + 90);
        g.drawString("Exit", exitButton.x + 80, exitButton.y + 90);

        g2d.draw(playButton);
        g2d.draw(exitButton);

    }

}
