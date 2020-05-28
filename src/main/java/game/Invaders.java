package game;

import actors.Actor;
import actors.Invader;
import actors.Player;
import actors.enemies.Ufo;
import actors.projectiles.HpBuff;
import actors.projectiles.ShootBuff;
import actors.projectiles.Shot;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.TargetDataLine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Invaders extends Stage{

	private static final long serialVersionUID = 1L;
	private JFrame gameFrame;
	private Player player;

	private InputHandler keyPressedHandler;
	private InputHandler keyReleasedHandler;
	private final int SPAWN_CHANCE = 998;
	private final int SPAWN_HEAL_CHANCE = 999;

	private long usedTime;//time taken per game step
	private BufferStrategy strategy;	 //double buffering strategy

	private BufferedImage background, backgroundTile; //background cache
	private int backgroundY; //background cache position

	private Invaders() {
		//init the UI
		setBounds(0,0,Stage.WIDTH,Stage.HEIGHT);
		setBackground(Color.BLACK);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(Stage.WIDTH,Stage.HEIGHT));
		panel.setLayout(null);

		panel.add(this);

		gameFrame = new JFrame("Invaders");
		gameFrame.add(panel);

		gameFrame.setBounds(0,0,Stage.WIDTH,Stage.HEIGHT);
		gameFrame.setResizable(false);
		gameFrame.setVisible(true);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//cleanup resources on exit
		gameFrame.addWindowListener( new WindowAdapter() {
			          public void windowClosing(WindowEvent e) {
			        	ResourceLoader.getInstance().cleanup();
			            System.exit(0);
			          }
			        });


		addKeyListener(new InputHandler());

		//create a double buffer
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		requestFocus();
		initWorld();
	}

	/**
	 * add a grid of invaders based on the screen size
	 */

	private void addInvaders() {

	/*public void addInvaders() {
		Invader invader = new Invader(this);
		//padding between units/rows
		int xPad = invader.getWidth() + 15;
		int yPad = invader.getHeight() + 20;
		//number of units per row
		int unitsPerRow = Stage.WIDTH/(xPad) - 1;
		int rows = (Stage.HEIGHT/yPad) - 3; //number of invader rows

		//create and add invaders for each row
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < unitsPerRow -1; j++) {
				Invader inv = new Invader(this);
				inv.setX((j + 1)*xPad);
				inv.setY((i + 1)*yPad);
				inv.setVx(10);
				//set movement boundaries for each invader
				inv.setLeftWall((j + 1)*xPad - 20);
				inv.setRightWall((j + 1)*xPad + 20);
				actors.add(inv);
			}
		}*/
	}


	private void initWorld() {
		gameOver = false;
		gameWon = false;
		actors = new ArrayList<>();
		//add a player
		player = new Player(this);
		player.setX(Stage.WIDTH / 2 - player.getWidth() / 2);
		player.setY(Stage.HEIGHT - 80);
		player.setVx(10);

		//load cached background
		backgroundTile = ResourceLoader.getInstance().getSprite("bgNew.png");
		background = ResourceLoader.createCompatible(
				WIDTH, HEIGHT + backgroundTile.getHeight(),
				Transparency.OPAQUE);
		Graphics2D g = (Graphics2D) background.getGraphics();
		g.setPaint(new TexturePaint(backgroundTile, new Rectangle(0, 0, backgroundTile.getWidth(), backgroundTile.getHeight())));
		g.fillRect(0, 0, background.getWidth(), background.getHeight());
		backgroundY = backgroundTile.getHeight();


		addInvaders();


		/*keyPressedHandler = new InputHandler(this, player);
		keyPressedHandler.action = InputHandler.Action.PRESS;
		keyReleasedHandler = new InputHandler(this, player);
		keyReleasedHandler.action = InputHandler.Action.RELEASE;*/
		//addInvaders();
	}

	private void resetGame(){
		initWorld();
		game();
	}

	private void paintWorld() {

		//get the graphics from the buffer
		Graphics g = strategy.getDrawGraphics();
		//init image to background
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		//load subimage from the background
		g.drawImage( background,0,0,Stage.WIDTH,Stage.HEIGHT,0,backgroundY,Stage.WIDTH,backgroundY+Stage.HEIGHT,this);

		//paint the actors (Enemies)
		for (int i = 0; i < actors.size(); i++) {
			Actor actor = actors.get(i);
			actor.paint(g);
		}

		player.paint(g);
		paintScore(g);
		paintLives(g);
		//swap buffer
		strategy.show();
	}

	private void paintGameOver() {
		Graphics g = strategy.getDrawGraphics();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());

		//about 310 pixels wide
		g.setFont(new Font("Arial",Font.BOLD,50));
		g.setColor(Color.RED);
		int xPos = getWidth()/2 - 155;
		g.drawString("GAME OVER",(xPos < 0 ? 0 : xPos),getHeight()/2 -200);
		g.setColor(Color.yellow);
		g.drawString("YOUR SCORE: " + player.getScore(),(getWidth()/2- 200),getHeight()/2);
		g.setColor(Color.red);
		xPos += 30;
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString("ENTER: try again",(xPos < 0 ? 0 : xPos),getHeight() - 100);

		strategy.show();
		waitForInput();
	}

	private void paintGameWon() {
		Graphics g = strategy.getDrawGraphics();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());

		paintScore(g);

		//about 300 pixels wide
		g.setFont(new Font("Arial",Font.BOLD,50));
		g.setColor(Color.RED);
		int xPos = getWidth()/2 - 145;
		g.drawString("GAME WON",(xPos < 0 ? 0 : xPos),getHeight()/2);

		xPos += 20;
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString("ENTER: try again",(xPos < 0 ? 0 : xPos),getHeight()/2 + 50);

		strategy.show();
		waitForInput();
	}

	private void waitForInput(){
		while (isShowing())
			if(Key.enter.isDown && (gameOver || gameWon))
				resetGame();
	}


	private void paintScore(Graphics g) {
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.setColor(Color.green);
		g.drawString("Score: ",20,20);
		g.setColor(Color.red);
		g.drawString("" + player.getScore(), 100, 20);

		if(player.getScore() <= 100){
			player.level = 1;
			g.setColor(Color.green);
			g.drawString("Level: ",20,40);
			g.setColor(Color.red);
			g.drawString("1", 100, 40);
		}
		else if(player.getScore() <= 200){
			player.level = 2;
			g.setColor(Color.green);
			g.drawString("Level: ",20,40);
			g.setColor(Color.red);
			g.drawString("2", 100, 40);
		}
		else if(player.getScore() <= 300){
			player.level = 3;
			g.setColor(Color.green);
			g.drawString("Level: ",20,40);
			g.setColor(Color.red);
			g.drawString("3", 100, 40);
		}
		else if(player.getScore() <= 400){
			player.level = 4;
			g.setColor(Color.green);
			g.drawString("Level: ",20,40);
			g.setColor(Color.red);
			g.drawString("4", 100, 40);
		}
		else if(player.getScore() >= 400){
			player.level = 5;
			g.setColor(Color.green);
			g.drawString("Level: ",20,40);
			g.setColor(Color.red);
			g.drawString("5", 100, 40);
		}
	}

	private void paintLives(Graphics g){
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.setColor(Color.green);
		g.drawString("Lives: ",gameFrame.getWidth()-120,20);
		g.setColor(Color.red);
		g.drawString("" + player.getLives(), gameFrame.getWidth()-50, 20);
	}

	public void paint(Graphics g) {}

	private void updateWorld() {

	    int i = 0;
		int numInvaders = 1;
		while (i < actors.size()) {
			Actor actor = actors.get(i);
			if (actor instanceof Shot)
				checkCollision(actor);

			if (actor.isMarkedForRemoval() && actor.isGotShot()) {
				player.updateScore(actor.getPointValue());
				actors.remove(i);
			} else if(actor.isMarkedForRemoval() && actor instanceof Invader){
				player.decLives();
				actors.remove(i);
			} else if(actor.isMarkedForRemoval()){
				actors.remove(i);
			}
			else {
				//check how many invaders are remaining
				//0 means player won the match
				if(actor instanceof Invader)
					numInvaders++;
				actor.act();
				i++;
			}
		}
		if (numInvaders == 0)
			super.gameWon = true;

		checkCollision(player);
		player.act();
	}

	private void checkCollision(Actor actor) {

		Rectangle actorBounds = actor.getBounds();
		for (Actor otherActor : actors) {
			if (null == otherActor || actor.equals(otherActor)) continue;
			if (actorBounds.intersects(otherActor.getBounds())) {
				actor.collision(otherActor);
				otherActor.collision(actor);
			}
		}
	}

	private void loopSound(final String name) {
		new Thread(new Runnable() {
			public void run() {
				ResourceLoader.getInstance().getSound(name).loop();
			}
		}).start();
	}

	public void game() {
		loopSound("kick.wav");
		usedTime= 0;
		while(isVisible()) {
			long startTime = System.currentTimeMillis();

			backgroundY--;
			if (backgroundY < 0)
				backgroundY = backgroundTile.getHeight();

			if (super.gameOver) {
				paintGameOver();
				break;
			}
			else if (super.gameWon) {
				paintGameWon();
				break;
			}

			int random = (int)(Math.random()*1000);
			if (random >= SPAWN_CHANCE - player.level) {

				Actor invader = new Invader(this);
				int Min = 10;
				int Max = Stage.WIDTH - invader.getWidth() - 10;
				//int Max = gameFrame.getX() - invader.getWidth() - 10;
				int xPosition = Min + (int)(Math.random() * ((Max - Min) + 1));
				invader.setX(xPosition);
				invader.setY(-40);
				invader.setVx(0);
				invader.setVy(1);
				actors.add(invader);
			}
			// Spawn HealKit
			if (random >= SPAWN_HEAL_CHANCE && player.getLives() <= 3) {

				Actor medKit = new HpBuff(this);
				int Min = 10;
				//int Max = gameFrame.getX() - medKit.getWidth() - 10;
				int Max = Stage.WIDTH - medKit.getWidth() - 10;
				int xPosition = Min + (int)(Math.random() * ((Max - Min) + 1));
				medKit.setX(xPosition);
				medKit.setY(-40);
				medKit.setVx(0);
				medKit.setVy(1);
				actors.add(medKit);
			}
			else if(random == 1) {

				Actor bullets = new ShootBuff(this);
				int Min = 10;
				//int Max = gameFrame.getX() - medKit.getWidth() - 10;
				int Max = Stage.WIDTH - bullets.getWidth() - 10;
				int xPosition = Min + (int)(Math.random() * ((Max - Min) + 1));
				bullets.setX(xPosition);
				bullets.setY(-40);
				bullets.setVx(0);
				bullets.setVy(1);
				actors.add(bullets);
			}
			updateWorld();
			player.updateControls();
			paintWorld();

			usedTime = System.currentTimeMillis() - startTime;
			//calculate sleep time
			if (usedTime == 0) usedTime = 1;
			int timeDiff = (int) ((1000/usedTime) - DESIRED_FPS);
			if (timeDiff > 0) {
				try {
					Thread.sleep(timeDiff/100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		Invaders inv = new Invaders();
		inv.game();
	}
}

