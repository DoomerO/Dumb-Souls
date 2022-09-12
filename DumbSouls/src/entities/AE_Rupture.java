package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class AE_Rupture extends Atack_Entity {
	
	public int time, frames, index, maxFrames = 10, maxIndex = 3;
	public double dmg;
	
	public AE_Rupture(int x, int y, int width, int height, BufferedImage sprite, int time, double dmg) {
		super(x, y, width, height, sprite, time);
		this.setMask(0, 0, width, height);
		this.getAnimation(80, 128, 16, 16, 3);
		this.dmg = dmg;
		this.depth = 0;
	}
	
	public void tick() {
		time ++;
		if (frames <= 40) {
			frames++;
			if (frames == maxFrames) {
				index++;
			}
		}
		else {
			index = 2;
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
				e.life -= dmg;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, width, height, null);
	}
}
