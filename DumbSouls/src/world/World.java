package world;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;
import entities.*;


public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static int maxEnemies = 10, wave = 1;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			tiles = new Tile[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for (int xx = 0; xx < WIDTH; xx++) {
				for (int yy=0; yy < HEIGHT; yy++) {
					
					int actual = pixels[xx + (yy * WIDTH)];
					
					tiles[xx + (yy * WIDTH)] = new Floor_Tile(xx * 16, yy * 16, Tile.floor_sprite[Game.rand.nextInt(Tile.floor_sprite.length)]);
					 
					if (actual == 0xFFFFFFFF) {
						tiles[xx + (yy * WIDTH)] = new Wall_Tile(xx * 16, yy * 16, Tile.wall_sprite);
					}
					else if (actual == 0xFF000000) {
						tiles[xx + (yy * WIDTH)] = new Floor_Tile(xx * 16, yy * 16, Tile.floor_sprite[Game.rand.nextInt(Tile.floor_sprite.length)]);
						if (Game.enemies.size() <= maxEnemies) {
							if (Game.rand.nextInt(100) <= 10) {
								Game.enemies.add(new Base_Enemy(xx * 16, yy * 16, 16, 16, Enemy.baseSprite));
							}
						}
					}
					
					if (actual == 0xFF0000FF) {
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);
					}
					
					if (actual == 0xFFFF0000) {
						continue;
					}
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void rizeMaxEnemies() {
		maxEnemies += 2;
	}
	
	public void spawnBoss() {
		while (true) {
			int pe = Game.rand.nextInt(100);
			int ex = Game.rand.nextInt(WIDTH - 2);
			int ey = Game.rand.nextInt(HEIGHT - 2);
			if (tiles[ex + (ey * WIDTH)] instanceof Floor_Tile) {
				if (pe < 50) {
					Game.enemies.add(new Boss_Sucubus(ex * 16, ey * 16, 32, 32, null));
				}
				else {
					Game.enemies.add(new Boss_Duality(ex * 16, ey * 16, 32, 32, null));
				}
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
					Game.enemies.add(new Base_Enemy(ex * 16, ey * 16, 16, 16, null));
				}
				else if (wave <= 11){
					if (pe <= 70) {
						Game.enemies.add(new Base_Enemy(ex * 16, ey * 16, 16, 16, null));
					}
					else if (pe <= 95){
						Game.enemies.add(new Eye_Enemy(ex * 16, ey * 16, 16, 16, null));
					}
					else{
						Game.enemies.add(new Mouth_Enemy(ex * 16, ey * 16, 16, 16, null));
					}
				}
				else if (wave <= 17) {
					if (pe <= 60) {
						Game.enemies.add(new Base_Enemy(ex * 16, ey * 16, 16, 16, null));
					}
					else if (pe <= 80) {
						Game.enemies.add(new Eye_Enemy(ex * 16, ey * 16, 16, 16, null));
					}
					else if (pe <= 90) {
						Game.enemies.add(new Mouth_Enemy(ex * 16, ey * 16, 16, 16, null));
					}
					else if (pe <= 99) {
						Game.enemies.add(new Trapper_Enemy(ex * 16, ey * 16, 32, 16, null));
					}
					else{
						Game.enemies.add(new Barrier_Enemy(ex * 16, ey * 16, 48, 32, null));
					}
				}
				else{
					if (pe <= 45) {
						Game.enemies.add(new Base_Enemy(ex * 16, ey * 16, 16, 16, null));
					}
					else if (pe <= 60) {
						Game.enemies.add(new Mouth_Enemy(ex * 16, ey * 16, 16, 16, null));
					}
					else if (pe <= 85){
						Game.enemies.add(new Eye_Enemy(ex * 16, ey * 16, 16, 16, null));
					}
					else if (pe <= 95) {
						Game.enemies.add(new Trapper_Enemy(ex * 16, ey * 16, 32, 16, null));
					}
					else{
						Game.enemies.add(new Barrier_Enemy(ex * 16, ey * 16, 48, 32 , null));
					}
				}
				break;
			}
		}
		
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xend = xstart + (Game.width >> 4);
		int yend = ystart + (Game.height >> 4);
		
		for (int xx = xstart; xx <= xend; xx++) {
			for(int yy = ystart; yy <= yend; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}
}
