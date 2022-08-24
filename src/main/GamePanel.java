package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	// FPS
	int FPS = 60;
	
	// System 
	TileManager tileM= new TileManager(this); // this = GamePanel
	KeyHandler keyH = new KeyHandler();
	Sound music = new Sound();
	Sound  se = new Sound();  // sound effect
	public collisionChecker cChecker = new collisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	
	// Entity and Object
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10]; // display 10 objects at the same time(0~9)
	
	// Game State
	public int gameState;
	
	
	// Set Player's default position 
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		
		aSetter.setObject();
		
		playMusic(0);
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; //
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;  // (currentTime - lastTime)はpassed time, drawIntervalは1
			
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();  //1 UPDATE: update information such as character positiuon
				repaint();  //2 DRAW: draw the screen with the updated information
				delta--;
				drawCount++;
			}
		}
	}
	
	public void update() {
		
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// Debug
		long drawStart = 0;
		drawStart = System.nanoTime();
		
		
		// Tile
		tileM.draw(g2); // first cause player is represented on the tile image.
		
		// Object
		for(int i = 0; i < obj.length; i++) {  // Check if the slot is not empty to avoid NullPointer error
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		// Player
		player.draw(g2); // second
		
		// UI
		ui.draw(g2);
		
		
		// Debug
		if(keyH.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10, 400);
			System.out.println("Draw Time: " + passed);
		}
		
		g2.dispose();
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	
	public void playSE(int i) {  // SE = Sound Effect
		
		se.setFile(i);
		se.play();
	}
}













