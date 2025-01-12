package entities.enemies;

import entities.*;
import entities.orbs.*;
import main.*;
import world.World;

public class Enemy_Stain extends Enemy{
	public Enemy_Stain(int x, int y) {
		super(x, y, 16, 16, Game.sheet.getSprite(0, 80, 16, 16));
		if (specialRare){
			specialMult = 3;
			hue = 0xFFFFFF;
		}
		getAnimation(0, 80, 16, 16, 3);
		expValue = 10 * specialMult;
		soulValue = 1 * specialMult;
		maxLife = 10 * specialMult + (int)(0.1 * World.wave);
		life = maxLife;
		damage = 8 * specialMult + 0.08 * World.wave;
		maxSpeed = 1 + (specialMult - 1) / 3;
		speed = maxSpeed;
		setMask(2, 2, 12, 13);
		timeSpawn = 150;
	}
	
	private void die() {
		Game.enemies.remove(this);
		Game.entities.add(new EXP_Orb(centerX(), centerY(), expValue, hue));
		Player.souls += soulValue;
	}
	
	public void tick() {
		animate();
		if (spawning == false) {
			if (!isColiding(Game.player)) {
				movement();
			}
			else {
				this.giveCollisionDamage(Game.player, 15, 1);
			}

			slownessEffect(0.99);
			
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
