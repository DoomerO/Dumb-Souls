package entities;

import java.awt.image.BufferedImage;

import main.Game;

import java.awt.Graphics;

public class Attack_Entity extends Entity {
	
	protected int timeLife;
	protected BufferedImage[] animation;
	
	public Attack_Entity(int x, int y, int width, int height, BufferedImage sprite, int timeLife) {
		super(x, y, width, height, sprite);
		this.timeLife = timeLife;
	}
	
	protected void getAnimation(int x, int y, int width, int height, int frames) {
		this.animation = new BufferedImage[frames];
		
		for(int i = 0; i < animation.length; i++ ) {
			animation[i] = Game.sheet.getSprite(x , y, width, height);
			x += 16;
		}
	}
	
	protected void die() {
		Game.entities.remove(this);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
	}
}
