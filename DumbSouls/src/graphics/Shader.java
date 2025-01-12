package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Shader {

	public static BufferedImage reColor(BufferedImage sprite, int hue){
		int w = sprite.getWidth();
		int h = sprite.getHeight();
		BufferedImage recolored = new BufferedImage(w, h, sprite.getType());
		Graphics2D graphic = recolored.createGraphics();
		int[] pixels = new int[w * h];
		sprite.getRGB(0, 0, w, h, pixels, 0, w);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] ^= hue;
		}
		recolored.setRGB(0, 0, w, h, pixels, 0, w);
		graphic.drawImage(recolored, null, 0, 0);
		graphic.dispose();
		return recolored;
	}

    public static BufferedImage rotate(BufferedImage sprite, double angle) {
		int w = sprite.getWidth();    
		int h = sprite.getHeight();
	
		BufferedImage rotated = new BufferedImage(w, h, sprite.getType());  
		Graphics2D graphic = rotated.createGraphics();

		graphic.rotate(angle, w/2, h/2);
		graphic.drawImage(sprite, null, 0, 0);
		graphic.dispose();
		return rotated;
	}
}