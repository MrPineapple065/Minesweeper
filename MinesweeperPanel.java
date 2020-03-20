import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * This {@code MinesweeperPanel} class holds all interactable and GUI elements of {@link Minesweeper}.
 * 
 * @version 20 March 2020
 * @author MrPineapple065
 */
public class MinesweeperPanel extends JPanel {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 0x80704238D46657C5L;
	
	/**
	 * The standard {@link Font}.
	 */
	private static final Font standardFont = new Font("Arial", Font.PLAIN, 30);
	
	/**
	 * {@link MenuButton}
	 */
	public final MenuButton m;
	
	/**
	 * A {@link JLabel} used to indicate {@link MinesweeperBoard#getFlags()}
	 */
	private final JLabel flagLabel = new JLabel("", JLabel.CENTER);
	
	/**
	 * A {@link JLabel} used to indicate {@link MinesweeperBoard#timer}
	 */
	private final JLabel timeLabel = new JLabel("0", JLabel.CENTER);
	
	/**
	 * The actual {@link MinesweeperBoard}
	 */
	private MinesweeperBoard board;
	
	/**
	 * Create a {@link MinesweeperPanel} with <code>row</code> number of rows, </br>
	 * <code>col</code> number of columns, </br>
	 * and <code>numBombs</code> bombs.
	 * 
	 * @param row		is the number of rows.
	 * @param col		is the number of columns.
	 * @param numBombs	is the number of bombs.
	 * 
	 * @throws	IllegalArgumentException  if construction of {@link MinesweeperBoard#MinesweeperBoard(MinesweeperPanel, int, int, int)} fails.
	 * @throws	IndexOutOfBoundsException if construction of {@link MinesweeperBoard#MinesweeperBoard(MinesweeperPanel, int, int, int)} fails.
	 */
	public MinesweeperPanel(int row, int col, int numBombs) throws IllegalArgumentException, IndexOutOfBoundsException {
		super();
		try {
			this.board = new MinesweeperBoard(this, row, col, numBombs);
		}
		
		catch (IllegalArgumentException iae) {
			throw iae;
		}
		
		catch (IndexOutOfBoundsException ioobe) {
			throw ioobe;
		}
		
		setLayout(new GridLayout(row + 1, col));
		
		UIManager.put("OptionPane.messageFont", standardFont);
		UIManager.put("OptionPane.buttonFont",	standardFont);
		UIManager.put("Label.font",				standardFont);
		UIManager.put("Label.background",		null);
		UIManager.put("Label.foreground",		Color.BLACK);
		
		this.m = new MenuButton(this);
		
		/**Create other GUI Elements*/
		this.createLabels();
		this.createTiles();
	}
	
	/**
	 * @return {@link #timeLabel}
	 */
	public JLabel getTimeLabel() {
		return this.timeLabel;
	}
	
	/**
	 * @return {@link #board}
	 */
	public MinesweeperBoard getBoard() {
		return this.board;
	}
	
	/**
	 * Updates {@link #bombLabel} to display the number of bombs left to flag.
	 */
	public void updateBLabel() {
		this.flagLabel.setText(String.valueOf(this.board.getFlags()));
	}
	
	/**
	 * Update {@link #timeLabel} to display {@code time}
	 * 
	 * @param time is the time to display
	 * 
	 * @see MinesweeperBoard#timer
	 */
	public void updateTLabel(int time) {
		this.timeLabel.setText(String.valueOf(time));
	}
	
	/**
	 * Create {@link JLabels} and add them to the {@link MinesweeperPanel}
	 */
	private void createLabels() {
		this.updateBLabel();
			
		for (int i = 0; i < this.board.getColMax(); i++) {
			if (i == this.board.getColMax() / 4) {
				this.add(this.flagLabel);
			}
			
			else if (i == this.board.getColMax() / 2) {
				this.add(m);
			}
			
			else if (i == 3 * this.board.getColMax() / 4) {
				this.add(this.timeLabel);
			}
			
			else {
				this.add(new JLabel("", JLabel.CENTER));
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
	
	/**
	 * This {@code MenuButton} class just helps create a button that opens a menu.
	 * 
	 * @version 18 March 2020
	 * @author MrPineapple065
	 */
	public static class MenuButton extends JButton implements ActionListener {
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 0x3EEF5D22FB602996L;
		
		/**
		 * A {@link ImageIcon} holding the default {@code ImageIcon}
		 */
		public static ImageIcon menuDefault		= null;
		
		/**
		 * A {@link ImageIcon} holding the {@code ImageIcon} to display when {@code Mouse} is pressed.
		 */
		public static ImageIcon menuClick		= null;
		
		/**
		 * A {@link ImageIcon} holding the {@code ImageIcon} to display when the game is over.
		 */
		public static ImageIcon menuGameOver	= null;
		
		/**
		 * The {@link MinesweeperPanel} holding this.
		 */
		private final MinesweeperPanel panel;
		
		static {
			try {
				MenuButton.menuDefault	= new ImageIcon(ImageIO.read(new File("menuDefault.png" )).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
				MenuButton.menuClick	= new ImageIcon(ImageIO.read(new File("menuClick.png"   )).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
				MenuButton.menuGameOver	= new ImageIcon(ImageIO.read(new File("menuGameOver.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
			}
			
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		/**
		 * Create {@code MenuButton} on {@code panel}
		 * 
		 * @param panel is the {@link MinesweeperPanel} that this button will be placed on.
		 * 
		 * @throws IllegalArgumentException if {@code panel} is {@code null}.
		 */
		public MenuButton(MinesweeperPanel panel) throws IllegalArgumentException {
			super();
			
			if (panel == null) {
				throw new IllegalArgumentException("MenuButton must be on a MinesweerPanel");
			}
			
			else {
				this.panel = panel;
			}
			
			this.reset();
			this.setFocusPainted(false);
			this.addActionListener(this);
			this.setOpaque(false);
			this.setContentAreaFilled(false);
		
		}
		
		public void click() {
			if (this.getIcon().equals(menuDefault)) {
				this.setIcon(menuClick);
			}
			
			else {
				this.reset();
			}
		}
		
		public void reset() {
			this.setIcon(menuDefault);
		}
		
		public void gameOver() {
			this.setIcon(menuGameOver);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			/**Determine which option is chosen.*/
			switch (JOptionPane.showOptionDialog(null, "Pick an option", "Menu", JOptionPane.DEFAULT_OPTION , JOptionPane.PLAIN_MESSAGE, null, new String[] {"Reset", "Quit", "Controls"}, 2)) {
			
			/**Reset the <code>Board</code>.*/
			case 0:
				if (this.panel.board.getGameOver()) {
					this.reset();
					this.panel.board.reset();
					break;
				}
				
				else {
					switch (JOptionPane.showConfirmDialog(null, "Are you sure you want to reset?", "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null)) {
					
					case JOptionPane.YES_OPTION:
						this.reset();
						this.panel.board.reset();
						break;
						
					default:
						break;
					}
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
}
