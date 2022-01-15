package GUI;



import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import modele.Orientation;
import modele.Piece;
import modele.PieceType;

/**
 * This class handles the GUI
 * 
 *
 */
public class GUI {

	private JFrame frame;

	/**
	 * 
	 * @param inputFile
	 *            String from IO
	 * @throws IOException
	 *             if there is a problem with the gui
	 */
	/*public static void startGUI(String inputFile) throws NullPointerException {
		// We have to check that the grid is generated before to launch the GUI
		// construction
		Runnable task = new Runnable() {
			public void run() {

				try {
					Grid grid = Checker.buildGrid(inputFile);
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							GUI window;
							window = new GUI(grid);
							window.frame.setVisible(true);
						}
					});
				} catch (IOException e) {
					throw new NullPointerException("Error with input file");
				}

			}
		};
		new Thread(task).start();

	}*/

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public GUI(Grid grid) {

		initialize(grid);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize(Grid grid) {
		
		// To implement:
		// creating frame, labels
		// Implementing method mouse clicked of interface MouseListener.
	}

	/**
	 * Display the correct image from the piece's type and orientation
	 * 
	 * @param p
	 *            the piece
	 * @return an image icon
	 */
/*	private ImageIcon getImageIcon(Piece p) {
		URL tmp= null;
		if (p.getType()==PieceType.VOID) {
			tmp=getClass().getResource("/images/Piece00.png");
			ImageIcon icon =new ImageIcon(tmp.getFile()); //devrait etre getImage()
		}
		else if (p.getType()==PieceType.ONECONN) {
			if (p.getOrientation()==Orientation.NORTH) return new ImageIcon(getClass().getResource("/images/Piece10.png").getFile());
			if (p.getOrientation()==Orientation.EAST) return new ImageIcon(getClass().getResource("/images/Piece11.png").getFile());
			if (p.getOrientation()==Orientation.SOUTH) return new ImageIcon(getClass().getResource("/images/Piece12.png").getFile());
			if (p.getOrientation()==Orientation.WEST) return new ImageIcon(getClass().getResource("/images/Piece13.png").getFile());
		}
		else if (p.getType()==PieceType.BAR) {
			if (p.getOrientation()==Orientation.NORTH) return new ImageIcon(getClass().getResource("/images/Piece20.png").getFile());
			if (p.getOrientation()==Orientation.EAST) return new ImageIcon(getClass().getResource("/images/Piece20.png").getFile());
		}
		else if (p.getType()==PieceType.LTYPE) {
			if (p.getOrientation()==Orientation.NORTH) return new ImageIcon(getClass().getResource("/images/Piece50.png").getFile());
			if (p.getOrientation()==Orientation.EAST) return new ImageIcon(getClass().getResource("/images/Piece51.png").getFile());
			if (p.getOrientation()==Orientation.SOUTH) return new ImageIcon(getClass().getResource("/images/Piece52.png").getFile());
			if (p.getOrientation()==Orientation.WEST) return new ImageIcon(getClass().getResource("/images/Piece53.png").getFile());
		}
		else if (p.getType()==PieceType.TTYPE) {
			if (p.getOrientation()==Orientation.NORTH) return new ImageIcon(getClass().getResource("/images/Piece30.png").getFile());
			if (p.getOrientation()==Orientation.EAST) return new ImageIcon(getClass().getResource("/images/Piece31.png").getFile());
			if (p.getOrientation()==Orientation.SOUTH) return new ImageIcon(getClass().getResource("/images/Piece32.png").getFile());
			if (p.getOrientation()==Orientation.WEST) return new ImageIcon(getClass().getResource("/images/Piece33.png").getFile());
		}
		else {
			return new ImageIcon(getClass().getResource("/images/Piece40.png").getFile());
		}
	}*/

}
