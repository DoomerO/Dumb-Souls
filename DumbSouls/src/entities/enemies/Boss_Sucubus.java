package entities.enemies;

import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import entities.*;
import entities.orbs.Rune_Orb;
import entities.shots.*;
import world.World;

public class Boss_Sucubus extends Enemy {
	private boolean balance, showAura;
	private BufferedImage spriteAtk = Game.sheet.getSprite(64, 160, 16, 16);
	private BufferedImage aura = Game.sheet.getSprite(80, 160, 16, 16);
	
	public Boss_Sucubus(int x, int y) {
		super(x, y, 32, 32, Game.sheet.getSprite(105, 192, 12, 10));
		getAnimation(96, 192, 32, 32, 2);
		expValue = 1500;
		soulValue = 20;
		maxLife = 300;
		life = maxLife;
		damage = 30;
		maxSpeed = 0.6;
		speed = maxSpeed;
		maxIndex = 2;
		maxFrames = 40;
		setMask(2, 0, 30, 32);
	}
	
	private void balanceStats() {
		maxLife =  300 * World.wave / 10;
		expValue = 1500 * World.wave / 10;
		soulValue = 20 * World.wave / 10; 
		life = maxLife;
		balance = true;
	}
	
	private void die() {
		Game.enemies.remove(this);
		Game.player.exp += expValue;
		Player.souls +=  soulValue;
		World.bossTime = false;
		World.bossName = "";
		Game.enemies.add(new Rune_Orb(centerX(), centerY(), 16, 16));
	}
	
	private void attack1() {
		double deltaX = Game.player.centerX() - centerX();
		double deltaY = Game.player.centerY() - centerY();
		double mag = Math.hypot(deltaX, deltaY);
		if(mag == 0) mag = 1;

		Game.eShots.add(new Shot(centerX(), centerY(), 6, 3, deltaX / mag, deltaY / mag, 0, 5, damage, 30, spriteAtk));
	}
	
	private void attack2() {
		double deltaX = Game.player.centerX() - centerX();
		double deltaY = Game.player.centerY() - centerY();
		double mag = Math.hypot(deltaX, deltaY);
		if(mag == 0) mag = 1;
		
		Game.eShots.add(new Shot_SuccubusVampireBat(Game.player.centerX(), Game.player.centerY(), deltaX / mag, deltaY / mag, damage / 3, this));
	}
	
	private void renderAura() {
		if (attackTimer > 280) {
			showAura = true;
		}
		else {
			showAura = false;
		}
	}
	
	private void heal() {
		if (attackTimer % 200 == 0 && life < ((maxLife / 100) * 20)) {
			life += (maxLife / 100) * 8;
		}
	}
	
	private void tp() {
		if (attackTimer % 300 == 0) {
			int prop;
			
			if (Game.rand.nextInt(2) == 1) {
				prop = -1;
			}
			else {
				prop = 1;
			}
			
			x = Game.player.centerX() + (48 * prop);
			y = Game.player.centerY();
			attackTimer = 0;
		}
	}
	
	public void tick() {
		if (!balance) {
			balanceStats();
		}
		attackTimer++;
		tp();
		renderAura();
		heal();
		if (Entity.calculateDistance(Game.player.centerX(), Game.player.centerY(), centerX(), centerY()) <= 140) {
			if (attackTimer % 20 == 0) {
				attack1();
			}
			if (attackTimer % 100 == 0) {
				attack2();
			}
		}		
		
		if (Entity.calculateDistance(Game.player.centerX(), Game.player.centerY(), centerX(), centerY()) > 120) {
			movement();
		}
		else if (Entity.calculateDistance(Game.player.centerX(), Game.player.centerY(), centerX(), centerY()) < 80) {
			reverseMovement();
		}
		animate();
		shotDamage();
		if (life <= 0) {
			die();
		}
	}
	
	public void render() {
		Game.gameGraphics.drawImage(animation[index], getX() - Camera.getX(), getY() - Camera.getY(), null);
		if (showAura) {
			Game.gameGraphics.drawImage(aura, getX() - Camera.getX(),  getY() - Camera.getY(), 32, 32, null);
		}
	}
}
