package entities.enemies;

import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.World;
import entities.*;
import entities.shots.*;
import entities.AE.BAE_Spike;
import entities.orbs.Rune_Orb;

public class Boss_Duality extends Enemy{
	
	private int shieldLife;
	private boolean balance, shieldActive;
	private BufferedImage aura = Game.sheet.getSprite(16, 160, 16, 16);
	private BufferedImage shield = Game.sheet.getSprite(16, 176, 16, 16);
	private BufferedImage spriteAtk = Game.sheet.getSprite(32, 176, 16, 16);
	
	public Boss_Duality(int x, int y){
		super(x, y, 32, 32, Game.sheet.getSprite(11, 195, 10, 10));
		getAnimation(0, 192, 32, 32, 3);
		expValue = 1500;
		soulValue = 20;
		maxLife = 800;
		life = maxLife;
		speed = 0.8;
		maxFrames = 40;
		setMask(11, 6, 12, 20);
	}
	
	private void balanceStats() {
		maxLife =  800 * World.wave / 10;
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
	
	private void closeAtk() {
		if (Entity.calculateDistance(Game.player.centerX(), Game.player.centerY(), centerX(), centerY()) <= 60) {
			double deltaX = Game.player.centerX() - centerX();
			double deltaY = Game.player.centerY() - centerY();
			double mag = Math.hypot(deltaX, deltaY);
			if(mag == 0) mag = 1;
			Game.eShots.add(new Shot_DualityBlackHand(centerX(), centerY(), deltaX / mag, deltaY / mag, this));
			Game.player.mana *= 0.75;
		}
	}
	
	private void shieldColision() {
		for (int i = 0;  i < Game.shots.size(); i++) {
			Shot sh = Game.shots.get(i);
			if (isColiding(sh)) {
				sh.die(null);
			}
		}
		
		if (life <= shieldLife) {
			life = shieldLife;
		}
	}
	
	private void addShield() {
		if (!shieldActive) {
			shieldActive = true;
			shieldLife = (int)life;
			setMask(0, 0, 98, 64);
		}
		else {
			shieldActive = false;
			setMask(11, 6, 12, 20);
		}
	}
	
	private void rangeAtk() {
		attackTimer++;
		if (attackTimer % 40 == 0) {
			Game.entities.add(new BAE_Spike(Game.player.getX(), Game.player.getY(), 16, 16, null, 60, 60));
		}
		if (attackTimer % 60 == 0) {
			double deltaX = Game.player.centerX() - centerX();
			double deltaY = Game.player.centerY() - centerY();
			double mag = Math.hypot(deltaX, deltaY);
			if(mag == 0) mag = 1;
			Game.eShots.add(new Shot_DualityBlackHand(centerX(), centerY(), deltaX / mag, deltaY / mag, this));
		}
		if (attackTimer % 80 == 0) {
			double deltaX = Game.player.centerX() - centerX();
			double deltaY = Game.player.centerY() - centerY();
			double mag = Math.hypot(deltaX, deltaY);
			if(mag == 0) mag = 1;
			Game.eShots.add(new Shot(centerX(), centerY(), 5, 5, deltaX / mag, deltaY / mag, Math.atan2(deltaY, deltaX), 3, 32, 50, spriteAtk));
		}
		if (attackTimer % 160 == 0) {
			addShield();
			attackTimer = 0;
		}
	}
	
	public void tick() {
		if (!balance) {
			balanceStats();
		}
		
		if (Entity.calculateDistance(Game.player.getX(), Game.player.getY(), getX(), getY()) > 40) {
			movement();
		}
		if (speed < 0.8) {
			speed = 0.8;
		}
		animate();
		closeAtk();
		rangeAtk();
		
		if (shieldActive) {
			shieldColision();
		}
		else {
			shotDamage();
		}
	
		if (life <= 0) {
			die();
		}
		
	}
	
	public void render() {
		Game.gameGraphics.drawImage(aura, getX() - Camera.getX() - 32, getY() - Camera.getY() - 32, 98, 98,null);
		Game.gameGraphics.drawImage(animation[index], getX() - Camera.getX(), getY() - Camera.getY(), null);
		
		if (shieldActive) {
			Game.gameGraphics.drawImage(shield, getX() - Camera.getX() - 32, getY() - Camera.getY() - 48, 98, 80, null);
		}
	}
}
