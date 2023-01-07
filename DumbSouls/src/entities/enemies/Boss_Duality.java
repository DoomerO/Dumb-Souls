package entities.enemies;

import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.World;
import entities.*;
import entities.shots.*;
import entities.AE.BAE_Spike;
import java.awt.Graphics;

public class Boss_Duality extends Enemy{
	
	private int frames, maxFrames = 40, index, maxIndex = 3, timeAtk, shieldLife;
	private boolean balance, shieldActive;
	private BufferedImage aura = Game.sheet.getSprite(16, 160, 16, 16);
	private BufferedImage shield = Game.sheet.getSprite(16, 176, 16, 16);
	private BufferedImage spriteAtk = Game.sheet.getSprite(32, 160, 16, 16);
	private BufferedImage spriteAtk2 = Game.sheet.getSprite(32, 176, 16, 16);
	
	public Boss_Duality(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.getAnimation(0, 192, 32, 32, 3);
		this.expValue = 1500;
		this.soulValue = 20;
		this.maxLife = 800;
		this.life = maxLife;
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
		this.maxLife =  800 * World.wave / 10;
		this.expValue = 1500 * World.wave / 10;
		this.soulValue = 20 * World.wave / 10; 
		this.life = this.maxLife;
		balance = true;
	}
	
	private void die() {
		Game.enemies.remove(this);
		Game.player.exp += this.expValue;
		Player.souls +=  this.soulValue;
		World.bossTime = false;
		World.bossName = "";
	}
	
	private void closeAtk() {
		if (Entity.calculateDistance(Game.player.getX(), Game.player.getY(), this.getX() + 16, this.getY() + 16) <= 60) {
			double ang = Math.atan2((Game.player.getY() - Camera.y) - (this.getY() - Camera.y) ,(Game.player.getX() - Camera.x) - (this.getX() - Camera.x));
			double dx = Math.cos(ang);
			double dy =  Math.sin(ang);

			Game.eShots.add(new Enemy_Shot(this.getX() + 3, this.getY() + 11, 6, 3, spriteAtk, dx, dy, 15, 2, 30, "straight"));
			Game.player.mana *= 0.75;
		}
	}
	
	private void shieldColision() {
		for (int i = 0;  i < Game.shots.size(); i++) {
			Shot e = Game.shots.get(i);
			if (isColiding(this, e)) {
				e.die();
			}
		}
		
		if (this.life <= shieldLife) {
			this.life = shieldLife;
		}
	}
	
	private void addShield() {
		if (!shieldActive) {
			shieldActive = true;
			shieldLife = (int)this.life;
			this.setMask(0, 0, 98, 64);
		}
		else {
			shieldActive = false;
			this.setMask(11, 6, 12, 20);
		}
	}
	
	private void rangeAtk() {
		timeAtk++;
		if (timeAtk % 40 == 0) {
			Game.entities.add(new BAE_Spike(Game.player.getX(), Game.player.getY(), 16, 16, null, 60, 60));
		}
		if (timeAtk % 60 == 0) {
			double ang = Math.atan2((Game.player.getY() - Camera.y) - (this.getY() - Camera.y) ,(Game.player.getX() - Camera.x) - (this.getX() - Camera.x));
			double dx = Math.cos(ang);
			double dy =  Math.sin(ang);

			Game.eShots.add(new Enemy_Shot(this.getX() + 3, this.getY() + 11, 6, 3, spriteAtk, dx, dy, 40, 5, 35, "straight"));
		}
		if (timeAtk % 80 == 0) {
			Game.eShots.add(new Enemy_Shot(this.getX() + 16, this.getY() + 11, 16, 5, spriteAtk2, 0, 0, 32, 3, 50, "focused"));
		}
		if (timeAtk % 160 == 0) {
			addShield();
			timeAtk = 0;
		}
	}
	
	public void tick() {
		if (!balance) {
			balanceStatus();
		}
		
		if (Entity.calculateDistance(Game.player.getX(), Game.player.getY(), this.getX(), this.getY()) > 40) {
			this.movement();
		}
		if (this.speed < 0.8) {
			this.speed = 0.8;
		}
		animate();
		closeAtk();
		rangeAtk();
		
		if (shieldActive) {
			shieldColision();
		}
		else {
			this.shotDamage();
		}
	
		if (this.life <= 0) {
			die();
		}
		
	}
	
	public void render(Graphics g) {
		g.drawImage(aura, this.getX() - Camera.x - 32, this.getY() - Camera.y - 32, 98, 98,null);
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		
		if (shieldActive) {
			g.drawImage(shield, this.getX() - Camera.x - 32, this.getY() - Camera.y - 48, 98, 80, null);
		}
	}
}
