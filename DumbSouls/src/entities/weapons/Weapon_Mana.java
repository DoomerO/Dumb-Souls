package entities.weapons;

import java.awt.image.BufferedImage;
import main.Game;
import entities.AE.AE_Animation;
import entities.AE.AE_ManaRay;
import entities.shots.Shot_PlayerMana;
import world.Camera;

public class Weapon_Mana extends Weapon {
	
	public static BufferedImage sprite = Game.sheet.getSprite(64, 64, 16, 16);
	private int shotDamage = 5, shotSpeed = 4, contUpgrades, spcShotsGain = 5, timeDash, maxTimeD = 900, timeAblt3 = 100, ablt3Dmg = 4;
	private float dashPercent = 1.5f;
	private static int ablt2Dmg = 25, ablt2Knck = 8, qntSpcShots, grafEfcCont;
	public static int soulCost = 0;
	public static boolean block = false;
	
	
	public Weapon_Mana() {
		super(sprite);
		ablt2Img = Game.sheet.getSprite(176, 128, 16, 16);
		ablt3Img = Game.sheet.getSprite(0, 224, 16, 16);
		qntSpcShots = 0;
		setAttackTimer(6);
		setOptionsNames(9);
		getAnimation(80, 64, 16, 16, 3);
	}
	
	private void setOptionsNames(int opt) {
		listNames = new String[opt];
		listNames[0] = "Life Boost";
		listNames[1] = "Speed Boost";
		listNames[2] = "Max Mana";
		listNames[3] = "Mana Recover";
		listNames[4] = "Mana Strength";
		listNames[5] = "Mana Speed";
		listNames[6] = "Mana Step";
		listNames[7] = "Mana Explosion";
		listNames[8] = "Mana Ray";
	}
	
	public void checkOptions(String opt) {
		switch(opt) {
			case "Life Boost":
				Game.player.life += 10;
				break;
			case "Speed Boost":
				Game.player.maxSpeed += 0.2;
				Game.player.speed = Game.player.maxSpeed;
				break;
			case "Max Mana":
				Game.player.maxMana += 15;
				if (contUpgrades < 3) {
					contUpgrades++;
				}
				else {
					contUpgrades = 0;
					shotDamage += 1;
				}
				break;
			case "Mana Recover":
				Game.player.manaRec += 0.5;
				if (contUpgrades < 3) {
					contUpgrades++;
				}
				else {
					contUpgrades = 0;
					shotDamage += 1;
				}
			break;
			case "Mana Strength": 
				shotDamage += 1;
				break;
			case "Mana Speed":
				shotSpeed += 1;
				break;
			case "Mana Step":
				if (dashAva) {
					maxTimeD += 300;
					dashPercent += 0.25f;
				}
				else {
					dashAva = true;
				}
				break;
			case "Mana Explosion":
				if (ablt2Ava) {
					ablt2Dmg += 5;
					ablt2Knck += 4;
					spcShotsGain += 5;
				}
				else {
					ablt2Ava = true;
				}
				break;
			case "Mana Ray":
				if (ablt3Ava) {
					ablt3Dmg += 2;
					timeAblt3 += 30;
				}
				else {
					ablt3Ava = true;
				}
				break;
		}
	}

	public void Dash() {
		int manaCost = 40;
		if (dashAva && Game.player.mana >= manaCost && !md1) {
			md1 = true;
			Game.player.mana -= manaCost;
		}
		if (md1) {
			Game.player.speedBoost *= dashPercent;
			if (timeDash % 10 == 0) {
				Game.entities.add(new AE_Animation(Game.player.centerX(), Game.player.centerY(), 16, 16, null, 20, 0, 1, 192, 128, 16, 16, "goToUp_1", null));
			}
			if (timeDash % 15 == 0) {
				Game.entities.add(new AE_Animation(Game.player.centerX() - 4, Game.player.centerY() + 6, 16, 16, null, 20, 0, 1, 192, 128, 16, 16, "goToUp_2", null));
				Game.entities.add(new AE_Animation(Game.player.centerX() + 4, Game.player.centerY() + 6, 16, 16, null, 20, 0, 1, 192, 128, 16, 16, "goToUp_2", null));
			}
			timeDash++;
			if (timeDash >= maxTimeD) {
				timeDash = 0;
				md1 = false;
			}
		}
	}
	
	public void Ablt2() {
		int manaCost = 68;
		if (ablt2Ava && Game.player.mana >= manaCost && qntSpcShots == 0) {
			Game.player.mana -= manaCost;
			qntSpcShots += spcShotsGain;
		}
	}
	
	public void Ablt3() {
		int manaCost = 35;
		if (ablt3Ava && Game.player.mana >= manaCost && !md3) {
			md3 = true;
			Game.player.mana -= manaCost;
		}
		if (md3) {
			Game.entities.add(new AE_ManaRay(Game.player.centerX(), Game.player.centerY(), 16, 16, null, timeAblt3, ablt3Dmg));
			md3 = false;
		}
	}
	
	public void Attack() {
		double ang = Math.atan2(Game.my / Game.scale - (Game.player.centerY() - Camera.getY()) , Game.mx / Game.scale - Game.player.centerX() + Camera.getX());
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);
		if(qntSpcShots > 0){
			Game.shots.add(new Shot_PlayerMana(Game.player.centerX(), Game.player.centerY(), dx, dy, ang, shotSpeed, shotDamage, ablt2Dmg, ablt2Knck));
			qntSpcShots--;
		} else{
			Game.shots.add(new Shot_PlayerMana(Game.player.centerX(), Game.player.centerY(), dx, dy, ang, shotSpeed, shotDamage, 0, 0));
		}
	}
	
	public static void grafficEffect() {
		if (qntSpcShots > 0) {
			if (grafEfcCont == 10) {
				grafEfcCont = 0;
				Game.entities.add(new AE_Animation(Game.player.centerX(), Game.player.centerY(), 16, 16, null, 15, 0, 1, 96, 144, 16, 16, "goToUp_Objt_1", Game.player));
			}
			grafEfcCont++;
		}
	}
}
