package main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Menu_Init {
	private int cur = 0;
	public boolean up, down, enter;
	private String[] options = {"New Game", "Help", "Exit"};
	
	public void tick() {
		if (down) {
			cur++;
			down = false;
			if (cur > options.length - 1) {
				cur = 0;
			}
		}
		else if (up) {
			cur--;
			up = false;
			if (cur < 0) {
				cur = options.length - 1;
			}
		}
		if (enter) {
			enter = false;
			if (options[cur] == "New Game") {
				Game.gameState = "MENUPLAYER";
			}
			else if (options[cur] == "Help") {
				Game.gameState = "MENUHELP";
			}
			else if (options[cur] == "Exit") {
				System.exit(1);
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black); 
		g.fillRect(0, 0, Game.width, Game.height);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("Dumb Souls", 70, 30);
		g.setFont(new Font("arial",  Font.BOLD, 10));
		
		g.drawString(options[0], 120, 60);
		g.drawString(options[1], 120, 85);
		g.drawString(options[2], 120, 110);
		
		if (cur == 0) {
			g.drawString(">", 90, 60);
		}
		else if (cur == 1) {
			g.drawString(">", 90, 85);
		}
		else if (cur == 2) {
			g.drawString(">", 90, 110);
		}
	}
}
