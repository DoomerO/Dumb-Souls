package entities;

import java.awt.image.BufferedImage;

import main.Game;

import java.awt.Graphics;

import world.Camera;

public class AE_Fire extends Attack_Entity {
	
	private int maxFrames = 20, frames = 0, index, maxIndex = 2, time;
	
	public AE_Fire(int x, int y, int width, int height, BufferedImage sprite, int time) {
		super(x, y, width, height, sprite, time);
		this.setMask(0, 8, 16, 8);
		this.getAnimation(0, 112, 16, 16, maxIndex);
		this.depth = 2;
	}
	
	public void tick() {
		frames ++;
		time ++;
		if (frames == maxFrames) {
			index++;
			frames = 0;
			if (index == maxIndex) {
				index = 0;
			}
		}
		if (time == this.timeLife) {
			this.die();
		}
		Colision();
	}
	
	private void Colision() {
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(Entity.isColiding(e, this)) {
				e.life -= 0.1;
			}
		}
		
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
