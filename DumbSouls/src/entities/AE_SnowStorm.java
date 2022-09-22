package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class AE_SnowStorm extends Attack_Entity {
	
	private double speed, damage;
	private int frames, maxFrames = 10, index, maxIndex = 2, time;
	
	public AE_SnowStorm(int x, int y, int width, int height, BufferedImage sprite, int time, double spd, double dmg) {
		super(x, y, width, height, sprite, time);
		this.speed = spd;
		this.damage = dmg;
		this.timeLife = time;
		this.depth = 3;
		this.getAnimation(64, 112, 16, 16, maxIndex);
		this.setMask(0, 24, 64, 40);
	}
	
	public void tick() {
		frames ++;
		time ++;
		
		if ((Game.mx * Game.scale) > this.getX()) {
			x += speed;
		}
		else if ((Game.mx * Game.scale) < this.getX()) {
			x -= speed;
		}
		if ((Game.my * Game.scale) > this.getY()) {
			y += speed;
		}
		else if ((Game.my * Game.scale) < this.getY()) {
			y -= speed;
		}
		
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
	}
	
	public void enemyColision() {
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (Entity.isColiding(this, e)) {
				e.life -= this.damage;
				e.speed -= 0.01;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, 64, 64, null);
	}
	
}
