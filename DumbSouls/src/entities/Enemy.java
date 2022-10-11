package entities;

import java.awt.image.BufferedImage;

import main.*;

public class Enemy extends Entity{
	
	protected BufferedImage[] animation;
	public static BufferedImage baseSprite = Game.sheet.getSprite(0, 80, 16, 16);
	public int maxLife, expValue, soulValue;
	public double speed, maxSpeed, frost, life;
	
	
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
				Game.shots.remove(e);
				if (Game.player.playerWeapon instanceof Ice_Weapon) {
					Ice_Weapon.IceAffect(this, e);
				}
				if (Game.player.playerWeapon instanceof Poison_Weapon) {
					Poison_Weapon.poisonEffect(e);
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
}