package world;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import entities.enemies.*;
import main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static int maxEnemies = 10, wave = 0;
	public static String bossName;
	public static boolean bossTime;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(new FileInputStream(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			tiles = new Tile[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for (int xx = 0; xx < WIDTH; xx++) {
				for (int yy=0; yy < HEIGHT; yy++) {
					
					int current = pixels[xx + (yy * WIDTH)];
					
					tiles[xx + (yy * WIDTH)] = new Floor_Tile(xx * 16, yy * 16, Tile.floor_sprite[Game.rand.nextInt(Tile.floor_sprite.length)]);
					
					switch (current) {
					case 0xFFFFFFFF:
						tiles[xx + (yy * WIDTH)] = new Wall_Tile(xx * 16, yy * 16, Tile.wall_sprite);
						break;
					case 0xFF0000FF:
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);
						Camera.x = xx * 16;
						Camera.y = yy * 16;
						Game.mx = Game.width / 2;
						Game.my = Game.height / 2;
						break;
					}
				}
			}
		}catch(IOException exc) {
			exc.printStackTrace();
		}	
	}
	
	public void raiseMaxEnemies() {
		maxEnemies += 2;
	}
	
	public void spawnBoss() {
		while (true) {
			int pe = Game.rand.nextInt(3);
			int ex = Game.rand.nextInt(WIDTH - 2);
			int ey = Game.rand.nextInt(HEIGHT - 2);
			if (tiles[ex + (ey * WIDTH)] instanceof Floor_Tile) {
				switch(pe){
				case 0:
					Game.enemies.add(new Boss_Sucubus(ex * 16, ey * 16));
					bossName = "Sucubus";
					break;
				case 1:
					Game.enemies.add(new Boss_Duality(ex * 16, ey * 16));
					bossName = "Duality";
					break;
				case 2:
					Game.enemies.add(new Boss_Hive(ex * 16, ey * 16));
					bossName = "Hive";
					break;
				}
				bossTime = true;
				break;
			}
		}		
	}

	public void spawnEnemy() {
		while (true) {
			int pe = Game.rand.nextInt(100);
			int ex = Game.rand.nextInt(WIDTH);
			int ey = Game.rand.nextInt(HEIGHT);
			if (tiles[ex + (ey * WIDTH)] instanceof Floor_Tile) {
				if (wave <= 5) {
					Game.enemies.add(new Enemy_Stain(ex * 16, ey * 16));
				}
				else if (wave <= 10){
					if (pe <= 70) {
						Game.enemies.add(new Enemy_Stain(ex * 16, ey * 16));
					}
					else if (pe <= 95){
						Game.enemies.add(new Enemy_Eye(ex * 16, ey * 16));
					}
					else{
						Game.enemies.add(new Enemy_Mouth(ex * 16, ey * 16));
					}
				}
				else if (wave <= 17) {
					if (pe <= 60) {
						Game.enemies.add(new Enemy_Stain(ex * 16, ey * 16));
					}
					else if (pe <= 80) {
						Game.enemies.add(new Enemy_Eye(ex * 16, ey * 16));
					}
					else if (pe <= 90) {
						Game.enemies.add(new Enemy_Mouth(ex * 16, ey * 16));
					}
					else if (pe <= 99) {
						Game.enemies.add(new Enemy_Trapper(ex * 16, ey * 16));
					}
					else{
						Game.enemies.add(new Enemy_Barrier(ex * 16, ey * 16));
					}
				}
				else{
					if (pe <= 45) {
						Game.enemies.add(new Enemy_Stain(ex * 16, ey * 16));
					}
					else if (pe <= 60) {
						Game.enemies.add(new Enemy_Mouth(ex * 16, ey * 16));
					}
					else if (pe <= 85){
						Game.enemies.add(new Enemy_Eye(ex * 16, ey * 16));
					}
					else if (pe <= 95) {
						Game.enemies.add(new Enemy_Trapper(ex * 16, ey * 16));
					}
					else{
						Game.enemies.add(new Enemy_Barrier(ex * 16, ey * 16));
					}
				}
				break;
			}
		}
		
	}
	
	public void render() {
		int xstart = Camera.getX() >> 4;
		int ystart = Camera.getY() >> 4;
		
		int xend = xstart + (Game.width >> 4);
		int yend = ystart + (Game.height >> 4);
		
		for (int xx = xstart; xx <= xend; xx++) {
			for(int yy = ystart; yy <= yend; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render();
			}
		}
	}
}
