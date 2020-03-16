import java.util.Arrays;
import java.util.Random;

/**
 * <p>This {@code MinesweeperBoard} class is the Minesweeper Board.</p>
 * 
 * @version 31 March 2020
 * @author MrPineapple070
 */
public class MinesweeperBoard {
	/**
	 * {@link Random}
	 */
	public static final Random rand = new Random();
	
	/**
	 * Maximum and minimum number of rows and columns.
	 * 
	 * @return themselves
	 */
	private final int rowMax, rowMin = 0, colMax, colMin = 0;
	
	/**
	 * The number of bombs on board.
	 */
	private final int numBombs;
	
	/**
	 * The chance of <code>this</code> being a bomb
	 */
	private final double chance;
	
	/**
	 * The {@link MinesweeperPanel} holding this.
	 */
	private final MinesweeperPanel panel;
	
	/**
	 * The number of {@link Tile} that can be revealed
	 */
	private final int revealableTile;
	
	/**
	 * Determine if the game is over.
	 */
	private boolean isGameOver = false;
	
	/**
	 * The number of {@link Tile} that are revealed
	 */
	private int numReveal = 0;
	
	/**
	 * The number of bombs left to flag.
	 */
	private int numFlag = 0;
	
	/**
	 * An 2D {@link Arrays} of {@link Tile}
	 */
	Tile[][] board;
	
	/**
	 * Creates a {@code MinesweeperBoard} initialising all atributes.
	 * 
	 * @param rowMax	is the maximum number of rows.
	 * @param colMax	is the maximum number of columns.
	 * @param numBombs	is the number of bombs on the board.
	 * @param panel		is the {@link MinesweeperPanel} holding this.
	 * @throws IllegalArgumentException		if {@code panel} is {@code null} or {@code numBombs} is greater than the area of the board - 1.
	 * @throws IndexOutOfBoundsException	if {@code rowMax} is less than 1 or {@code colMax} is less than 3.
	 */
	public MinesweeperBoard(int rowMax, int colMax, int numBombs, MinesweeperPanel panel) throws IllegalArgumentException, IndexOutOfBoundsException {
		if (rowMax < 1) {
			throw new IndexOutOfBoundsException("Illegal maximum number of rows " + rowMax);
		}
		
		else {
			this.rowMax = rowMax;
		}
		
		if (colMax < 3) {
			throw new IndexOutOfBoundsException("Illegal maximun number of columns " + colMax);
		}
		
		else {
			this.colMax = rowMax;
		}
		
		if (numBombs > this.rowMax * this.colMax - 1) {
			throw new IllegalArgumentException("Illegal number of bombs " + numBombs);
		}
		
		else {
			this.numBombs = numBombs;
		}
		
		this.revealableTile = this.rowMax * this.colMax - this.numBombs;
		
		if (panel == null) {
			throw new IllegalArgumentException("MinesweeperBoard must be on a MinesweeperPanel");
		}
		
		else {
			this.panel = panel;
		}
		
		this.chance = this.numBombs / (this.rowMax * this.colMax * 1.0);
		
		
		this.board = new Tile[this.rowMax][this.colMax];
		this.createBoard(); this.newGame();
	}
	
	/**
	 * Determine if {@code row} is a valid row;
	 * 
	 * @param row is the row to validate
	 * @return	<tt>true</tt> if the row is valid.</br>
	 * 			<tt>false</tt> if the row is not valid.
	 */
	public boolean validateRow(int row) {
		return this.rowMin <= row && row <= this.colMax;
	}
	
	/**
	 * Determine if {@code col} is a valid column.
	 * 
	 * @param col is the column to validate.
	 * @return	<tt>true</tt> if the column is valid.</br>
	 * 			<tt>false</tt> if the column is not valid.
	 */
	public boolean validateCol(int col) {
		return this.colMin <= col && col <= this.colMax;
	}
	
	/**
	 * @return {@link board}.
	 */
	public Tile[][] getBoard() {
		return this.board;
	}
	
	/**
	 * @param row is the row of {@code Tile}
	 * @param col is the column of {@code Tile}
	 * @return {@link Tile} at position ({@code row}, {@code col}).
	 */
	public Tile getTile(int row, int col) {
		return this.board[row][col];
	}
	
	/**
	 * @return {@link #rowMax}
	 */
	public int getRowMax() {
		return this.rowMax;
	}
	
	/**
	 * @return {@link #colMax}
	 */
	public int getColMax() {
		return this.colMax;
	}
	
	/**
	 * @return {@link #revealableTile}
	 */
	public int getRevealableTile() {
		return this.revealableTile;
	}
	
	/**
	 * @return {@link #numReveal}
	 */
	public int getNumReveal() {
		return this.numReveal;
	}
	
	/**
	 * @return {@link #numFlag}
	 */
	public int getFlags() {
		return this.numFlag;
	}
	
	/**
	 * @return {@link #isGameOver}
	 */
	public boolean getGameOver() {
		return this.isGameOver;
	}
	
	/**
	 * Set {@link #isGameOver} to {@code isGameOver}.
	 * 
	 * @param isGameOver is the new value.
	 */
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	/**
	 * Increment the number of {@link Tile} that have been <i>revealed</i>.
	 */
	public void incTileReveal() {
		this.numReveal++;
	}
	
	/**
	 * Decrement the number of flags.
	 */
	public void decFlagCount() {
		this.numFlag--;
	}
	
	/**
	 * Creates a new game.
	 */
	public void newGame() {
		this.numFlag = this.numBombs;
		int numBombs = this.numBombs;
		while (numBombs > 0) {
			for (Tile[] row : this.board) {
				for (Tile tile : row) {
					tile.reset();
					if (! tile.isBomb()){
						if (rand.nextDouble() < this.chance) {
							tile.setBomb(true);
							numBombs--;
						}
					}
					
					if (numBombs <= 0) {
						break;
					}
				}
			}
		}
		
		for (Tile[] row : this.board) {
			for (Tile tile : row) {
				tile.setCount();
			}
		}
	}
	
	/**
	 * Place all {@link Tile} into {@link #board}
	 */
	private void createBoard() {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				this.board[i][j] = new Tile(i, j, this, this.panel);
			}
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
		long temp;
		temp = Double.doubleToLongBits(chance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + colMax;
		result = prime * result + colMin;
		result = prime * result + (isGameOver ? 1231 : 1237);
		result = prime * result + numBombs;
		result = prime * result + numReveal;
		result = prime * result + ((panel == null) ? 0 : panel.hashCode());
		result = prime * result + revealableTile;
		result = prime * result + rowMax;
		result = prime * result + rowMin;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MinesweeperBoard other = (MinesweeperBoard) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (Double.doubleToLongBits(chance) != Double.doubleToLongBits(other.chance))
			return false;
		if (colMax != other.colMax)
			return false;
		if (colMin != other.colMin)
			return false;
		if (isGameOver != other.isGameOver)
			return false;
		if (numBombs != other.numBombs)
			return false;
		if (numReveal != other.numReveal)
			return false;
		if (panel == null) {
			if (other.panel != null)
				return false;
		} else if (!panel.equals(other.panel))
			return false;
		if (revealableTile != other.revealableTile)
			return false;
		if (rowMax != other.rowMax)
			return false;
		if (rowMin != other.rowMin)
			return false;
		return true;
	}

	@Override
	public String toString() {
		String str = "";
		
		for (Tile[] row : this.board) {
			for (Tile tile : row) {
				if (tile.isBomb()) {
					str += "bomb\t";
				}
				
				else {
					str += tile.toString() + "\t";
				}
			}
			str += "\n";
		}
		
		return str;
	}
}
