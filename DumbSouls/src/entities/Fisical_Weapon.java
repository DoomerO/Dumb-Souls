package entities;

import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class Fisical_Weapon extends Weapon {
	public static BufferedImage shotFace;
	public static BufferedImage sprite = Game.sheet.getSprite(144, 16, 16, 16);
	private int shotDamage = 4, ablt2Dmg = 10, ablt2W = 64, ablt2H = 64, tspw;
	private double di = 0, dashDistance = 30, ablt3Dmg = 6;
	
	public Fisical_Weapon() {
		super(sprite);
		shotFace = Game.sheet.getSprite(208, 16, 16, 16);
		
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
				Game.player.maxLife += 20;
				break;
			case "Speed Boost":
				Game.player.maxSpeed += 0.2;
				Game.player.speed = Game.player.maxSpeed;
				break;
			case "Max Mana":
				Game.player.maxMana += 10;
				break;
			case "Mana Recover":
				Game.player.manaRec += 0.01;
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
				Game.player.lifeRec += 0.0003;
				break;
		}
	}
	
	public void Attack() {
		double ang = Math.atan2(my - (Game.player.getY() + 8 - Camera.y) , mx - (Game.player.getX() + 8 - Camera.x));
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);
		
		Game.shots.add(new Shot(Game.player.getX(), Game.player.getY(), 3, 3, shotFace, dx, dy, shotDamage, 2.5, 20));
	}
	
	public void Dash() {
		int manaCost = 4;
		
		if (this.dashAva && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
			di += 5.0;
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
				md = false;
				Game.player.dash = false;
			}
		}
	}
	
		public void Effect() {
			if (Game.player.life < Game.player.maxLife) {
				Game.player.life *= Game.player.lifeRec;
		}	
	}
	
	public void Ablt2() {
		int manaCost = 30;	
		if (this.ablt2Ava && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
			Game.entities.add(new AE_Rupture(Game.player.getX() - ablt2W / 2 + 8, Game.player.getY() - ablt2H / 2 + 8, ablt2W, ablt2H, null, 80, ablt2Dmg));
			Game.player.ablt2 = false;
			md = false;
		}
	}
	
	public void Ablt3() {
		int manaCost = 50;
		if (this.ablt3Ava && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
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
				md = false;
			}
		}
	}
}
