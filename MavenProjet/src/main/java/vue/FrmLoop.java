package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import checker.Checker;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import modele.Piece;
import GUI.Grid;

public class FrmLoop extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private Grid grid; 
	private int h;
	private int w;
	private int imageSize;
	private JButton[][] b;
	
	
	public FrmLoop(Grid grid) {
		super();
		this.grid = grid;
		this.h = grid.getHeight();
		this.w = grid.getWidth();
		this.b=new JButton[h][w];
		this.imageSize= 1000/Math.max(h,w);
		JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(1000,1000);
		this.setTitle("INFINITY LOOP GAME");
	    JPanel panel=new JPanel();
		this.setContentPane(panel);
		panel.setLayout(new GridLayout(h,w));
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		drawGrid();
	}
	
	public void drawGrid() {
		Checker checker=new Checker(grid);
		Piece[][] board = grid.getAllPieces();
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				ImageIcon icon = (new ImageIcon ((grid.getImageIcon(board[i][j])).getImage().getScaledInstance(imageSize, imageSize, java.awt.Image.SCALE_SMOOTH)));
				this.b[i][j]=new JButton(icon);
				this.b[i][j].addActionListener(this);
				add(this.b[i][j]);
				}
		}
		addColor();
		if(checker.isSolution())System.out.println("SOLVED");
	}
	
	public void addColor() { //en cours de reflexion -> le isConnected ne fonctionne peut etre pas correctement
		Checker checker=new Checker(grid);
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				if (checker.isConnected(grid.getAllPieces()[i][j])) {
					this.b[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN,1));
				}
				else {
					this.b[i][j].setBorder(BorderFactory.createLineBorder(Color.RED,1));
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent evt) {
		Checker checker=new Checker(grid);
		Object source = evt.getSource();
		Piece[][] board = this.grid.getAllPieces();
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				if(b[i][j]==source) {
					board[i][j].turn();
					b[i][j].setIcon((new ImageIcon ((grid.getImageIcon(board[i][j])).getImage().getScaledInstance(imageSize, imageSize, java.awt.Image.SCALE_SMOOTH))));
					break;
				}
			}
		}
		addColor();
		if(checker.isSolution()) System.out.println("SOLVED");
	}
}
