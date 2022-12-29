package entities;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import main.Game;
import world.World;
import world.Camera;

public class Trapper_Enemy extends Enemy{
	private int xP, yP, index = 1, maxIndex = 3, frames, maxFrames = 10, timer = 0, cont = 0;
	private boolean stage2 = false;
	
	public Trapper_Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.getAnimation(240, 80, 16, 16, 3);
		this.expValue = 37;
		this.soulValue = 5;
		this.maxLife = 40 + (int)(40 * 0.01 * World.wave);
		this.life = maxLife;
		this.maxSpeed = 1;
		this.frost = 0;
		this.speed = this.maxSpeed;
		xP = Game.player.getX();
		yP = Game.player.getY();
		this.spawning = true;
		this.timeSpawn = 180;
	}
	
	private void animate() {
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index == maxIndex) {
				index = 1;
			}
		}
	}
	
	private void attack() {
		Game.player.life -= 48;
		timer = 0;
	}
	
	private void die() {
		Game.enemies.remove(this);
		Game.entities.add(new EXP_Orb(this.getX(), this.getY(), 16, 16, Enemy.baseSprite, this.expValue));
		Player.souls += this.soulValue;
	}
	private void stage2() {
		this.shotDamage();
		this.setMask(2, 0, 14, 32);
		animate();
		cont++;
		if (isColiding(this, Game.player)) {
			if (timer % 30 == 0) {
				attack();
			}
			timer += 1;
		}
		if (cont >= 120) {
			stage2 = false;
			cont = 0;
			this.xP = Game.player.getX();
			this.yP = Game.player.getY();
		}
	}
	
	public void tick() {
		if (!spawning) {
			if (this.getX() != xP && this.getY() != yP) {
				objectiveMovement(xP, yP);
				this.setMask(0, 0, 0, 0);
			}
			else {
				stage2 = true;
				stage2();
			}
			
			this.frostEffect(0.995);
			
			if (life <= 0) {
				die();
			}
		}
		else {
			this.spawnAnimation(60);
		}
	}
	
	public void render(Graphics g) {
		if (stage2) {
			g.drawImage(animation[index], this.getX() - Camera.x, this.getY() - Camera.y, 16, 32, null);
		}
		else {
			g.drawImage(animation[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
