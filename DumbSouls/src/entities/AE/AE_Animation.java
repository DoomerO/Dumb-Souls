package entities.AE;

import java.awt.image.BufferedImage;
import entities.*;
import main.Game;

import world.Camera;

public class AE_Animation extends AE_Attack_Entity {
	
	private int maxFrames, maxIndex, frames, index, time;
	private String style;
	private Entity followEntity;
	
	public AE_Animation(int x, int y, int width, int height, BufferedImage sprite, int time, 
			int maxFrames, int maxIndex, int xSprite,int ySprite, int wSprite, int hSprite, String style, Entity entity) {
		super(x, y, width, height, sprite, time);
		getAnimation(xSprite, ySprite, wSprite, hSprite, maxIndex);
		this.maxFrames = maxFrames;
		this.maxIndex = maxIndex;
		this.style = style;
		followEntity = entity;
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
				depth = 0;
				y -= 0.3;
			break;
			case "goToUp_2": 
				depth = 2;
				y -= 0.1;
			break;
			case "goToUp_Objt_1": 
				depth = 2;
				y -= 0.4;
				x = followEntity.getX();
			break;
			case "frames_1":
				animate();
			break;	
		}
	}
	
	public void tick() {
		time++;
		styleReader();
		
		if (time == life) {
			die();
		}
		
	}
	
	public void render() {
		Game.gameGraphics.drawImage(animation[index], getX() - Camera.getX(), getY() - Camera.getY(), getWidth(), getHeight(), null);
	}
}
