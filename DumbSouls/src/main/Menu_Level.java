package main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Menu_Level {
	private String[] options;
	
	public boolean up, down, enter, space;
	private int cur;
	
	public Menu_Level(int numOP) {
		options = new String[numOP];
		for (int c = 0; c < numOP; c++) {
			options[c] = "";
		}
	}
	
	private void sortOptions(int numOP) {
		int opt = 0, lastOpt = -1;
		for (int c = 0; c < numOP; c++) {
			opt = Game.rand.nextInt(Game.player.playerWeapon.listNames.length);
			while (opt == lastOpt) {
				opt = Game.rand.nextInt(Game.player.playerWeapon.listNames.length);
			}
			options[c] = Game.player.playerWeapon.checkOptionName(opt);
			lastOpt = opt;
		}
	}
	
	public void tick() {
		if (Game.player.levelUp) {
			sortOptions(3);
			Game.player.levelUp = false;
		}
		 if (up) {
			 up = false;
			 cur --;
			 if (cur < 0) {
				 cur = options.length - 1;
			 }
		 }
		 else if (down) {
			 down = false;
			 cur ++;
			 if (cur > options.length - 1) {
				 cur = 0;
			 }
		 }
		 if (enter) {
			enter = false;
			Game.player.playerWeapon.checkOptions(options[cur]);
			Game.player.life = Game.player.maxLife;
			Game.gameState = "NORMAL";
		 }
		 
		 if (space) {
			 space = false;
			 if (Game.player.souls >= 100) {
				 Game.player.souls -= 100;
				 sortOptions(3);
			 }
		 }
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.width * Game.scale, Game.height * Game.scale);
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 10));
			
		g.drawString(options[0], 120, 60);
		g.drawString(options[1], 120, 90);
		g.drawString(options[2], 120, 120);
		
		if (cur == 0) {
			g.drawString(">", 100, 60);
		}
		else if (cur == 1) {
			g.drawString(">", 100, 90);
		}
		else if (cur == 2) {
			g.drawString(">", 100, 120);
		}
		
		g.setColor(new Color(74, 52, 160));
		g.drawString("Souls : " + Game.player.souls, 255, 20);
		g.drawString("[space] Refresh -100 souls", 180, 150);
	}
}
