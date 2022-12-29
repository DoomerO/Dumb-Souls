package entities;

import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;

import java.awt.Graphics;

public class Weapon {
	
	public BufferedImage sprite;
	protected BufferedImage[] animation;
	private int frames, maxFrames = 10, index, maxIndex = 3;
	public int mx, my, attackTimer;
	public String[] listNames;
	public boolean md1, md2, md3;
	protected boolean dashAva = true, ablt2Ava = true, ablt3Ava = true;
	
	public Weapon(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	protected void getAnimation(int x, int y, int width, int height, int frames) {
		this.animation = new BufferedImage[frames];
		
		for(int i = 0; i < animation.length; i++ ) {
			animation[i] = Game.sheet.getSprite(x , y, width, height);
			x += 16;
		}
	}
	
	protected void setAttackTimer(int frames){
		this.attackTimer = frames;
	}

	public void checkOptions(String opt) {
		
	}
	
	public String checkOptionName(int opt) {
		switch(opt){
			case 0:
				return this.listNames[0];
			case 1:
				return this.listNames[1];
			case 2:
				return this.listNames[2];
			case 3:
				return this.listNames[3];
			case 4:
				return this.listNames[4];
			case 5:
				return this.listNames[5];
			case 6:
				return this.listNames[6];
			case 7:
				return this.listNames[7];
			case 8:
				return this.listNames[8];
			default:
				return "";
		}
	}
	
	public void tick() {
		frames ++;
		if (frames == maxFrames) {
			index++;
			frames = 0;
			if (index == maxIndex) {
				index = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(this.animation[index], (Game.player.getX() - Camera.x) - 12, (Game.player.getY() - Camera.y) - 8, null);
	}
	
	public void Attack() {
		
	}
	
	public void Dash() {
		
	}
	
	public void Ablt2() {
		
	}
	
	public void Ablt3() {
		
	}
	
	public void Effect() {
		
	}
}
