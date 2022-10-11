package entities;

import java.awt.image.BufferedImage;

import world.Camera;
import world.World;
import main.*;

import java.awt.Graphics;

public class Mimic_Enemy extends Enemy{
    private int index, maxIndex = 3, frames, maxFrames = 6, timer = 0;
	
	public Mimic_Enemy(int x, int y, int width, int height, BufferedImage sprite, int expValue) {
		super(x, y, width, height, sprite);
		
		this.getAnimation(48, 144, 16, 16, 3);
        this.expValue = expValue;
        this.maxLife = 8 + (int)(8 * 0.01 * World.wave);
        this.life = this.maxLife;
		this.expValue = expValue;
        this.maxSpeed = 0.2;
        this.speed = this.maxSpeed;
        this.frost = 0;
		this.setMask(1, 1, 14, 14);
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
		Game.player.life -= 6;
		timer = 0;
	}

    public void die(){
        Game.enemies.remove(this);
        Game.player.exp +=  this.expValue;
    }

    public void tick() {
		animate();

        if (calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 48){
            this.maxSpeed = 1.5;
        }

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
        this.frostEffect(0.95);

        this.shotDamage();
		if (life <= 0) {
			die();
		}
	}

    public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}