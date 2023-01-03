package graphics;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import main.*;
import world.World;

public class UI {
	
	private void lifeBar(Graphics g) {
		g.setColor(new Color(10, 10, 10));
		g.fillRect(5, 5, 200 + 4, 14);
		g.setColor(Color.red);
		g.fillRect(7, 7, ((int)(Game.player.life * 100) / Game.player.maxLife) * 2, 10);
	}

	private void wave(Graphics g) {
		g.setColor(new Color(50, 50, 50));
		g.fillRect(900, 5, 50, 30);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 10));
		g.drawString("Wave: " + World.wave, 900, 22);
	}
	
	private void level(Graphics g) {
		g.setColor(new Color(50, 50, 50));
		g.fillRect(204, 5, 33, 15);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 10));
		g.drawString("Lv." + Game.player.level, 204, 15);
	}
	
	private void levelBar(Graphics g) {
		g.setColor(new Color(12, 12, 12));
		g.fillRect(5, 26, 100 + 4, 9);
		
		g.setColor(new Color(255, 190, 28));
		g.fillRect(7, 28, (int)((Game.player.exp * 100) / Game.player.maxExp), 5);
		
	}
	
	private void manaBar(Graphics g) {
		g.setColor(new Color(15, 15, 15));
		g.fillRect(5, 18, 200 + 4, 9);
		
		g.setColor(new Color(63, 92, 255));
		g.fillRect(7, 20, (int)((Game.player.mana * 100) / Game.player.maxMana) * 2, 5);
	}
	
	private void bossStatus(Graphics g) {
		if (World.bossTime == true) {
			g.setColor(new Color(60, 60, 60));
			g.fillRect(920, 55, 40, 40);
			g.drawImage(Game.enemies.get(0).sprite, 924, 59, 32, 32, null);
			
			g.setColor(new Color(30, 30, 30));
			g.fillRect(816, 80, 104, 15);
			g.fillRect(870, 65, 50, 15);
			
			g.setColor(Color.black);
			g.fillRect(820, 85, 100, 7);
			
			g.setColor(new Color(125, 23, 145));
			g.fillRect(820, 85, (int)((Game.enemies.get(0).life * 100) / Game.enemies.get(0).maxLife), 7);
			
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 10));
			g.drawString(World.bossName, 874, 76);
		}
	}
	
	public void render(Graphics g) {
		lifeBar(g);
		level(g);
		manaBar(g);
		levelBar(g);
		wave(g);
		bossStatus(g);
	}
}
