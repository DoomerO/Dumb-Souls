package entities;

import java.awt.image.BufferedImage;

import world.Camera;
import world.World;
import main.*;

import java.awt.Graphics;

public class Barrier_Enemy extends Enemy{
	
	private int index, maxIndex = 2, frames, maxFrames = 20, timer = 0;
	
	public Barrier_Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		this.getAnimation(144, 80, 48, 32, 2);
		this.expValue = 30;
		this.soulValue = 12;
		this.maxLife = 250 + (int)(250 * 0.01 * World.wave);
		this.life = maxLife;
		this.maxSpeed = 0.6;
		this.speed = this.maxSpeed;
		this.frost = 0;
		this.setMask(1, 1, 46, 30);
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
		Game.player.life -= 82;
		timer = 0;
	}
	
	private void die() {
		Game.enemies.remove(this);
		Game.enemies.add(new Mimic_Enemy(this.getX(), this.getY() - 16, 16, 16, Enemy.baseSprite, this.expValue));
		Game.enemies.add(new Mimic_Enemy(this.getX() - 16, this.getY() + 16, 16, 16, Enemy.baseSprite, this.expValue));
		Game.enemies.add(new Mimic_Enemy(this.getX() + 16, this.getY() + 16, 16, 16, Enemy.baseSprite, this.expValue));
		Player.souls += this.soulValue;
	}
	
	public void tick() {
		animate();
		
		if (!isColiding(this, Game.player)) {
			this.movement();
		}
		else {
			if (timer % 30 == 0) {
				attack();
			}
			timer += 1;
		}

		this.frostEffect(0.92);
		
		this.shotDamage();
		if (life <= 0) {
			die();
		}
		
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
