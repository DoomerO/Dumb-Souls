package entities;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import world.Camera;

import main.Game;

public class AE_PoisonPool extends Attack_Entity {
	
	public int time, frames, index, maxFrames = 20, maxIndex = 2;
	public double dmg;
	
	public AE_PoisonPool(int x, int y, int width, int height, BufferedImage sprite, int time, int dmg) {
		super(x, y, width, height, sprite, time);
		this.setMask(0, 0, width, height);
		this.getAnimation(128, 128, 16, 16, 2);
		this.dmg = dmg;
		this.depth = 0;
	}
	
	public void tick() {
		time ++;
		frames++;
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
	
	public void Colision() {
		if (time % 10 == 0) {
			for (int i = 0; i < Game.enemies.size(); i++) {
				Enemy e = Game.enemies.get(i);
				if(Entity.isColiding(e, this)) {
					e.life -= dmg;
				}
			}	
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, this.width, this.height, null);
	}
}
