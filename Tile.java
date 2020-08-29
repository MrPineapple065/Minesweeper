import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * This {@code Tile} class represents a Tile on {@link MinesweeperBoard}.
 * 
 * @version 3 April 2020
 * @author MrPineapple065
 */
public class Tile extends JButton implements MouseListener, KeyListener {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 0x448000763278F6FAL;
	
	/**
	 * A {@code Array} of {@link ImageIcon} holding all the {@code ImageIcon} that this will display.
	 */
	public static final ImageIcon[] numbers = new ImageIcon[9];
	
	/**
	 * A {@link Function} to convert an inputed {@link String} to a {@link ImageIcon}.
	 */
	private static final Function<String, ImageIcon> f = str -> {
		try {
			return new ImageIcon(ImageIO.read(new File(str)).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
	};
	
	/**
	 * A {@link ImageIcon} holding the {@code ImageIcon} for the bomb.
	 */
	public static final ImageIcon bomb			= f.apply("bomb.png");
	
	/**
	 * A {@link ImageIcon} holding the {@code ImageIcon} for an incorrectly flagged {@link Tile}.
	 */
	public static final ImageIcon incorrectFlag	= f.apply("incorrectFlag.png");
	
	/**
	 * A {@link ImageIcon} holding the {@code ImageIcon} for the flag.
	 */
	private static final ImageIcon flag			= f.apply("flag.png");
	
	/**
	 * A reference holding a {@link Color} that every Tile will be.
	 */
	private static final Color color = new Color(0xBDBDBD);
	
	static {
		UIManager.put("TextArea.font", new Font("Arial", Font.PLAIN, 30));
		try {
			Tile.numbers[1] = new ImageIcon(ImageIO.read(new File("one.png"		)).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
			Tile.numbers[2] = new ImageIcon(ImageIO.read(new File("two.png"		)).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
			Tile.numbers[3] = new ImageIcon(ImageIO.read(new File("three.png"	)).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
			Tile.numbers[4] = new ImageIcon(ImageIO.read(new File("four.png"	)).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
			Tile.numbers[5] = new ImageIcon(ImageIO.read(new File("five.png"	)).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
			Tile.numbers[6] = new ImageIcon(ImageIO.read(new File("six.png"		)).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
			Tile.numbers[7] = new ImageIcon(ImageIO.read(new File("seven.png"	)).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
			Tile.numbers[8] = new ImageIcon(ImageIO.read(new File("eight.png"	)).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(0);
		}
	}
	
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
	 * Creates a {@code Tile} with row, col, panel.getBoard(), and panel defined.
	 * 
	 * @param panel is the {@link MinesweeperPanel} holding this.
	 * @param row	is the {@link #row}
	 * @param col	is the {@link #col}
	 */
	public Tile(MinesweeperPanel panel, int row, int col) {
		super(null, null);
		
		this.panel = Objects.requireNonNull(panel, "Tile must be on MinesweeperPanel");
		this.row = row; this.col = col;
		
		//Set Default GUI Elements
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setHorizontalAlignment(JButton.CENTER);	this.setVerticalAlignment(JButton.CENTER);
		this.setFocusPainted(false);
		
		this.reset();
		
		//Add Interactivity
		this.addKeyListener(this); this.addMouseListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)							return true;
		if (!(obj instanceof Tile))					return false;
		Tile other = (Tile) obj;
		if (col != other.col)						return false;
		if (count != other.count)					return false;
		if (isBomb != other.isBomb) 				return false;
		if (isFlagged != other.isFlagged)			return false;
		if (isRevealed != other.isRevealed) 		return false;
		if (panel == null) if (other.panel != null) return false;
		else if (!panel.equals(other.panel))		return false;
		if (row != other.row) 						return false;
		return true;
	}
	
	/**
	 * @return {@link #col}
	 */
	public int getCol() {
		return this.col;
	}
	
	/**
	 * @return {@link #count}
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * @return {@link #row}
	 */
	public int getRow() {
		return this.row;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + count;
		result = prime * result + (isBomb ? 1231 : 1237);
		result = prime * result + (isFlagged ? 1231 : 1237);
		result = prime * result + (isRevealed ? 1231 : 1237);
		result = prime * result + ((panel == null) ? 0 : panel.hashCode());
		result = prime * result + row;
		return result;
	}
	
	/**
	 * @return {@link Tile#isBomb}
	 */
	public boolean isBomb() {
		return isBomb;
	}
	
	
	/**
	 * @return {@link Tile#isFlagged}
	 */
	public boolean isFlagged() {
		return this.isFlagged;
	}
	
	/**
	 * @return {@link Tile#isRevealed}
	 */
	public boolean isRevealed() {
		return this.isRevealed;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {return;}
	
	@Override
	public void keyReleased(KeyEvent e) {return;}
	
	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
		case KeyEvent.VK_ESCAPE:
			this.panel.m.actionPerformed(null);
			return;
		case 'r':
			switch (JOptionPane.showConfirmDialog(null, "Are you sure you want to reset?", "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null)) {
			case JOptionPane.YES_OPTION:
				this.panel.m.reset();
				this.panel.getBoard().reset();
			default:
				return;
			}
		case 'q':
			switch (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null)) {
			case JOptionPane.YES_OPTION:
				System.exit(0);
			default:
				return;
			}
		}	
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			this.panel.getBoard().reveal(this);
			return;
		case MouseEvent.BUTTON3:
			if (this.isRevealed) return;
			if (this.panel.getBoard().getGameOver()) return;
			if (this.isFlagged) {
				this.setIcon(null);
				this.setForeground(null);
				this.panel.getBoard().incFlagCount();
				this.toggleFlagged();
			} else {
				this.setIcon(flag);
				this.panel.getBoard().decFlagCount();
				this.toggleFlagged();
			} return;
		default:
			return;
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e)	{return;}
	
	@Override
	public void mouseExited(MouseEvent e)	{return;}

	@Override
	public void mousePressed(MouseEvent e) {
		if (this.panel.getBoard().getGameOver()) return;
		this.getModel().setPressed(true);
		this.panel.m.click();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.panel.getBoard().getGameOver()) return;
		this.getModel().setPressed(false);
		this.panel.m.reset();
	}

	/**
	 * Reset Tile
	 */
	public void reset() {
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.isFlagged = false; this.isBomb = false; this.isRevealed = false;
		this.setIcon(null);	this.setBackground(color);
	}

	/**
	 * Set {@link #isBomb} to {@code isBomb}
	 * 
	 * @param isBomb new {@link #isBomb} value.
	 */
	public void setBomb(boolean isBomb) {
		this.isBomb = isBomb;
	}

	/**
	 * Set {@link #count}
	 * 
	 * @param count is the new {@code count}.
	 * 
	 * @return the new value.
	 */
	public int setCount(int count) {
		this.count = count; return this.count;
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
	 * Set {@link #isRevealed} to {@code isRevealed}
	 * 
	 * @param isRevealed	new {@link #isRevealed} value.
	 */
	public void setRevealed(boolean isRevealed) {
		this.isRevealed = isRevealed;
	}

	/**
	 * Toggle {@link #isFlagged}
	 */
	public void toggleFlagged() {
		this.isFlagged ^= true;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s)", String.valueOf(this.row), String.valueOf(this.col));
	}
}