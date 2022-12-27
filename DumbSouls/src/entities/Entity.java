package entities;

import java.awt.image.BufferedImage;
import java.util.Comparator;

import main.Game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class Entity {
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected int mx, my, mw, mh;
	protected Rectangle mask;
	protected double push;
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
	public double getAngle(double destY, double startY, double destX, double startX){
		return Math.atan2(destY - startY,destX - startX); 
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
	
	public static boolean lineColision(Line2D line, Entity e) {
		e.mask = new Rectangle(e.getX() + e.mx, e.getY() + e.my, e.mw, e.mh);
		return line.intersects(e.mask);
	}

	protected void knockBack(Entity e, Entity taker){
		double angle = getAngle(taker.getY() + taker.getHeight() / 2, e.getY() + e.getHeight() / 2, taker.getX() + taker.getWidth() / 2, e.getX() + e.getWidth() / 2);

		taker.x += Math.cos(angle) * e.push;
		taker.y += Math.sin(angle) * e.push;
	}
	
}
