package main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Menu_Pause {
    private String[] options = {"Initial Menu", "Resume"};
	
	public boolean up, down, enter, space;
	private int cur;

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
			if (options[cur] == "Initial Menu") {
				Game.gameState = "MENUINIT";
			}
			else if (options[cur] == "Resume") {
				Game.gameState = "NORMAL";
			}
		}
	}

    public void render(Graphics g) {
        g.setColor(Color.black); 
		g.fillRect(0, 0, Game.width, Game.height);
        g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString("Pause", 120, 30);
		g.setFont(new Font("arial",  Font.BOLD, 10));
		
		g.drawString(options[0], 120, 60);
		g.drawString(options[1], 120, 90);
		
		if (cur == 0) {
			g.drawString(">", 100, 60);
		}
		else if (cur == 1) {
			g.drawString(">", 100, 90);
		}
    }
}
