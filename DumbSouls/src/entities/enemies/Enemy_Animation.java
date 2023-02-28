package entities.enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import entities.Entity;
import main.Game;
import world.Camera;

public class Enemy_Animation extends Enemy {
	private int maxFrames, maxIndex, frames, index, time, timeLife;
	private String style;
	private Entity followEntity;
	
	public Enemy_Animation(int x, int y, int width, int height, BufferedImage sprite, int time, 
			int maxFrames, int maxIndex, int xSprite,int ySprite, int wSprite, int hSprite, String style, Entity entity, boolean specialRare) {
		super(x, y, width, height, sprite);
		this.timeLife = time;
		this.specialRare = specialRare;
		this.getAnimation(xSprite, ySprite, wSprite, hSprite, maxIndex);
		this.maxFrames = maxFrames;
		this.maxIndex = maxIndex;
		this.style = style;
		this.followEntity = entity;
		this.depth = 2;
	}
	
	private void animate() {
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index == maxIndex) {
				index = 0;
			}
		}
	}
	
	private void styleReader() {
		switch (style){
			case "goToUp_1": 
				this.depth = 0;
				this.y -= 0.3;
			break;
			case "goToUp_2": 
				this.depth = 2;
				this.y -= 0.1;
			break;
			case "goToUp_Objt_1": 
				this.depth = 2;
				this.y -= 0.4;
				this.x = followEntity.getX();
			break;
			case "frames_1":
				animate();
			break;	
		}
	}
	
	public void tick() {
		time++;
		styleReader();
		
		if (time == timeLife) {
			Game.enemies.remove(this);
		}
		
	}
	
	public void render(Graphics g) {
		g.drawImage(animation[index], this.getX() - Camera.x, this.getY() - Camera.y, this.getWidth(), this.getHeight(), null);
	}
}
