package main;

import entities.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu_Runes {
	
	private int cur, curR;
	private String[] options = {"Runes", "Equipe", "Dequipe", "Delete", "+Limit", "Back"};
	public boolean up, down, left, right, enter;
	private boolean clickR, clickL;
	
	public void tick() {
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
		
		if (options[cur] == "Runes") {
			if (right) {
				clickR = true;
				clickL = false;
				right = false;
				curR++;
				if (curR > Player.runesInventory.size() - 1) {
					curR = 0;
				}
			}
			else if (left) {
				left = false;
				clickR = false;
				clickL = true;
				curR--;
				if (curR < 0) {
					curR = Player.runesInventory.size() - 1;
				}
			}
		}
		
		if(options[cur] == "Equipe") {
			if (enter) {
				enter = false;
				if (Game.player.runesEquiped.size() < Player.runeLimit ) {
					if (!Player.runesInventory.get(curR).equiped) {
						Game.player.runesEquiped.add(Player.runesInventory.get(curR));
						Player.runesInventory.get(curR).equiped = true;
					}
				}
			}	
		}
		
		if(options[cur] == "Dequipe") {
			if (enter) {
				enter = false;
				Game.player.runesEquiped.remove(Player.runesInventory.get(curR));
				Player.runesInventory.get(curR).equiped = false;
			}	
		}
		
		if (options[cur] == "Delete") {
			if (enter) {
				enter = false;
				if (Player.runesInventory.size() > 1) {
					Player.runesInventory.remove(Player.runesInventory.get(curR));
				}
			}	
		}
		
		if (options[cur] == "+Limit") {
			if (enter) {
				enter = false;
				if (Player.souls >= 5000) {
					Player.runeLimit ++;
					Player.souls -= 5000;
				}
			}	
		}
		
		if (options[cur] == "Back") {
			if (enter) {
				enter = false;
				Game.gameState = "MENUPLAYER";
			}	
		}
	}
	
	private void renderSprites(Graphics g) {
		g.setColor(new Color(50, 50, 50));
		g.fillRect(200, 54, 32, 32);
		
		g.drawImage(Player.runesInventory.get(curR).sprite, 200, 54, 32, 32, null);
		
		if (Player.runesInventory.get(curR).equiped) {
			g.setColor(new Color(0, 127, 14));
			g.drawString("Equiped", 193, 100);
		}
		else {
			g.setColor(new Color(127, 0, 0));
			g.drawString("Unequiped", 193, 100);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.drawString(Player.runesInventory.get(curR).description, 145, 113);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.width, Game.height);
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 15));
		g.drawString("Player Configuration/ Runes", 90, 20);
		
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.drawString(Player.runesInventory.get(curR).name, 30, 40);
		g.drawString("Equipe", 30, 60);
		g.drawString("Dequipe", 30, 80);
		g.drawString("Delete", 30, 100);
		g.drawString("+Limit", 30, 120);
		g.drawString("Back", 30, 140);
		
		if (cur == 0) {
			g.drawString("<", 20, 40);
			g.drawString(">", 100, 40);
			if (clickR) {
				g.setColor(Color.red);
				g.drawString(">", 100, 40);
			}
			if (clickL) {
				g.setColor(Color.red);
				g.drawString("<", 20, 40);
			}
		}
		else if (cur == 1) {
			g.drawString(">", 20, 60);
		}
		else if (cur == 2) {
			g.drawString(">", 20, 80);
		}
		else if (cur == 3) {
			g.drawString(">", 20, 100);
		}
		else if (cur == 4) {
			g.drawString(">", 20, 120);
			g.setColor(new Color(74, 52, 160));
			g.drawString("-5000 souls", 60, 120);
		}
		else if (cur == 5) {
			g.drawString(">", 20, 140);
		}
		
		g.setColor(new Color(0, 127, 0));
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.drawString("Rune Limit: " + Game.player.runesEquiped.size() + "/" + Player.runeLimit, 20, 150);
		
		g.setColor(new Color(74, 52, 160));
		g.drawString("Souls : " + Player.souls, 255, 150);
		
		renderSprites(g);
	}
}
