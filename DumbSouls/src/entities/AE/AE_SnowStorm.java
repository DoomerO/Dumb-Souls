package entities.AE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import entities.*;
import entities.enemies.Enemy;
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
		
		double destX = Game.mx / Game.scale;
		double destY = Game.my / Game.scale;
		double startX = this.x + 26 - Camera.x;
		double startY = this.y + 16 - Camera.y;

		double ang = getAngle(destY, startY, destX, startX);

		if (calculateDistance((int)destX, (int)destY, (int)startX, (int)startY) > 1){
			this.x += Math.cos(ang) * this.speed;
			this.y += Math.sin(ang) * this.speed;
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
		
		enemyCollision();
		refreshTick();
	}
	
	public void enemyCollision() {
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
