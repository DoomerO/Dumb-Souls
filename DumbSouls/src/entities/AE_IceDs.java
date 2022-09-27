package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class AE_IceDs extends Attack_Entity {
	
	public int damage;
	private int time = 0;
	
	public AE_IceDs(int x, int y, int width, int height, BufferedImage sprite, int time) {
		super(x, y, width, height, sprite, time);
		this.setMask(0, 0, 16, 16);
		this.getAnimation(32, 112, 16, 16, 1);
		this.depth = 0;
	}
	
	public void tick() {
		time ++;
		if (time == this.timeLife) {
			this.die();
		}
		Colision();
		refreshTick();
	}
	
	private void Colision() {
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(Entity.isColiding(e, this) && TickTimer(5)) {
				e.frost += 0.25;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
