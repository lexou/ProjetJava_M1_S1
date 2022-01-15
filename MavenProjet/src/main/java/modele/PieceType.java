package modele;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * Type of the piece enum
 * 
 */
public enum PieceType {
	BAR(2),
	VOID(0),
	LTYPE(6),
	ONECONN(1),
	TTYPE(3),
	FOURCONN(4);// Each Type has a number of connectors and a specific value
	
	private int nbrType;
	
	
	private PieceType(int nbrType) {
		this.setNbr(nbrType);
	}

	public int getNbr() {
		return nbrType;
	}
	
	/*public Orientation getOrientation(Orientation orientation) { //Que doit elle faire?
		return orientation;
	}*/
	
	public void setNbr(int nbrType) {
		this.nbrType = nbrType;
	}
	
	
	static PieceType getTypefromValue(int typeValue) { //normalement bonne
		for (PieceType tmp : PieceType.values()) {
			if(typeValue==tmp.getNbr()) {
				return tmp;
			}
		}
		return null;
	}
	 
	
	LinkedList<Orientation> setConnectorsList(Orientation orientation) { //normalement bon
		LinkedList<Orientation> list=new LinkedList<Orientation>();
		if(this.nbrType==0) {
			//list=null;
		}
		else if(this.nbrType==1){
			list.add(orientation);
		
		}
		else if(this.nbrType==2){
			if(orientation==Orientation.NORTH) {
				list.add(Orientation.NORTH);
				list.add(Orientation.SOUTH);
			}
			else if(orientation==Orientation.EAST) {
				list.add(Orientation.EAST);
				list.add(Orientation.WEST);
			}
		}
		else if(this.nbrType==3){
			if(orientation==Orientation.NORTH) {
				list.add(Orientation.NORTH);
				list.add(Orientation.EAST);
				list.add(Orientation.WEST);
			}
			else if(orientation==Orientation.EAST) {
				list.add(Orientation.NORTH);
				list.add(Orientation.SOUTH);
				list.add(Orientation.EAST);
			}
			else if(orientation==Orientation.SOUTH) {
				list.add(Orientation.EAST);
				list.add(Orientation.SOUTH);
				list.add(Orientation.WEST);
			}
			else if(orientation==Orientation.WEST) {
				list.add(Orientation.NORTH);
				list.add(Orientation.SOUTH);
				list.add(Orientation.WEST);
			}
		}
		else if(this.nbrType==4){
			list.add(Orientation.NORTH);
			list.add(Orientation.SOUTH);
			list.add(Orientation.WEST);
			list.add(Orientation.EAST);
		}
		else {
			if(orientation==Orientation.NORTH) {
				list.add(Orientation.NORTH);
				list.add(Orientation.EAST);
			}
			else if(orientation==Orientation.EAST) {
				list.add(Orientation.SOUTH);
				list.add(Orientation.EAST);
			}
			else if(orientation==Orientation.SOUTH) {
				list.add(Orientation.SOUTH);
				list.add(Orientation.WEST);
			}
			else if(orientation==Orientation.WEST) {
				list.add(Orientation.NORTH);
				list.add(Orientation.WEST);
			}
		}
		return list;
	}


	ArrayList<Orientation> getListOfPossibleOri() {  //normalement bonne
		ArrayList<Orientation> list= new ArrayList<Orientation>();
		if(this.nbrType==0) {
			list.add(Orientation.NORTH);
		}
		else if(this.nbrType==1){
			list.add(Orientation.NORTH);
			list.add(Orientation.SOUTH);
			list.add(Orientation.EAST);
			list.add(Orientation.WEST);
		}
		else if(this.nbrType==2){
			list.add(Orientation.NORTH);
			list.add(Orientation.EAST);
		}
		else if(this.nbrType==3){
			list.add(Orientation.NORTH);
			list.add(Orientation.SOUTH);
			list.add(Orientation.EAST);
			list.add(Orientation.WEST);
		}
		else if(this.nbrType==4){
			list.add(Orientation.NORTH);
		}
		else {
			list.add(Orientation.NORTH);
			list.add(Orientation.SOUTH);
			list.add(Orientation.EAST);
			list.add(Orientation.WEST);
		}
		return list;
	}
	

	 Orientation getOrientation(Orientation orientation) {
		if(this.nbrType==0) {
			return Orientation.NORTH;
		}
		else if(this.nbrType==1){
			return Orientation.getOrifromValue(orientation.getNbr()%4);
		}
		else if(this.nbrType==2){
			return Orientation.getOrifromValue(orientation.getNbr()%2); 
			}
		else if(this.nbrType==3){
			return Orientation.getOrifromValue(orientation.getNbr()%4);
			}
		else if(this.nbrType==4){
			return Orientation.getOrifromValue(orientation.getNbr()%4);
		}
		else {
			return Orientation.getOrifromValue(orientation.getNbr()%4);
		}
	}

	public int getNbConnectors() { //normalement bon
		if(this.nbrType==0) {
			return 0;
		}
		else if(this.nbrType==1){
			return 1;
		}
		else if(this.nbrType==2){
			return 2; 
			}
		else if(this.nbrType==3){
			return 3;
			}
		else if(this.nbrType==4){
			return 4;
		}
		else {
			return 2;
		}
	}

}
