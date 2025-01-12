package entities.AE;

import entities.enemies.Enemy;
import java.awt.image.BufferedImage;
import java.util.function.Function;
import main.Game;

public class AE_WindDS extends AE_Attack_Entity {
	
	private int time;
	
	public AE_WindDS(int x, int y, int width, int height, BufferedImage sprite, int time) {
		super(x, y, width, height, sprite, time);
		push = 10;
		setMask(0, 0, 16, 16);
		getAnimation(48, 112, 16, 16, 1);
		depth = 2;
	}
	
	public void tick() {
		time ++;
		x = Game.player.getX();
		y = Game.player.getY();
		if (time == life) {
			die();
		}
		collisionEnemy(false, 0, attackCollision);
	}

	Function<Enemy, Void> attackCollision = (target) -> { 
		target.life -= 0.2;
		target.receiveKnockback(this);
		return null;
	};
}
