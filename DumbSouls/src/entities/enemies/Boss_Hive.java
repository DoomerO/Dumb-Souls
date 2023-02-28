package entities.enemies;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import main.Game;
import world.Camera;
import entities.*;
import world.World;

public class Boss_Hive extends Enemy{
	
	private int frames, maxFrames = 40, index, maxIndex = 3, timeAtk, spawnX, spawnY, contPosition;
	private boolean balance;
			
	public Boss_Hive(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.spawnX = x;
		this.spawnY = y;
		this.getAnimation(160, 192, 32, 32, 3);
		this.expValue = 1800;
		this.soulValue = 30;
		this.maxLife = 600;
		this.life = maxLife;
		this.setMask(2, 4, 30, 28);
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
	
	private void attack() {
		timeAtk ++;
		if (timeAtk % 120 == 0) {
			Game.enemies.add(new Base_Enemy(this.getX() + 80, this.getY() + 60, 16, 16, null));
			Game.enemies.add(new Base_Enemy(this.getX() - 80, this.getY() + 60, 16, 16, null));
			Game.enemies.add(new Base_Enemy(this.getX() + 16, this.getY() - 60, 16, 16, null));
		}
		
		if (timeAtk % 160 == 0) {
			Game.enemies.add(new Eye_Enemy(this.getX() + 80, this.getY() - 60, 16, 16, null));
			Game.enemies.add(new Eye_Enemy(this.getX() - 80, this.getY() - 60, 16, 16, null));
		}
		
		if (timeAtk % 240 == 0) {
			Game.enemies.add(new Mouth_Enemy(this.getX() + 16, this.getY() + 60, 16, 16, null));
		}
		
		if (timeAtk % 320 == 0) {
			Game.enemies.add(new Base_Enemy(Game.player.getX() + 80, Game.player.getY() + 40, 16, 16, null));
			Game.enemies.add(new Base_Enemy(Game.player.getX() - 80, Game.player.getY() + 40, 16, 16, null));
			Game.enemies.add(new Base_Enemy(Game.player.getX() + 8, Game.player.getY() - 40, 16, 16, null));
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
	
	public void tick() {
		if (!balance) {
			balanceStatus();
		}
		animate();
		attack();
		this.shotDamage();
		 
		if (this.getX() != spawnX && this.getY() != spawnY) {
			contPosition++;
			if (contPosition == 120) {
				this.x = spawnX;
				this.y = spawnY;
			}
		}
		 
		if (this.life <= 0) {
			this.die();
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
