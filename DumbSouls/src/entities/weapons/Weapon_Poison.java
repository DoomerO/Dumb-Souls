package entities.weapons;

import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import entities.shots.Shot_PlayerPoison;
import entities.AE.*;

public class Weapon_Poison extends Weapon{
    
    public static BufferedImage sprite = Game.sheet.getSprite(144, 32, 16, 16);
    private static int poisonPoolW = 32, poisonPoolH = 32, poisonPoolD = 1;
    private int ablt2W = 64, ablt2H = 64, ablt2D = 5;
    private int dsW = 18, dsH= 18, dsD = 5, dsT = 120;
    private int tspw, maxTspw = 180;
    private double ablt3D = 0.05;
    public static int soulCost = 700;
    public static boolean block = true;
	
    public Weapon_Poison() {
		super(sprite);
		super.setAttackTimer(30);
		
        setOptionsNames(9);
        getAnimation(160, 32, 16, 16, 3);
    }
	
    private void setOptionsNames(int opt) {
        listNames = new String[opt];
		listNames[0] = "Life Boost";
		listNames[1] = "Speed Boost";
		listNames[2] = "Max Mana";
		listNames[3] = "Mana Recover";
		listNames[4] = "Poison Strength";
		listNames[5] = "Poison Area";
		listNames[6] = "Poison Barrier";
		listNames[7] = "Venom Pool";
		listNames[8] = "Venom Gas";
    }

    public void checkOptions(String opt) {
        switch(opt) {
            case "Life Boost":
                Game.player.maxLife += 30;
                break;
            case "Speed Boost":
                Game.player.maxSpeed += 0.2;
                Game.player.speed = Game.player.maxSpeed;
                break;
            case "Max Mana":
                Game.player.maxMana += 15;
                break;
            case "Mana Recover":
                    Game.player.manaRec += 0.75;
                break;
            case "Poison Strength":
            		poisonPoolD += 1;
            	break;
            case "Poison Area":
            		poisonPoolW += 8;
            		poisonPoolH += 8;
            	break;
            case "Poison Barrier":
            	if (dashAva) {
            		dsW += 2;
            		dsH += 2;
            		dsD += 2;
            		dsT += 30;
            	}
            	else {
            		dashAva = true;
            	}
            	break;
            case "Venom Pool":
            	if (ablt2Ava) {
            		ablt2W += 16;
            		ablt2H += 16;
            		ablt2D += 1;
            	}
            	else {
            		ablt2Ava = true;
            	}
            	break;
            case "Venom Gas":
            	if (ablt3Ava) {
            		 maxTspw += 20;
                	 ablt3D += 0.03;
            	}
            	else {
            		ablt3Ava = true;
            	}
            	break;
        }
    }

    public void Attack() {
		double ang = Math.atan2(Game.my / Game.scale  - (Game.player.centerY() - Camera.getY()) , Game.mx / Game.scale  - Game.player.centerX() + Camera.getX());
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);
		
		Game.shots.add(new Shot_PlayerPoison(Game.player.centerX(), Game.player.centerY(), dx, dy, ang, poisonPoolD, poisonPoolW, poisonPoolH));
	}
    
    public void Dash() {
		int manaCost = 30;
		
		if (dashAva && Game.player.mana >= manaCost && !md1) {
			md1 = true;
			Game.player.mana -= manaCost;
		}
		if (md1) {
			Game.entities.add(new AE_PoisonDs(Game.player.centerX(), Game.player.centerY(), dsW, dsH, null, dsT, dsD));
			md1 = false;
        }
    }

    public void Ablt2() {
		int manaCost = 36;
		
		if (ablt2Ava && Game.player.mana >= manaCost && !md2) {
			md2 = true;
			Game.player.mana -= manaCost;
		}
		if (md2) {
			Game.entities.add(new AE_PoisonPool(Game.player.centerX(), Game.player.centerY(), ablt2W, ablt2H, null, 150, ablt2D));
			md2 = false;
        }
    }

    public void Ablt3() {
		int manaCost = 54;
		
		if (ablt3Ava && Game.player.mana >= manaCost) {
			if (!md3) {
				md3 = true;
				Game.player.mana -= manaCost;
			}
		}
		if (md3) {
			tspw++;
			double off = Game.rand.nextInt(40);
			int off2 = Game.rand.nextInt(1);
			
			if (off2 == 0) {
				off *= -1;
			}
		
			
			if (tspw % 2 == 0) {
				double deltaX = Game.mx / Game.scale + off - Game.player.centerX() + Camera.getX();
				double deltaY = Game.my / Game.scale + off - Game.player.centerY() + Camera.getY();
				double mag = Math.hypot(deltaX, deltaY);
				Game.entities.add(new AE_VenomGas(Game.player.centerX(), Game.player.centerY() + 6, 32, 32, 1.3, deltaX / mag, deltaY / mag, ablt3D , null, 80));
			}
			if (tspw == maxTspw) {
				tspw = 0;
				md3 = false;
			}
		}
    }
}
