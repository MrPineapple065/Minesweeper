import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * This {@code Minesweeper} class initializes the logic for the game of Minesweeper.
 * 
 * @version 3 April 2020
 * @author MrPineapple065
 */
public class Minesweeper {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Minesweeper");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		MinesweeperPanel panel = null;
		
		switch (JOptionPane.showOptionDialog(null, "Choose Dificulty", "", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Easy", "Medium", "Hard", "Custom", "Cancel" },  2)) {
		
		case 0:
			panel = new MinesweeperPanel(9, 9, 10);
			frame.add(panel);
			break;
			
		case 1:
			panel = new MinesweeperPanel(16, 16, 40);
			frame.add(panel);
			break;
			
		case 2:
			panel = new MinesweeperPanel(16, 30, 99);
			frame.add(panel);
			break;
		
		case 3:
			JPanel jp = new JPanel();
			jp.setLayout(new GridLayout(3,2));
			
			UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 20));
			UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 20));
			
			JTextField row = new JTextField(10), col = new JTextField(10), bomb = new JTextField(10);
			
			jp.add(new JLabel("Row:"));
			jp.add(row);
			
			jp.add(new JLabel("Column:"));
			jp.add(col);
			
			jp.add(new JLabel("Bomb:"));
			jp.add(bomb);
			
			boolean fail = true;
			
			while (fail) {
				switch (JOptionPane.showConfirmDialog(null, jp, "", JOptionPane.OK_CANCEL_OPTION)) {
				
				case JOptionPane.OK_OPTION:
					try {
						panel = new MinesweeperPanel(Integer.parseInt(row.getText()), Integer.parseInt(col.getText()), Integer.parseInt(bomb.getText()));
						frame.add(panel);
						fail = false;
					}
					
					catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(null, "Try again", "", JOptionPane.ERROR_MESSAGE, null);
					}
					
					break;
					
				default:
					System.exit(0);
				}
			}
			break;
			
		default:
			System.exit(0);
		}
		
		frame.pack();
		frame.setSize(panel.getBoard().getColMax() * 50, (panel.getBoard().getRowMax() + 1 ) * 50);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
		        switch (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Careful!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null)) {

		        case JOptionPane.YES_OPTION:
		            System.exit(0);
		            break;
		        
		        default:
		        	break;
		        }
		    }
		});
	}
}
