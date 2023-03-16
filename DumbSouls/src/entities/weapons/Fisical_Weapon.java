package entities.weapons;

import java.awt.image.BufferedImage;
import entities.shots.Shot;
import entities.AE.*;
import main.Game;
import world.Camera;

public class Fisical_Weapon extends Weapon {
	public static BufferedImage shotFace;
	public static BufferedImage sprite = Game.sheet.getSprite(144, 16, 16, 16);
	private int shotDamage = 4, ablt2Dmg = 10, ablt2W = 64, ablt2H = 64, ablt3Dmg = 6, tspw, combo;
	private double di = 0, dashDistance = 30;
	public static int soulCost = 500;
	 public static boolean block = true;
	
	public Fisical_Weapon() {
		super(sprite);
		shotFace = Game.sheet.getSprite(208, 16, 16, 16);
		super.setAttackTimer(3);
		Game.player.push = 2.5;

		setOptionsNames(9);
		this.getAnimation(160, 16, 16, 16, 3);
	}
	
	private void setOptionsNames(int opt) {
		this.listNames = new String[opt];
		this.listNames[0] = "Life Boost";
		this.listNames[1] = "Speed Boost";
		this.listNames[2] = "Max Mana";
		this.listNames[3] = "Mana Recover";
		this.listNames[4] = "Punch Strength";
		this.listNames[5] = "Fisical Dash";
		this.listNames[6] = "Strength Wave";
		this.listNames[7] = "Punch Rain";
		this.listNames[8] = "Fisical Condition";
	}
	
	public void checkOptions(String opt) {
		switch(opt){
			case "Life Boost":
				Game.player.maxLife += 40;
				break;
			case "Speed Boost":
				Game.player.maxSpeed += 0.3;
				Game.player.speed = Game.player.maxSpeed;
				break;
			case "Max Mana":
				Game.player.maxMana += 10;
				break;
			case "Mana Recover":
				Game.player.manaRec += 0.5;
				break;
			case "Punch Strength":
				shotDamage += 2;
				break;
			case "Fisical Dash":
				if (dashAva == false) {
					dashAva = true;
				}
				else {
					dashDistance += 20;
				}
				break;
			case "Strength Wave":
				if (ablt2Ava == false) {
					ablt2Ava = true;
				}
				else {
					ablt2W += 24;
					ablt2H += 24;
					ablt2Dmg += 5;
				}
				break;
			case "Punch Rain":
				if (ablt3Ava == false) {
					ablt3Ava = true;
				}
				else {
					ablt3Dmg += 3;
				}
				break;
			case "Fisical Condition":
				Game.player.lifeRec += 0.004;
				break;
		}
	}
	
	public void Attack() {
		combo += 1;
		int ruptureScale = shotDamage * 7;
		double ang = Math.atan2(my - (Game.player.getY() + 8 - Camera.y) , mx - (Game.player.getX() + 8 - Camera.x));
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);
		switch (combo) {
			case 1:
				Game.shots.add(new Shot(Game.player.getX(), Game.player.getY(), 3, 3, shotFace, dx, dy, ang, shotDamage, 2.5, 20));
			break;
			case 3:
				Game.shots.add(new Shot(Game.player.getX(), Game.player.getY(), 3, 3, shotFace, dx, dy, ang, shotDamage, 3.5, 20));
				Game.shots.add(new Shot(Game.player.getX(), Game.player.getY(), 3, 3, shotFace, dx, dy, ang, shotDamage + 2, 3.75, 20));
			break;
			case 5:
				Game.shots.add(new Shot(Game.player.getX(), Game.player.getY(), 3, 3, shotFace, dx, dy, ang, shotDamage, 3.5, 20));
				Game.shots.add(new Shot(Game.player.getX() + 10, Game.player.getY(), 3, 3, shotFace, dx, dy, ang, shotDamage + 4, 4, 20));
				Game.entities.add(new AE_Rupture(Game.player.getX() - ruptureScale / 2 + 8, Game.player.getY() - ruptureScale / 2 + 8, ruptureScale, ruptureScale, null, 60, shotDamage * 2));
				combo = 0;
			break;
			default: 
				Game.shots.add(new Shot(Game.player.getX(), Game.player.getY(), 3, 3, shotFace, dx, dy, ang, shotDamage + combo, 2.5 + (double)(combo / 10), 20));
			break;
		}
	}
	
	public void AttackRandom() {
		int xdir = Game.rand.nextInt(1);
		int ydir = Game.rand.nextInt(1);
		
		int xoff = Game.rand.nextInt(20);
		int yoff = Game.rand.nextInt(20);
		
		if (xdir == 1) {
			xoff *= -1;
		}
		
		if (ydir == 1) {
			yoff *= -1;
		}
		
		double ang = Math.atan2(my + yoff - (Game.player.getY() + 8 - Camera.y) , mx + xoff - (Game.player.getX() + 8 - Camera.x));
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);
		
		Game.shots.add(new Shot(Game.player.getX(), Game.player.getY(), 3, 3, shotFace, dx, dy, ang, shotDamage, 2.5, 35));
	}
	
	public void Dash() {
		int manaCost = 4;
		
		if (this.dashAva && Game.player.mana >= manaCost) {
			if (!md1) {
				md1 = true;
				Game.player.mana -= manaCost;
			}
		}
		if (md1) {
			di += 5.0;
			if (di % 5 == 0) {
				Game.entities.add(new AE_Animation(Game.player.getX() + Game.rand.nextInt(17) - 8, Game.player.getY() + Game.rand.nextInt(17) - 8, 16, 16, null, 20, 0, 1, 192, 128, 16, 16, "goToUp_1", null));
			}
			if (di < dashDistance) {
				if (Game.player.right) {
					Game.player.x += 5.0;
				}
				else if (Game.player.left) {
					Game.player.x -= 5.0;
				}
				if (Game.player.down) {
					Game.player.y += 5.0;
				}
				else if (Game.player.up) {
					Game.player.y -= 5.0;
				}
			}
			else {
				di = 0;
				md1 = false;
				Game.player.dash = false;
			}
		}
	}
	
	public void Ablt2() {
		int manaCost = 30;	
		if (this.ablt2Ava && Game.player.mana >= manaCost) {
			if (!md2) {
				md2 = true;
				Game.player.mana -= manaCost;
			}
		}
		if (md2) {
			Game.entities.add(new AE_Rupture(Game.player.getX() - ablt2W / 2 + 8, Game.player.getY() - ablt2H / 2 + 8, ablt2W, ablt2H, null, 80, ablt2Dmg));
			Game.player.ablt2 = false;
			md2 = false;
		}
	}
	
	public void Ablt3() {
		int manaCost = 50;
		if (this.ablt3Ava && Game.player.mana >= manaCost) {
			if (!md3) {
				md3 = true;
				Game.player.mana -= manaCost;
			}
		}
		if (md3) {
			tspw++;
			double off = Game.rand.nextInt(20);
			double ang = Math.atan2(Game.my / Game.scale + off - (Game.player.getY() + 8 - Camera.y) , Game.mx / Game.scale - (Game.player.getX() + 8 - Camera.x));
			double dx = Math.cos(ang);
			double dy =  Math.sin(ang);
			if (tspw % 2 == 0) {
				Game.entities.add(new AE_PunchRain(Game.player.getX(), Game.player.getY(), 16, 16, 3, dx, dy, ablt3Dmg, null, 20));
			}
			if (tspw == 40) {
				Game.player.ablt3 = false;
				tspw = 0;
				md3 = false;
			}
		}
	}
}
