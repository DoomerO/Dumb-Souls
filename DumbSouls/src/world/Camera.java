package world;

public class Camera {
	public static int x;
	public static int y;
	
	public static int Clamp(int xAtual, int xmin, int xmax) { 
		if (xAtual < xmin) {
			xAtual = xmin;
		} 
		if (xAtual > xmax) {
			xAtual = xmax;
		}
		return xAtual;
	}
}
