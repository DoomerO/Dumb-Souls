package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Shader {

	public static BufferedImage reColor(BufferedImage sprite, int hue){
		int w = sprite.getWidth();
		int h = sprite.getHeight();
		BufferedImage recolored = new BufferedImage(w, h, sprite.getType());
		Graphics2D graphic = recolored.createGraphics();
		 for(int x = 0; x < w; x++){
			for(int y = 0; y < h; y++){
				int color = sprite.getRGB(x, y);
				int setColor = color + hue;
				recolored.setRGB(x, y, setColor);
			}
		 }
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