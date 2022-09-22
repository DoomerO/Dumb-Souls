package entities;

import java.awt.image.BufferedImage;
import world.Camera;
import java.awt.Graphics;
import main.Game;

public class AE_IceSpike extends Attack_Entity {
	
	private double damage;
	private int time;
	
	public AE_IceSpike(int x, int y, int width, int height, BufferedImage sprite, int time, double damage) {
		super(x, y, width, height, sprite, time);
		this.damage = damage;
		this.getAnimation(48, 128, 16, 16, 1);
		this.setMask(2, 4, 12, 12);
	}
	
	public void tick() {
		time++;
		if (time == this.timeLife) {
			this.die();
		}
		Colision();
	}
	
	public void Colision() {
		for(int i = 0; i < Game.enemies.size(); i++ ) {
			Enemy e = Game.enemies.get(i);
			if (Entity.isColiding(this, e)) {
				e.life -= damage;
				e.speed -= 0.8;
				if (e.speed < 0) {
					e.speed = 0;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
	
}
