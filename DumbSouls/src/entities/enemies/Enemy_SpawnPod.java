package entities.enemies;

import main.Game;

public class Enemy_SpawnPod extends Enemy {
	
	public Enemy_SpawnPod(int x, int y, int w, int h, int time, boolean special) {
		super(x, y, w, h, null);
		life = time;
		specialRare = special;
		getAnimation(112, 153, 32, 23, 3);
		maxFrames = time / 3;
		this.maxIndex = 3;
		depth = 2;
	}
	
	public void tick() {
		attackTimer++;
		animate();
		
		if (attackTimer == life) {
			Game.enemies.remove(this);
		}
		
	}
}
