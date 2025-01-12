package entities.runes;

import java.awt.image.BufferedImage;

import main.Game;

public class EXP_Rune extends Rune {
	private int timer;
	public static BufferedImage sprite = Game.sheet.getSprite(64, 256, 16, 16);
	
	public EXP_Rune() {
		super(sprite);
		name = "Rune of Experience";
		index = 4;
		description = "Passive EXP +10, scales per level";
	}
	
	public void tick() {
		timer++;
		if(timer == 120) {
			timer = 0;
			Game.player.exp += 10 + (Game.player.level / 2);
		}
	}
}
