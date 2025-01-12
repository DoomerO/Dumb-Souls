package entities.weapons;

import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import entities.shots.Shot_PlayerIce;
import entities.AE.AE_IceDs;
import entities.AE.AE_IceSpike;
import entities.AE.AE_SnowStorm;

public class Weapon_Ice extends Weapon{
	
	public static BufferedImage sprite = Game.sheet.getSprite(64, 48, 16, 16);
	private int shotDamage = 3, shotSpeed = 2, ablt2Dmg = 4, ablt2Quant = 3, ablt3Dmg = 5, dashTime = 300, dt = 0;
	private double ablt3Spd = 0.5;
	private static double frost = 5;
	public static int soulCost = 400;
	 public static boolean block = true;
	
	public Weapon_Ice() {
		super(sprite);
		super.setAttackTimer(6);
		setOptionsNames(9);
		getAnimation(80, 48, 16, 16, 3);
	}
	
	private void setOptionsNames(int opt) {
		listNames = new String[opt];
		listNames[0] = "Life Boost";
		listNames[1] = "Speed Boost";
		listNames[2] = "Max Mana";
		listNames[3] = "Mana Recover";
		listNames[4] = "Cold Strength";
		listNames[5] = "Cold Speed";
		listNames[6] = "Ice Dash";
		listNames[7] = "Ice Spike";
		listNames[8] = "Snow Storm";
	}
	
	public void checkOptions(String opt) {
		switch(opt){
			case "Life Boost":
				Game.player.maxLife += 30;
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
			case "Cold Strength":
				shotDamage += 1;
				frost += 1;
				break;
			case "Cold Speed":
				shotSpeed += 1;
				frost += 0.5;
				break;
			case "Ice Dash":
				if (dashAva == false) {
					dashAva = true;
				}
				else {
					dashTime += 20;
				}
				break;
			case "Ice Spike":
				if (ablt2Ava == false) {
					ablt2Ava = true;
				}
				else {
					ablt2Quant += 1;
					ablt2Dmg += 2;
				}
				break;
			case "Snow Storm":
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
		double ang = Math.atan2(Game.my / Game.scale - (Game.player.centerY() - Camera.getY()) , Game.mx / Game.scale - Game.player.centerX() + Camera.getX());
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);
		
		Game.shots.add(new Shot_PlayerIce(Game.player.centerX(), Game.player.centerY(), dx, dy, shotSpeed, shotDamage, frost));
	}
	
	public void Dash() {
		int manaCost = 25;
		if (dashAva && Game.player.mana >= manaCost && !md1) {
			md1 = true;
			Game.player.mana -= manaCost;
		}
		if (md1) {
			dt += 1;
			double dashSpeed = Game.player.speed / 3;
			if (dt < dashTime) {
				Game.player.moveX += Math.signum(Game.player.moveX) * dashSpeed;
				Game.player.moveY += Math.signum(Game.player.moveY) * dashSpeed;
				if(dt % 4 == 0) {
					Game.entities.add(new AE_IceDs(Game.player.centerX(), Game.player.centerY() + 4, 16, 16, null, 60));
				}
			}
			else {
				md1 = false;
				dt = 0;
			}
		}
	}

	public void Ablt2() {
		int manaCost = 50;
		if (ablt2Ava && Game.player.mana >= manaCost && !md2) {
			md2 = true;
			Game.player.mana -= manaCost;
		}
		if (md2) {
			for (int c = 0; c < 8; c++) {
				for (int i = 1; i <= ablt2Quant; i++) {
					Game.entities.add(new AE_IceSpike((int)(Game.player.centerX() + i * 14 * Math.cos(c * Math.PI / 4)), (int)(Game.player.centerY() + i * 14 * Math.sin(c * Math.PI / 4)), 6, 16, null, 60, ablt2Dmg));
				}
			}
			md2 = false;
		}
	}
	
	public void Ablt3() {
		int manaCost = 60;
		if (ablt3Ava && Game.player.mana >= manaCost && !md3) {
			md3 = true;
			Game.player.mana -= manaCost;
		}
		if (md3) {
			Game.entities.add(new AE_SnowStorm(Game.player.centerX() - 16, Game.player.centerY() - 16, 32, 32, null, 240, ablt3Spd, ablt3Dmg));
			md3 = false;
		}
	}
}
