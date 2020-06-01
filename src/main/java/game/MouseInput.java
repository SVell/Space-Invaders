package game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static game.Stage.WIDTH;

public class MouseInput implements MouseListener {


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }


    public void mousePressed(MouseEvent mouseEvent) {

        int mouseX = mouseEvent.getX();
        int mouseY = mouseEvent.getY();


        // private Rectangle exitButton = new Rectangle(WIDTH/2 - 150, 500, 300, 150);

       // listener for playButton
       if(mouseX >= WIDTH/2 -150 && mouseX < WIDTH/2 + 150){
           if(mouseY >= 200 && mouseY <= 350){

               Invaders.State = Invaders.STATE.GAME;
           }
       }

       if(mouseX >= WIDTH/2 - 150 && mouseX < WIDTH/2 + 150){
           if(mouseY >= 500 && mouseY <= 650){
                System.exit(0);
           }
       }


    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
