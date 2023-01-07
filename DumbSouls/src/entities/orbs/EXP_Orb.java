package entities.orbs;

import java.awt.image.BufferedImage;

import world.Camera;
import main.*;
import entities.enemies.*;
import entities.*;
import java.awt.Graphics;

public class EXP_Orb extends Enemy{
    private int index, maxIndex = 3, frames, maxFrames = 6;
	
	public EXP_Orb(int x, int y, int width, int height, BufferedImage sprite, int expValue) {
		super(x, y, width, height, sprite);
		
		this.getAnimation(0, 144, 16, 16, 3);
		this.expValue = expValue;
		this.speed = 0.4;
		this.setMask(0, 0, 16, 16);
		this.depth = 1;
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

    public void tick() {
		animate();

		if (Entity.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 32){
			this.movement();
		}

		if (isColiding(this, Game.player)){
            Game.entities.remove(this);
			Game.player.exp +=  this.expValue;
        }
	}

    public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}