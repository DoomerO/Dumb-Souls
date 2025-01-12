package entities.AE;

import entities.enemies.Enemy;
import java.util.function.Function;
import main.Game;
import world.Camera;

public class AE_ManaExplosion extends AE_Attack_Entity{
	
	private int maxFrames, maxIndex, frames, index, time;
	
	public AE_ManaExplosion(int x, int y, double dmg, int knockback) {
		super(x, y, 32, 32, null, 10);
		getAnimation(160, 128, 16, 16, 2);
		setMask(0, 0, width, height);
		damage = dmg;
		push = knockback;
		maxFrames = 5;
		maxIndex = 2;
	}
	
	private void animate() {
		if (index < maxIndex) {
			frames++;
			if (frames == maxFrames) {
				index++;
				frames = 0;
			}
		}
	}
	
	private void Collision() {
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy ene = Game.enemies.get(i);
			if(isColiding(ene)) {
				ene.life -= damage;
				ene.receiveKnockback(this);
			}
		}	
	}

	Function<Enemy, Void> attackCollision = (target) -> { 
		target.life -= damage;
		target.receiveKnockback(this);
        return null;
	};
	
	public void tick() {
		time++;
		animate();
		
		if (index == maxIndex) {
			collisionEnemy(false, 0, attackCollision);
			Collision();
		}
		
		if (time == life) {
			die();
		}
	}
	
	public void render() {
		Game.gameGraphics.drawImage(animation[index], getX() - Camera.getX(), getY() - Camera.getY(), width, height, null);
	}
}
