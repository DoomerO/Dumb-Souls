package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import entities.Entity;
import entities.Player;
import entities.enemies.*;
import entities.shots.*;
import graphics.Spritesheet;
import graphics.UI;
import sounds.SoundPlayer;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private boolean isRuning = false;
	
	public static JFrame frame;
	public static final int width = 320;
	public static final int height = 160;
	public static final int scale = 3;
	public static int mx;
	public static int my;
	
	private BufferedImage image;
	public static Spritesheet sheet;
	
	public static String gameState = "MENUINIT";
	public static UI ui;
	public static World world;
	public static Player player;
	public static Random rand;
	private static SoundPlayer soundTrack;
	public static Menu_Level levelUpMenu;
	public static Menu_Player playerMenu;
	public static Menu_Init startMenu;
	public static Menu_Help helpMenu;
	public static Menu_Pause pauseMenu;
	public static Menu_Runes runesMenu;
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<Shot> shots;
	public static List<Enemy_Shot> eShots;

	public Game() {
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(width * scale, height * scale));
		initFrame();
		entities = new ArrayList<Entity>();
		shots = new ArrayList<Shot>();
		enemies = new ArrayList<Enemy>();
		eShots = new ArrayList<Enemy_Shot>();
		rand = new Random();
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		sheet = new Spritesheet("/spritesheet.png");
		player = new Player(0, 0, 16, 16, sheet.getSprite(0, 16, 16, 16));
		entities.add(player);
		world = new World("/map00.png");
		ui = new UI();
		soundTrack = new SoundPlayer("Gurenge.wav");
		startMenu = new Menu_Init();
		playerMenu = new Menu_Player();
		pauseMenu = new Menu_Pause();
		levelUpMenu = new Menu_Level(3);
		helpMenu = new Menu_Help();
		runesMenu = new Menu_Runes();
		Save_Game.loadSave();
	}
	
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
		isRuning = true;
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
		soundTrack.loop();
	}
	public void end() {
		isRuning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void initFrame() {
		frame = new JFrame("Dumb Souls");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void spawnEnemies() {
		if (enemies.size() == 0) {
			if (World.wave % 10 != 0) {
				world.rizeMaxEnemies();
				World.wave++;
				for (int c = 0; c <= World.maxEnemies; c++) {
					world.spawnEnemy();
				}
			}
			else {
				world.spawnBoss();
				World.wave++;
			}
		}
	}
	
	public void tick() {
		switch(gameState){
		case "NORMAL":
			for(int i = 0; i < entities.size(); i++) {
				entities.get(i).tick();
			}
			for(int i = 0; i < shots.size(); i++) {
				shots.get(i).tick();
			}
			for(int i = 0; i < enemies.size(); i++) {
				enemies.get(i).tick();
			}
			for(int i = 0; i < eShots.size(); i++) {
				eShots.get(i).tick();
			}
			
			spawnEnemies();
			break;
		case "LEVELUP":
			levelUpMenu.tick();
			break;
		case "MENUINIT":
			startMenu.tick();
			break;
		case "MENUPLAYER":
			playerMenu.tick();
			break;
		case "MENUHELP":
			helpMenu.tick();
			break;
		case "MENUPAUSE":
			pauseMenu.tick();
			break;
		case "MENURUNES":
			runesMenu.tick();
			break;
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		switch(gameState){
		case "NORMAL":
			g.setColor(new Color(0, 0, 0));
			g.fillRect(0, 0, width, height);
			world.render(g);
			Collections.sort(entities, Entity.entityDepth);
			Collections.sort(enemies, Entity.entityDepth);
			for(int i = 0; i < entities.size(); i++) {
				entities.get(i).render(g);
			}
			for(int i = 0; i < enemies.size(); i++) {
				enemies.get(i).render(g);
			}
			for(int i = 0; i < shots.size(); i++) {
				shots.get(i).render(g);
			}
			for(int i = 0; i < eShots.size(); i++) {
				eShots.get(i).render(g);
			}
			g.dispose();
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, width * scale, height * scale, null);
			ui.render(g);
			break;
		case "LEVELUP":
			levelUpMenu.render(g);
			g.dispose();
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, width * scale, height * scale, null);
			break;
		case "MENUINIT":
			startMenu.render(g);
			g.dispose();
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, width * scale, height * scale, null);
			break;
		case "MENUPLAYER":
			playerMenu.render(g);
			g.dispose();
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, width * scale, height * scale, null);
			break;
		case "MENUHELP":
			helpMenu.render(g);
			g.dispose();
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, width * scale, height * scale, null);
			break;
		case "MENUPAUSE":
			pauseMenu.render(g);
			g.dispose();
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, width * scale, height * scale, null);
			break;
		case "MENURUNES":
			runesMenu.render(g);
			g.dispose();
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, width * scale, height * scale, null);
			break;
		}
		bs.show();
	}
	
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double amountTicks = 60.0;
		double ns = 1000000000 / amountTicks;
		double delta = 0;
		
		while(isRuning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				delta --;
			}
			render();
		}
		end();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			switch(gameState){
				case "NORMAL":
					player.up = true;
					player.moving = true;
					break;
				case "LEVELUP":
					levelUpMenu.up = true;
					break;
				case "MENUINIT":
					startMenu.up = true;
					break;
				case "MENUPLAYER":
					playerMenu.up = true;
					break;
				case "MENUPAUSE":
					pauseMenu.up = true;
					break;
				case "MENURUNES":
					runesMenu.up = true;
					break;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			switch(gameState){
				case "NORMAL":
					player.down = true;
					player.moving = true;
					break;
				case "LEVELUP":
					levelUpMenu.down = true;
					break;
				case "MENUINIT":
					startMenu.down = true;
					break;
				case "MENUPLAYER":
					playerMenu.down = true;
					break;
				case "MENUPAUSE":
					pauseMenu.down = true;
					break;
				case "MENURUNES":
					runesMenu.down = true;
					break;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			switch(gameState){
				case "NORMAL":
					player.left = true;
					player.moving = true;
					break;
				case "MENUPLAYER":
					playerMenu.left = true;
					break;
				case "MENURUNES":
					runesMenu.left = true;
					break;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			switch(gameState){
				case "NORMAL":
					player.right = true;
					player.moving = true;
					break;
				case "MENUPLAYER":
					playerMenu.right = true;
					break;
				case "MENURUNES":
					runesMenu.right = true;
					break;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			switch(gameState){
				case "LEVELUP":
					levelUpMenu.enter = true;
					break;
				case "MENUINIT":
					startMenu.enter = true;
					break;
				case "MENUPLAYER":
					playerMenu.enter = true;
					break;
				case "MENUHELP":
					helpMenu.enter = true;
					break;
				case "MENUPAUSE":
					pauseMenu.enter = true;
					break;
				case "MENURUNES":
					runesMenu.enter = true;
					break;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			switch(gameState) {
				case "NORMAL":
					gameState = "MENUPAUSE";
					break;
				case "MENUPAUSE":
					player.stopMoving();
					gameState = "NORMAL";
					break;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			switch(gameState){
				case "NORMAL":
					player.dash = true;
					break;
				case "LEVELUP":
					levelUpMenu.space = true;
					break;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_1) {
			if (gameState == "NORMAL") {
				player.ablt2 = true;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_2) {
			if (gameState == "NORMAL") {
				player.ablt3 = true;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			switch(gameState){
				case "NORMAL":
					player.up = false;
					break;
				case "LEVELUP":
					levelUpMenu.up = false;
					break;
				case "MENUINIT":
					startMenu.up = false;
					break;
				case "MENUPLAYER":
					playerMenu.up = false;
					break;
				case "MENURUNES":
					runesMenu.up = false;
					break;
		}
		}
		else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			switch(gameState){
				case "NORMAL":
					player.down = false;
					break;
				case "LEVELUP":
					levelUpMenu.down = false;
					break;
				case "MENUINIT":
					startMenu.down = false;
					break;
				case "MENUPLAYER":
					playerMenu.down = false;
					break;
				case "MENURUNES":
					runesMenu.down = false;
					break;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			switch(gameState){
				case "NORMAL":
					player.left = false;
					break;
				case "MENUPLAYER":
					playerMenu.left = false;
					break;
				case "MENURUNES":
					runesMenu.left = false;
					break;
				}
		}
		else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			switch(gameState){
				case "NORMAL":
					player.right = false;
					break;
				case "MENUPLAYER":
					playerMenu.right = false;
					break;
				case "MENURUNES":
					runesMenu.right = false;
					break;
				}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			switch(gameState){
			case "LEVELUP":
				levelUpMenu.enter = false;
				break;
			case "MENUINIT":
				startMenu.enter = false;
				break;
			case "MENUPLAYER":
				playerMenu.enter = false;
				break;
			case "MENUHELP":
				helpMenu.enter = false;
				break;
			case "MENURUNES":
				runesMenu.up = false;
				break;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.attack = true;
		player.playerWeapon.mx = e.getX() / scale;
		player.playerWeapon.my = e.getY() / scale;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		player.attack = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}
}