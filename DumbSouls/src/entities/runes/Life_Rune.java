package entities.runes;

import main.Game;
import java.awt.image.BufferedImage;

public class Life_Rune extends Rune {
	
	private boolean effectsApllyed;
	public static BufferedImage sprite = Game.sheet.getSprite(0, 256, 16, 16);
	
	public Life_Rune() {
		super(sprite);
		this.name = "Rune of Life";
		this.description = "Health +50, Regen +0.001";
	}
	
	public void tick() {
		if (!effectsApllyed) {
			Game.player.maxLife += 50;
			Game.player.lifeRec += 0.001;
			effectsApllyed = true;
		}
	}
}
