package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import main.*;
import world.World;

public class UI {
	
	private Spritesheet keyIcons = new Spritesheet("res/keyIcons.png");
	private BufferedImage spaceIcon = keyIcons.getSprite(0, 0, 16, 16),
	shiftIcon = keyIcons.getSprite(32, 0, 16, 16),
	controlIcon = keyIcons.getSprite(16, 0, 16, 16);

	private void lifeBar() {
		Game.gameGraphics.setColor(new Color(10, 10, 10));
		Game.gameGraphics.fillRect(5, 5, 200 + 4, 14);
		Game.gameGraphics.setColor(Color.red);
		Game.gameGraphics.fillRect(7, 7, ((int)(Game.player.life * 100) / Game.player.maxLife) * 2, 10);
	}

	private void wave() {
		Game.gameGraphics.setColor(new Color(50, 50, 50));
		Game.gameGraphics.fillRect(900, 5, 50, 30);
		Game.gameGraphics.setColor(Color.white);
		Game.gameGraphics.setFont(new Font("arial", Font.BOLD, 10));
		Game.gameGraphics.drawString("Wave: " + World.wave, 905, 24);
	}
	
	private void level() {
		Game.gameGraphics.setColor(new Color(50, 50, 50));
		Game.gameGraphics.fillRect(204, 5, 33, 15);
		Game.gameGraphics.setColor(Color.white);
		Game.gameGraphics.setFont(new Font("arial", Font.BOLD, 10));
		Game.gameGraphics.drawString("Lv." + Game.player.level, 210, 16);
	}
	
	private void levelBar() {
		Game.gameGraphics.setColor(new Color(12, 12, 12));
		Game.gameGraphics.fillRect(5, 26, 100 + 4, 9);
		
		Game.gameGraphics.setColor(new Color(255, 190, 28));
		Game.gameGraphics.fillRect(7, 28, (int)((Game.player.exp * 100) / Game.player.maxExp), 5);
		
	}
	
	private void manaBar() {
		Game.gameGraphics.setColor(new Color(15, 15, 15));
		Game.gameGraphics.fillRect(5, 18, 200 + 4, 9);
		
		Game.gameGraphics.setColor(new Color(63, 92, 255));
		Game.gameGraphics.fillRect(7, 20, (int)((Game.player.mana * 100) / Game.player.maxMana) * 2, 5);
	}
	
	private void bossStatus() {
		if (World.bossTime == true) {
			Game.gameGraphics.setColor(new Color(60, 60, 60));
			Game.gameGraphics.fillRect(920, 55, 40, 40);
			Game.gameGraphics.drawImage(Game.enemies.get(0).sprite, 924, 59, 32, 32, null);
			
			Game.gameGraphics.setColor(new Color(30, 30, 30));
			Game.gameGraphics.fillRect(816, 80, 104, 15);
			Game.gameGraphics.fillRect(870, 65, 50, 15);
			
			Game.gameGraphics.setColor(Color.black);
			Game.gameGraphics.fillRect(820, 85, 100, 7);
			
			Game.gameGraphics.setColor(new Color(125, 23, 145));
			Game.gameGraphics.fillRect(820, 85, (int)((Game.enemies.get(0).life * 100) / Game.enemies.get(0).maxLife), 7);
			
			Game.gameGraphics.setColor(Color.white);
			Game.gameGraphics.setFont(new Font("arial", Font.BOLD, 10));
			Game.gameGraphics.drawString(World.bossName, 874, 76);
		}
	}

	private void skills(){
		if(Game.player.playerWeapon.dashAva){
			Game.gameGraphics.setColor(new Color(50, 50, 50));
			Game.gameGraphics.fillRect(20, 320, 60, 60);
			Game.gameGraphics.setColor(Color.black);
			Game.gameGraphics.fillRect(25, 325, 50, 50);
			Game.gameGraphics.drawImage(Game.player.playerWeapon.ablt2Img, 25, 325, 50, 50, null);
			Game.gameGraphics.drawImage(spaceIcon, 56, 356, 48, 48, null);
		}
		if(Game.player.playerWeapon.ablt2Ava){
			Game.gameGraphics.setColor(new Color(50, 50, 50));
			Game.gameGraphics.fillRect(20, 400, 60, 60);
			Game.gameGraphics.setColor(Color.black);
			Game.gameGraphics.fillRect(25, 405, 50, 50);
			Game.gameGraphics.drawImage(Game.player.playerWeapon.ablt2Img, 25, 405, 50, 50, null);
			Game.gameGraphics.drawImage(shiftIcon, 56, 436, 48, 48, null);
		}
		if(Game.player.playerWeapon.ablt3Ava){
			Game.gameGraphics.setColor(new Color(50, 50, 50));
			Game.gameGraphics.fillRect(100, 400, 60, 60);
			Game.gameGraphics.setColor(new Color(15, 15, 15));
			Game.gameGraphics.fillRect(105, 405, 50, 50);
			Game.gameGraphics.drawImage(Game.player.playerWeapon.ablt3Img, 105, 405, 50, 50, null);
			Game.gameGraphics.drawImage(controlIcon, 136, 436, 48, 48, null);
		}
	}
	
	public void render() {
		lifeBar();
		level();
		manaBar();
		levelBar();
		skills();
		wave();
		bossStatus();
	}
}