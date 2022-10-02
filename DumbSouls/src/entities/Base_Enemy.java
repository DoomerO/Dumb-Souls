package entities;

import java.awt.image.BufferedImage;

import world.Camera;
import main.*;

import java.awt.Graphics;

public class Base_Enemy extends Enemy{
	
	private int index, maxIndex = 3, frames, maxFrames = 10, timer = 0;;
	
	public Base_Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		this.getAnimation(0, 80, 16, 16, 3);
		this.expValue = 10;
		this.soulValue = 1;
		this.maxLife = 10;
		this.life = maxLife;
		this.maxSpeed = 1;
		this.frost = 0;
		this.speed = this.maxSpeed;
		this.setMask(3, 2, 8, 14);
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
	
	private void attack() {
		Game.player.life -= 8;
		timer = 0;
	}
	
	private void die() {
		Game.enemies.remove(this);
		Game.player.exp +=  this.expValue;
		Game.player.souls += this.soulValue;
	}
	
	public void tick() {
		animate();
		
		if (!isColiding(this, Game.player)) {
			this.movement();
		}
		else {
			if (timer % 15 == 0) {
				attack();
				timer = 0;
			}
			timer += 1;
		}

		this.frostEffect(0.99);
		
		this.shotDamage();
		if (life <= 0) {
			die();
		}
		
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
