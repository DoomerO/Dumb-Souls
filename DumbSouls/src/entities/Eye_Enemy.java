package entities;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import world.Camera;
import world.World;
import main.Game;

public class Eye_Enemy extends Enemy{
	private int frames, maxFrames = 15, index, maxIndex = 3, timeAtk = 0;
	private BufferedImage spriteAtk = Game.sheet.getSprite(0, 160, 16, 16);
	public Enemy_Shot atk;
	
	public Eye_Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		this.getAnimation(48, 80, 16, 16, 3);
		this.expValue = 25;
		this.soulValue = 2;
		this.maxLife = 15 + (int)(15 * 0.05 * World.wave);
		this.life = maxLife;
		this.maxSpeed = 0.8;
		this.frost = 0;
		this.speed = this.maxSpeed;
		this.setMask(3, 3, 12, 11);
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
		Game.player.exp +=  this.expValue;
		Game.player.souls += this.soulValue;
	}
	
	private void attack() {
		timeAtk++;
		if (timeAtk == 60) {
			double ang = Math.atan2((Game.player.getY() - Camera.y) - (this.getY() - Camera.y) ,(Game.player.getX() - Camera.x) - (this.getX() - Camera.x));
			double dx = Math.cos(ang);
			double dy =  Math.sin(ang);
			
			atk = new Enemy_Shot(this.getX(), this.getY(), 6, 3, spriteAtk, dx, dy, 15, 3, 35);
			Game.eShots.add(atk);
			timeAtk = 0;
		}
	}
	
	
	public void tick() {
		animate();
		
		if (Entity.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) >= 80) {
			this.movement();
		}
		else {
			attack();
		}
		this.shotDamage();

		this.frostEffect(0.995);

		if (this.life <= 0) {
			die();
		}
		
	}
	
	public void render(Graphics g) {
		g.drawImage(animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
