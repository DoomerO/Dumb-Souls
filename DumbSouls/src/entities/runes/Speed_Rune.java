package entities.runes;

import java.awt.image.BufferedImage;

import main.Game;

public class Speed_Rune extends Rune {
	
	private boolean effectsApllyed;
	public static BufferedImage sprite = Game.sheet.getSprite(32, 256, 16, 16);
	
	public Speed_Rune() {
		super(sprite);
		this.name = "Rune of Speed";
		this.description = "Speed +0.5";
	}
	
	public void tick() {
		if (!effectsApllyed) {
			Game.player.maxSpeed += 0.5;
			Game.player.speed = Game.player.maxSpeed;
			effectsApllyed = true;
		}
	}
}
