package entities.enemies;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import main.Game;
import world.Camera;
import entities.*;
import entities.orbs.Rune_Orb;
import entities.shots.*;
import world.World;

public class Boss_Sucubus extends Enemy {
	private int frames, maxFrames = 40, index, maxIndex = 2, timeAtk ;
	private boolean balance, showAura;
	private BufferedImage spriteAtk = Game.sheet.getSprite(64, 160, 16, 16);
	private BufferedImage spriteAtk2 = Game.sheet.getSprite(96, 160, 16, 16);
	private BufferedImage aura = Game.sheet.getSprite(80, 160, 16, 16);
	
	public Boss_Sucubus(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.getAnimation(96, 192, 32, 32, 2);
		this.expValue = 1500;
		this.soulValue = 20;
		this.maxLife = 300;
		this.life = maxLife;
		this.maxSpeed = 0.6;
		this.speed = this.maxSpeed;
		this.setMask(2, 0, 30, 32);
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
		this.maxLife =  300 * World.wave / 10;
		this.expValue = 1500 * World.wave / 10;
		this.soulValue = 20 * World.wave / 10; 
		this.life = maxLife;
		balance = true;
	}
	
	private void die() {
		Game.enemies.remove(this);
		Game.player.exp += this.expValue;
		Player.souls +=  this.soulValue;
		World.bossTime = false;
		World.bossName = "";
		Game.enemies.add(new Rune_Orb(this.getX(), this.getY(), 16, 16));
	}
	
	private void attack1() {
		double ang = Math.atan2((Game.player.getY() - Camera.y) - (this.getY() - Camera.y) ,(Game.player.getX() - Camera.x) - (this.getX() - Camera.x));
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);

		Game.eShots.add(new Enemy_Shot(this.getX() + 6, this.getY() + 11, 6, 3, spriteAtk, dx, dy, 36, 5, 30, "straight"));
	}
	
	private void attack2() {
		int prob;
		int prob2;
		
		if (Game.rand.nextInt(2) == 1) {
			prob = -1;
		}
		else {
			prob = 1;
		}
		
		if (Game.rand.nextInt(2) == 1) {
			prob2 = -1;
		}
		else  {
			prob2 = 1;
		}
		
		int distance = 100 * prob, distance2 = 80 * prob2 ;
		
		double ang = Math.atan2((Game.player.getY() - Camera.y) - (Game.player.getY() - Camera.y + distance2) ,(Game.player.getX() - Camera.x) - (Game.player.getX() - Camera.x  + distance));
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);
		
		Game.eShots.add(new Enemy_Shot(Game.player.getX() + distance, Game.player.getY() + distance2, 6, 3, spriteAtk2, dx, dy, 10, 7, 50, "straight"));
	}
	
	private void renderAura() {
		if (timeAtk > 280) {
			showAura = true;
		}
		else {
			showAura = false;
		}
	}
	
	private void cure() {
		if (timeAtk % 200 == 0 && this.life < ((this.maxLife / 100) * 20)) {
			this.life += (this.maxLife / 100) * 8;
		}
	}
	
	private void tp() {
		if (timeAtk % 300 == 0) {
			int prop;
			
			if (Game.rand.nextInt(2) == 1) {
				prop = -1;
			}
			else {
				prop = 1;
			}
			
			this.x = Game.player.getX() + (48 * prop);
			this.y = Game.player.getY();
			timeAtk = 0;
		}
	}
	
	public void tick() {
		if (!balance) {
			balanceStatus();
		}
		timeAtk++;
		tp();
		renderAura();
		cure();
		if (Entity.calculateDistance(Game.player.getX(), Game.player.getY(), this.getX(), this.getY()) <= 140) {
			if (timeAtk % 20 == 0) {
				attack1();
			}
			if (timeAtk % 100 == 0) {
				attack2();
			}
		}		
		
		if (Entity.calculateDistance(Game.player.getX(), Game.player.getY(), this.getX(), this.getY()) > 120) {
			this.movement();
		}
		else if (Entity.calculateDistance(Game.player.getX(), Game.player.getY(), this.getX(), this.getY()) < 80) {
			this.reverseMovement();
		}
		animate();
		this.shotDamage();
		if (this.life <= 0) {
			die();
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		if (showAura) {
			g.drawImage(aura, this.getX() - Camera.x,  this.getY() - Camera.y, 32, 32, null);
		}
	}
}
