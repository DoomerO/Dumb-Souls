package entities;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import world.Camera;
import main.Game;

public class BAE_Spike extends Atack_Entity{
	
	private int life = 0, index, damage;
	
	public BAE_Spike(int x, int y, int width, int height, BufferedImage sprite, int timeLife, int dmg) {
		super(x, y, width, height, sprite, timeLife);
		this.depth = 0;
		this.damage = dmg;
		this.getAnimation(16, 160, 16, 16, 3);
		this.setMask(2, 0, 8, 16);
	}
	
	public void tick() {
		life++;
		if (life > 30) {
			index = 2;
			atack();
		}
		if (this.life == this.timeLife) {
			this.die();
		}
	}
	
	private void atack() {
		if (Entity.isColiding(Game.player, this)) {
			Game.player.life -= damage;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
