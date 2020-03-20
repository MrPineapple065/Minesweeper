import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * This {@code Tile} class represents a Tile on {@link MinesweeperBoard}.
 * 
 * @version 20 March 2020
 * @author MrPineapple065
 */
public class Tile extends JButton implements MouseListener, KeyListener {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 0x448000763278F6FAL;
	
	/**
	 * A {@link Arrays} of {@link Color} holding all the colors that this will be.
	 */
	public static final Color[] numColor = new Color[] {null, new Color(0x0000FF), new Color(0x008000), new Color(0xFF0000), new Color(0x000082), new Color(0x820000), new Color(0x008080), Color.BLACK, new Color(0x808080)};
	
	/**
	 * The {@link Color} of this.
	 */
	private static final Color tileColor = new Color(0xBDBDBD);
	
	/**
	 * A {@link ImageIcon} holding the {@code ImageIcon} for the flag.
	 */
	private static ImageIcon flag;
	
	/**
	 * A {@link ImageIcon} holding the {@code ImageIcon} for the bomb.
	 */
	private static ImageIcon bomb;
	
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
	
	static {
		try {
			Tile.flag = new ImageIcon(ImageIO.read(new File("flag.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
			Tile.bomb = new ImageIcon(ImageIO.read(new File("bomb.png")));
		}
		
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Creates a {@code Tile} with row, col, panel.getBoard(), and panel defined.
	 * 
	 * @param row	is the {@link Tile#row}
	 * @param col	is the {@link Tile#col}
	 * 
	 */
	public Tile(MinesweeperPanel panel, int row, int col) {
		super(null, null);
		
		if (panel == null) {
			throw new IllegalArgumentException("Tile must be on MinesweeperPanel");
		}
		
		else {
			this.panel = panel;
		}
		
		this.row = row; this.col = col;
		
		//Set Default GUI Elements
		this.setFont(new Font("", Font.PLAIN, 30));		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setHorizontalAlignment(JButton.CENTER);	this.setVerticalAlignment(JButton.CENTER);
		this.setFocusPainted(false);
		
		UIManager.put("TextArea.font", new Font("Arial", Font.PLAIN, 30));
		
		this.reset();
		
		//Add Interactivity
		this.addKeyListener(this); this.addMouseListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	public static ImageIcon getBombIcon() {
		return Tile.bomb;
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
		return Tile.tileColor;
	}
	
	/**
	 * Determine the row that this is in.
	 * 
	 * @return {@link #row}
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * Determine the column that this is in.
	 * 
	 * @return {@link #col}
	 */
	public int getCol() {
		return this.col;
	}
	
	/**
	 * Determine the number of bombs surrounding this.
	 * 
	 * @return {@link #count}
	 */
	public int getCount() {
		return this.count;
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
	
	/**
	 * Set {@link #count}
	 * 
	 * @return {@link #count}
	 */
	public int setCount(int count) {
		this.count = count; return this.count;
	}
	
	/**
	 * Reset Tile
	 */
	public void reset() {
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.isFlagged = false; this.isBomb = false; this.isRevealed = false;
		this.setText(null); this.setIcon(null); this.setForeground(null); this.setBackground(Tile.tileColor);
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
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (col != other.col)
			return false;
		if (count != other.count)
			return false;
		if (isBomb != other.isBomb)
			return false;
		if (isFlagged != other.isFlagged)
			return false;
		if (isRevealed != other.isRevealed)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("(%s, %s)", String.valueOf(this.row), String.valueOf(this.col));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (e.getButton()) {
		
		/**Left Click*/
		case MouseEvent.BUTTON1:
			this.panel.getBoard().reveal(this);
			break;
			
		/**Middle Clicl*/
		case MouseEvent.BUTTON2:
			String tileText = this.getText();
			if (("".equals(tileText)) || ! ((this.toString()).equals(tileText))) {
				this.setForeground(new Color(0x333333));
				this.setText(this.toString());
			}
			
			else {
				this.setForeground(this.count == 0 ? null : Tile.numColor[this.count]);
				this.setText(this.count == 0 ? null : String.valueOf(this.count));
			}
			break;
			
			
		/**Right Click*/
		case MouseEvent.BUTTON3:
			if (this.isRevealed) {
				break;
			}
			
			if (this.panel.getBoard().getGameOver()) {
				break;
			}
			
			if (this.isFlagged) {
				this.setIcon(null);
				this.setForeground(null);
				this.panel.getBoard().incFlagCount();
				this.toggleFlagged();
			}
			
			else {
				this.setIcon(flag);
				this.panel.getBoard().decFlagCount();
				this.toggleFlagged();
			}
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.getModel().setPressed(true);
		this.panel.m.click();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.getModel().setPressed(false);
		this.panel.m.click();
	}

	@Override
	public void mouseEntered(MouseEvent e)	{return;}

	@Override
	public void mouseExited(MouseEvent e)	{return;}

	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
		case KeyEvent.VK_ESCAPE:
			this.panel.m.actionPerformed(null);
			break;
			
		case 'r':
			switch (JOptionPane.showConfirmDialog(null, "Are you sure you want to reset?", "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null)) {
			
			case JOptionPane.YES_OPTION:
				this.panel.getBoard().reset();
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
