package monsters;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manual {
	LinkedList<Species> specList;
	LinkedList<Trait> traitList;
	
	public Manual() {
		specList = new LinkedList<Species> ();
		traitList = new LinkedList<Trait> ();
	}

	public boolean read(String file) {
		return readOne(file);
	}
	/**
	 * @param args
	 */
	public boolean readOne(String file) {
		Pattern SpecStart = Pattern.compile("^((?:[A-Z][,\\w\\-]*)(?:\\s[,\\w\\-]+)*(?:\\s?\\([,\\w\\-\\s]*\\))?)\\s(?:Level\\s(\\d+)\\s)(\\s?\\w+(?:\\s\\w+)?)\\s?+(?:\\(((Leader))\\))?$");
		Pattern SpecType = Pattern.compile("^([A-Z]\\w*)\\s(\\w*)\\s(\\w*(?:\\s\\w+)?)\\s?(?:\\(([^\\)]+)\\))?(?:,\\s(\\w+))?\\sXP\\s((\\d*,?)+)$");
		Pattern Init = Pattern.compile("^Initiative .*$");
		Pattern HP = Pattern.compile("^[HAR][PCe] .*$");
		Pattern AuraStart = Pattern.compile("^([A-Z].*)\\saura\\s([^\\;]+);\\s?((.*))$");
		Pattern capLine = Pattern.compile("^[A-Z].*$");
		Pattern AbilityStart = Pattern.compile("^([mrMRCA]?)\\s?([A-Z][^A-Z](?:\\s?[A-Za-z’'\\-]+)?(?:\\s?[A-Za-z’'\\-]+)?(?:\\s?[A-Za-z’'\\-]+)?(?:\\s?[A-Za-z’'\\-]+)?)(?:\\s?(?:\\(([^(\\)|;)]+);?\\s?([^\\)]*)\\)?)?)(?:\\s[\\?\\u2726]\\s?((\\s?[A-Za-z]+[,;]?)+)?)?$");
		Pattern Trt = Pattern.compile("^(.+)\\s(([A-Z][^A-Z]*))$");
		Pattern Speed = Pattern.compile("^Speed.*");
		Pattern Align = Pattern.compile("^Align.*$");
		Pattern unclosed = Pattern.compile("^.*((\\([^\\)]*)|.*,)$");
		Matcher match;

		PrintWriter out = null, err = null;

		Species spec;
		Trait trait = new Trait();
		String currLine, nextLine;

		try {
			//FileInputStream fread = new FileInputStream("C:\\Documents and Settings\\HP_Administrator.HP-ADMIN\\My Documents\\PaulH\\creations\\DnDMM.txt");
			FileInputStream fread = new FileInputStream(file);

			InputStreamReader isr = new InputStreamReader(fread, "UTF-16");
			BufferedReader buff = new BufferedReader(isr);
			out = new PrintWriter(System.out, true);
			err = new PrintWriter(System.err, true);

			//in line
			while ((currLine = buff.readLine()) != null) { //read in non eof line to currLine
//err.println("1\t\t" + currLine);
				spec = new Species();
				trait = new Trait(); // for junk input
				
				//Search for Stat Block
				match = SpecStart.matcher(currLine);
				if (match.matches()) {
//err.println(":SS:\t" + currLine);
					spec = new Species();
					spec.setName(match.group(1));
					spec.setLevel(Integer.parseInt(match.group(2)));
					spec.setRole(match.group(3));
					spec.setLeader(match.group(4));

					//Get Type
					currLine = buff.readLine(); // read in next line
//err.println("2\t\t" + currLine);
					match = SpecType.matcher(currLine);
					if (match.matches()) {
//err.println(":ST:\t" + currLine);
						spec.setSize(match.group(1));
						spec.setOrigin(match.group(2));
						spec.setType(match.group(3));
						spec.setSubType(match.group(4));
						spec.setSpec(match.group(5));
						spec.setXP(match.group(6));

						specList.add(spec);

						//find aura's & traits
						//skip to Initiative line
						while(!Init.matcher(currLine = buff.readLine()).matches()) {
//err.println("3\t\t" + currLine);							
						}
						currLine = buff.readLine();
//err.println("4\t\t" + currLine);
						while (!capLine.matcher(currLine).matches()) {
							currLine = buff.readLine();
						}
						// First line after Init, if it isn't HP/AC/Regen
						// then it is an aura or a trait
						if (!HP.matcher(currLine).matches() && !AuraStart.matcher(currLine).matches()) {
//err.println(":Trait:\t" + currLine);
							// First line is a Trait
							match = Trt.matcher(currLine);
							if (match.matches()) {
/*
for (int i = 0; i < match.groupCount(); ++i) {
	err.print("[" + match.group(i) + "]");
}
err.println("<---");
*/
								trait = new Trait();
								trait.setName(match.group(1));
								trait.setType(Trait.TRAIT);
								trait.setDesc(match.group(2));
							}
							currLine = buff.readLine();
//err.println("5\t\t" + currLine);
							while (!capLine.matcher(currLine).matches()) {
								trait.appendDesc(currLine);
								currLine = buff.readLine();
//err.println("6\t\t" + currLine);
							}
							spec.addTrait(trait);
							trait.setSpec(spec);
							traitList.add(trait);
						}
						while (!HP.matcher(currLine).matches()) {
							match = capLine.matcher(currLine);
							if (capLine.matcher(currLine).matches()) {
								if (trait.hasName()) {
									spec.addTrait(trait);
									traitList.add(trait);
									trait.setSpec(spec);
								}
								trait = new Trait();
								// line was a cap, not HP
								match = AuraStart.matcher(currLine);
								if (match.matches()) {
//err.println(":Aura:\t" + currLine);
									trait.setType(Trait.AURA);
									trait.setName(match.group(1));
									trait.setRadius(match.group(2));
									trait.setDesc(match.group(3));
								} 
							} else {
								// line was not a cap, append
								trait.appendDesc(currLine);
							}

							//*/
							currLine = buff.readLine(); // read in next line
//err.println("7\t\t" + currLine);
						} // currLine was HP line, any aura's and auto traits have been found
						
						// find Abilities and Traits
						// read lines in until the Speed Line
						while (!Speed.matcher(currLine = buff.readLine()).matches()){
//err.println("8\t\t" + currLine);
						}
						
						// Next line is either an Ability Points line, or a continuation, or the first Trait Line
						currLine = buff.readLine();
//err.println("9\t\t" + currLine);
						trait = new Trait();
						while (!Align.matcher(currLine).matches()) {
							
							// If the line isn't closed, (no ')' or ends with ','), attach
							// next line and check if it matches, if not, append first to
							// previous trait, and set next to current
							if (unclosed.matcher(currLine).matches()) {
								nextLine = buff.readLine();
//err.println("0\t\t" + nextLine);
								if (AbilityStart.matcher(currLine + " " + nextLine).matches()) {
									currLine = currLine + " " + nextLine;
								} else {
									trait.appendDesc(currLine);
									currLine = nextLine;
								}
							}
							
							// If current matches the AbilityStart Pattern, set up a new
							// Trait and add in the relevant info
							match = AbilityStart.matcher(currLine);
							if (match.matches()) {
//err.println(":Abil:\t" + currLine);								
								// store the old trait first, if it was properly started
								if (trait != null && trait.hasName()) {
									spec.addTrait(trait);
									traitList.add(trait);
									trait.setSpec(spec);
								}
								trait = new Trait();
								trait.setType(match.group(1));
								trait.setName(match.group(2));
								trait.setAction(match.group(3));
								trait.setRecharge(match.group(4));
								trait.setElement(match.group(5));
								// First line after Ability is Description
								currLine = buff.readLine();
//err.println("A\t\t" + currLine);
								trait.setDesc(currLine);
							} else {
								// Line wasn't an ability starter, so just a previous description
								trait.appendDesc(currLine);
							}
							currLine = buff.readLine();
//err.println("B\t\t" + currLine);
						}
					} else {
						System.err.println("bad input: " + currLine);
					}
				}
			}

		} catch (Exception e) {
			System.err.println(e);
			err.println(e);
			return false;
		}
		out.println("Yay!");
		return true;
	}
	
	public static void main(String args[]) {
		Manual man = new Manual();
		if (man.read("E:\\Downloads\\DnDMM.txt")) {
			System.out.println("Double Yay!");
		}
		
		TComp tc = new TComp();
		TreeSet<Trait> ts = new TreeSet<Trait>(tc);
		
		for (Trait t : man.traitList){
			if (t.name != null) {
				ts.add(t);
			}
		}
		
		try {
		PrintWriter out = new PrintWriter(new FileWriter("E:\\Downloads\\DnDMMFormatted.txt"));
		for (Trait t : ts) {
			out.print(t.spec.name + "\t");
			out.print(t.spec.level + "\t");
			out.print(t.spec.role + "\t");
			if (t.spec.leader) {
				out.print("Leader");
			}
			out.print("\t");
			out.print(t.spec.size + "\t");
			out.print(t.spec.origin + "\t");
			out.print(t.spec.type + "\t");
			if (t.spec.subtype != null) {
				out.print(t.spec.subtype);
			}
			out.print("\t");
			if (t.spec.specification != null) {
				out.print(t.spec.specification);
			}
			out.print("\t");
			out.print(t.spec.xp + "\t");
			
			out.print(t.typeS() + "\t");
			out.print(t.name + "\t");
			if (t.radius != null) {
				out.print(t.radius);
			}
			out.print("\t");
			if (t.action != null) {
				out.print(t.action);
			}
			out.print("\t");
			if (t.recharge != null) {
				out.print(t.recharge);
			}
			out.print("\t");
			if (t.element != null) {
				out.print(t.element);
			}
			out.print("\t");
			out.println(t.desc);
			
		}
		out.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
