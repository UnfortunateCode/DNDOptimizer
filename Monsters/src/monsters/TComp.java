package monsters;

import java.util.Comparator;
import java.util.LinkedList;

public class TComp implements Comparator<Trait> {
	public static final int SNAME = 0;
	public static final int LEVEL = 1;
	public static final int ROLE = 2;
	public static final int LEADER = 3;
	public static final int SIZE = 4;
	public static final int ORIGIN = 5;
	public static final int STYPE = 6;
	public static final int SUBTYPE = 7;
	public static final int SPECTYPE = 8;
	public static final int XP = 9;
	public static final int TTYPE = 10;
	public static final int TNAME = 11;
	public static final int RADIUS = 12;
	public static final int ACTION = 13;
	public static final int RECHARGE = 14;
	public static final int ELEMENTS = 15;
	
	
	private class sortNode {
		int field;
		int order;
		
		public sortNode(int f) {
			field = f;
			order = 1;
		}
		
		public sortNode(int f, int ord) {
			field = f;
			if (ord < 0) {
				order = -1;
			} else if (ord == 0) {
				order = 0;
			} else {
				order = 1;
			}
		}
	}

	LinkedList<sortNode> sorts;
	
	public TComp() {
		sorts = new LinkedList<sortNode>();
	}
	
	public void sortBy(int field) {
		if (field >= 0 && field < 16) {
			sortNode sN = new sortNode(field);
			sorts.addFirst(sN);
		}
	}
	
	public void sortBy(int field, int order) {
		if (field >= 0 && field < 16) {
			sortNode sN = new sortNode(field, order);
			sorts.addFirst(sN);
		}
	}
	
	public void thenBy(int field) {
		if (field >= 0 && field < 16) {
			sortNode sN = new sortNode(field);
			sorts.addLast(sN);
		}
	}
	
	public void thenBy(int field, int order) {
		if (field >= 0 && field < 16) {
			sortNode sN = new sortNode(field, order);
			sorts.addLast(sN);
		}
	}
	
	@Override
	public int compare(Trait o1, Trait o2) {
		if (o1 == null) {
			if (o2 == null) {
				return 0;
			} else {
				return -1;
			}
		} else if (o2 == null) {
			return 1;
		}
		
		for (sortNode sN : sorts) {
			switch (sN.field){
			case SNAME:
				if (!o1.spec.name.equals(o2.spec.name)){
					return sN.order * o1.spec.name.compareTo(o2.spec.name);
				}
				break;
			case LEVEL:
				if (o1.spec.level < o2.spec.level){
					return -s
				}
			}
		}
		if (o1.name.compareTo(o2.name)!= 0 ) {
			return o1.name.compareTo(o2.name);
		}
		return o1.spec.name.compareTo(o2.spec.name);
	}

}
