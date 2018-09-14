package firsted;

import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {

		Dice d = new Dice();
		d.roll(2, 6, 1, 4, 6, 1, 3);
/**/		
		LinkedList<Archetype> chars = new LinkedList<Archetype>();
		
		Defn defs = new Defn();
			
		for (Race r : defs.RaceList) {
			for (Classline c : r.classlines()) {
				chars.add(new Archetype(r, c, defs.Female));
				chars.add(new Archetype(r, c, defs.Male));
			}
		}
		
/**/
	}

}
