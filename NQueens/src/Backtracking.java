import java.util.Stack;


/**
 * Try to solve the n queens problem by backtracking
 *
 */
public class Backtracking {
	private int nqueens;
	private int positionsSearched;
	// Stores queen locations in row,column format
	private Stack<int[]> queenLocations;
	
	/**
	 * Assume board is nqueens x nqueens
	 * @param nqueens
	 */
	public Backtracking(int nqueens) {
		this.nqueens = nqueens;
		queenLocations = new Stack<int[]>();
		positionsSearched = 0;
	}
	
	/**
	 * Run the backtracking algorithm
	 */
	public void run() {
		long startTime = System.currentTimeMillis();
		// Start on the first column
		boolean solved = fillColumn(0);
		long endTime = System.currentTimeMillis();
		
		if (solved) {
			printBoard();
			System.out.printf("Solved in %f seconds\n", (endTime - startTime) / 1000.0);
			System.out.printf("Searched %d positions\n", positionsSearched);
		}
	}

	/**
	 * Recursive backtracking
	 * @param c
	 * @return true if final column was filled, false otherwise
	 */
	private boolean fillColumn(int c) {
		// End condition, we filled all the columns
		if (c >= nqueens) {
			return true;
		}
		
		positionsSearched++;
		
		// Go through all rows
		for (int i = 0; i < nqueens; i++) {
			int[] location = new int[2];
			location[0] = i;
			location[1] = c;
			
			if (isValid(location)) {
				queenLocations.push(location);
				boolean solvable = fillColumn(c + 1);
				if (solvable) {
					return true;
				} else {
					queenLocations.pop();
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Check if a location is valid with the current locations
	 * @param location
	 * @return
	 */
	private boolean isValid(int[] location) {
		for (int[] l : queenLocations) {
			// Check rows
			if (l[0] == location[0]) {
				return false;
			}
			
			// Check downwards diagonal
			// 0,0 is top right
			// If the x difference is the same as the y difference, same diagonal
			if ((location[0] - l[0]) == (location[1] - l[1])) {
				return false;
			}
			
			// Check upwards diagonal
			// X = -Y
			if ((location[0] - l[0]) == (-1 * (location[1] - l[1]))) {
				return false;
			}
		}
		
		return true;
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
		int queens = 50;
		Backtracking bt = new Backtracking(queens);
		bt.run();
	}
}
