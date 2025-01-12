package entities.enemies;

import main.Game;
import entities.*;
import entities.orbs.Rune_Orb;
import world.World;

public class Boss_Hive extends Enemy{
	
	private int spawnX, spawnY, contPosition;
	private boolean balance;
			
	public Boss_Hive(int x, int y) {
		super(x, y, 32, 32, Game.sheet.getSprite(160, 192, 32, 32));
		spawnX = x;
		spawnY = y;
		getAnimation(160, 192, 32, 32, 3);
		expValue = 1800;
		soulValue = 30;
		maxLife = 600;
		life = maxLife;
		maxFrames = 40;
		setMask(2, 4, 30, 28);
	}
	
	private void attack() {
		attackTimer ++;
		if (attackTimer % 120 == 0) {
			Game.enemies.add(new Enemy_Stain(centerX() + 80, centerY() + 60));
			Game.enemies.add(new Enemy_Stain(centerX() - 80, centerY() + 60));
			Game.enemies.add(new Enemy_Stain(centerX() + 16, centerY() - 60));
		}
		
		if (attackTimer % 160 == 0) {
			Game.enemies.add(new Enemy_Eye(centerX() + 80, centerY() - 60));
			Game.enemies.add(new Enemy_Eye(centerX() - 80, centerY() - 60));
		}
		
		if (attackTimer % 240 == 0) {
			Game.enemies.add(new Enemy_Mouth(centerX() + 16, centerY() + 60));
		}
		
		if (attackTimer % 320 == 0) {
			Game.enemies.add(new Enemy_Stain(Game.player.centerX() + 80, Game.player.centerY() + 40));
			Game.enemies.add(new Enemy_Stain(Game.player.centerX() - 80, Game.player.centerY() + 40));
			Game.enemies.add(new Enemy_Stain(Game.player.centerX(), Game.player.centerY() - 40));
		}
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
		Game.enemies.add(new Rune_Orb(centerX(), centerY(), 16, 16));
		World.bossName = "";
	}
	
	public void tick() {
		if (!balance) {
			balanceStats();
		}
		animate();
		attack();
		shotDamage();
		 
		if (centerX() != spawnX && centerY() != spawnY) {
			contPosition++;
			if (contPosition == 120) {
				x = spawnX;
				y = spawnY;
			}
		}
		 
		if (life <= 0) {
			die();
		}
	}
}
