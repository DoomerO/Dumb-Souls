package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Shader {

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