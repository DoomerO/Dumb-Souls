package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class Enemy_Shoot extends Entity{
	
	private double dirx, diry;
	private double speed;
	public int damage;
	
	private int life;
	private int curlife = 0;
	private BufferedImage sprite;
	
	public Enemy_Shoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy, int dmg, int speed, int life) {
		super(x, y, width, height, sprite);
		this.dirx = dx;
		this.diry = dy;
		this.damage = dmg;
		this.speed = speed;
		this.mw = width;
		this.mh = height;
		this.sprite = sprite;
		this.life = life;
	}
	
	public void render(Graphics g) {
		g.drawImage(this.sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
	
	public void tick() {
		x += this.dirx * this.speed;
		y += this.diry * this.speed;
		curlife++;
		if (this.curlife == life) {
			Game.eShoots.remove(this);
		}
	}
}
