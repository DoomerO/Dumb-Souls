package entities;

import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;

public class Fire_Weapon extends Weapon {
	
	public static BufferedImage shotFace;
	public static BufferedImage sprite = Game.sheet.getSprite(64, 16, 16, 16);
	private int shotDamage = 5, shotSpeed = 3, dashDistance = 30, tspw, tspw2, maxtspw2 = 60;
	private double ablt3Dmg = 1.0, ablt3Spd = 0.8, di = 0, ablt2Dmg = 1;
 
	public Fire_Weapon() {
		super(sprite);
		shotFace = Game.sheet.getSprite(128, 16, 16, 16);
		
		setOptionsNames(9);
		this.getAnimation(80, 16, 16, 16, 3);
	}
	
	private void setOptionsNames(int opt) {
		this.listNames = new String[opt];
		this.listNames[0] = "Life Boost";
		this.listNames[1] = "Speed Boost";
		this.listNames[2] = "Max Mana";
		this.listNames[3] = "Mana Recover";
		this.listNames[4] = "FireBall Strength";
		this.listNames[5] = "FireBall Speed";
		this.listNames[6] = "Fire Dash";
		this.listNames[7] = "Blaze";
		this.listNames[8] = "Hell Flame";
	}
	
	public void checkOptions(String opt) {
		switch(opt){
			case "Life Boost":
				Game.player.maxLife += 10;
				break;
			case "Speed Boost":
				Game.player.maxSpeed += 0.1;
				Game.player.speed = Game.player.maxSpeed;
				break;
			case "Max Mana":
				Game.player.maxMana += 20;
				break;
			case "Mana Recover":
				Game.player.manaRec += 0.02;
				break;
			case "Fireball Strength":
				shotDamage += 2;
				break;
			case "FireBall Speed":
				shotSpeed += 1;
				break;
			case "Fire Dash":
				if (dashAva == false) {
					dashAva = true;
				}
				else {
					dashDistance += 15;
				}
				break;
			case "Blaze":
				if (ablt2Ava == false) {
					ablt2Ava = true;
				}
				else {
					ablt2Dmg += 1;
					maxtspw2 += 5;
				}
				break;
			case "Hell Flame":
				if (ablt3Ava == false) {
					ablt3Ava = true;
				}
				else {
					ablt3Dmg += 0.2;
					ablt3Spd += 0.2;
				}
				break;
			}
		}
	
	public void Attack() {
		double ang = Math.atan2(my - (Game.player.getY() + 8 - Camera.y) , mx - (Game.player.getX() + 8 - Camera.x));
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);
		
		Game.shots.add(new Shot(Game.player.getX(), Game.player.getY(), 3, 3, shotFace, dx, dy, shotDamage, shotSpeed, 35));
	}
	
	public void Dash() {
		int manaCost = 12;
		
		if (this.dashAva && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
			if (tspw > 2) {
				tspw = 0;
			}
			tspw ++;
			di += 2.5;
			if (di < dashDistance) {
				if (Game.player.right) {
					Game.player.x += 2.5;
				}
				else if (Game.player.left) {
					Game.player.x -= 2.5;
				}
				if (Game.player.down) {
					Game.player.y += 2.5;
				}
				else if (Game.player.up) {
					Game.player.y -= 2.5;
				}
				if (tspw == 2) {
					Game.entities.add(new AE_Fire(Game.player.getX(), Game.player.getY(), 16, 16, null, 125));
					tspw = 0;
				}
			}
			else {
				Game.player.dash = false;
				md = false;
				di = 0;
			}
			
		}
	}
	
	public void Ablt2() {
		int manaCost = 34;
		
		if (ablt2Ava && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
			tspw2++;
			double ang = Math.atan2(Game.my / Game.scale - (Game.player.getY() + 8 - Camera.y) , Game.mx / Game.scale - (Game.player.getX() + 8 - Camera.x));
			double dx = Math.cos(ang);
			double dy =  Math.sin(ang);
			if (tspw2 % 4 == 0) {
				Game.entities.add(new AE_Fire2(Game.player.getX(), Game.player.getY(), 16, 16, 2, dx, dy, ablt2Dmg, null, 60));
			}
			if (tspw2 == maxtspw2) {
				tspw2 = 0;
				Game.player.ablt2 = false;
				md = false;
			}
		}
	}
	
	public void Ablt3() {
		int manaCost = 68;
		
		if (ablt3Ava && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
			double ang = Math.atan2(Game.my / Game.scale - (Game.player.getY() + 8 - Camera.y) , Game.mx / Game.scale - (Game.player.getX() + 8 - Camera.x));
			double dx = Math.cos(ang);
			double dy =  Math.sin(ang);
			Game.entities.add(new AE_HellFlame(Game.player.getX(), Game.player.getY() - 32, 32, 32, ablt3Spd, dx, dy, ablt3Dmg, null, 80));
			Game.player.ablt3 = false;
			md = false;
		}
	}
}
