package main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import entities.*;

public class Menu_Player {
	private int cur, curW;
	private String[] weapons = {"Fire Weapon", "Wind Weapon", "Ice Weapon", "Fisical Weapon", "Poison Weapon"}, options = {"Books", "Start", "Back"};
	public boolean up, down, left, right, enter;
	private boolean clickR, clickL;
	private int weaponCost;
	private boolean weaponBlock;
	
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
			costWeapon();
		}
		
		if (options[cur] == "Start") {
			clickR = false;
			clickL = false;
			if (enter) {
				enter = false;
				if (costPossible() || isWeaponBlock()) {
					if (!isWeaponBlock()) {
						Player.souls -= weaponCost;
					}
					Game.gameState = "NORMAL";
					weaponVerification();
				}
			}
		}
		
		if (options[cur] == "Back") {
			if (enter) {
				enter = false;
				Game.gameState = "MENUINIT";
			}	
		}
	}
	
	private boolean isWeaponBlock() {
		if (weaponBlock == false) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean costPossible() {
		if (Player.souls >= weaponCost) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private void weaponVerification() {
		switch(weapons[curW]){
			case "Fire Weapon":
				Game.player.playerWeapon = new Fire_Weapon();
				Fire_Weapon.block = false;
				break;
			case "Wind Weapon":
				Game.player.playerWeapon = new Wind_Weapon();
				Wind_Weapon.block = false;
				break;
			case "Ice Weapon":
				Game.player.playerWeapon = new Ice_Weapon();
				Ice_Weapon.block = false;
				break;
			case "Fisical Weapon":
				Game.player.playerWeapon = new Fisical_Weapon();
				Fisical_Weapon.block = false;
				break;
			case "Poison Weapon":
				Game.player.playerWeapon = new Poison_Weapon();
				Poison_Weapon.block = false;
				break;
		}
	}
	
	private void costWeapon() {
		switch(curW) {
		case 0:
			weaponCost = Fire_Weapon.soulCost;
			weaponBlock = Fire_Weapon.block;
			break;
		case 1:
			weaponCost = Wind_Weapon.soulCost;
			weaponBlock = Wind_Weapon.block;
			break;
		case 2:
			weaponCost = Ice_Weapon.soulCost;
			weaponBlock = Ice_Weapon.block;
			break;
		case 3:
			weaponCost = Fisical_Weapon.soulCost;
			weaponBlock = Fisical_Weapon.block;
			break;
		case 4:
			weaponCost = Poison_Weapon.soulCost;
			weaponBlock = Poison_Weapon.block;
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
		case 4:
			g.drawImage(Poison_Weapon.sprite, 200, 54, 32, 32, null);
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
		
		if (costPossible()) {
			g.setColor(new Color(0, 127, 14));
		}
		else {
			g.setColor(new Color(127, 0, 0));
		}
		g.drawString("Soul Cost: " + weaponCost, 183, 100);
		
		if (isWeaponBlock()) {
			g.setColor(new Color(0, 127, 14));
			g.drawString("Unblocked", 183, 110);
		}
		else {
			g.setColor(new Color(127, 0, 0));
			g.drawString("Blocked", 183, 110);
		}
		
		g.setColor(new Color(74, 52, 160));
		g.drawString("Souls : " + Player.souls, 255, 150);
		
		g.setColor(new Color(50, 50, 50));
		g.fillRect(200, 54, 32, 32);
		
		imgWeapon(g);
	}
}
