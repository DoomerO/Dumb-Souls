package entities;

import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;

public class Ice_Weapon extends Weapon{
	
	public BufferedImage shotFace;
	public static BufferedImage sprite = Game.sheet.getSprite(64, 48, 16, 16);
	private int shotDamage = 3, shotSpeed = 2, dashTime = 300, dt = 0;
	private double ablt2Dmg = 5;
	private static double frost = 5;
	private double ablt3Dmg = 0.1, ablt3Spd = 0.5;
	
	public Ice_Weapon() {
		super(sprite);
		shotFace = Game.sheet.getSprite(128, 48, 16, 16);
		
		setOptionsNames(9);
		this.getAnimation(80, 48, 16, 16, 3);
	}
	
	private void setOptionsNames(int opt) {
		this.listNames = new String[opt];
		this.listNames[0] = "Life Boost";
		this.listNames[1] = "Speed Boost";
		this.listNames[2] = "Max Mana";
		this.listNames[3] = "Mana Recover";
		this.listNames[4] = "Cold Strength";
		this.listNames[5] = "Cold Speed";
		this.listNames[6] = "Ice Dash";
		this.listNames[7] = "Ice Spike";
		this.listNames[8] = "Snow Storm";
	}
	
	public void checkOptions(String opt) {
		switch(opt){
			case "Life Boost":
				Game.player.maxLife += 20;
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
					ablt2Dmg += 0.1;
				}
				break;
			case "Snow Storm":
				if (ablt3Ava == false) {
					ablt3Ava = true;
				}
				else {
					ablt3Dmg += 0.1;
					ablt3Spd += 0.1;
				}
				break;
		}
	}
	
	public void Attack() {
		double ang = Math.atan2(my - (Game.player.getY() + 8 - Camera.y) , mx - (Game.player.getX() + 8 - Camera.x));
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);
		
		Shot e = new Shot(Game.player.getX(), Game.player.getY(), 3, 3, shotFace, dx, dy, shotDamage, shotSpeed, 35);
		Game.shots.add(e);
	}

	public static void IceAffect(Enemy e1, Shot e2) {
		if (Entity.isColiding(e1, e2)) {
			e1.frost += frost;
		}
	}
	
	public void Dash() {
		int manaCost = 25;
		if (this.dashAva && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
			dt += 1;
			double dashSpeed = Game.player.speed + 0.8;
			if (dt != dashTime) {
				if (Game.player.right) {
					Game.player.x += dashSpeed ;
				}
				else if (Game.player.left) {
					Game.player.x -= dashSpeed;
				}
				if (Game.player.down) {
					Game.player.y += dashSpeed;
				}
				else if (Game.player.up) {
					Game.player.y -= dashSpeed;
				}
				if(dt % 4 == 0) {
					Game.entities.add(new AE_IceDs(Game.player.getX(), Game.player.getY() + 5, 16, 16, null, 60));
				}
			}
			else {
				Game.player.dash = false;
				md = false;
				dt = 0;
			}
			
		}
	}

	public void Ablt2() {
		int manaCost = 50;
		if (ablt2Ava && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
			for (int c = 1; c <= 3; c++) {
				Game.entities.add(new AE_IceSpike(Game.player.getX() + (13 * c), Game.player.getY(), 6, 16, null, 60, ablt2Dmg));
			}
			for (int c = 1; c <= 3; c++) {
				Game.entities.add(new AE_IceSpike(Game.player.getX(), Game.player.getY() + (13 * c), 6, 16, null, 60, ablt2Dmg));
			}
			for (int c = 1; c <= 3; c++) {
				Game.entities.add(new AE_IceSpike(Game.player.getX() - (13 * c), Game.player.getY(), 6, 16, null, 60, ablt2Dmg));
			}
			for (int c = 1; c <= 3; c++) {
				Game.entities.add(new AE_IceSpike(Game.player.getX(), Game.player.getY() - (13 * c), 6, 16, null, 60, ablt2Dmg));
			}
			for (int c = 1; c <= 3; c++) {
				Game.entities.add(new AE_IceSpike(Game.player.getX() + (8 * c), Game.player.getY() + (8 * c), 6, 16, null, 60, ablt2Dmg));
			}
			for (int c = 1; c <= 3; c++) {
				Game.entities.add(new AE_IceSpike(Game.player.getX() - (8 * c), Game.player.getY() - (8 * c), 6, 16, null, 60, ablt2Dmg));
			}
			for (int c = 1; c <= 3; c++) {
				Game.entities.add(new AE_IceSpike(Game.player.getX() + (8 * c), Game.player.getY() - (8 * c), 6, 16, null, 60, ablt2Dmg));
			}
			for (int c = 1; c <= 3; c++) {
				Game.entities.add(new AE_IceSpike(Game.player.getX() - (8 * c), Game.player.getY() + (8 * c), 6, 16, null, 60, ablt2Dmg));
			}
			Game.player.ablt2 = false;
			md = false;
		}
	}
	
	public void Ablt3() {
		int manaCost = 60;
		if (ablt3Ava && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
			Game.entities.add(new AE_SnowStorm(Game.player.getX(), Game.player.getY(), 32, 32, null, 240, ablt3Spd, ablt3Dmg));
			md = false;
			Game.player.ablt3 = false;
		}
	}
}
