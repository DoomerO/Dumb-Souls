package entities;

import java.awt.image.BufferedImage;
import java.util.Comparator;

import main.Game;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Entity {
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected int mx, my, mw, mh;
	protected Rectangle mask;
	public int depth;
	
	public BufferedImage sprite;
	
	public static BufferedImage player_sprite = Game.sheet.getSprite(0, 16, 16, 16);
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public static double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public static double calculeDistance2pt(int p1, int p2) {
		return Math.sqrt(p1 * p1 + p2 * p2);
	}
	
	public static Comparator<Entity> entityDepth = new Comparator<Entity>() {
		public int compare(Entity n0, Entity n1) {
			if (n1.depth < n0.depth) {
				return +1;
			}
			if (n1.depth > n0.depth) {
				return -1;
			}
			return 0;
		}
	};
	
	public void setX(int newX) {
		this.x = newX;
	}
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	public int getY() {
		return (int)this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX(), this.getY(), null);
	}
	
	protected void setMask(int mx, int my, int mw, int mh) {
		this.mx = mx;
		this.my = my;
		this.mw = mw;
		this.mh = mh;
		
		
	}
	
	public static boolean isColiding(Entity e1, Entity e2) {
		e1.mask = new Rectangle(e1.getX() + e1.mx, e1.getY() + e1.my, e1.mw, e1.mh);
		e2.mask = new Rectangle(e2.getX() + e2.mx, e2.getY() + e2.my, e2.mw, e2.mh);
		return e1.mask.intersects(e2.mask);
	}
}
