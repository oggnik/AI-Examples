public class Main {
	static int[][] puzzle;
	static boolean changed;
	public static final int BOXSIZE = 3;

	public static int[][] getPuzzle() {
		int p[][] = new int[9][9];

		p = new int[][] { { 0, 0, 3, 0, 4, 2, 0, 9, 0 },
				{ 0, 9, 0, 0, 6, 0, 5, 0, 0 }, { 5, 0, 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 1, 7, 0, 0, 2, 8, 5 }, { 0, 0, 8, 0, 0, 0, 1, 0, 0 },
				{ 3, 2, 9, 0, 0, 8, 7, 0, 0 }, { 0, 3, 0, 0, 0, 0, 0, 0, 1 },
				{ 0, 0, 5, 0, 9, 0, 0, 2, 0 }, { 0, 8, 0, 2, 1, 0, 6, 0, 0 } };

		return p;
	}

	public static void printPuzzle() {
		for (int i = 0; i < 9; i++) {
			if ((i % 3) == 0) {
				System.out.println("- - - - - - - - - - - - - - - - - - -");
			}
			for (int j = 0; j < 9; j++) {
				if ((j % 3) == 0) {
					System.out.print("| " + puzzle[i][j] + " ");
				} else {
					System.out.print("  " + puzzle[i][j] + " ");
				}
			}
			System.out.println("|");
		}
		System.out.println("- - - - - - - - - - - - - - - - - - -");
	}

	public static boolean checkSolved() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (puzzle[i][j] <= 0) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean boxContainsValue(int x, int y, int val) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (puzzle[(x * BOXSIZE) + i][(y * BOXSIZE) + j] == val) {
					return true;
				}
			}
		}
		return false;
	}

	public static int numBoxContainsValue(int x, int y, int val) {
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (puzzle[(x * BOXSIZE) + i][(y * BOXSIZE) + j] == val) {
					count++;
				}
			}
		}
		return count;
	}

	public static boolean rowContainsValue(int row, int val) {
		for (int i = 0; i < 9; i++) {
			if (puzzle[row][i] == val) {
				return true;
			}
		}
		return false;
	}

	public static int numRowContainsValue(int row, int val) {
		int count = 0;
		for (int i = 0; i < 9; i++) {
			if (puzzle[row][i] == val) {
				count++;
			}
		}
		return count;
	}

	public static boolean colContainsValue(int col, int val) {
		for (int i = 0; i < 9; i++) {
			if (puzzle[i][col] == val) {
				return true;
			}
		}
		return false;
	}

	public static int numColContainsValue(int col, int val) {
		int count = 0;
		for (int i = 0; i < 9; i++) {
			if (puzzle[i][col] == val) {
				count++;
			}
		}
		return count;
	}

	public static void solveBoxes() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				for (int val = 1; val < 10; val++) {
					// Already in the box
					if (boxContainsValue(x, y, val)) {
						continue;
					}

					// Try to find a possible value
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							if (puzzle[(x * BOXSIZE) + i][(y * BOXSIZE) + j] == 0) {
								if (rowContainsValue((x * BOXSIZE) + i, val)
										|| colContainsValue((y * BOXSIZE) + j,
												val)) {
									puzzle[(x * BOXSIZE) + i][(y * BOXSIZE) + j] = -1;
								}
							}
						}
					}

					// If there is only one 0 leftover
					if (numBoxContainsValue(x, y, 0) == 1) {
						for (int i = 0; i < 3; i++) {
							for (int j = 0; j < 3; j++) {
								if (puzzle[(x * BOXSIZE) + i][(y * BOXSIZE) + j] == 0) {
									puzzle[(x * BOXSIZE) + i][(y * BOXSIZE) + j] = val;
									changed = true;
								}
							}
						}
					}

					// Reset negative values to 0 again
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							if (puzzle[(x * BOXSIZE) + i][(y * BOXSIZE) + j] < 0) {
								puzzle[(x * BOXSIZE) + i][(y * BOXSIZE) + j] = 0;
							}
						}
					}
				}
			}
		}
	}

	public static void solveRows() {
		for (int x = 0; x < 9; x++) {
			for (int val = 1; val < 10; val++) {
				// Already in the row
				if (rowContainsValue(x, val)) {
					continue;
				}

				// Try to find a possible value
				for (int y = 0; y < 9; y++) {
					if (puzzle[x][y] == 0) {
						if (boxContainsValue(x / BOXSIZE, y / BOXSIZE, val)
								|| colContainsValue(y, val)) {
							puzzle[x][y] = -1;
						}
					}
				}

				// If there is only one 0 leftover
				if (numRowContainsValue(x, 0) == 1) {
					for (int y = 0; y < 9; y++) {
						if (puzzle[x][y] == 0) {
							puzzle[x][y] = val;
							changed = true;
						}
					}
				}

				// Reset negative values to 0 again
				for (int y = 0; y < 9; y++) {
					if (puzzle[x][y] < 0) {
						puzzle[x][y] = 0;
					}
				}
			}
		}
	}

	public static void solveCols() {
		for (int y = 0; y < 9; y++) {
			for (int val = 1; val < 10; val++) {
				// Already in the col
				if (colContainsValue(y, val)) {
					continue;
				}

				// Try to find a possible value
				for (int x = 0; x < 9; x++) {
					if (puzzle[x][y] == 0) {
						if (boxContainsValue(x / BOXSIZE, y / BOXSIZE, val)
								|| rowContainsValue(x, val)) {
							puzzle[x][y] = -1;
						}
					}
				}

				// If there is only one 0 leftover
				if (numColContainsValue(y, 0) == 1) {
					for (int x = 0; x < 9; x++) {
						if (puzzle[x][y] == 0) {
							puzzle[x][y] = val;
							changed = true;
						}
					}
				}

				// Reset negative values to 0 again
				for (int x = 0; x < 9; x++) {
					if (puzzle[x][y] < 0) {
						puzzle[x][y] = 0;
					}
				}
			}
		}
	}

	public static void solveSingleCandidate() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if(puzzle[x][y] > 0){
					continue;
				}
				int res = -1;
				for (int val = 1; val < 10; val++) {
					if (boxContainsValue(x / BOXSIZE, y / BOXSIZE, val)
							|| colContainsValue(y, val)
							|| rowContainsValue(x, val)) {
						continue;
					}else{
						if(res > 0){
							res = -1;
							break;
						}else{
							res = val;
						}
					}
				}
				if(res > 0){
					puzzle[x][y] = res;
				}
			}
		}
	}

	public static void solvePuzzle() {
		while (true) {
			changed = false;
			solveBoxes();
			solveRows();
			solveCols();
			solveSingleCandidate();
			if (!changed) {
				break;
			}
		}
	}

	public static void main(String[] args) {
		puzzle = getPuzzle();
		// This is the starting puzzle
		printPuzzle();

		// This solves the puzzle
		solvePuzzle();

		// This is the final solution
		printPuzzle();

	}

}
