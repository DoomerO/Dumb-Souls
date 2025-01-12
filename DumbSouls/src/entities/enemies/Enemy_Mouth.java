package entities.enemies;

import entities.*;
import entities.orbs.*;
import main.Game;
import world.World;

public class Enemy_Mouth extends Enemy {
	public Enemy_Mouth(int x, int y) {
		super(x, y, 16, 16, Game.sheet.getSprite(96, 80, 16, 16));
		if (specialRare){
			specialMult = 2;
			hue = 0xFFFFFF;
		}
		getAnimation(96, 80, 16, 16, 3);
		expValue = 20 * specialMult;
		soulValue = 2 * specialMult;
		maxLife = 12 * specialMult + (int)(0.12 * World.wave);
		life = maxLife;
		damage = 26 * specialMult + 0.26 * World.wave;
		maxSpeed = 1.5 + (specialMult - 1) / 3;
		speed = maxSpeed;
		timeSpawn = 240;
		setMask(2, 3, 15, 13);
	}
	
	private void die() {
		Game.enemies.remove(this);
		Game.entities.add(new EXP_Orb((int)x, (int)y, expValue, hue));
		Player.souls += soulValue;
	}
	
	public void tick() {
		animate();
		if (!spawning) {
			movement();
			
			if (isColiding(Game.player)) {
				this.giveCollisionDamage(Game.player, 30, 1);
			}
			
			slownessEffect(0.85);
			
			shotDamage();
			if (life <= 0) {
				die();
			}
		}
		else {
			spawnAnimation(timeSpawn / 3);
		}
	}
}
