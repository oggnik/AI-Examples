import java.util.ArrayList;


public class MinConflictFast {

	private int nqueens;
	private int positionsSearched;
	// Stores queen locations in row,column format
	private ArrayList<int[]> queenLocations;
	
	// position in array is: row
	private int[] rowConflicts;
	// position in array is: column
	private int[] colConflicts;
	// position in array is: n - row + column - 1
	private int[] diagRightConflicts;
	// position in array is: column + row
	private int[] diagLeftConflicts;
	
	/**
	 * Assume board is nqueens x nqueens
	 * @param nqueens
	 */
	public MinConflictFast(int nqueens) {
		this.nqueens = nqueens;
		queenLocations = new ArrayList<int[]>();
		positionsSearched = 0;
		
		rowConflicts = new int[nqueens];
		colConflicts = new int[nqueens];
		diagRightConflicts = new int[nqueens * 2 - 1];
		diagLeftConflicts = new int[nqueens * 2 - 1];
	}
	
	/**
	 * Run the minconflict algorithm
	 */
	public void run() {
		long startTime = System.currentTimeMillis();
		// Start on the first column
		int maxSteps = 50000;
		for (int col = 0; col < nqueens; col++) {
			int row = (int) (Math.random() * nqueens);
			int[] loc = new int[2];
			loc[0] = row;
			loc[1] = col;
			rowConflicts[row]++;
			colConflicts[col]++;
			diagRightConflicts[nqueens - row + col - 1]++;
			diagLeftConflicts[row + col]++;
			queenLocations.add(loc);
		}
		//printBoard();
//		int[] location = {0,4};
//		System.out.println(numConflicts(location));
//		System.exit(1);
		boolean solved = minConflict(maxSteps);
		
		long endTime = System.currentTimeMillis();
		
		if (solved) {
			if (nqueens <= 100) {
				printBoard();
			}
			System.out.printf("Solved in %f seconds\n", (endTime - startTime) / 1000.0);
			System.out.printf("Searched %d positions\n", positionsSearched);
		} else {
			System.out.println("Could not solve");
		}
	}

	private boolean minConflict(int maxSteps) {
		for (int i = 0; i < maxSteps; i++) {
			positionsSearched++;
			if (isSolution()) {
				return true;
			}
			
			// Get a random queen in conflict
			int col;
			do {
//				System.out.println("in conflict");
				col = (int) (Math.random() * nqueens);
			// 4 because queens in that location are counted 4 times
			} while (numConflicts(queenLocations.get(col)) <= 4);
			int row = queenLocations.get(col)[0];
			
			// Place it in position of least conflicts
			int leastConflicts = Integer.MAX_VALUE;
			int[] bestLoc = new int[2];
			bestLoc[0] = 0;
			bestLoc[1] = col;
			for (int irow = 0; irow < nqueens; irow++) {
				int[] l = new int[2];
				l[0] = irow;
				l[1] = col;
				int conflicts = numConflicts(l);
				if (conflicts < leastConflicts) {
					leastConflicts = conflicts;
					bestLoc[0] = irow;
				} else if (conflicts == leastConflicts) {
					if (Math.random() < .5) {
						bestLoc[0] = irow;
					}
				}
			}
			
			int bestRow = bestLoc[0];
			int bestCol = bestLoc[1];
			
			rowConflicts[row]--;
			rowConflicts[bestRow]++;
			
			colConflicts[col]--;
			colConflicts[bestLoc[1]]++;
			
			diagRightConflicts[nqueens - row + col - 1]--;
			diagRightConflicts[nqueens - bestRow + bestCol - 1]++;
			
			diagLeftConflicts[col + row]--;
			diagLeftConflicts[bestCol + bestRow]++;
			
			queenLocations.remove(col);
			queenLocations.add(col, bestLoc);
			
		}
		return false;
	}

	
	private boolean isSolution() {
		for (int[] l : queenLocations) {
			// 4 because queens in that location are counted 4 times
			if (numConflicts(l) > 4) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if a location is valid with the current locations
	 * @param location
	 * @return
	 */
	private int numConflicts(int[] location) {
		int row = location[0];
		int col = location[1];
		
		int numConflicts = 0;
		
		numConflicts += rowConflicts[row];
		numConflicts += colConflicts[col];
		numConflicts += diagRightConflicts[nqueens - row + col - 1];
		numConflicts += diagLeftConflicts[col + row];
		
		
//		printBoard();
//		System.out.println(numConflicts);
//		System.out.printf("%d, %d\n", row, col);
//		System.out.println("Row: " + rowConflicts[row]);
//		System.out.println("Col: " + colConflicts[col]);
//		System.out.println("DownRight: " + diagRightConflicts[nqueens - row + col - 1]);
//		System.out.println("DownLeft: " + diagLeftConflicts[col + row]);
//		System.out.println();
		
		return numConflicts;
	}
	
	/**
	 * Print the board
	 */
	private void printBoard() {
		for (int row = 0; row < nqueens; row++) {
			for (int col = 0; col < nqueens; col++) {
				int[] loc = new int[2];
				loc[0] = row;
				loc[1] = col;
				boolean contains = false;
				for (int[] l : queenLocations) {
					if (l[0] == loc[0] && l[1] == loc[1]) {
						contains = true;
					}
				}
				if (contains) {
					System.out.print("Q ");
				} /*else if (row == 0 && col == 4) {
					System.out.print(". ");
				}*/ else {
					System.out.print("_ ");
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int queens = 100;
		MinConflictFast mc = new MinConflictFast(queens);
		mc.run();
	}
	
}
