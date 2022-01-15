package modele;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Piece {
	private int posX; //coordonnée i de la piece
	private int posY; //coordonnée j de la piece
	private Orientation orientation; //orientation de la piece
	//protected boolean haut;
	//protected boolean bas;
	//protected boolean droite;
	//protected boolean gauche;
	protected  String unicode;
	protected URL linksImage;
	protected PieceType type;
	private LinkedList<Orientation> connectors;
	private ArrayList<Orientation> possibleOrientations;
	private boolean isFixed;
	private ArrayList<Piece> alreadyTaken = new ArrayList<Piece>();
	
	
	public Piece(int posY, int posX) {
		this.posX = posX;
		this.posY = posY;
		this.type = PieceType.VOID;
		this.orientation = type.getOrientation(Orientation.NORTH);
		this.connectors = type.setConnectorsList(Orientation.NORTH);
		this.isFixed = true; 
		this.possibleOrientations = type.getListOfPossibleOri();
	}

	public Piece(int posY, int posX, PieceType type, Orientation ori) {
		this.posX = posX;
		this.posY = posY;
		this.type = type;
		this.orientation = type.getOrientation(ori);
		this.connectors = type.setConnectorsList(orientation);
		if (type==PieceType.FOURCONN || type==PieceType.VOID) this.isFixed=true;
		else this.isFixed =false;
		this.possibleOrientations = type.getListOfPossibleOri();
	}

	public Piece(int posY, int posX, int typeValue, int orientationValue) {
		this.posX = posX;
		this.posY = posY;
		this.type = PieceType.getTypefromValue(typeValue);
		this.orientation = type.getOrientation(Orientation.getOrifromValue(orientationValue));
		this.connectors = type.setConnectorsList(Orientation.getOrifromValue(orientationValue));
		if (this.type==PieceType.FOURCONN || this.type==PieceType.VOID) this.isFixed = true;
		else this.isFixed =false;
		this.possibleOrientations = type.getListOfPossibleOri();
	}

	public void setPossibleOrientations(ArrayList<Orientation> possibleOrientations) {
		this.possibleOrientations = possibleOrientations;
	}

	public ArrayList<Orientation> getPossibleOrientations() {
		return this.possibleOrientations;
	}

	public LinkedList<Orientation> getInvPossibleOrientation() {
		LinkedList<Orientation> invPossibleOrientations = new LinkedList<Orientation>();
		for (Orientation ori : this.getPossibleOrientations()) {
			invPossibleOrientations.addFirst(ori);
		}
		return invPossibleOrientations;
	}

	public void deleteFromPossibleOrientation(Orientation ori) {
		if (this.possibleOrientations.contains(ori)) {
			this.possibleOrientations.remove(ori);
		}
	}

	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}

	public boolean isFixed() {
		return isFixed;
	}

	public void setOrientation(int orientationValue) {
		this.orientation =type.getOrientation(Orientation.getOrifromValue(orientationValue));
		this.connectors = type.setConnectorsList(this.orientation);
	}


	public LinkedList<Orientation> getConnectors() {
		return connectors;
	}

	public boolean hasTopConnector() {
		//if(this.getConnectors()==null) return false;
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.NORTH) {
				return true;
			}
		}
		return false;
	}

	public boolean hasRightConnector() {
		//if(this.getConnectors()==null) return false;
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.EAST) {
				return true;
			}
		}
		return false;
	}

	public boolean hasBottomConnector() {
		//if(this.getConnectors()==null) return false;
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.SOUTH) {
				return true;
			}
		}
		return false;
	}

	public boolean hasLeftConnector() {
		//if(this.getConnectors()==null) return false;
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.WEST) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Turn the piece 90° on the right and redefine the connectors's position
	 */
	public void turn() { 
		this.orientation = type.getOrientation(orientation.turn90());
		this.connectors = type.setConnectorsList(orientation);
	}

	@Override
	public String toString() {
		String s = "[" + this.getPosY() + ", " + this.getPosX() + "] " + this.getType() + " ";
		for (Orientation ori : this.getConnectors()) {
			s += " " + ori.toString();
		}
		s += " Orientation / " + this.getOrientation();
		return s;
	}
	
	//constructeur
	public Piece(int i, int j, Orientation orientation) {
		this.posX=i;
		this.posY=j;
		this.orientation=orientation;
	}

	//Getter
	public int getPosX() {
		return this.posX;
	}

	public int getPosY() {
		return this.posY;
	}
	
	
	public PieceType getType() {
		return type;
	}

	public Orientation getOrientation() {
		return this.orientation;
	}
	

	//Setter
	public void setPosX(int i) {
		this.posX=i;
	}

	public void setPosY(int j) {
		this.posY=j;
	}
	
	public void setType(PieceType type) {
		this.type = type;
	}

	public ArrayList<Piece> getAlreadyTaken() {
		return alreadyTaken;
	}

	public void setAlreadyTaken(Piece p) {
		this.alreadyTaken.add(p);
	}
	
}
