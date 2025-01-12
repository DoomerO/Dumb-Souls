package entities.weapons;

import java.awt.image.BufferedImage;
import entities.shots.Shot;
import entities.AE.*;
import main.Game;
import world.Camera;

public class Weapon_Fisical extends Weapon {
	public static BufferedImage shotFace;
	public static BufferedImage sprite = Game.sheet.getSprite(144, 16, 16, 16);
	private int shotDamage = 4, di = 0, ablt2Dmg = 10, ablt2W = 64, ablt2H = 64, ablt3Dmg = 6, tspw, combo, dashDistance = 30;
	public static int soulCost = 500;
	public static boolean block = true;
	
	public Weapon_Fisical() {
		super(sprite);
		shotFace = Game.sheet.getSprite(208, 16, 16, 16);
		super.setAttackTimer(3);
		Game.player.push = 10;
		attackTimer = 12;
		setOptionsNames(9);
		getAnimation(160, 16, 16, 16, 3);
	}
	
	private void setOptionsNames(int opt) {
		listNames = new String[opt];
		listNames[0] = "Life Boost";
		listNames[1] = "Speed Boost";
		listNames[2] = "Max Mana";
		listNames[3] = "Mana Recover";
		listNames[4] = "Punch Strength";
		listNames[5] = "Fisical Dash";
		listNames[6] = "Strength Wave";
		listNames[7] = "Punch Rain";
		listNames[8] = "Fisical Condition";
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
		double ang = Math.atan2(Game.my / Game.scale  - (Game.player.centerY() - Camera.getY()) , Game.mx / Game.scale  - Game.player.centerX() + Camera.getX());
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);
		switch (combo) {
			case 1:
				Game.shots.add(new Shot(Game.player.centerX(), Game.player.centerY(), 3, 3, dx, dy, ang, 2.5, shotDamage, 20, shotFace));
			break;
			case 3:
				Game.shots.add(new Shot(Game.player.centerX(), Game.player.centerY(), 3, 3, dx, dy, ang, 3.5, shotDamage, 20, shotFace));
				Game.shots.add(new Shot(Game.player.centerX(), Game.player.centerY(), 3, 3, dx, dy, ang, 3.75, shotDamage + 2, 20, shotFace));
			break;
			case 5:
				Game.shots.add(new Shot(Game.player.centerX(), Game.player.centerY(), 3, 3, dx, dy, ang, 3.5, shotDamage, 20, shotFace));
				Game.shots.add(new Shot(Game.player.centerX(), Game.player.centerY(), 3, 3, dx, dy, ang, 4, shotDamage + 4, 20, shotFace));
				Game.entities.add(new AE_Rupture(Game.player.centerX() - ruptureScale / 2, Game.player.centerY() - ruptureScale / 2, ruptureScale, ruptureScale, null, 60, shotDamage * 2));
				combo = 0;
			break;
			default: 
				Game.shots.add(new Shot(Game.player.centerX(), Game.player.centerY(), 3, 3, dx, dy, ang, 2.5 + (double)(combo / 10), shotDamage + combo, 20, shotFace));
			break;
		}
	}
	
	public void Dash() {
		int manaCost = 4;
		
		if (dashAva && Game.player.mana >= manaCost && !md1) {
			md1 = true;
			Game.player.mana -= manaCost;
		}
		if (md1) {
			di += 5;
			Game.entities.add(new AE_Animation(Game.player.centerX() + Game.rand.nextInt(17), Game.player.centerY() + Game.rand.nextInt(17), 16, 16, null, 20, 0, 1, 192, 128, 16, 16, "goToUp_1", null));
			Game.player.speedBoost *= 1.2;
			if(di >= dashDistance) {
				di = 0;
				md1 = false;
			}
		}
	}
	
	public void Ablt2() {
		int manaCost = 30;	
		if (ablt2Ava && Game.player.mana >= manaCost && !md2) {
			md2 = true;
			Game.player.mana -= manaCost;
		}
		if (md2) {
			Game.entities.add(new AE_Rupture(Game.player.centerX() - ablt2W / 2, Game.player.centerY() - ablt2H / 2, ablt2W, ablt2H, null, 80, ablt2Dmg));
			md2 = false;
		}
	}
	
	public void Ablt3() {
		int manaCost = 50;
		if (ablt3Ava && Game.player.mana >= manaCost && !md3) {
			md3 = true;
			Game.player.mana -= manaCost;
		}
		if (md3) {
			tspw++;
			double off = Game.rand.nextInt(20);
			double deltaX = Game.mx / Game.scale + off - Game.player.centerX() + Camera.getX();
			double deltaY = Game.my / Game.scale + off - Game.player.centerY() + Camera.getY();
			double mag = Math.hypot(deltaX, deltaY);
			if (tspw % 2 == 0) {
				Game.entities.add(new AE_PunchRain(Game.player.centerX(), Game.player.centerY(), 16, 16, 3, deltaX / mag, deltaY / mag, ablt3Dmg, null, 20));
			}
			if (tspw == 40) {
				tspw = 0;
				md3 = false;
			}
		}
	}
}
