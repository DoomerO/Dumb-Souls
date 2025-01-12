package entities.runes;

import main.Game;
import java.awt.image.BufferedImage;

public class Life_Rune extends Rune {
	
	private boolean effectsAplied;
	public static BufferedImage sprite = Game.sheet.getSprite(0, 256, 16, 16);
	
	public Life_Rune() {
		super(sprite);
		name = "Rune of Life";
		index = 1;
		description = "Health +50, Regen +0.1%";
	}
	
	public void tick() {
		if (!effectsAplied) {
			Game.player.maxLife += 50;
			Game.player.lifeRec += 0.001;
			effectsAplied = true;
		}
	}
}
