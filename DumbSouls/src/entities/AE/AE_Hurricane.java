package entities.AE;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import world.Camera;
import entities.enemies.Enemy;
import main.Game;

public class AE_Hurricane extends Attack_Entity{
	
	private double speed, damage;
	private int frames, maxFrames = 10, index, maxIndex = 2, time;
	
	public AE_Hurricane(int x, int y, int width, int height, BufferedImage sprite, int time, double spd, int dmg) {
		super(x, y, width, height, sprite, time);
		this.speed = spd;
		this.damage = dmg;
		this.timeLife = time;
		this.push = - 2;
		this.depth = 2;
		this.getAnimation(16, 128, 16, 16, maxIndex);
		this.setMask(6, 0, 52, 32);
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
			if (isColiding(this, e)) {
				e.life -= this.damage;
				knockBack(this, e);
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, 64, 32, null);
	}
	
}
