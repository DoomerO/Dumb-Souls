package main;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import entities.Player;
import entities.weapons.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Save_Game {
	
	private static int w1, w2, w3, w4, w5;
	
	public static void save() throws IOException{
		final int souls = Player.souls;
		final int fireBlock = booleanIntReader(Fire_Weapon.block);
		final int windBlock = booleanIntReader(Wind_Weapon.block);
		final int iceBlock = booleanIntReader(Ice_Weapon.block);
		final int fisicalBlock = booleanIntReader(Fisical_Weapon.block);
		final int poisonBlock = booleanIntReader(Poison_Weapon.block);
		
		final Path path = Paths.get("SaveDS.txt");
		
		 try (final BufferedWriter writer = Files.newBufferedWriter(path,StandardCharsets.UTF_8, StandardOpenOption.CREATE);) {
			 writer.write("" + souls);
			 writer.newLine();
			 writer.write("" + fireBlock);
			 writer.newLine();
			 writer.write("" + windBlock);
			 writer.newLine();
			 writer.write("" + iceBlock);
			 writer.newLine();
			 writer.write("" + fisicalBlock);
			 writer.newLine();
			 writer.write("" + poisonBlock);
		     writer.flush();
		 }
	}
	
	public static void loadSave() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("SaveDS.txt"));
			
			Player.souls = Integer.parseInt(reader.readLine());
			w1 = Integer.parseInt(reader.readLine());
			w2 = Integer.parseInt(reader.readLine());
			w3 = Integer.parseInt(reader.readLine());
			w4 = Integer.parseInt(reader.readLine());
			w5 = Integer.parseInt(reader.readLine());
			
			reader.close();
			
			Fire_Weapon.block = booleanReader(w1);
			Wind_Weapon.block = booleanReader(w2);
			Ice_Weapon.block = booleanReader(w3);
			Fisical_Weapon.block = booleanReader(w4);
			Poison_Weapon.block = booleanReader(w5);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean booleanReader(int value) {
		if (value == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static int booleanIntReader(boolean value) {
		if (value == true) {
			return 0;
		}
		else {
			return 1;
		}
	}
}
