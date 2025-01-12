package entities.AE;

import entities.Entity;
import entities.enemies.Enemy;
import entities.shots.Shot;
import java.awt.image.BufferedImage;
import java.util.function.Function;
import main.Game;
import world.Camera;

public class AE_Attack_Entity extends Entity {
	protected int tickTimer;
	protected BufferedImage[] animation;
	
	public AE_Attack_Entity(int x, int y, int width, int height, BufferedImage sprite, int life) {
		super(x, y, width, height, sprite);
		this.life = life;
	}
	
	protected void getAnimation(int x, int y, int width, int height, int frames) {
		animation = new BufferedImage[frames];
		
		for(int i = 0; i < animation.length; i++ ) {
			animation[i] = Game.sheet.getSprite(x , y, width, height);
			x += width;
		}
	}
	
	protected void die() {
		Game.entities.remove(this);
	}
	
	public void tick() {
		
	}

	protected void collisionEnemy(boolean hasTickTimer, int framesDamage, Function<Enemy, Void> attack) {
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy ene = Game.enemies.get(i);
			if(hasTickTimer){
				if(TickTimer(framesDamage)) {
					if (isColiding(ene)){
						attack.apply(ene);
					}
				}
			}
			else {
				if (isColiding(ene)){
					attack.apply(ene);
				}
			}
		}
	}

	protected void collisionEnemiesShots(boolean hasTickTimerEnemy, boolean hasTickTimerShot, int framesDamage, Function<Enemy, Void> enemies, Function<Shot, Void> shots) {
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy ene = Game.enemies.get(i);
			if(hasTickTimerEnemy){
				if(TickTimer(framesDamage)) {
					if (isColiding(ene)){
						enemies.apply(ene);
					}
				}
			}
			else {
				if (isColiding(ene)){
					enemies.apply(ene);
				}
			}
		}
		for (int i = 0; i < Game.eShots.size(); i++) {
			Shot eSh = Game.eShots.get(i);
			if(hasTickTimerShot){
				if(TickTimer(framesDamage)) {
					if (isColiding(eSh)){
						shots.apply(eSh);
					}
				}
			}
			else {
				if (isColiding(eSh)){
					shots.apply(eSh);
				}
			}
		}
	}

	protected boolean TickTimer(int frames){
		if (tickTimer % frames == 0){
			return true;
		}
		else{
			return false;
		}
	}

	protected void refreshTick(){
		tickTimer++;
		if (tickTimer >= 60){
			tickTimer = 0;
		}
	}
	
	public void render() {
		Game.gameGraphics.drawImage(animation[0], getX() - Camera.getX(), getY() - Camera.getY(), null);
	}
}
