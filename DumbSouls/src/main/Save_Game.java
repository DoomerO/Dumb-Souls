package main;

import entities.Player;
import entities.runes.*;
import entities.weapons.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

public class Save_Game{

	private static int bitIntWriter(boolean value, int bitIndex) {
		return value ? 0 : bitIndex;
	}
	
	public static void save() throws Exception{
		int weaponSum = bitIntWriter(Weapon_Fire.block, 1);
		weaponSum += bitIntWriter(Weapon_Wind.block, 2);
		weaponSum += bitIntWriter(Weapon_Ice.block, 4);
		weaponSum += bitIntWriter(Weapon_Fisical.block, 8);
		weaponSum += bitIntWriter(Weapon_Poison.block, 16);
		try (final PrintWriter writer = new PrintWriter("SaveDS.save")) {
			writer.write((char)(Player.souls & -16777216));
			writer.write((char)(Player.souls & 16711680));
			writer.write((char)(Player.souls & 65280));
			writer.write((char)(Player.souls & 255));
			writer.write((char)weaponSum);
			if(!Player.runesInventory.isEmpty()){
				for(Rune r : Player.runesInventory){
					writer.write(r.index);
				}
			}
			writer.close();
		} catch(Exception exc){}
	}

	private static boolean intBitReader(int value, int bitIndex){
		return (value & bitIndex) == 0;
	}
	
	public static void loadSave(){
		try {
			BufferedInputStream reader = new BufferedInputStream(new FileInputStream("SaveDS.save"));
			
			int souls[] = new int[4], weapons[] = new int[1], runes[] = new int[5];
			for(int i = 0; i < souls.length; i++){
				souls[i] = reader.read();
				if(souls[i] < 0){
					souls[i] = 0;
				}
			}
			weapons[0] = reader.read();
			if(weapons[0] < 0){
				weapons[0] = 0;
			}
			for(int i = 0; i < runes.length; i++){
				runes[i] = reader.read();
			}
			reader.close();
			Player.souls = souls[0] << 24 | souls[1] << 16 | souls[2] << 8 | souls[3];
			Weapon_Fire.block = intBitReader(weapons[0], 1);
			Weapon_Wind.block = intBitReader(weapons[0], 2);
			Weapon_Ice.block = intBitReader(weapons[0], 4);
			Weapon_Fisical.block = intBitReader(weapons[0], 8);
			Weapon_Poison.block = intBitReader(weapons[0], 16);
			for(int i = 0; i < runes.length; i++){
				if(runes[i] > 0){
					Player.runesInventory.add(InventoryMaker(runes[i]));
				}
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
			try{
				new File("SaveDS.save").createNewFile();
				PrintWriter pWriter = new PrintWriter("SaveDS.save");
				pWriter.print("\0\0\0\0\0");
				pWriter.close();
			} catch(Exception err){}
		}
	}
	
	private static Rune InventoryMaker(int index) {
		switch(index) {
		case 1:
			return new Life_Rune();
		case 2:
			return new Mana_Rune();
		case 3:
			return new Speed_Rune();
		case 4:
			return new EXP_Rune();
		case 5:
			return new MultiAttack_Rune();
		default:
			return null;
		}
	}
}
