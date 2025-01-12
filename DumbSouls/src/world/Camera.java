package world;

import main.Game;

public class Camera {
	protected static int x = World.WIDTH, y = World.HEIGHT;
	
	public static void Clamp(int targetX, int targetY) { 
		if (targetX < 0) {
			targetX = 0;
		} 
		else if (targetX > World.WIDTH * 16 - Game.width) {
			targetX = World.WIDTH * 16 - Game.width;
		}
		if (targetY < 0) {
			targetY = 0;
		} 
		else if (targetY > World.HEIGHT * 16 - Game.height) {
			targetY = World.HEIGHT * 16 - Game.height;
		}
		final int deltaX = targetX - x, deltaY = targetY - y;
		double magnitude = Math.hypot(deltaX, deltaY);
		if(magnitude == 0.0) magnitude = 1.0;

		x += deltaX / magnitude * Math.ceil(magnitude / 10);
		y += deltaY / magnitude * Math.ceil(magnitude / 10);
	}

	public static int getX(){
		return x;
	}

	public static int getY(){
		return y;
	}

	public static void centerPlayer(){
		x = Game.player.getX();
		y = Game.player.getY();
	}
}