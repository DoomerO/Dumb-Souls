package main;

import java.awt.Color;
import java.awt.Font;

import entities.Player;
import graphics.TextObject;

public class Menu_Pause {
	private static int cur;
	private static TextObject
	resumeBox = new TextObject("arial", Font.BOLD, 10, "Resume", 120, 60, Color.white),
	initialMenuBox = new TextObject("arial", Font.BOLD, 10, "Initial Menu", 120, 90, Color.white);
	
    public static void tick() {
		if(resumeBox.hover()){
			cur = 0;
		}
		if(initialMenuBox.hover()){
			cur = 1;
		}
		if (Game.keyController.contains(83) || Game.keyController.contains(40)) {//S DOWN
			cur++;
			if (cur > 1) {
				cur = 0;
			}
		}
		if (Game.keyController.contains(87) || Game.keyController.contains(38)) {//W UP
			cur--;
			if (cur < 0) {
				cur = 1;
			}
		}
		if (Game.keyController.contains(10)) {
			if (cur == 0) {
				Game.player.stopMoving();
				Game.gameStateHandler = Game.gameState.NORMAL;
			}
			else {
				cur = 0;
				Player.die();
			}
		}
		if(resumeBox.click()){
			Game.player.stopMoving();
			Game.gameStateHandler = Game.gameState.NORMAL;
		}
		if(initialMenuBox.click()){
			cur = 0;
			Player.die();
		}
		Game.keyController.clear();
		Game.clickController.clear();
	}
	
    public static void render() {
        Game.gameGraphics.setColor(new Color(0, 0, 0, 192)); 
		Game.gameGraphics.fillRect(0, 0, Game.width, Game.height);
        Game.gameGraphics.setColor(Color.white);
		Game.gameGraphics.setFont(new Font("arial", Font.BOLD, 20));
		Game.gameGraphics.drawString("Pause", 120, 30);
		Game.gameGraphics.setFont(new Font("arial",  Font.BOLD, 10));
		
		resumeBox.render();
		initialMenuBox.render();

		Game.gameGraphics.drawString("Life : " + (int)Game.player.life + "/" + Game.player.maxLife, 10, Game.height - 15);
		Game.gameGraphics.drawString("Mana : " + (int)Game.player.mana + "/" + Game.player.maxMana, 10, Game.height - 5);
		Game.gameGraphics.drawString("Souls : " + Game.player.getSouls(), Game.width * 2 / 3, Game.height - 15);
		Game.gameGraphics.drawString("EXP : " + Game.player.exp + "/" + Game.player.maxExp, Game.width * 2 / 3, Game.height - 5);
		
		if (cur == 0) {
			Game.gameGraphics.drawString(">", 100, 60);
		}
		else if (cur == 1) {
			Game.gameGraphics.drawString(">", 100, 90);
		}
    }
}
