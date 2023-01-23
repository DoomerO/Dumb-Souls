package entities.runes;

import java.awt.image.BufferedImage;

public class Rune {
	
	public BufferedImage sprite;
	public boolean equiped;
	public String name, description;
	
	public Rune(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	public void tick() {
		
	}
	
}
