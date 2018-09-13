package structures;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;


/**
 * A list of all Stat sets based on a number of stats, a point cost
 * chart and a maximum point value.
 * <p>
 * Example stats are in the Dungeons and Dragons tabletop game. In this
 * game, player characters have the following stats: Strength, Dexterity,
 * Constitution, Intelligence, Wisdom and Charisma. Players are given the
 * option to spend up to 27 points to increase these stats from the score 8
 * to a higher score up to 15. The scores 8 through 13 have costs of 0
 * through 5 respectively. The score 14 has a cost of 7, and the score 15
 * has a cost of 9. Thus, a player could have scores as extreme as [15,15,15,0,0,0]
 * to as flat as [13,13,13,12,12,12].
 * <p>
 * Stat sets listed in this class are ordered from the largest cost to the 
 * smallest cost. Thus, the first value in the stats array will be the best
 * value of the array.
 * <p>
 * This class is assuming it is implementing point-buy systems that humans
 * would do manually. It is not intended for large scale packing problems.
 * 
 * @author  UnfortunatelyEvil
 * @version 1.1, 09/10/18
 */
public class StatList {

	private final static LinkedList<StatList> constructedLists = new LinkedList<>(); 
	
	private TreeMap<Integer, Integer> pointCosts; 
	private int numStats; 
	private int pointTotal; 
	private LinkedList<int[]> linkStatList; // set to null after construction of arrStatList
	private int[][] arrStatList;

	/**
	 * Returns a <code>StatList</code> built on the cost/score map, number of stats, and total available points.
	 * 
	 * 
	 * @param pointCosts  mapping of the cost (key) and best score obtained from it (value). Do not include overflow 
	 *                    costs ([2,13], [3,13], [4,14] : [3,13] wastes a point). Do include a default value if it 
	 *                    exists: [0,8].
	 * @param numStats    number of stats to spread points across
	 * @param pointTotal  total number of points to spread across numStats stats
	 * @return            a series of numStats possible stats that have a combined cost equal to the pointTotal
	 */
	public static StatList getInstance (Map<Integer, Integer> pointCosts, int numStats, int pointTotal) {
		
		for (StatList sl : constructedLists) {
			if (pointTotal == sl.pointTotal && numStats == sl.numStats && pointCosts.equals(sl.pointCosts)) {
				return sl;
			}
		}
		
		StatList sl = new StatList(pointCosts, numStats, pointTotal);
		constructedLists.add(sl);
		return sl;
	}
	
	/**
	 * Returns a <code>StatList</code> built on the cost and score arrays, number of stats, and total available points.
	 * 
	 * @param costs       array of specified costs for scores. These should only include the lowest cost for any given score.
	 * @param scores      array of the scores obtained from the costs. Scores should be in the same index as the corresponding
	 *                    cost.
	 * @param numStats    number of stats to spread points across
	 * @param pointTotal  total number of points to spread across numStats stats
	 * @return            a series of numStats possible stats that have a combined cost equal to the pointTotal
	 * 
	 * @throws IndexOutOfBoundsException if the costs and scores arrays have different lengths        
	 */
	public static StatList getInstance(int[] costs, int[] scores, int numStats, int pointTotal) throws IndexOutOfBoundsException {
		if (costs.length != scores.length) {
			throw new IndexOutOfBoundsException("Costs/Scores length mismatch");
		}
		
		TreeMap<Integer, Integer> tm = new TreeMap<>();
		
		for (int i = 0; i < costs.length; ++i) {
			tm.put(costs[i], scores[i]);
		}
		
		return getInstance(tm, numStats, pointTotal);
	}
	
	
	private StatList(Map<Integer, Integer> pointCosts, int numStats, int pointTotal) {
		this.pointCosts = new TreeMap<>(pointCosts);
		this.numStats = numStats;
		this.pointTotal = pointTotal;
		
		linkStatList = new LinkedList<>();
		
		if (this.pointCosts != null) {
			listBuilder(new int[numStats], 0, this.pointCosts.lastKey(), 0);
		} else {
			this.pointCosts = new TreeMap<>();
		}
		
		arrStatList = new int[linkStatList.size()][numStats];
		
		int i = 0;
		for (int[] j : linkStatList) {
			arrStatList[i++] = j;
		}
		
		linkStatList = null;
	}
		
	private void listBuilder(int[] arr, int index, int max, int sumSoFar) {
		if (index == numStats) {
			if (sumSoFar == pointTotal) {
				linkStatList.add(Arrays.copyOf(arr, arr.length));
			}
			return;
		}
		
		Integer i = pointCosts.floorKey(Math.min(max, pointTotal - sumSoFar));
		
		while (i != null) {
			arr[index] = i;
			listBuilder(arr, index + 1, i, sumSoFar + i);
			i = pointCosts.lowerKey(i);
		}
	}
	
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (o instanceof StatList) {
			StatList sl = (StatList) o;
			return this.pointCosts.equals(sl.pointCosts) && this.numStats == sl.numStats && this.pointTotal == sl.pointTotal; 
		}
		
		return false;
	}
	
	private void display(int[] arr) {
		for (int i = 0; i < numStats-1; ++i) {
			System.out.print(pointCosts.get(arr[i]) + ", ");
		}
		System.out.println(pointCosts.get(arr[numStats-1]));
	}
	
	public void displayAll() {
		for (int i = 0; i < arrStatList.length; ++i) {
			display(arrStatList[i]);
		}
	}
	
	public int getIndexOfArray(int[] arr) {
		if (arr.length != arrStatList[0].length) {
			return -1;
		}
		int min = 0;
		int max = arrStatList.length;
		int mid = arrStatList.length / 2;
		
		int i, j, k;
		while (min <= max) {
			for (k = 0; k < arr.length; ++k) {
				i = arrStatList[mid][k];
				j = arr[k];
				
				if (i > j) {
					min = mid + 1;
					mid = (min + max) / 2;
					break;
				} else if (i < j) {
					max = mid - 1;
					mid = (min + max) / 2;
					break;
				}
			}
			if (k == arr.length) {
				return mid;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		TreeMap<Integer, Integer> tm = new TreeMap<>();
		tm.put(0,  8);
		tm.put(1,  9);
		tm.put(2, 10);
		tm.put(3, 11);
		tm.put(4, 12);
		tm.put(5, 13);
		tm.put(7, 14);
		tm.put(9, 15);
		int size = 27;
		
		StatList sl = new StatList(tm, 6, size);
		sl.displayAll();
		
		int[] alist = new int[sl.arrStatList.length];
		for (int i = 0; i < alist.length; ++i) {
			alist[i] = 0;
		}
		
		int[] arr;
		int index;
		for (int i = 0; i < 1000000; ++i) {
			do {
				arr = QuickStat.get(tm, 6, size);
			} while (arr == null);
			//QuickStat.display(arr, tm);
			
			index = sl.getIndexOfArray(arr);
			
			if (index < 0) {
				System.out.print("Could not find: ");
				QuickStat.display(arr, tm);
			} else {
				++alist[index];
			}
		}
		
		for (int i = 0; i < alist.length; ++i) {
			System.out.print(alist[i] + " ");
			//if (alist[i] > 0) {
			//	QuickStat.display(sl.arrStatList[i], tm);
			//}
		}
		System.out.println();
		
		Random r = new Random();
		for (int i = 0; i < 4; ++i) {
			QuickStat.display(sl.arrStatList[r.nextInt(sl.arrStatList.length)], tm);
		}
	}

}
