package entities;

import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;

public class Wind_Weapon extends Weapon {
	
	public static BufferedImage shotFace;
	public static BufferedImage sprite = Game.sheet.getSprite(64, 32, 16, 16);
	private int shotDamage = 3, shotSpeed = 6, hrcDamage = 1, ablt3Dmg = 6;
	private double hrcSpeed = 0.8, ablt3Spd = 5.0;
	private double di, dashDistance = 40;
	public static int soulCost = 100;
	 public static boolean block = true;
	
	public Wind_Weapon() {
		super(sprite);
		shotFace = Game.sheet.getSprite(128, 32, 16, 16);
		super.setAttackTimer(2);
		Game.player.push = 5;
		
		setOptionsNames(9);
		this.getAnimation(80, 32, 16, 16, 3);
	}
	
	private void setOptionsNames(int opt) {
		this.listNames = new String[opt];
		this.listNames[0] = "Life Boost";
		this.listNames[1] = "Speed Boost";
		this.listNames[2] = "Max Mana";
		this.listNames[3] = "Mana Recover";
		this.listNames[4] = "Wind Strength";
		this.listNames[5] = "Wind Speed";
		this.listNames[6] = "Wind Dash";
		this.listNames[7] = "Hurricane";
		this.listNames[8] = "Wind Barrage";
	}
	
	public void checkOptions(String opt) {
		switch (opt) {
		case "Life Boost":
			Game.player.maxLife += 20;
			break;
		case "Speed Boost":
			Game.player.maxSpeed += 0.4;
			Game.player.speed = Game.player.maxSpeed;
			break;
		case "Wind Strength":
			shotDamage += 1;
			Game.player.push += 0.4;
			break;
		case "Max Mana":
			Game.player.maxMana += 10;
			break;
		case "Mana Recover":
			Game.player.manaRec += 0.5;
			break;
		case "Wind Speed":
			shotSpeed += 2;
			Game.player.push += 0.2;
			break;
		case "Wind Dash":
			if (dashAva == false) {
				dashAva = true;
			}
			else {
				dashDistance += 3;
			}
			break;
		case "Hurricane":
			if (ablt2Ava == false) {
				ablt2Ava = true;
			}
			else {
				hrcSpeed += 0.1;
				hrcDamage += 1;
			}
			break;
		case "Wind Barrage":
			if (ablt3Ava == false) {
				ablt3Ava = true;
			}
			else {
				ablt3Dmg += 2;
				ablt3Spd += 0.3;
			}
			break;
		}
	}
	
	public void Attack() {
		double ang = Math.atan2(my - (Game.player.getY() + 8 - Camera.y) , mx - (Game.player.getX() + 8 - Camera.x));
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);
		
		Game.shots.add(new Shot(Game.player.getX(), Game.player.getY(), 3, 6, shotFace, dx, dy, ang, shotDamage, shotSpeed, 35));
	}
	
	public void Dash() {
		int manaCost = 20;
		if (this.dashAva && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
		}
		if(md) {
			Game.entities.add(new AE_WindDS(Game.player.getX(), Game.player.getY(), 16, 16, null, (int)(dashDistance / 4)));
			di += 4.0;
			if (di < dashDistance) {
				if (Game.player.right) {
					Game.player.x += 4.0;
				}
				else if (Game.player.left) {
					Game.player.x -= 4.0;
				}
				if (Game.player.down) {
					Game.player.y += 4.0;
				}
				else if (Game.player.up) {
					Game.player.y -= 4.0;
				}
			}
			else {
				di = 0;
				md = false;
				Game.player.dash = false;
			}
		}
	}
	
	public void Ablt2() {
		int manaCost = 64;
		if (ablt2Ava && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
		}
		if (md) {
			Game.player.ablt2 = false;
			md = false;
			Game.entities.add(new AE_Hurricane(Game.player.getX(), Game.player.getY(), 32, 32, null, 240, hrcSpeed, hrcDamage));
		}
	}
	
	public void Ablt3() {
		int manaCost = 36;
		if (ablt3Ava && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
		}
		if (md) {
			double ang = Math.atan2(Game.my / Game.scale - (Game.player.getY() + 8 - Camera.y) , Game.mx / Game.scale - (Game.player.getX() + 8 - Camera.x));
			double dx = Math.cos(ang);
			double dy =  Math.sin(ang);
			Game.entities.add(new AE_WindBarrage(Game.player.getX(), Game.player.getY(), 32, 32, ablt3Spd, dx, dy, ablt3Dmg, null, 30));
			Game.player.ablt3 = false;
			md = false;
		}
	}
}
