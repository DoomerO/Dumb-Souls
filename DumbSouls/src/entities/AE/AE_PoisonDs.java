package entities.AE;

import entities.enemies.Enemy;
import entities.shots.Shot;
import java.awt.image.BufferedImage;
import java.util.function.Function;
import main.Game;
import world.Camera;

public class AE_PoisonDs extends AE_Attack_Entity{
	
	private int time, frames, maxFrames = 10, index, maxIndex = 2, dmg;
	
	public AE_PoisonDs(int x, int y, int width, int height, BufferedImage sprite, int time, int dmg) {
		super(x, y, width, height, sprite, time);
		this.dmg = dmg;
		setMask(0, 0, width, height);
		getAnimation(64, 112, 16, 16, 2);
		depth = 2;
	}
	
	public void tick() {
		time ++;
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index == maxIndex) {
				index = 0;
			}
		}
		x = Game.player.centerX();
		y = Game.player.centerY();
		if (time == life) {
			die();
		}
		collisionEnemiesShots(false, false, 0, enemyCollision, shotCollision);
	}

	Function<Enemy, Void> enemyCollision = (target) -> { 
		if (time % 5 == 0) {
			target.slowness = Math.max(target.slowness, dmg);
			target.life -= dmg;
		}
		return null;
	};

	Function<Shot, Void> shotCollision = (target) -> { 
		Game.eShots.remove(target);
		return null;
	};
	
	public void render() {
		Game.gameGraphics.drawImage(animation[index], getX() - Camera.getX(), getY() - Camera.getY(), width, height, null);
	}
}
