package monsters;

//Holder for trait/ability info
public class Trait {

	public static final int TRAIT = 0;
	public static final int AURA = 1;
	public static final int B_MELEE = 2;
	public static final int B_RANGED = 3;
	public static final int MELEE = 4;
	public static final int RANGED = 5;
	public static final int CLOSE = 6;
	public static final int AOE = 7;
	
	
	
	Species spec;
	String name, radius, action, recharge, desc, element;
	int type;
	
	public Trait () {
		name = null;
		radius = null;
		action = null;
		recharge = null;
		desc = "";
		element = null;
		type = TRAIT;
	}
	
	public String typeS() {
		switch (type) {
		case TRAIT:
			return "Trait";
		case AURA:
			return "Aura";
		case B_MELEE:
			return "Melee Basic";
		case B_RANGED:
			return "Ranged Basic";
		case MELEE:
			return "Melee";
		case RANGED:
			return "Ranged";
		case CLOSE:
			return "Close";
		case AOE:
			return "AoE";
		}
		return "Trait";
	}
	
	public void setSpec(Species sp) {
		spec = sp;
	}
	
	public void setType(int i) {
		type = 0;
		if (i < 0 || i > 7) {
			type = i;
		}
	}

	public void setName(String s) {
		name = s;
	}

	public void setRadius(String s) {
		radius = s;
	}

	public void setDesc(String s) {
		desc = s;
	}

	public void appendDesc(String s) {
		desc += " " + s;
	}

	public boolean hasName() {
		return name != null;
	}

	public void setType(String s) {
		if (s == null || s.equals("")) {
			type = TRAIT;
		} else if (s.equals("m")) {
			type = B_MELEE;
		} else if (s.equals("r")) {
			type = B_RANGED;
		} else if (s.equals("M")) {
			type = MELEE;
		} else if (s.equals("R")) {
			type = RANGED;
		} else if (s.equals("C")) {
			type = CLOSE;
		} else if (s.equals("A")) {
			type = AOE;
		} else if (s.equals("aura")) {
			type = AURA;
		} else {
			type = TRAIT;
		}
	}

	public void setAction(String s) {
		action = s;
	}

	public void setRecharge(String s) {
		recharge = s;
	}

	public void setElement(String s) {
		element = s;
	}

	public String toString() {
		String s;

		switch (type) {
		case TRAIT:
			s = name;
			break;
		case AURA:
			s = name + " aura " + radius;
			break;
		case B_MELEE:
			s = "m " + name;
			break;
		case B_RANGED:
			s = "r " + name;
			break;
		case MELEE:
			s = "M " + name;
			break;
		case RANGED:
			s = "R " + name;
			break;
		case CLOSE:
			s = "C " + name;
			break;
		case AOE:
			s = "A " + name;
			break;
		default:
			s = name;
		}
		
		if (action != null || recharge != null) {
			s += " (";
			if (action != null) {
				s += action;
			}
			s += "; ";
			if (recharge != null) {
				s += recharge;
			}
			s += ")";
		}
		
		if (element != null) {
			s += " \u2726 " + element;
		}
		
		s += "\n" + desc;
		
		return s;			
	}

	private static void main (String args[]) {
		Trait t = new Trait();
		t.setAction("m");
		t.setName("Bite");
		t.setAction("standard");
		t.setRecharge("at-will");
		t.setElement("Fire");
		t.setDesc("+24 vs. AC; 2d6 + 1 damage plus 1d6 fire damage.");
		
		System.out.println(t);
	}
}
