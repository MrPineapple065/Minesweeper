import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * This {@code Tile} class represents a Tile on {@link MinesweeperBoard}.
 * 
 * @version 31 March 2020
 * @author MrPineapple070
 */
public class Tile extends JButton implements KeyListener, MouseListener {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 0x448000763278F6FAL;
	
	/**
	 * A {@link Arrays} of {@link Color} holding all the colors that this will be.
	 */
	private static final Color[] numColor = new Color[] {null, new Color(0x0000FF), new Color(0x008000), new Color(0xFF0000), new Color(0x000082), new Color(0x820000), new Color(0x008080), Color.BLACK, new Color(0x808080)};
	
	/**
	 * The {@link MinesweeperBoard} holding this.
	 */
	private final MinesweeperBoard board;
	
	/**
	 * The {@link Color} of this.
	 */
	private final Color tileColor = new Color(0xBDBDBD);
	
	/**
	 * The {@link MinesweeperPanel} holding this.
	 */
	private final MinesweeperPanel panel;
	
	/**
	 * The row that this is in.
	 */
	private final int row;
	
	/**
	 * The column that this is in.
	 */
	private final int col;
	
	/**
	 * The number of bombs surrounding this.
	 */
	private int count;
	
	/**
	 * A {@code boolean} determining if this has been flagged.
	 */
	private boolean isFlagged;
	
	/**
	 * A {@code boolean} determining if this is a bomb.
	 */
	private boolean isBomb;
	
	/**
	 * A {@code boolean} determininf if this is revealed
	 */
	private boolean isRevealed;
	
	/**
	 * Creates a {@code Tile} with row, col, board, and panel defined.
	 * 
	 * @param row	is the {@link Tile#row}
	 * @param col	is the {@link Tile#col}
	 * @param board is the {@link Tile#board}
	 * @param panel is the {@link Tile#panel}
	 * 
	 * @throws IndexOutOfBoundsException if {@code row} and {@code col} are outside the board.
	 * @throws IllegalArgumentException if {@code board} and {@code panel} are {@code null}.
	 */
	public Tile(int row, int col, MinesweeperBoard board, MinesweeperPanel panel) throws IndexOutOfBoundsException, IllegalArgumentException {
		super(null, null);
		
		if (board  == null) {
			throw new IllegalArgumentException("Tile must be created on a MinesweeperBoard.");
		}
		
		else {
			this.board = board;
		}
		
		if (panel == null) {
			throw new IllegalArgumentException("Tile must be created on a MinesweeperPanel.");
		}
		
		else {
			this.panel = panel;
		}
		
		if (! this.board.validateRow(row)) {
			throw new IndexOutOfBoundsException(String.format("Illegal row: %s", row));
		}
		
		else {
			this.row = row;
		}
		
		if (! this.board.validateCol(col)) {
			throw new IndexOutOfBoundsException(String.format("Illegal column: %s.", col));
		}
		
		else {
			this.col = col;
		}
		
		//Set Default GUI Elements
		this.setFont(new Font("", Font.PLAIN, 30));		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setBackground(this.tileColor);				this.setForeground(null);
		this.setHorizontalAlignment(JButton.CENTER);	this.setVerticalAlignment(JButton.CENTER);
		this.setFocusPainted(false);
		
		UIManager.put("TextArea.font", new Font("Arial", Font.PLAIN, 30));
		
		//Add Interactivity
		this.addKeyListener(this);	this.addMouseListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		this.isFlagged = false; this.isBomb = false; this.isRevealed = false;
	}
	
	/**
	 * Determine if this is flagged.
	 * 
	 * @return {@link Tile#isFlagged}
	 */
	public boolean isFlagged() {
		return this.isFlagged;
	}
	
	/**
	 * Determine if this is a bomb.
	 * 
	 * @return {@link Tile#isBomb}
	 */
	public boolean isBomb() {
		return isBomb;
	}
	
	/**
	 * Determined if this is revealed
	 * 
	 * @return {@link Tile#isRevealed}
	 */
	public boolean isRevealed() {
		return this.isRevealed;
	}
	
	/**
	 * Determine the {@link Color} of this.
	 * 
	 * @return {@link Tile#tileColor}
	 */
	public Color getTileColor() {
		return this.tileColor;
	}
	
	/**
	 * Determine the row that this is in.
	 * 
	 * @return {@link Tile#row}
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * Determine the column that this is in.
	 * 
	 * @return {@link col}
	 */
	public int getCol() {
		return this.col;
	}
	
	/**
	 * Set {@link #isFlagged} to {@code isFlagged}
	 * 
	 * @param isFlagged	new {@link #isFlagged} value.
	 */
	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}
	
	/**
	 * Toggle {@link #isFlagged}
	 */
	public void toggleFlagged() {
		this.isFlagged ^= true;
	}
	
	/**
	 * Set {@link #isBomb} to {@code isBomb}
	 * 
	 * @param isBomb	new {@link #isBomb} value.
	 */
	public void setBomb(boolean isBomb) {
		this.isBomb = isBomb;
	}
	
	/**
	 * Set {@link #isRevealed} to {@code isRevealed}
	 * 
	 * @param isRevealed	new {@link #isRevealed} value.
	 */
	public void setRevealed(boolean isRevealed) {
		this.isRevealed = isRevealed;
	}
	
	public void setCount() {
		int count = 0;
		for (int i = this.row - 1; i < this.row + 2; i++) {
			for (int j = this.col - 1; j < this.col + 2; j++) {
				try {
					count += this.board.getTile(i, j).isBomb() ? 1 : 0;
				}
				
				catch (ArrayIndexOutOfBoundsException aiooe) {
					continue;
				}
			}
		}
		
		this.count = count;
	}
	
	/**
	 * Reveal {@code this}. </br>
	 * Revealing a {@link Tile} either results in gameover or a</br>
	 * number indicating the number of bombs directly surrounding {@code this}.
	 */
	public void reveal() {
		if (this.isFlagged) {
			return;
		}
		
		if (this.isRevealed) {
			return;
		}
		
		if (this.board.getGameOver()) {
			return;
		}
		
		this.setRevealed(true);
		this.board.incTileReveal();
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		
		if (this.isBomb()) {
			JOptionPane.showMessageDialog(null, "Game Over", "Game Over!", JOptionPane.PLAIN_MESSAGE, null);
			this.revealBomb();
			return;
		}
		
		if (this.count != 0) {
			this.setText(String.valueOf(this.count));
			this.setForeground(Tile.numColor[this.count]);
		}
		
		else {
			this.specialReveal();
		}
		
		this.checkGameOver();
	}
	
	/**
	 * Reveal all {@link Tile} directly surrounding <code>this</code>.
	 */
	public void specialReveal() {
		for (int i = this.row - 1; i < this.row + 2; i++) {
			for (int j = this.col - 1; j < this.col + 2; j++) {
				try {
					if (! (this.row == i && this.col == j)) {
						this.board.getBoard()[i][j].reveal();
					}
				}
				
				catch (ArrayIndexOutOfBoundsException aiooe) {
					continue;
				}
			}
		}
	}
	
	/**
	 * Reveals the locations of all bombs on {@link MinesweeperBoard#getBoard()}
	 */
	public void revealBomb() {
		for (Tile[] row : this.board.getBoard()) {
			for (Tile tile : row) {
				tile.removeMouseListener(tile);
				if (tile.isBomb()) {
					tile.setBackground(Color.RED);
				}
				
				else {
					continue;
				}
			}
		}
		return;
	}
	
	/**
	 * Reset Tile
	 */
	public void reset() {
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setFlagged(false); this.setRevealed(false); this.setBomb(false);
		this.setText(null); this.setForeground(null); this.setBackground(this.tileColor);
		this.addMouseListener(this); this.addKeyListener(this);
	}
	
	/**
	 * Checks if the game is over.
	 */
	private void checkGameOver() {
		if (! this.board.getGameOver()) {
			if (this.board.getRevealableTile() == this.board.getNumReveal()) {
				JTextArea jta = new JTextArea("You Win!\n" + this.panel.getTimeLabel().getText());
				JOptionPane.showMessageDialog(null, jta, "", JOptionPane.PLAIN_MESSAGE, null);
				this.board.setGameOver(true);
			}
		}
	}
	
	@Override
	public String toString() {
		return String.format("(%s, %s)", String.valueOf(this.row), String.valueOf(this.col));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + col;
		result = prime * result + (isBomb ? 1231 : 1237);
		result = prime * result + (isFlagged ? 1231 : 1237);
		result = prime * result + (isRevealed ? 1231 : 1237);
		result = prime * result + ((panel == null) ? 0 : panel.hashCode());
		result = prime * result + row;
		result = prime * result + ((tileColor == null) ? 0 : tileColor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		
		Tile other = (Tile) obj;
		
		if (this.board == null) {
			if (other.board != null)
				return false;
		} else if (!this.board.equals(other.board))
			return false;
		if (col != other.col)
			return false;
		if (isBomb != other.isBomb)
			return false;
		if (isFlagged != other.isFlagged)
			return false;
		if (isRevealed != other.isRevealed)
			return false;
		if (panel == null) {
			if (other.panel != null)
				return false;
		} else if (!panel.equals(other.panel))
			return false;
		if (row != other.row)
			return false;
		if (tileColor == null) {
			if (other.tileColor != null)
				return false;
		} else if (!tileColor.equals(other.tileColor))
			return false;
		return true;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (e.getButton()) {
		
		/**Left Click*/
		case MouseEvent.BUTTON1:
			this.reveal();
			break;
			
		/**Middle Clicl*/
		case MouseEvent.BUTTON2:
			String tileText = this.getText();
			
			if (("".equals(tileText)) || ! ((this.toString()).equals(tileText))) {
				this.setForeground(new Color(0x333333));
				this.setText(this.toString());
			}
			
			else {
				this.setForeground(Tile.numColor[this.count]);
				this.setText(String.valueOf(this.count));
			}
			break;
			
			
		/**Right Click*/
		case MouseEvent.BUTTON3:
			if (this.isRevealed) {
				break;
			}
			
			if (this.isFlagged) {
				this.setText("");
				this.setForeground(null);
				this.toggleFlagged();
			}
			
			else {
				this.setText("\u2691");
				this.setForeground(Color.RED);
				this.toggleFlagged();
			}
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.getModel().setPressed(true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.getModel().setPressed(false);
	}

	@Override
	public void mouseEntered(MouseEvent e) {this.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));}

	@Override
	public void mouseExited(MouseEvent e) {
		if (this.isRevealed)
			this.setBorder(BorderFactory.createLoweredBevelBorder());
		
		else
			this.setBorder(BorderFactory.createRaisedBevelBorder());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
		case KeyEvent.VK_ESCAPE:
			this.panel.actionPerformed(null);
			break;
			
		case 'r':
			switch (JOptionPane.showConfirmDialog(null, "Are you sure you want to reset?", "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null)) {
			
			case JOptionPane.YES_OPTION:
				this.board.newGame();
				break;
				
			default:
				break;
			}
			break;
			
		case 'q':
			switch (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null)) {
			
			case JOptionPane.YES_OPTION:
				System.exit(0);
				break;
				
			default:
				break;
			}
			break;
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {return;}

	@Override
	public void keyReleased(KeyEvent e) {return;}
}
