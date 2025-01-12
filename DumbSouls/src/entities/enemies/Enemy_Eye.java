package entities.enemies;

import entities.Entity;
import entities.Player;
import entities.orbs.EXP_Orb;
import entities.shots.Shot;
import world.World;
import main.Game;

public class Enemy_Eye extends Enemy{
	private float range;
	
	public Enemy_Eye(int x, int y) {
		super(x, y, 16, 16, Game.sheet.getSprite(48, 80, 16, 16));
		if (specialRare){
			specialMult = 2;
			hue = 0xFFFFFF;
		}
		getAnimation(48, 80, 16, 16, 3);
		expValue = 25 * specialMult;
		soulValue = 3 * specialMult;
		range += 80f + 0.04f * World.wave;
		maxLife = 15 * specialMult + (int)(0.15 * World.wave);
		life = maxLife;
		damage = 15 * specialMult + 0.15 * World.wave;
		maxSpeed = 0.8 + (specialMult - 1)/3;
		speed = maxSpeed;
		setMask(2, 3, 12, 11);
		timeSpawn = 180;
		maxFrames = 15;
	}
	
	private void die() {
		Game.enemies.remove(this);
		Game.entities.add(new EXP_Orb((int)x, (int)y, expValue, hue));
		Player.souls += soulValue;
	}
	
	private void attack() {
		if (attackTimer >= 60) {
			double deltaX = Game.player.centerX() - centerX(), deltaY = Game.player.centerY() - centerY();
			double mag = Math.hypot(deltaX, deltaY);
			if(mag == 0) mag = 1;
			Game.eShots.add(new Shot(centerX(), centerY(), 3, 3, deltaX / mag, deltaY / mag, 0, 3, damage, (int)(range / 2), Game.sheet.getSprite(0, 160, 16, 16)));
			attackTimer = 0;
		}
	}
	
	public void tick() {
		animate();
		if (spawning) {
			spawnAnimation(timeSpawn / 3);
			return;
		}
		if (Entity.calculateDistance(centerX(), centerY(), Game.player.centerX(), Game.player.centerY()) >= range) {
			movement();
		}
		else attack();
		attackTimer++;
		shotDamage();

		slownessEffect(0.995);

		if (life <= 0) die();
	}
}
