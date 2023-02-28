package entities.enemies;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import world.Camera;
import world.World;
import entities.*;
import entities.orbs.*;
import main.Game;

public class Mouth_Enemy extends Enemy {
	private int frames, maxFrames = 10, index, maxIndex = 3, timer = 0;
	
	public Mouth_Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		if (specialRare){
			this.specialMult = 2;
			hue = 2502;
		}
		this.getAnimation(96, 80, 16, 16, 3);
		this.expValue = 20 * specialMult;
		this.soulValue = 2 * specialMult;
		this.maxLife = 12 * specialMult + (int)(0.12 * World.wave);
		this.life = maxLife;
		this.maxSpeed = 1 + Game.player.maxSpeed/2 + (specialMult - 1)/3;
		this.frost = 0;
		this.speed = this.maxSpeed;
		this.setMask(0, 2, 16, 14);
		this.spawning = true;
		this.timeSpawn = 180;
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
	
	private void die() {
		Game.enemies.remove(this);
		Game.entities.add(new EXP_Orb((int)this.x, (int)this.y, 16, 16, Enemy.baseSprite, this.expValue, this.specialRare));
		Player.souls += this.soulValue;
	}
	
	private void attack() {
		Game.player.life -= 26 * specialMult + 0.26 * World.wave;
		timer = 0;
	}
	
	public void tick() {
		animate();
		if (!spawning) {
			movement();
			
			if (Entity.isColiding(this, Game.player)) {
				if (timer % 30 == 0) {
					attack();
				}
				timer += 1;
			}
			
			this.frostEffect(0.8);
			
			shotDamage();
			if (this.life <= 0) {
				this.die();
			}
		}
		else {
			this.spawnAnimation(60);
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
