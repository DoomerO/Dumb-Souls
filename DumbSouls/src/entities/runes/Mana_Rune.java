package entities.runes;

import java.awt.image.BufferedImage;

import main.Game;

public class Mana_Rune extends Rune{
	
	private boolean effectsApllyed;
	public static BufferedImage sprite = Game.sheet.getSprite(16, 256, 16, 16);
	
	public Mana_Rune() {
		super(sprite);
		this.name = "Rune of Mana";
		this.description = "Max Mana +50, Mana Regen +2";
	}
	
	public void tick() {
		if (!effectsApllyed) {
			Game.player.maxMana += 50;
			Game.player.manaRec += 2;
			effectsApllyed = true;
		}
	}
	
}
