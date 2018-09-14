package monsters;

import java.util.LinkedList;

// Holder for Species name and type
public class Species {
	String name, role, size, origin, type, subtype, specification;
	int level, xp;
	boolean leader;
	LinkedList<Trait> traits;
	
	public Species () {
		name = "";
		role = "";
		size = "";
		origin = "";
		type = "";
		subtype = null;
		specification = null;
		level = -1;
		xp = -1;
		leader = false;
		traits = new LinkedList<Trait>();
	}

	public void setName(String s) {
		name = s;
	}

	public void setLevel(int i) {
		level = i;		
	}

	public void setRole(String s) {
		role = s;
	}

	public void setSize(String s) {
		size = s;
	}

	public void setOrigin(String s) {
		origin = s;
	}

	public void setType(String s) {
		type = s;
	}

	public void setSubType(String s) {
		subtype = s;
	}

	public void setXP(String s) {
		xp = 0;
		for (int i = 0; i < s.length(); ++i) {
			if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
				xp = 10*xp+(s.charAt(i)-'0');
			} else if (s.charAt(i) != ',') {
				return;
			}
		}
	}

	public void setLeader(String s) {
		leader = (s != null);
	}

	public void setSpec(String s) {
		specification = s;
	}

	public void addTrait(Trait trait) {
		traits.add(trait);
	}

	public String toString() {
		String s = name + "\t" + role;
		if (leader) {
			s += " (Leader)";
		}
		s += "\n" + size + " " + origin + " " + type;
		if (subtype != null) {
			s += " (" + subtype + ")";
		}
		if (specification != null) {
			s += ", " + specification;
		}
		s += "\tXP " + xp;
		return s;
	}
}
