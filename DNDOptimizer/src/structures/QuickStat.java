package structures;

import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;

public class QuickStat {

	public static void display(int[] arr, TreeMap<Integer, Integer> pointCosts) {
		
		for (int i = 0; i < arr.length-1; ++i) {
			System.out.print(pointCosts.get(arr[i]) + ", ");
		}
		System.out.println(pointCosts.get(arr[arr.length-1]));
	}
	
	
	public static int[] get(TreeMap<Integer, Integer> pointCosts, int numStats, int pointTotal) {
		int[] stats = new int[numStats];
		
		Integer[] keys = pointCosts.keySet().toArray(new Integer[pointCosts.size()]);
		
		Random r = new Random();
		int sum = 0;
		int count = 0;
		int attempts = 100;
		int index;
		Integer tempKey;
		
		for (int i = 0; i < numStats; ++i) {
			stats[i] = keys[r.nextInt(keys.length)];
			sum += stats[i];
		}
//System.out.println("New get");
//QuickStat.display(stats, pointCosts);
		while (sum != pointTotal && count < attempts) {
			++count;
			
			index = r.nextInt(numStats);
			
			if (sum > pointTotal) {
				tempKey = pointCosts.lowerKey(stats[index]);
				if (tempKey != null) {
					sum -= stats[index];
					stats[index] = tempKey;
					sum += stats[index];
				
				}
			} else {
				tempKey = pointCosts.higherKey(stats[index]);
				if (tempKey != null) {
					sum -= stats[index];
					stats[index] = tempKey;
					sum += stats[index];
				
				}
			}			
		}
//System.out.println("Attempts: " + count);
//QuickStat.display(stats, pointCosts);		
		if (sum != pointTotal) {
			return null;
		}
		
		int[] stats2 = new int[numStats];
		Arrays.sort(stats);
//QuickStat.display(stats, pointCosts);		
		for (int i = 0; i < numStats; ++i) {
			stats2[i] = stats[numStats - 1 - i];
		}
//QuickStat.display(stats2, pointCosts);
		return stats2;
	}
}
