package main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import entities.*;

public class Menu_Player {
	private int cur, curW;
	private String[] weapons = {"Fire Weapon", "Wind Weapon", "Ice Weapon", "Fisical Weapon"}, options = {"Books", "Start", "Back"};
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
		
		if (options[cur] == "Books") {
			if (right) {
				clickR = true;
				clickL = false;
				right = false;
				curW++;
				if (curW > weapons.length - 1) {
					curW = 0;
				}
			}
			else if (left) {
				left = false;
				clickR = false;
				clickL = true;
				curW--;
				if (curW < 0) {
					curW = weapons.length - 1;
				}
			}
		}
		
		if (options[cur] == "Start") {
			clickR = false;
			clickL = false;
			if (enter) {
				enter = false;
				Game.gameState = "NORMAL";
				weaponVerification();
			}
		}
		
		if (options[cur] == "Back") {
			if (enter) {
				enter = false;
				Game.gameState = "MENUINIT";
			}	
		}
	}
	
	private void weaponVerification() {
		switch(weapons[curW]){
			case "Fire Weapon":
				Game.player.playerWeapon = new Fire_Weapon();
				break;
			case "Wind Weapon":
				Game.player.playerWeapon = new Wind_Weapon();
				break;
			case "Ice Weapon":
				Game.player.playerWeapon = new Ice_Weapon();
				break;
			case "Fisical Weapon":
				Game.player.playerWeapon = new Fisical_Weapon();
				break;
		}
	}
	
	private void imgWeapon(Graphics g) {
		switch(curW){
		case 0:
			g.drawImage(Fire_Weapon.sprite, 200, 54, 32, 32, null);
			break;
		case 1:
			g.drawImage(Wind_Weapon.sprite, 200, 54, 32, 32, null);
			break;
		case 2:
			g.drawImage(Ice_Weapon.sprite, 200, 54, 32, 32, null);
			break;
		case 3:
			g.drawImage(Fisical_Weapon.sprite, 200, 54, 32, 32, null);
			break;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.width, Game.height);
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 15));
		g.drawString("Player Configuration", 90, 20);
		
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.drawString(weapons[curW], 30, 70);
		g.drawString("Start", 30, 100);
		g.drawString("Back", 30, 130);
		
		if (cur == 0) {
			g.drawString("<", 20, 70);
			g.drawString(">", 100, 70);
			if (clickR) {
				g.setColor(Color.red);
				g.drawString(">", 100, 70);
			}
			if (clickL) {
				g.setColor(Color.red);
				g.drawString("<", 20, 70);
			}
		}
		else if (cur == 1) {
			g.drawString(">", 20, 100);
		}
		else if (cur == 2) {
			g.drawString(">", 20, 130);
		}
		
		g.setColor(new Color(50, 50, 50));
		g.fillRect(200, 54, 32, 32);
		
		imgWeapon(g);
	}
}
