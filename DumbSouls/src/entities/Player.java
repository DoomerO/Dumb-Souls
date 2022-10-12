package entities;

import java.awt.image.BufferedImage;

import graphics.UI;

import java.awt.Graphics;

import main.*;
import world.*;

public class Player extends Entity{
	
	private int tickTimer, attackTimer;
	public boolean up, down, left, right, moving, attack, levelUp, dash, ablt2, ablt3;
	public int maxLife = 100, exp = 0, maxExp = 100, maxMana = 100, souls = 0;
	public int level = 1;
	private int index, maxIndex = 4, frames, maxFrames = 10;
	public int direct = 2;
	public double maxSpeed = 1.5, speed = maxSpeed, mana = 100, manaRec = 2, life = 100, lifeRec=1.001;
	public Weapon playerWeapon;
	
	private BufferedImage[] playerDown;
	private BufferedImage[] playerRight;
	private BufferedImage[] playerLeft;
	private BufferedImage[] playerUp;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		playerDown = new BufferedImage[4];
		playerRight = new BufferedImage[4];
		playerLeft = new BufferedImage[4];
		playerUp = new BufferedImage[4];
		
		for (int xsp = 0; xsp < 4; xsp++) {
			playerDown[xsp] = Game.sheet.getSprite(xsp * 16, 16, 16, 16);
		}
		for (int xsp = 0; xsp < 4; xsp++) {
			playerRight[xsp] = Game.sheet.getSprite(xsp * 16, 16 * 2, 16, 16);
		}
		for (int xsp = 0; xsp < 4; xsp++) {
			playerLeft[xsp] = Game.sheet.getSprite(xsp * 16, 16 * 3, 16, 16);
		}
		for (int xsp = 0; xsp < 4; xsp++) {
			playerUp[xsp] = Game.sheet.getSprite(xsp * 16, 16 * 4, 16, 16);
		}
		
		setMask(4, 1, 8, 15);
		this.depth = 1;
	}
	
	private void isAttacking() {
		if (attackTimer == playerWeapon.attackTimer) {
			if (attack) {
			attack = false;
			attackTimer = 0;
			playerWeapon.Attack();
			}
		}
		if (attackTimer < playerWeapon.attackTimer){
			attackTimer++;
		}
	}

	public void stopMoving(){
		Game.player.up = false;
		Game.player.right = false;
		Game.player.down = false;
		Game.player.left = false;
	}
	
	private void dashing() {
		if (dash) {
			playerWeapon.Dash();
		}
	}
	
	private void ablt2Using() {
		if (ablt2) {
			playerWeapon.Ablt2();
		}
	}
	
	private void ablt3Using() {
		if (ablt3) {
			playerWeapon.Ablt3();
		}
	}
	
	public static void die() {
		Game.entities.clear();
		Game.shots.clear();
		Game.enemies.clear();
		Game.eShots.clear();
		Game.player = new Player(0, 0, 16, 16, Game.sheet.getSprite(0, 16, 16, 16));
		Game.entities.add(Game.player);
		World.maxEnemies = 5;
		World.wave = 1;
		Game.world = new World("/map00.png");
		Game.ui = new UI();
		Game.startMenu = new Menu_Init();
		Game.playerMenu = new Menu_Player();
		Game.levelUpMenu = new Menu_Level(3);
		Game.gameState = "MENUINIT";
	}
	
	private void checkExp() {
		if (exp >= maxExp) {
			levelUp = true;
			level ++;
			exp -= maxExp;
			down = false;
			up = false;
			left = false;
			right = false;
			maxExp += (maxExp * 20) / 100;
			Game.gameState = "LEVELUP";
		}
	}
	
	private void isMoving() {
		if (moving) {
			frames++;
			if (frames == maxFrames) {
				index ++;
				frames = 0;
				if (index == maxIndex) {
					index = 0;
				}
			}
		}
		
		if (up == false && down == false && right == false && left == false) {
			moving = false;
		}
	}
	
	private void shotDamage() {
		for (int i = 0;  i < Game.eShots.size(); i++) {
			Enemy_Shot e = Game.eShots.get(i);
			if (isColiding(this, e)) {
				life -= e.damage;
				Game.eShots.remove(e);
			}
		}
	}

	private boolean TickTimer(int frames) {
		if (this.tickTimer % frames == 0) {
			return true;
		}
		else{
			return false;
		}
	}

	private void refreshTick(){
		this.tickTimer++;
		if (this.tickTimer >= 60){
			this.tickTimer = 0;
		}
	}
	
	public void tick() {
		if (up) {
			this.y -= speed;
			direct = 3;
		}else if(down) {
			this.y += speed;
			direct = 2;
		}
		
		if (right) {
			this.x += speed;
			direct = 0;
		} else if (left) {
			this.x -= speed;
			direct = 1;
		}
		
		if (mana < maxMana && TickTimer(20)) {
			mana += manaRec;
			if (mana > maxMana){
				mana = maxMana;
			}
		}

		if (Game.player.life < Game.player.maxLife && TickTimer(10)) {

			Game.player.life *= Game.player.lifeRec;
			if (Game.player.life > Game.player.maxLife){
				Game.player.life = Game.player.maxLife;
			}
		}

		refreshTick();

		if (life <= 0) {
			die();
		}
		
		playerWeapon.tick();
		playerWeapon.Effect();
		isAttacking();
		isMoving();
		checkExp();
		dashing();
		ablt2Using();
		ablt3Using();
		if (!playerWeapon.md){
			dash = false;
			ablt2 = false;
			ablt3 = false;
		}
		shotDamage();
		
		Camera.x = Camera.Clamp(this.getX() - (Game.width / 2), 0, World.WIDTH * 16 - Game.width);
		Camera.y = Camera.Clamp(this.getY() - (Game.height / 2), 0, World.HEIGHT * 16 - Game.height);
	}
	
	public void render(Graphics g) {
		if (right) {
			g.drawImage(playerRight[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if (left) {
			g.drawImage(playerLeft[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if (down) {
			g.drawImage(playerDown[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if (up) {
			g.drawImage(playerUp[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else {
			if (direct == 0) {
				g.drawImage(playerRight[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			else if (direct == 1) {
				g.drawImage(playerLeft[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			else if (direct == 2) {
				g.drawImage(playerDown[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			else if (direct == 3) {
				g.drawImage(playerUp[0], this.getX() - Camera.x, this.getY() - Camera.y, null);			
			}
		}
		
		playerWeapon.render(g);
	}
}
