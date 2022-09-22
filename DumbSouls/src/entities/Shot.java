package entities;

import java.awt.image.BufferedImage;
import java.awt.Graphics;



import main.Game;
import world.Camera;

public class Shot extends Entity{
	
	private double dirx, diry;
	private double speed;
	public int damage;
	
	private int life;
	private int curlife = 0;
	private BufferedImage sprite;
	
	public Shot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy, int dmg, double speed, int life) {
		super(x, y, width, height, sprite);
		this.dirx = dx;
		this.diry = dy;
		this.damage = dmg;
		this.speed = speed;
		this.mw = width;
		this.mh = height;
		this.sprite = sprite;
		this.life = life;
		this.setMask(4, 5, width, height);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
	
	public void tick() {
		x += dirx * speed;
		y += diry * speed;
		curlife++;
		if (curlife == life) {
			Game.shots.remove(this);
		}
	}
}
