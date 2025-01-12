package entities.enemies;

import entities.*;
import entities.orbs.*;
import main.Game;
import world.Camera;
import world.World;

public class Enemy_Trapper extends Enemy{
	private int xP, yP, cont = 120;
	private boolean stage2 = true;
	
	public Enemy_Trapper(int x, int y) {
		super(x, y, 16, 16, Game.sheet.getSprite(240, 80, 16, 16));
		if(specialRare){
			specialMult = 2;
			hue = 0xFFFFFF;
		}
		getAnimation(240, 80, 16, 16, 3);
		expValue = 37 * specialMult;
		soulValue = 5 * specialMult;
		maxLife = 40 + (int)(0.4 * World.wave);
		life = maxLife;
		damage = 48 * specialMult + 0.48 * World.wave;
		maxSpeed = 1 + (specialMult - 1) / 3;
		speed = maxSpeed;
		timeSpawn = 600;
		maxIndex = 3;
		maxFrames = 10;
	}
	
	private void die() {
		Game.enemies.remove(this);
		Game.entities.add(new EXP_Orb(centerX(), centerY(), expValue, hue));
		Player.souls += soulValue;
	}
	private void stage2() {
		shotDamage();
		setMask(2, 0, 14, 32);
		animate();
		cont++;
		if (isColiding(Game.player)) {
			giveCollisionDamage(Game.player, 30, 1);
		}
		if (cont >= 120) {
			stage2 = false;
			cont = 0;
			xP = Game.player.centerX();
			yP = Game.player.centerY();
		}
	}
	
	public void tick() {
		if (!spawning) {
			if (centerX() != xP && centerY() != yP && !isColiding(Game.player)) {
				objectiveMovement(xP, yP);
				setMask(0, 0, 0, 0);
			}
			else {
				stage2 = true;
				stage2();
			}
			
			slownessEffect(0.995);
			
			if (life <= 0) {
				die();
			}
		}
		else {
			spawnAnimation(timeSpawn / 3);
		}
	}
	
	public void render() {
		if (stage2) {
			Game.gameGraphics.drawImage(animation[index], getX() - Camera.getX(), getY() - Camera.getY(), 16, 32, null);
		}
		else {
			Game.gameGraphics.drawImage(animation[0], getX() - Camera.getX(), getY() - Camera.getY(), null);
		}
	}
}
