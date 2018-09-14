package firsted;

import java.util.Arrays;
import java.util.Random;

public class Dice {
	
	Random r = new Random();
	int[][] results;
	int[] rolls;
	int rMax, rAdd, attempt, sum;

	// 6x4d6r1k3, 12c6x3d6, 1c6x6t3d6
	// 4c6x6t4d6r2k3
	public int[][] roll (int numChars, int numStats, int numTryPerStat, int numDice, int dieSize, int rerollThisOrLess, int numKeep) {
		results = new int[numChars][numStats];
		
		if (numKeep == 0) { numKeep = numDice; }
		
		rolls = new int[numDice];
		rMax = dieSize - rerollThisOrLess;
		rAdd = 1 + dieSize - rMax;
		
		for (int charIter = 0; charIter < numChars; ++charIter) {
			for (int statIter = 0; statIter < numStats; ++statIter) {
				attempt = 0;
				for (int tryIter = 0; tryIter < numTryPerStat; ++tryIter) {

					for (int diceIter = 0; diceIter < numDice; ++diceIter) {
						rolls[diceIter] = rAdd + (int)Math.floor(rMax * r.nextDouble());
					}
					
					Arrays.sort(rolls);
/**/System.out.print(Arrays.toString(rolls));

					sum = 0;
					for (int sumIter = 1; sumIter <= numKeep; ++sumIter) {
						sum += rolls[numDice - sumIter];
					}
					
/**/System.out.println(sum);					
					if (sum > attempt) { attempt = sum; }
				}
				
				results[charIter][statIter] = attempt;
			}
		}
/**/for (int i = 0; i < numChars; ++i) {
/**/System.out.println(Arrays.toString(results[i]));
/**/}
		return results;
	}
	
	public int[][] quickroll(int numDice, int dieSize, int numKeep) {
		return roll(1,6,1,numDice,dieSize,0,numKeep);
	}
}
