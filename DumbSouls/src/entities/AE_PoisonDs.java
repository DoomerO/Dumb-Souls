package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;

public class AE_PoisonDs extends Attack_Entity{
	
	private int time, frames, maxFrames = 10, index, maxIndex = 2, dmg;
	
	public AE_PoisonDs(int x, int y, int width, int height, BufferedImage sprite, int time, int dmg) {
		super(x, y, width, height, sprite, time);
		this.dmg = dmg;
		this.setMask(0, 0, width, height);
		this.getAnimation(64, 112, 16, 16, 2);
		this.depth = 2;
	}
	
	public void tick() {
		time ++;
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index == maxIndex) {
				index = 0;
			}
		}
		x = Game.player.getX() - width / 2 + 8;
		y = Game.player.getY() - height / 2 + 8;
		if (time == this.timeLife) {
			this.die();
		}
		Colision();
	}
	
	private void Colision() {
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(Entity.isColiding(e, this)) {
				if (time % 5 == 0) {
					e.life -= dmg;
				}
			}
		}
		for (int i = 0; i < Game.eShots.size(); i++) {
			Enemy_Shot e = Game.eShots.get(i);
			if(Entity.isColiding(e, this)) {
				Game.eShots.remove(e);
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, this.width, this.height, null);
	}
}
