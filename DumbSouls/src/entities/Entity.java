 package entities;

import java.awt.image.BufferedImage;
import java.util.Comparator;

import main.Game;
import world.Camera;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class Entity {
	
	public double x, y, life, maxLife, damage, push, speed, maxSpeed, slowness;
	public int width, height, depth;
	protected int mx, my, mw, mh;
	protected Rectangle mask;
	
	public BufferedImage sprite;
	
	public Entity(int x, int y, int w, int h, BufferedImage sprt) {
		this.x = x - w / 2;
		this.y = y - h / 2;
		width = w;
		height = h;
		sprite = sprt;
	}
	
	public static double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.hypot(x1 - x2, y1 - y2);
	}
	
	public int outOfBounds(){
		return (x > 1264 - width ? 1 : 0) + (y > 1264 - height ? 2 : 0) - (x < 16 ? 1 : 0) - (y < 16 ? 2 : 0);
	}

	public void clampBounds(int sign){
		switch(sign){
		case -1:
			x = 16; break;
		case -2:
			y = 16; break;
		case -3:
			x = y = 16; break;
		case 1:
			x = 1264 - width; break;
		case 2:
			y = 1264 - height; break;
		case 3:
			x = 1264 - width;
			y = 1264 - height; break;
		}
	}

	public static Comparator<Entity> entityDepth = new Comparator<Entity>() {
		public int compare(Entity n0, Entity n1) {
			if (n1.depth < n0.depth)
				return +1;
			if (n1.depth > n0.depth)
				return -1;
			return 0;
		}
	};
	
	public void setX(int newX) {
		x = newX;
	}
	public void setY(int newY) {
		y = newY;
	}
	
	public int getX() {
		return (int)x;
	}
	public int getY() {
		return (int)y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int centerX(){
		return (int)x + width / 2;
	}
	public int centerY(){
		return (int)y + height / 2;
	}

	public void tick() {
		
	}
	
	public void render() {
		Game.gameGraphics.drawImage(sprite, getX() - Camera.getX(), getY() - Camera.getY(), null);
	}

	protected int[] getMask(){
		int[] temp = {mx, my, mw, mh};
		return temp;
	}
	
	protected void setMask(int mx, int my, int mw, int mh) {
		this.mx = mx;
		this.my = my;
		this.mw = mw;
		this.mh = mh;
	}

	protected void setMask(int[] mask) {
		mx = mask[0];
		my = mask[1];
		mw = mask[2];
		mh = mask[3];
	}
	
	public boolean isColiding(Entity other) {
		mask = new Rectangle(getX() + mx, getY() + my, mw, mh);
		other.mask = new Rectangle(other.getX() + other.mx, other.getY() + other.my, other.mw, other.mh);
		return mask.intersects(other.mask);
	}
	
	public static boolean lineCollision(Line2D line, Entity ent) {
		ent.mask = new Rectangle(ent.getX() + ent.mx, ent.getY() + ent.my, ent.mw, ent.mh);
		return line.intersects(ent.mask);
	}

	public void receiveKnockback(Entity source){
		double deltaX = centerX() - source.centerX();
		double deltaY = centerY() - source.centerY();
		double mag = Math.hypot(deltaX, deltaY);
		if(mag == 0) mag = 1;
		x += deltaX / mag * source.push;
		y += deltaY / mag * source.push;
	}

	public void receiveKnockback(Entity source, int amount){
		double deltaX = centerX() - source.centerX();
		double deltaY = centerY() - source.centerY();
		double mag = Math.hypot(deltaX, deltaY);
		if(mag == 0) mag = 1;
		x += deltaX / mag * amount;
		y += deltaY / mag * amount;
	}
	
	public void receiveDamage(Entity source){
		life -= source.damage;
	}
}
