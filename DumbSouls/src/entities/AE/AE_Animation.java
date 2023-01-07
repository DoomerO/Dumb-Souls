package entities.AE;

import java.awt.image.BufferedImage;
import entities.*;
import java.awt.Graphics;
import world.Camera;

public class AE_Animation extends Attack_Entity {
	
	private int maxFrames, maxIndex, frames, index, time;
	private String style;
	private Entity followEntity;
	
	public AE_Animation(int x, int y, int width, int height, BufferedImage sprite, int time, 
			int maxFrames, int maxIndex, int xSprite,int ySprite, int wSprite, int hSprite, String style, Entity entity) {
		super(x, y, width, height, sprite, time);
		this.getAnimation(xSprite, ySprite, wSprite, hSprite, maxIndex);
		this.maxFrames = maxFrames;
		this.maxIndex = maxIndex;
		this.style = style;
		this.followEntity = entity;
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
		
		if (time == this.timeLife) {
			this.die();
		}
		
	}
	
	public void render(Graphics g) {
		g.drawImage(animation[index], this.getX() - Camera.x, this.getY() - Camera.y, this.getWidth(), this.getHeight(), null);
	}
}
