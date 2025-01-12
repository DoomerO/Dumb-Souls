package entities.runes;

import java.awt.image.BufferedImage;

public class Rune {
	
	public BufferedImage sprite;
	public boolean equipped;
	public short index = 0;
	public String name, description;
	
	public Rune(BufferedImage sprite) {
		this.sprite = sprite;
		name = "Default";
		description = "Default desc.";
	}
	
	public void tick() {
		
	}
	
}
