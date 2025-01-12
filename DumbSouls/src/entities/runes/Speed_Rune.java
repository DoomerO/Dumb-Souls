package entities.runes;

import java.awt.image.BufferedImage;

import main.Game;

public class Speed_Rune extends Rune {
	
	public static BufferedImage sprite = Game.sheet.getSprite(32, 256, 16, 16);
	
	public Speed_Rune() {
		super(sprite);
		name = "Rune of Speed";
		index = 3;
		description = "Speed +0.5";
	}
	
	public void tick() {
		Game.player.speedBoost *= 1.5;
	}
}
