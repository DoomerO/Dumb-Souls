package entities;

import java.awt.image.BufferedImage;

import main.*;

public class Enemy extends Entity{
	
	protected BufferedImage[] animation;
	public static BufferedImage baseSprite = Game.sheet.getSprite(0, 80, 16, 16);
	public int maxLife, expValue, soulValue;
	public double speed, maxSpeed, frost, life;
	protected boolean spawning;
	protected int timeSpawn, contTS;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	protected void getAnimation(int x, int y, int width, int height, int frames) {
		this.animation = new BufferedImage[frames];
		
		for(int i = 0; i < animation.length; i++ ) {
			animation[i] = Game.sheet.getSprite(x , y, width, height);
			x += width;
		}
	}

	protected void frostEffect(double measure) {
		speed = maxSpeed - frost;
		if (speed < 0) {
			speed = 0;
		}
		frost *= measure;
	}
	
	protected void shotDamage() {
		for (int i = 0;  i < Game.shots.size(); i++) {
			Shot e = Game.shots.get(i);
			if (isColiding(this, e)) {
				this.life -= e.damage;
				knockBack(Game.player, this);
				e.die();
				if (Game.player.playerWeapon instanceof Ice_Weapon) {
					Ice_Weapon.IceAffect(this, e);
				}
			}
		}
	}

	protected void movement() {
		int xP = Game.player.getX();
		int yP = Game.player.getY();
		double angle = getAngle(yP, this.y, xP, this.x);

		this.x += Math.cos(angle) * this.speed;
		this.y += Math.sin(angle) * this.speed;
	}

	protected void reverseMovement() {
		int xP = Game.player.getX();
		int yP = Game.player.getY();
		double angle = getAngle(yP, this.y, xP, this.x);

		this.x -= Math.cos(angle) * this.speed;
		this.y -= Math.sin(angle) * this.speed;
	}
	
	protected void objectiveMovement(int xObjct, int yObjct) {
		double angle = getAngle(yObjct, this.y, xObjct, this.x);
		
		this.x += Math.cos(angle) * this.speed;
		this.y += Math.sin(angle) * this.speed;
	}
	
	protected void spawnAnimation(int frames) {
		if (contTS == 0) {
			Game.enemies.add(new Enemy_Animation(this.getX() - this.width/2, this.getY() - this.height, this.width*2, this.height*2, null, timeSpawn, frames, 3, 112, 144, 32, 32, "frames_1", null));
		}
		this.contTS++;
		if (this.contTS == this.timeSpawn) {
			spawning = false;
		}
	}
}