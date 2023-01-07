package entities.AE;

import java.awt.image.BufferedImage;
import world.Camera;
import main.Game;
import java.awt.Graphics;
import entities.*;
import entities.enemies.Enemy;

public class AE_Explosion extends Attack_Entity{
	
	private int damage, maxFrames, maxIndex, frames, index, time;
	
	public AE_Explosion(int x, int y, int width, int height, BufferedImage sprite, int time, int dmg, int knockback,
			int maxFrames, int maxIndex, int xSprite,int ySprite, int wSprite, int hSprite) {
		
		super(x, y, width, height, sprite, time);
		this.getAnimation(xSprite, ySprite, wSprite, hSprite, maxIndex);
		this.setMask(0, 0, width, height);
		this.damage = dmg;
		this.push = knockback;
		this.maxFrames = maxFrames;
		this.maxIndex = maxIndex;
	}
	
	private void animate() {
		if (index < maxIndex) {
			frames++;
			if (frames == maxFrames) {
				index++;
				frames = 0;
			}
		}
	}
	
	private void Collision() {
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(Entity.isColiding(e, this)) {
				e.life -= damage;
				knockBack(this, e);
			}
		}	
	}
	
	public void tick() {
		time++;
		animate();
		
		if (index == maxIndex) {
			Collision();
		}
		
		if (time == this.timeLife) {
			this.die();
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, this.width, this.height, null);
	}
}
