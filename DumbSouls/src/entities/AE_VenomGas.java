package entities;

import java.awt.image.BufferedImage;

import main.Game;

import java.awt.Graphics;
import world.Camera;

public class AE_VenomGas extends Attack_Entity {
	
	private double speed;
	private double dx, dy, damage;
	private int time;
	
	public AE_VenomGas(int x, int y, int height, int width, double spd, double dirx, double diry, double dmg, BufferedImage sprite, int time) {
		super(x, y, height, width, sprite, time);
		this.speed = spd;
		this.dx = dirx;
		this.dy = diry;
		this.damage = dmg;
		this.getAnimation(192, 112, 16, 16, 1);
		this.setMask(2, 2, 4, 8);
		this.depth = 2;
	}
	
	public void tick() {
		x += dx * speed;
		y += dy * speed;
		time++;
		if (time == this.timeLife) {
			this.die();
		}
		Collision();
	}
	
	private void Collision() {
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(Entity.isColiding(this, e)) {
				e.frost = e.maxSpeed * 0.8;
				e.life -= damage;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(animation[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
