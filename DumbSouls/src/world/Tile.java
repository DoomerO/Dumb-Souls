package world;

import java.awt.image.BufferedImage;

import main.Game;

public class Tile {
	
	public static BufferedImage[] floor_sprite = {Game.sheet.getSprite(0, 0, 16, 16),
			Game.sheet.getSprite(16, 0, 16, 16), Game.sheet.getSprite(32, 0, 16, 16)};
	
	public static BufferedImage wall_sprite = Game.sheet.getSprite(48, 0, 16, 16);
	
	private BufferedImage sprite;
	private int x, y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render() {
		Game.gameGraphics.drawImage(sprite, x - Camera.getX(), y - Camera.getY(), null);
	}
}
