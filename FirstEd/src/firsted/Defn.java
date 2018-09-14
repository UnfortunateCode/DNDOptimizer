package firsted;

import java.util.LinkedList;

public class Defn {
	public static int Female = 0;
	public static int Male = 1;
	
	//Hum, HElf, HOrc, Dwa, Elf, Hling, Gno
	public static int Human = 0;
	public static int HalfElf = 1;
	public static int HalfOrc = 2;
	public static int Dwarf = 3;
	public static int Elf = 4;
	public static int Halfling = 5;
	public static int Gnome = 6;
	public static int numRaces = 7;
	
	public static int Str = 0;
	public static int Int = 1;
	public static int Wis = 2;
	public static int Dex = 3;
	public static int Con = 4;
	public static int Cha = 5;
	
	public static int Cleric = 0;
	public static int Druid = 1;
	public static int Fighter = 2;
	public static int Paladin = 3;
	public static int Ranger = 4;
	public static int MagicUser = 5;
	public static int Illusionist = 6;
	public static int Thief = 7;
	public static int Assassin = 8;
	public static int Monk = 9;
	public static int Bard = 10;
	
	public LinkedList<Race> RaceList = new LinkedList<Race>();
	
	public Defn() {
		for (int i = 0; i < numRaces; ++i) {
			RaceList.add(new Race(i));
		}
	}
}
