package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class AE_SnowStorm extends Attack_Entity {
	
	private double speed, damage;
	private int frames, maxFrames = 10, index, maxIndex = 2, time;
	
	public AE_SnowStorm(int x, int y, int width, int height, BufferedImage sprite, int time, double spd, int dmg) {
		super(x, y, width, height, sprite, time);
		this.speed = spd;
		this.damage = dmg;
		this.timeLife = time;
		this.depth = 3;
		this.getAnimation(96, 112, 16, 16, maxIndex);
		this.setMask(0, 24, 64, 40);
	}
	
	public void tick() {
		frames ++;
		time ++;

		double angle = Math.atan2(Game.my - this.y, Game.mx - this.x);

		this.x += Math.cos(angle) * this.speed;
		this.y += Math.sin(angle) * this.speed;
		
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index == maxIndex) {
				index = 0;
			}
		}
		
		if (time == this.timeLife) {
			this.die();
		}
		
		enemyColision();
		refreshTick();
	}
	
	public void enemyColision() {
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (Entity.isColiding(this, e) && TickTimer(20)) {
				e.life -= this.damage;
				e.frost += 2;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, 64, 64, null);
	}
	
}
