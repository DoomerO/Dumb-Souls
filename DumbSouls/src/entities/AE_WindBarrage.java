package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera; 

public class AE_WindBarrage extends Atack_Entity{
	
	private double speed;
	private double dx, dy, damage;
	private int time;
	
	public AE_WindBarrage(int x, int y, int width, int height, double spd, double dx, double dy, double dmg, BufferedImage sprite, int time) {
		super(x, y, width, height, sprite, time);
		this.speed = spd;
		this.dx = dx;
		this.dy = dy;
		this.damage = dmg;
		this.depth = 2;
		this.getAnimation(96, 112, 16, 16, 1);
		this.setMask(0, 0, 32, 32);
	}
	
	public void tick() {
		x += dx * speed;
		y += dy * speed;
		time++;
		if (time == this.timeLife) {
			this.die();
		}
		
		colidingEnemy();
	}
	
	private void colidingEnemy() {
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(Entity.isColiding(this, e)) {
				e.life -= damage;
				if (this.getX() < e.getX()) {
					e.x -= this.speed;
				}
				else if (this.getX() > e.getX()) {
					e.x += this.speed;
				}
				if (this.getY() < e.getY()) {
					e.y -= this.speed;
				}
				else if (this.getY() > e.getY()) {
					e.y += this.speed;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(animation[0], this.getX() - Camera.x, this.getY() - Camera.y, 32, 32, null);
	}
}
