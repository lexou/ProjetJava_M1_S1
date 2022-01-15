package checker;

import GUI.Grid;
import modele.Piece;

public class Checker {
	public Grid grid;
	
	public Checker(Grid grid) {
		this.grid=grid;
	}
	
	//Getter
	public Grid getGame() {
		return this.grid;
	}
	
	//Methodes
	public boolean isSolution() { //normalement bon
		for(int i=0; i<this.grid.getHeight(); i++) {
			for(int j=0; j<this.grid.getWidth(); j++) {
				if(!isConnected(this.grid.getAllPieces()[i][j])) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isConnected(Piece p) { //normalement bon
		int j=p.getPosX();
		int i=p.getPosY();
		Piece p2=null;
		if(j-1>=0) { //le cote gauche est connecte?
			p2=this.grid.getAllPieces()[i][j-1];
			if (p2!= null) {
				if(p2.hasRightConnector()) {
					if (!p.hasLeftConnector()) {
						return false;
						}
				}
				else if (p.hasLeftConnector()) {
					return false;
				}
			}
			else if (p.hasLeftConnector()) {
				return false;
			}
		}
		if(j+1<this.grid.getWidth()) { //le cote droit est connecte?
			p2=this.grid.getAllPieces()[i][j+1];
			if (p2!=null) {
				if(p2.hasLeftConnector()) {
					if (!p.hasRightConnector()) {
						return false;
					}
				}
				else if (p.hasRightConnector()) {
					return false;
				}
			}
			else if (p.hasRightConnector()) {
				return false;
			}
		}
		if(i-1>=0) { //le haut est connecte?
			p2=this.grid.getAllPieces()[i-1][j];
			if (p2!=null) {
				if(p2.hasBottomConnector()) {
					if (!p.hasTopConnector()) {
						return false;
					}
				}
				else if (p.hasTopConnector()) {
					return false;
				}
			}
			else if (p.hasTopConnector()) {
				return false;
			}
		}
		if(i+1<this.grid.getHeight()) { //le bas est connecte?
			p2=this.grid.getAllPieces()[i+1][j];
			if (p2!=null) {
				if(p2.hasTopConnector()) {
					if (!p.hasBottomConnector()) {
						return false;
					}
				}
				else if (p.hasBottomConnector()) {
					return false;
				}
			}
			else if (p.hasBottomConnector()) {
				return false;
			}
		}
		return true;
	}
}
