package main;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Menu_Help {
	public boolean enter;
	
	public void  tick() {
		if (enter) {
			enter = false;
			Game.gameState = "MENUINIT";
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.width, Game.height);
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString("Controls", 120, 20);
		
		g.setFont(new Font("arial", Font.BOLD, 10));
		g.drawString("Moviment Controls: wasd / also in menus", 40, 36);
		g.drawString("Dash Key : space", 40, 51);
		g.drawString("Basic Atack : click on any mouse's buttons", 40, 66);
		g.drawString("Skill : 1", 40, 81);
		g.drawString("Select : enter", 40, 96);
		g.drawString("> Back", 10, 140);
	}
}
