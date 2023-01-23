package entities.runes;

import java.awt.image.BufferedImage;
import main.Game;

public class MultiAttack_Rune extends Rune{
	public static BufferedImage sprite = Game.sheet.getSprite(48, 256, 16, 16);
	
	public MultiAttack_Rune() {
		super(sprite);
		this.name = "Double Attack Rune";
		this.description = "Adds 1 aditional normal attack.";
	}
	
	public void tick() {
		if (Game.player.attack) {
			Game.player.playerWeapon.AttackRandom();
		}
	}
}
