package entities;

import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;

public class Poison_Weapon extends Weapon{
    
    public static BufferedImage sprite = Game.sheet.getSprite(144, 32, 16, 16);
    public BufferedImage shotFace;
    private static int poisonPoolW = 16, poisonPoolH = 16, poisonPoolD = 5;
    private int ablt2W = 48, ablt2H = 48, ablt2D = 7;
    private int dsW = 18, dsH= 18, dsD = 5, dsT = 120;
    private int tspw, maxTspw = 180;
    private double ablt3D = 0.03;

    public Poison_Weapon() {
        super(sprite);
        shotFace = Game.sheet.getSprite(208, 32, 16, 16);

        setOptionsNames(9);
        this.getAnimation(160, 32, 16, 16, 3);
    }

    private void setOptionsNames(int opt) {
        this.listNames = new String[opt];
		this.listNames[0] = "Life Boost";
		this.listNames[1] = "Speed Boost";
		this.listNames[2] = "Max Mana";
		this.listNames[3] = "Mana Recover";
		this.listNames[4] = "Poison Strength";
		this.listNames[5] = "Poison Area";
		this.listNames[6] = "Poison Barrier";
		this.listNames[7] = "Venom Pool";
		this.listNames[8] = "Venom Gas";
    }

    public void checkOptions(String opt) {
        switch(opt) {
            case "Life Boost":
                Game.player.maxLife += 10;
                break;
            case "Speed Boost":
                Game.player.maxSpeed += 0.1;
                Game.player.speed = Game.player.maxSpeed;
                break;
            case "Max Mana":
                Game.player.maxMana += 30;
                break;
            case "Mana Recover":
                    Game.player.manaRec += 2;
                break;
            case "Poison Strength":
            		poisonPoolD += 1;
            	break;
            case "Poison Area":
            		poisonPoolW += 1;
            		poisonPoolH += 1;
            	break;
            case "Poison Barrier":
            	if (dashAva) {
            		dsW += 1;
            		dsH += 1;
            		dsD += 1;
            		dsT += 20;
            	}
            	else {
            		dashAva = true;
            	}
            	break;
            case "Venom Pool":
            	if (ablt2Ava) {
            		ablt2W += 8;
            		ablt2H += 8;
            		ablt2D += 2;
            	}
            	else {
            		ablt2Ava = true;
            	}
            	break;
            case "Venom Gas":
            	if (ablt3Ava) {
            		 maxTspw += 20;
                	 ablt3D += 0.01;
            	}
            	else {
            		ablt3Ava = true;
            	}
            	break;
        }
    }

    public static void poisonEffect(Shot atck) {
       Game.entities.add(new AE_PoisonPool(atck.getX(), atck.getY(), poisonPoolW, poisonPoolH, null, 30, poisonPoolD));
    }

    public void Attack() {
		double ang = Math.atan2(my - (Game.player.getY() + 8 - Camera.y) , mx - (Game.player.getX() + 8 - Camera.x));
		double dx = Math.cos(ang);
		double dy =  Math.sin(ang);
		
		Game.shots.add(new Shot(Game.player.getX(), Game.player.getY(), 3, 3, shotFace, dx, dy, 0, 3, 35));
	}

    public void Dash() {
		int manaCost = 30;
		
		if (this.dashAva && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
			Game.entities.add(new AE_PoisonDs(Game.player.getX() - dsW / 2 + 8, Game.player.getY() - dsH / 2 + 8, dsW, dsH, null, dsT, dsD));
			Game.player.dash = false;
			md = false;
        }
    }

    public void Ablt2() {
		int manaCost = 36;
		
		if (ablt2Ava && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
			Game.entities.add(new AE_PoisonPool(Game.player.getX() - ablt2W / 2 + 8, Game.player.getY() - ablt2H / 2 + 8, ablt2W, ablt2H, null, 60, ablt2D));
			Game.player.ablt2 = false;
			md = false;
        }
		
    }

    public void Ablt3() {
		int manaCost = 54;
		
		if (ablt3Ava && Game.player.mana >= manaCost) {
			if (!md) {
				md = true;
				Game.player.mana -= manaCost;
			}
			tspw++;
			double off = Game.rand.nextInt(40);
			int off2 = Game.rand.nextInt(1);
			
			if (off2 == 0) {
				off *= -1;
			}
		
			
			double ang = Math.atan2(Game.my / Game.scale + off - (Game.player.getY() + 8 - Camera.y) , Game.mx / Game.scale - (Game.player.getX() + 8 - Camera.x));
			double dx = Math.cos(ang);
			double dy =  Math.sin(ang);
			
			if (tspw % 2 == 0) {
				Game.entities.add(new AE_VenomGas(Game.player.getX(), Game.player.getY() + 6, 32, 32, 1.3, dx, dy, ablt3D , null, 80));
			}
			if (tspw == maxTspw) {
				tspw = 0;
				Game.player.ablt3 = false;
				md = false;
			}
        }
    }
}
