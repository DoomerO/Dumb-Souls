package entities;

import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.World;

import java.awt.Color;
import java.awt.Graphics;

public class Boss_Duality extends Enemy{
	
	private int frames, maxFrames = 40, index, maxIndex = 3, timeAtk ;
	private int maxlife = 800;
	private boolean balance;
	private BufferedImage aura = Game.sheet.getSprite(16, 160, 16, 16);
	private BufferedImage spriteAtk = Game.sheet.getSprite(32, 160, 16, 16);
	
	public Boss_Duality(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.getAnimation(0, 192, 32, 32, 3);
		this.expValue = 1500;
		this.soulValue = 20;
		this.life = maxlife;
		this.speed = 0.8;
		this.setMask(11, 6, 12, 20);
	}
	
	private void animate() {
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index == maxIndex) {
				index = 0;
			}
		}
	}
	
	private void balanceStatus() {
		this.maxlife =  (800 * World.wave) / 10;
		this.expValue = (1500 * World.wave) / 10;
		this.soulValue = (20 * World.wave) / 10; 
		this.life = maxlife;
		balance = true;
	}
	
	private void die() {
		Game.enemies.remove(this);
		Game.player.exp +=  this.expValue;
		Game.player.souls +=  this.soulValue;
	}
	private void closeAtk() {
		if (Entity.calculateDistance(Game.player.getX(), Game.player.getY(), this.getX() + 16, this.getY() + 16) <= 60) {
			double ang = Math.atan2((Game.player.getY() - Camera.y) - (this.getY() - Camera.y) ,(Game.player.getX() - Camera.x) - (this.getX() - Camera.x));
			double dx = Math.cos(ang);
			double dy =  Math.sin(ang);

			Game.eShoots.add(new Enemy_Shoot(this.getX() + 3, this.getY() + 11, 6, 3, spriteAtk, dx, dy, 5, 2, 30));
			Game.player.mana -= 1;
		}
	}
	
	private void rangeAtk() {
		timeAtk++;
		if (timeAtk % 40 == 0) {
			Game.entities.add(new BAE_Spike(Game.player.getX(), Game.player.getY(), 16, 16, null, 60, 5));
		}
		if (timeAtk % 60 == 0) {
			double ang = Math.atan2((Game.player.getY() - Camera.y) - (this.getY() - Camera.y) ,(Game.player.getX() - Camera.x) - (this.getX() - Camera.x));
			double dx = Math.cos(ang);
			double dy =  Math.sin(ang);

			Game.eShoots.add(new Enemy_Shoot(this.getX() + 3, this.getY() + 11, 6, 3, spriteAtk, dx, dy, 5, 5, 35));
		}
		if (timeAtk % 80 == 0) {
			Game.enemies.add(new Base_Enemy(this.getX() - 25, this.getY(), 16, 16, null));
		}
		if (timeAtk % 160 == 0) {
			Game.enemies.add(new Eye_Enemy(this.getX() + 25, this.getY(), 16, 16, null));
			timeAtk = 0;
		}
	}
	
	public void tick() {
		if (!balance) {
			balanceStatus();
		}
		
		
		if (Entity.calculateDistance(Game.player.getX(), Game.player.getY(), this.getX(), this.getY()) > 40) {
			if (Game.player.getX() > this.getX()) {
				x += speed;
			}
			else if (Game.player.getX() < this.getX()) {
				x -= speed;
			}
			
			if (Game.player.getY() > this.getY()) {
				y += speed;
			}
			else if (Game.player.getY() < this.getY()) {
				y -= speed;
			}
		}
		if (this.speed < 0.8) {
			this.speed = 0.8;
		}
		animate();
		closeAtk();
		rangeAtk();
		
		this.shootDamage();
		if (this.life <= 0) {
			die();
		}
		
	}
	
	public void render(Graphics g) {
		g.drawImage(aura, this.getX() - Camera.x - 32, this.getY() - Camera.y - 32, 98, 98,null);
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		
		g.setColor(new Color(30, 30, 30));
		g.fillRect((this.getX() + 1) - Camera.x, (this.getY() - 6) - Camera.y, 32, 4);
		
		g.setColor(Color.black);
		g.fillRect((this.getX() + 2) - Camera.x, (this.getY() - 5) - Camera.y, 30, 2);
		
		g.setColor(new Color(125, 23, 145));
		g.fillRect((this.getX() + 2) - Camera.x, (this.getY() - 5) - Camera.y, (int)((this.life * 30) / maxlife), 2);
	}
	
}
