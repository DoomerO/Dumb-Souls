package entities;

import java.awt.image.BufferedImage;

import main.*;

public class Enemy extends Entity{
	
	protected BufferedImage[] animation;
	public static BufferedImage baseSprite = Game.sheet.getSprite(0, 80, 16, 16);
	public int life, expValue, soulValue;
	public double speed, maxSpeed, frost;
	
	
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
	
	protected void shootDamage() {
		for (int i = 0;  i < Game.shoots.size(); i++) {
			Shoot e = Game.shoots.get(i);
			if (isColiding(this, e)) {
				this.life -= e.damage;
				Game.shoots.remove(e);
				if (Game.player.playerWeapon instanceof Ice_Weapon) {
					Ice_Weapon.IceAffect(this, e);
				}
			}
		}
	}
}