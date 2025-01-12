package entities.AE;

import java.awt.image.BufferedImage;
import java.awt.Color;
import main.Game;
import entities.*;
import entities.enemies.Enemy;
import world.Camera;

public class AE_ManaRay extends AE_Attack_Entity {
	
	private static Color manaColor = new Color(47, 141, 224, 150);
	private int time = 0;
	private Enemy closest;
	
	public AE_ManaRay(int x, int y, int width, int height, BufferedImage sprite, int time, int dmg) {
		super(x, y, width, height, sprite, time);
		damage = dmg;
		depth = 2;
		getAnimation(208, 112, 12, 12, 1);
	}
	
	private void findClosest() {
		closest = null;
		double distE, distS = 144;
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy ene = Game.enemies.get(i);
			distE = Entity.calculateDistance(Game.player.centerX(), Game.player.centerY(), ene.centerX(), ene.centerY());
			if(closest != null) distS = Entity.calculateDistance(Game.player.centerX(), Game.player.centerY(), closest.centerX(), closest.centerY());
			if(distE < distS && distE < 144) {
				closest = ene;
			}
		}
	}
	
	private void Collision() {
		findClosest();
		if (time % 10 == 0 && closest != null) {
			closest.life -= damage;
		}
	}
	
	public void tick() {
		time++;
		Collision();
		x = Game.player.centerX();
		y = Game.player.centerY();
		
		if (time == life) {
			die();
		}
		if(closest == null) return; 
		width = (int)Entity.calculateDistance(Game.player.centerX(), Game.player.centerY(), closest.centerX(), closest.centerY());
	}
	
	public void render() {
		if(closest == null) return;
		Game.gameGraphics.setColor(manaColor);
		Game.gameGraphics.drawLine(Game.player.centerX() - Camera.getX(), Game.player.centerY() - Camera.getY(), closest.centerX() - Camera.getX(), closest.centerY()- Camera.getY());
	}
}
