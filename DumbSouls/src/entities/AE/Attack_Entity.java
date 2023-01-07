package entities.AE;

import java.awt.image.BufferedImage;
import entities.*;
import main.Game;

import java.awt.Graphics;

public class Attack_Entity extends Entity {
	
	protected int timeLife, tickTimer;
	protected BufferedImage[] animation;
	
	public Attack_Entity(int x, int y, int width, int height, BufferedImage sprite, int timeLife) {
		super(x, y, width, height, sprite);
		this.timeLife = timeLife;
	}
	
	protected void getAnimation(int x, int y, int width, int height, int frames) {
		this.animation = new BufferedImage[frames];
		
		for(int i = 0; i < animation.length; i++ ) {
			animation[i] = Game.sheet.getSprite(x , y, width, height);
			x += width;
		}
	}
	
	protected void die() {
		Game.entities.remove(this);
	}
	
	public void tick() {
		
	}

	protected boolean TickTimer(int frames){
		if (this.tickTimer % frames == 0){
			return true;
		}
		else{
			return false;
		}
	}

	protected void refreshTick(){
		this.tickTimer++;
		if (this.tickTimer >= 60){
			this.tickTimer = 0;
		}
	}
	
	public void render(Graphics g) {
		
	}
}
