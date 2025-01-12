package entities.weapons;

import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import entities.shots.Shot;
import entities.AE.*;
import sounds.SoundPlayer;

public class Weapon_Fire extends Weapon {
	
	public static BufferedImage shotFace, light;
	public static BufferedImage sprite = Game.sheet.getSprite(64, 16, 16, 16);
	private int shotDamage = 5, shotSpeed = 3, dashDistance = 30, tspw, tspw2, maxtspw2 = 60, ablt2Dmg = 1, ablt3Dmg = 16;
	private double ablt3Spd = 0.8, di = 0;
	public static int soulCost = 100;
	public static boolean block = true;
	private SoundPlayer sound1, sound2, sound3;
	
	public Weapon_Fire() {
		super(sprite);
		shotFace = Game.sheet.getSprite(128, 16, 16, 16);
		super.setAttackTimer(3);
		sound1 = new SoundPlayer("res/sounds/fire_atk.wav");
		sound2 = new SoundPlayer("res/sounds/fire_ablt1.wav");
		sound3 = new SoundPlayer("res/sounds/fire_ablt2.wav");
		setOptionsNames(9);
		getAnimation(80, 16, 16, 16, 3);
	}
	
	private void setOptionsNames(int opt) {
		listNames = new String[opt];
		listNames[0] = "Life Boost";
		listNames[1] = "Speed Boost";
		listNames[2] = "Max Mana";
		listNames[3] = "Mana Recover";
		listNames[4] = "FireBall Strength";
		listNames[5] = "FireBall Speed";
		listNames[6] = "Fire Dash";
		listNames[7] = "Blaze";
		listNames[8] = "Hell Flame";
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
				Game.player.maxMana += 20;
				break;
			case "Mana Recover":
				Game.player.manaRec += 1;
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
					ablt3Dmg += 3;
					ablt3Spd += 0.2;
				}
				break;
			}
		}
	
	public void Attack() {
		if(!sound1.clip.isActive())
			sound1.PlaySound();
		double ang = Math.atan2(Game.my / Game.scale - Game.player.centerY() + Camera.getY(), Game.mx / Game.scale  - Game.player.centerX() + Camera.getX());
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);
		Game.shots.add(new Shot(Game.player.centerX(), Game.player.centerY(), 3, 3, dx, dy, ang, shotSpeed, shotDamage, 35, shotFace));
	}
	
	public void Dash() {
		int manaCost = 12;
		
		if (dashAva && Game.player.mana >= manaCost && !md1) {
			md1 = true;
			Game.player.mana -= manaCost;
			sound3.PlaySound();
		}
		if (md1) {
			Game.player.speedBoost *= 2.5;
			
			di += 1;
			if (tspw > 2) {
				tspw = 0;
			}
			tspw ++;
			if (tspw == 2) {
				Game.entities.add(new AE_Fire(Game.player.centerX(), Game.player.centerY(), 16, 16, null, 125));
				tspw = 0;
			}
			if (di >= dashDistance) {
				md1 = false;
				di = 0;
				Game.player.speed = Game.player.maxSpeed;
			}
		}
	}
	
	public void Ablt2() {
		int manaCost = 34;
		if (ablt2Ava && Game.player.mana >= manaCost && !md2) {
			sound2.PlaySound();
			md2 = true;
			Game.player.mana -= manaCost;
		}
		if (md2) {
			tspw2++;
			double deltaX = Game.mx / Game.scale - Game.player.centerX() + Camera.getX();
			double deltaY = Game.my / Game.scale - Game.player.centerY() + Camera.getY();
			double mag = Math.hypot(deltaX, deltaY);
			if (tspw2 % 4 == 0) {
				Game.entities.add(new AE_Fire2(Game.player.getX(), Game.player.getY(), 16, 16, 2, deltaX / mag, deltaY / mag, ablt2Dmg, null, 60));
			}
			if (tspw2 == maxtspw2) {
				tspw2 = 0;
				md2 = false;
				sound2.StopSound();
			}
		}
	}
	
	public void Ablt3() {
		int manaCost = 68;
		
		if (ablt3Ava && Game.player.mana >= manaCost && !md3) {
			sound3.PlaySound();
			md3 = true;
			Game.player.mana -= manaCost;
		}
		if(md3){
			double deltaX = Game.mx / Game.scale - Game.player.centerX() + Camera.getX();
			double deltaY = Game.my / Game.scale - Game.player.centerY() + Camera.getY();
			double mag = Math.hypot(deltaX, deltaY);
			Game.entities.add(new AE_HellFlame(Game.player.centerX(), Game.player.centerY(), 32, 32, ablt3Spd, deltaX / mag, deltaY / mag, ablt3Dmg, null, 80));
			md3 = false;
		}	
	}
}
