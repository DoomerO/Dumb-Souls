package main;

import java.awt.Color;
import java.awt.Font;
import entities.Player;
import graphics.TextObject;

public class Menu_Level {
	private static TextObject[] optionsBox;
	private static int cur;

	public Menu_Level(int numOP) {
		optionsBox = new TextObject[numOP];
		for (int c = 0; c < numOP; c++) {
			optionsBox[c] = new TextObject("arial", Font.BOLD, 10, "Blank", 120, 60 + 90 / numOP * c, Color.white);
		}
	}
	
	private static void sortOptions(int numOP) {
		int opt = 0, lastOpt = -1;
		for (int c = 0; c < numOP; c++) {
			opt = Game.rand.nextInt(Game.player.playerWeapon.listNames.length);
			while (opt == lastOpt) {
				opt = Game.rand.nextInt(Game.player.playerWeapon.listNames.length);
			}
			optionsBox[c].updateText(Game.player.playerWeapon.checkOptionName(opt));
			lastOpt = opt;
		}
	}
	
	public static void tick() {
		if (Game.player.levelUp) {
			sortOptions(3);
			Game.player.levelUp = false;
		}
		for(int i = 0; i < optionsBox.length; i++){
			if(optionsBox[i].hover()) cur = i;
		}
		if (Game.keyController.contains(87) || Game.keyController.contains(38)) {//W UP
			cur --;
			if (cur < 0) 
				cur = optionsBox.length - 1;
		}
		if (Game.keyController.contains(83) || Game.keyController.contains(40)) {//S DOWN
			cur ++;
			if (cur > optionsBox.length - 1)
				cur = 0;
		}
		if (Game.keyController.contains(10) || optionsBox[cur].click()) {
			Game.player.playerWeapon.checkOptions(optionsBox[cur].txt);
			Game.player.life = Game.player.maxLife;
			Game.gameStateHandler = Game.gameState.NORMAL;
		}
		 
		if (Game.keyController.contains(32) && Player.souls >= 20) {
			Player.souls -= 20;
			sortOptions(3);
		}
		Game.keyController.clear();
		Game.clickController.clear();
	}
	
	public static void render() {
		Game.gameGraphics.setColor(Color.black);
		Game.gameGraphics.fillRect(0, 0, Game.width * Game.scale, Game.height * Game.scale);
			
		for(TextObject opt : optionsBox){
			opt.render();
		}
		
		if (cur == 0) {
			Game.gameGraphics.drawString(">", 100, 60);
		}
		else if (cur == 1) {
			Game.gameGraphics.drawString(">", 100, 90);
		}
		else if (cur == 2) {
			Game.gameGraphics.drawString(">", 100, 120);
		}
		Game.gameGraphics.setColor(new Color(74, 52, 160));
		Game.gameGraphics.drawString("Souls : " + Player.souls, 255, 20);
		Game.gameGraphics.drawString("[space] Refresh -20 souls", 180, 150);
	}
}