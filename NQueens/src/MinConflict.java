import java.util.ArrayList;


public class MinConflict {

	private int nqueens;
	private int positionsSearched;
	// Stores queen locations in row,column format
	private ArrayList<int[]> queenLocations;
	
	/**
	 * Assume board is nqueens x nqueens
	 * @param nqueens
	 */
	public MinConflict(int nqueens) {
		this.nqueens = nqueens;
		queenLocations = new ArrayList<int[]>();
		positionsSearched = 0;
	}
	
	/**
	 * Run the backtracking algorithm
	 */
	public void run() {
		long startTime = System.currentTimeMillis();
		// Start on the first column
		int maxSteps = 50000;
		for (int i = 0; i < nqueens; i++) {
			int j = (int) (Math.random() * nqueens);
			int[] loc = new int[2];
			loc[0] = i;
			loc[1] = j;
			queenLocations.add(loc);
		}
		boolean solved = minConflict(maxSteps);
		
		long endTime = System.currentTimeMillis();
		
		if (solved) {
			printBoard();
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
			int r;
			do {
				r = (int) (Math.random() * nqueens);
			} while (numConflicts(queenLocations.get(r)) == 0);
			
			// Place it in position of least conflicts
			int leastConflicts = Integer.MAX_VALUE;
			int[] bestLoc = new int[2];
			bestLoc[0] = 0;
			bestLoc[1] = r;
			for (int j = 0; j < nqueens; j++) {
				int[] l = new int[2];
				l[0] = j;
				l[1] = r;
				int conflicts = numConflicts(l);
				if (conflicts < leastConflicts) {
					leastConflicts = conflicts;
					bestLoc[0] = j;
				} else if (conflicts == leastConflicts) {
					if (Math.random() < .5) {
						bestLoc[0] = j;
					}
				}
			}
			
			queenLocations.remove(r);
			queenLocations.add(r, bestLoc);
			
		}
		return false;
	}

	
	private boolean isSolution() {
		for (int[] l : queenLocations) {
			if (numConflicts(l) > 0) {
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
		int numConflicts = 0;
		for (int[] l : queenLocations) {
			// Don't check same position
			if ((l[0] == location[0]) && (l[1] == location[1])) {
				continue;
			}
			// Check rows
			if (l[0] == location[0]) {
				numConflicts++;
			}
			
			// Check columns
			if (l[1] == location[1]) {
				numConflicts++;
			}
			
			// Check downwards diagonal
			// 0,0 is top right
			// If the x difference is the same as the y difference, same diagonal
			if ((location[0] - l[0]) == (location[1] - l[1])) {
				numConflicts++;
			}
			
			// Check upwards diagonal
			// X = -Y
			if ((location[0] - l[0]) == (-1 * (location[1] - l[1]))) {
				numConflicts++;
			}
		}
		
		return numConflicts;
	}
	
	/**
	 * Print the board
	 */
	private void printBoard() {
		for (int i = 0; i < nqueens; i++) {
			for (int j = 0; j < nqueens; j++) {
				int[] loc = new int[2];
				loc[0] = i;
				loc[1] = j;
				boolean contains = false;
				for (int[] l : queenLocations) {
					if (l[0] == loc[0] && l[1] == loc[1]) {
						contains = true;
					}
				}
				if (contains) {
					System.out.print("Q ");
				} else {
					System.out.print("_ ");
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int queens = 100;
		MinConflict mc = new MinConflict(queens);
		mc.run();
	}
	
}
