package entities.runes;

import java.awt.image.BufferedImage;

import main.Game;

public class Mana_Rune extends Rune{
	
	private boolean effectsAplied;
	public static BufferedImage sprite = Game.sheet.getSprite(16, 256, 16, 16);
	
	public Mana_Rune() {
		super(sprite);
		name = "Rune of Mana";
		index = 2;
		description = "Max Mana +50, Mana Regen +2";
	}
	
	public void tick() {
		if (!effectsAplied) {
			Game.player.maxMana += 50;
			Game.player.manaRec += 2;
			effectsAplied = true;
		}
	}
	
}
