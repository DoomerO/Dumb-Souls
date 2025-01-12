package main;

import entities.Player;
import graphics.TextObject;

import java.awt.Color;
import java.awt.Font;

public class Menu_Runes {
	
	private static TextObject
	runeBox = new TextObject("arial", Font.BOLD, 9, "Rune", 30, 40, Color.white),
	equipBox = new TextObject("arial", Font.BOLD, 9, "Equip", 30, 60, Color.white),
	unequipBox = new TextObject("arial", Font.BOLD, 9, "Unequip", 30, 80, Color.white),
	deleteBox = new TextObject("arial", Font.BOLD, 9, "Delete", 30, 100, Color.white),
	limitBox = new TextObject("arial", Font.BOLD, 9, "Limit Break", 30, 120, Color.white),
	backBox = new TextObject("arial", Font.BOLD, 9, "Back", 30, 140, Color.white);
	private static int cur = 0, curR = 0;
	private static boolean clickR, clickL;
	
	public static void tick() {
		if(Player.runesInventory.isEmpty()){
			Game.gameStateHandler = Game.gameState.MENUPLAYER;
		};

		if(runeBox.hover()) cur = 0;
		if(equipBox.hover()) cur = 1;
		if(unequipBox.hover()) cur = 2;
		if(deleteBox.hover()) cur = 3;
		if(limitBox.hover()) cur = 4;
		if(backBox.hover()) cur = 5;

		boolean enter = Game.keyController.contains(10);
		if (Game.keyController.contains(87) || Game.keyController.contains(38)) {//W UP
			cur --;
			if (cur < 0) cur = 5;
		}
		if (Game.keyController.contains(83) || Game.keyController.contains(40)) {//S DOWN
			cur ++;
			if (cur > 5) cur = 0;
		}
		
		runeBox.updateText(Player.runesInventory.get(curR).name);
		if (cur == 0) {
			if (Game.keyController.contains(68) || Game.keyController.contains(39) || Game.scrollNum > 0) {//D RIGHT
				clickR = true;
				clickL = false;
				curR++;
				if (curR > Player.runesInventory.size() - 1) curR = 0;
			}
			if (Game.keyController.contains(65) || Game.keyController.contains(37) || Game.scrollNum < 0) {//A LEFT
				clickR = false;
				clickL = true;
				curR--;
				if (curR < 0) curR = Player.runesInventory.size() - 1;
			}
		}
		
		if(cur == 1) {
			if (enter || equipBox.click()) {
				if (Game.player.runesEquipped.size() < Player.runeLimit ) {
					if (!Player.runesInventory.get(curR).equipped) {
						Game.player.runesEquipped.add(Player.runesInventory.get(curR));
						Player.runesInventory.get(curR).equipped = true;
					}
				}
			}	
		}
		
		if(cur == 2) {
			if (enter || unequipBox.click()) {
				Game.player.runesEquipped.remove(Player.runesInventory.get(curR));
				Player.runesInventory.get(curR).equipped = false;
			}	
		}
		
		if (cur == 3) {
			if (enter || deleteBox.click()) {
				Player.runesInventory.remove(Player.runesInventory.get(curR));
			}	
		}
		
		if (cur == 4) {
			if (enter || limitBox.click()) {
				if (Player.souls >= 5000) {
					Player.runeLimit ++;
					Player.souls -= 5000;
				}
			}	
		}
		
		if (cur == 5) {
			if (enter || backBox.click()) {
				Game.gameStateHandler = Game.gameState.MENUPLAYER;
			}	
		}

		Game.scrollNum = 0;
		Game.clickController.clear();
		Game.keyController.clear();
	}
	
	private static void renderSprites() {
		Game.gameGraphics.setColor(new Color(50, 50, 50));
		Game.gameGraphics.fillRect(200, 54, 32, 32);
		
		Game.gameGraphics.drawImage(Player.runesInventory.get(curR).sprite, 200, 54, 32, 32, null);
		
		if (Player.runesInventory.get(curR).equipped) {
			Game.gameGraphics.setColor(new Color(0, 127, 14));
			Game.gameGraphics.drawString("Equipped", 193, 100);
		}
		else {
			Game.gameGraphics.setColor(new Color(127, 0, 0));
			Game.gameGraphics.drawString("Unequipped", 193, 100);
		}
		
		Game.gameGraphics.setColor(Color.white);
		Game.gameGraphics.setFont(new Font("arial", Font.BOLD, 9));
		Game.gameGraphics.drawString(Player.runesInventory.get(curR).description, 145, 113);
	}
	
	public static void render() {
		Game.gameGraphics.setColor(Color.black);
		Game.gameGraphics.fillRect(0, 0, Game.width, Game.height);
		
		Game.gameGraphics.setColor(Color.white);
		Game.gameGraphics.setFont(new Font("arial", Font.BOLD, 15));
		Game.gameGraphics.drawString("Player Runes", 90, 20);

		if(!Player.runesInventory.isEmpty()){
			renderSprites();
		}

		runeBox.render();
		equipBox.render();
		unequipBox.render();
		deleteBox.render();
		limitBox.render();
		backBox.render();
		
		if (cur == 0) {
			Game.gameGraphics.drawString("<", 20, 40);
			Game.gameGraphics.drawString(">", runeBox.getWidth() + 25, 40);
			if (clickR) {
				Game.gameGraphics.setColor(Color.red);
				Game.gameGraphics.drawString(">", runeBox.getWidth() + 25, 40);
			}
			if (clickL) {
				Game.gameGraphics.setColor(Color.red);
				Game.gameGraphics.drawString("<", 20, 40);
			}
		}
		else if (cur == 1) {
			Game.gameGraphics.drawString(">", 20, 60);
		}
		else if (cur == 2) {
			Game.gameGraphics.drawString(">", 20, 80);
		}
		else if (cur == 3) {
			Game.gameGraphics.drawString(">", 20, 100);
		}
		else if (cur == 4) {
			Game.gameGraphics.drawString(">", 20, 120);
			Game.gameGraphics.setColor(new Color(74, 52, 160));
			Game.gameGraphics.drawString("-5000 souls", 60, 129);
		}
		else if (cur == 5) {
			Game.gameGraphics.drawString(">", 20, 140);
		}
		
		Game.gameGraphics.setColor(new Color(0, 127, 0));
		Game.gameGraphics.setFont(new Font("arial", Font.BOLD, 9));
		Game.gameGraphics.drawString("Rune Limit: " + Game.player.runesEquipped.size() + "/" + Player.runeLimit, 20, 150);
		
		Game.gameGraphics.setColor(new Color(74, 52, 160));
		Game.gameGraphics.drawString("Souls : " + Player.souls, 255, 150);
	}
}
