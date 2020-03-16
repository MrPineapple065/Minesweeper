import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * This {@code MinesweeperPanel} class holds all interactable and GUI elements of {@code Minesweeper}.
 * 
 * @version 16 March 2020
 * @author MrPineapple065
 */
public class MinesweeperPanel extends JPanel implements ActionListener {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 0x80704238D46657C5L;
	
	/**
	 * The standard {@link Font}.
	 */
	private static final Font standard = new Font("Arial", Font.PLAIN, 30);
	
	/**
	 * The actual {@link MinesweeperBoard}
	 */
	private MinesweeperBoard board;
	
	/**
	 * GUI {@link JLabel}.
	 */
	private final JLabel bombLabel = new JLabel("Bomb"), timeLabel = new JLabel("Time: ");
	
	/**
	 * Create a {@link MinesweeperPanel} with <code>row</code> number of rows, </br>
	 * <code>col</code> number of columns, </br>
	 * and <code>numBombs</code> bombs.
	 * 
	 * @param row		is the number of rows.
	 * @param col		is the number of columns.
	 * @param numBombs	is the number of bombs.
	 * 
	 * @throws	IllegalArgumentException if <code>row</code> or <code>col</code> less than or equal to 3, </br>
	 * 			if <code>numBombs</code> is less than 0 or is greater or equal to <code>row</code> * <code>col</code>.
	 */
	public MinesweeperPanel(int row, int col, int numBombs) throws IllegalArgumentException {
		/**Set default GUI Elements*/
		super();
		
		if (row < 3) {
			throw new IllegalArgumentException("Illegal number of rows " + row);
		}
		
		if (col < 3) {
			throw new IllegalArgumentException("Illegal number of columns " + col);
		}
		
		if (numBombs <= 0 || numBombs >= row * col) {
			throw new IllegalArgumentException("Illegal number of bombs " + numBombs);
		}
		
		this.board = new MinesweeperBoard(row, col, numBombs, this);
		
		setLayout(new GridLayout(row + 1, col));
		
		UIManager.put("OptionPane.messageFont", standard);
		UIManager.put("OptionPane.buttonFont",	standard);
		UIManager.put("Button.font",			new Font("Arial", Font.PLAIN, 15));
		UIManager.put("Label.font",				standard);
		
		this.createLabels();
		this.createTiles();
	}
	
	/**
	 * Add {@link JLabels} to the Panel
	 */
	private void createLabels() {
		bombLabel.setText(String.valueOf(this.board.getFlags()));
		JButton menuButton = new JButton("Menu");
		menuButton.setFocusPainted(false);
		menuButton.addActionListener(this);
		
		for (int i = 0; i < this.board.getColMax(); i++) {
			if (i == this.board.getColMax() / 4) {
				this.add(this.bombLabel);
			}
			
			else if (i == this.board.getColMax() / 2) {
				this.add(menuButton);
			}
			
			else if (i == 3 * this.board.getColMax() / 4) {
				this.add(this.timeLabel);
			}
			
			else {
				this.add(new JLabel(""));
			}
		}
		
	}
	
	/**
	 * Inisialize all {@link Tile} in {@link board}
	 */
	private void createTiles() {
		for (Tile[] row : this.board.getBoard()) {
			for (Tile tile : row) {
				this.add(tile);
			}
		}
	}
	
	public JLabel getTimeLabel() {
		return this.timeLabel;
	}
	
	public MinesweeperBoard getBoard() {
		return this.board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/**Determine which option is chosen.*/
		switch (JOptionPane.showOptionDialog(null, "Pick an option", "Menu", JOptionPane.DEFAULT_OPTION , JOptionPane.PLAIN_MESSAGE, null, new String[] {"Reset", "Quit", "Controls"}, 2)) {
		
		/**Reset the <code>Board</code>.*/
		case 0:
			switch (JOptionPane.showConfirmDialog(null, "Are you sure you want to reset?", "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null)) {
			
			case JOptionPane.YES_OPTION:
				this.board.newGame();
				break;
				
			default:
				break;
			}
			break;
			
		case 1:
			switch (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null)) {
			
			case JOptionPane.YES_OPTION:
				System.exit(0);
				break;
				
			default:
				break;
			}
			break;
		
		case 2:
			JTextArea jta = new JTextArea("Escape:\tPause\nr:\tReset\nq:\tQuit");
			jta.setOpaque(false);
			jta.setFont(new Font("Arial", Font.PLAIN, 20));
			JOptionPane.showMessageDialog(null, jta, "Controls", JOptionPane.PLAIN_MESSAGE, null);
			break;
			
		default:
			break;
		}
	}
}
