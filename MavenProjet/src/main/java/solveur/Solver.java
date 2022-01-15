package solveur;



import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

import GUI.Grid;
import checker.Checker;
import modele.Orientation;
import modele.Piece;
import modele.PieceType;


public class Solver {

	private Grid grid;
	private Stack<Piece> pile =new Stack<Piece>();

	public Solver(Grid grid) {
		this.grid=grid;
	}

	public Grid solve() {
		Piece depart = grid.getAllPieces()[0][0];
		pile.push(depart);
		int jeux=0;
		while(!pile.isEmpty()) {
			int cmpt=4;
			Piece enCoursDeTraitement = pile.peek();
			//System.out.println("en cours : "+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
			int i = enCoursDeTraitement.getPosX();
			int j = enCoursDeTraitement.getPosY();

			//Grid solved ?
			Checker checker=new Checker(grid);
			if(checker.isSolution()) {
				System.out.println("la grille est SOLVED");
				return grid;
			}
			if(enCoursDeTraitement.isFixed()) pile.pop();
			if(!enCoursDeTraitement.isFixed() && cmpt>0) {
				if(grid.hasFixedNeighbour(enCoursDeTraitement)) {
					while (!grid.isTotallyConnected(enCoursDeTraitement) && cmpt!=0) {
						enCoursDeTraitement.turn();
						cmpt--;
						//System.out.println("Je suis ici, mes voisins sont tous fixed");
					}
					enCoursDeTraitement.setFixed(true);
					pile.pop();
				}
			//Je suis un coin de type L
			else if(grid.isCorner(enCoursDeTraitement.getPosX(), enCoursDeTraitement.getPosY()) && enCoursDeTraitement.getType()==PieceType.LTYPE) {
				//System.out.println("je suis un coin de type L"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
				if (enCoursDeTraitement.getPosX()==0 && enCoursDeTraitement.getPosY()==0) {
					enCoursDeTraitement.setOrientation(1);
					enCoursDeTraitement.setFixed(true);
					pile.pop();
				}
				else if (enCoursDeTraitement.getPosY()!=0 && enCoursDeTraitement.getPosX()==0) {
					enCoursDeTraitement.setOrientation(0);
					enCoursDeTraitement.setFixed(true);
					pile.pop();
				}
				else if (enCoursDeTraitement.getPosY()==0 && enCoursDeTraitement.getPosX()!=0) {
					enCoursDeTraitement.setOrientation(2);
					enCoursDeTraitement.setFixed(true);
					pile.pop();
				}
				else {
					enCoursDeTraitement.setOrientation(3);
					enCoursDeTraitement.setFixed(true);
					pile.pop();
				}
				//System.out.println("je suis le meme coin de type L"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
			}
			//Je suis un coin de type ONECONN
			else if(grid.isCorner(enCoursDeTraitement.getPosX(), enCoursDeTraitement.getPosY()) && enCoursDeTraitement.getType()==PieceType.ONECONN) {
				//System.out.println("je suis un coin de type OneCONN"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
				if (grid.listOfFixedNeighbours(enCoursDeTraitement)!=null && !enCoursDeTraitement.isFixed()) {
					//System.out.println("je rentre bien ici");
					int tmp=0;
					for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
						if(enCoursDeTraitement.getPosX()==0 && enCoursDeTraitement.getPosY()==0) {//coin gauche haut
							//System.out.println("je suis un coin gauche");
						if (p_tmp==grid.rightNeighbor(enCoursDeTraitement)) {
							if(p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector()&& !enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasLeftConnector()) tmp=1;
							while((p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector())|| enCoursDeTraitement.hasTopConnector() || enCoursDeTraitement.hasLeftConnector()) {
								enCoursDeTraitement.turn();
								//System.out.println("yo1");
								tmp++;
							}
							if(!p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()&& !enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasLeftConnector())tmp=1;
							while((!p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector())|| enCoursDeTraitement.hasTopConnector()|| enCoursDeTraitement.hasLeftConnector()) {
								enCoursDeTraitement.turn();
								//System.out.println("yo11");
								tmp++;
							}
						}
						else {
							if(p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector()&& !enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasLeftConnector())tmp=1;
							while((p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector())|| enCoursDeTraitement.hasTopConnector()|| enCoursDeTraitement.hasLeftConnector()) {
								enCoursDeTraitement.turn();
								//System.out.println("yo4");
								tmp++;
							}
							if(!p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()&& !enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasLeftConnector())tmp=1;
							while((!p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector())|| enCoursDeTraitement.hasTopConnector()|| enCoursDeTraitement.hasLeftConnector()) {
								enCoursDeTraitement.turn();
								//System.out.println("yo14");
								tmp++;
							}
							}
						}
						else if(enCoursDeTraitement.getPosX()==grid.getWidth()-1 && enCoursDeTraitement.getPosY()==0) {//coin droit haut
							if (p_tmp==grid.rightNeighbor(enCoursDeTraitement)) {
								if(p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector()&& !enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasRightConnector()) tmp=1;
								while((p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector())|| enCoursDeTraitement.hasTopConnector() || enCoursDeTraitement.hasRightConnector()) {
									enCoursDeTraitement.turn();
									//System.out.println("yo1");
									tmp++;
								}
								if(!p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()&& !enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasRightConnector())tmp=1;
								while((!p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector())|| enCoursDeTraitement.hasTopConnector()|| enCoursDeTraitement.hasRightConnector()) {
									enCoursDeTraitement.turn();
									//System.out.println("yo11");
									tmp++;
								}
							}

							 if (p_tmp==grid.leftNeighbor(enCoursDeTraitement)) {
								if(p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector()&& !enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasRightConnector()) tmp=1;
								while(p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()|| enCoursDeTraitement.hasTopConnector()|| enCoursDeTraitement.hasRightConnector()) {
									enCoursDeTraitement.turn();
									//System.out.println("yo2");
									tmp++;
								}
								if(!p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()&& !enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasRightConnector()) tmp=1;
								while((!p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector()) || enCoursDeTraitement.hasTopConnector()|| enCoursDeTraitement.hasRightConnector()) {
									enCoursDeTraitement.turn();
									//System.out.println("yo12");
									tmp++;
								}
							}
							else {
								if(p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector()&& !enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasRightConnector())tmp=1;
								while((p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector())|| enCoursDeTraitement.hasTopConnector()|| enCoursDeTraitement.hasRightConnector()) {
									enCoursDeTraitement.turn();
									//System.out.println("yo4");
									tmp++;
								}
								if(!p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()&& !enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasRightConnector())tmp=1;
								while((!p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector())|| enCoursDeTraitement.hasTopConnector()|| enCoursDeTraitement.hasRightConnector()) {
									enCoursDeTraitement.turn();
									//System.out.println("yo14");
									tmp++;
								}
							}
						}
						else if(enCoursDeTraitement.getPosX()==0 && enCoursDeTraitement.getPosY()==grid.getWidth()-1) {//coin gauche bas
						if (p_tmp==grid.rightNeighbor(enCoursDeTraitement)) {
							if(p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector()&& !enCoursDeTraitement.hasBottomConnector() && !enCoursDeTraitement.hasLeftConnector()) tmp=1;
							while(p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()|| enCoursDeTraitement.hasBottomConnector() || enCoursDeTraitement.hasLeftConnector()) {
								enCoursDeTraitement.turn();
								//System.out.println("yo1");
								tmp++;
							}
							if(!p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()&& !enCoursDeTraitement.hasBottomConnector() && !enCoursDeTraitement.hasLeftConnector())tmp=1;
							while(!p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector()|| enCoursDeTraitement.hasBottomConnector()|| enCoursDeTraitement.hasLeftConnector()) {
								enCoursDeTraitement.turn();
								//System.out.println("yo11");
								tmp++;
							}
						}
						else if (p_tmp==grid.topNeighbor(enCoursDeTraitement)) {
							if(p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector() && !enCoursDeTraitement.hasLeftConnector())tmp=1;
							while(p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector() || enCoursDeTraitement.hasBottomConnector()|| enCoursDeTraitement.hasLeftConnector()) {
								enCoursDeTraitement.turn();
								//System.out.println("yo3");
								tmp++;
							}
							if(!p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()&& !enCoursDeTraitement.hasBottomConnector() && !enCoursDeTraitement.hasLeftConnector()) tmp=1;
							while(!p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector()|| enCoursDeTraitement.hasBottomConnector()|| enCoursDeTraitement.hasLeftConnector()) {
								enCoursDeTraitement.turn();
								//System.out.println("yo13");
								tmp++;
							}
						}
					}
						else if(enCoursDeTraitement.getPosX()==grid.getHeight()-1 && enCoursDeTraitement.getPosY()==grid.getWidth()-1) {//coin droit bas
						 if (p_tmp==grid.leftNeighbor(enCoursDeTraitement)) {
							if(p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector()&& !enCoursDeTraitement.hasBottomConnector() && !enCoursDeTraitement.hasRightConnector()) tmp=1;
							while(p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()|| enCoursDeTraitement.hasBottomConnector()|| enCoursDeTraitement.hasRightConnector()) {
								enCoursDeTraitement.turn();
								//System.out.println("yo2");
								tmp++;
							}
							if(!p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()&& !enCoursDeTraitement.hasBottomConnector() && !enCoursDeTraitement.hasRightConnector()) tmp=1;
							while(!p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector() || enCoursDeTraitement.hasBottomConnector()|| enCoursDeTraitement.hasRightConnector()) {
								enCoursDeTraitement.turn();
								//System.out.println("yo12");
								tmp++;
							}
						}
						else if (p_tmp==grid.topNeighbor(enCoursDeTraitement)) {
							if(p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector() && !enCoursDeTraitement.hasRightConnector())tmp=1;
							while(p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector() || enCoursDeTraitement.hasBottomConnector()|| enCoursDeTraitement.hasRightConnector()) {
								enCoursDeTraitement.turn();
								//System.out.println("yo3");
								tmp++;
							}
							if(!p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()&& !enCoursDeTraitement.hasBottomConnector() && !enCoursDeTraitement.hasRightConnector()) tmp=1;
							while(!p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector()|| enCoursDeTraitement.hasBottomConnector()|| enCoursDeTraitement.hasRightConnector()) {
								enCoursDeTraitement.turn();
								//System.out.println("yo13");
								tmp++;
							}
						}
					}

				}
					if(tmp!=0) enCoursDeTraitement.setFixed(true);
				}
				//System.out.println("je suis le meme coin de type oneCOnn"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
			}
			//Je suis sur le bord ligne
			else if(enCoursDeTraitement.getPosY()==0 || enCoursDeTraitement.getPosY()==grid.getWidth()-1)  {
				if (enCoursDeTraitement.getType()==PieceType.BAR) {
				//	System.out.println("je suis une piece bar" + enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
					enCoursDeTraitement.setOrientation(1);
					enCoursDeTraitement.setFixed(true);
					pile.pop();
				}
				else if (enCoursDeTraitement.getType()==PieceType.TTYPE) {
				//	System.out.println("je suis une piece TTYpe"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
					if (enCoursDeTraitement.getPosY()==0) {
						enCoursDeTraitement.setOrientation(2);
						enCoursDeTraitement.setFixed(true);
						pile.pop();
					}
					else {
						enCoursDeTraitement.setOrientation(0);
						enCoursDeTraitement.setFixed(true);
						pile.pop();
					}
				}
				else if(enCoursDeTraitement.getType()==PieceType.LTYPE) {
					//	System.out.println("je suis sur une ligne de type l "+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
						if(grid.listOfFixedNeighbours(enCoursDeTraitement)!=null) {
					//		System.out.println("J'ai des voisins fixes");
							for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
								int tmp=0;
								if(enCoursDeTraitement.getPosY()==0) { //premiere ligne
									if (p_tmp==grid.rightNeighbor(enCoursDeTraitement)) {
									//	System.out.println("J'ai des voisins fixes 1");
										if(p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector() && !enCoursDeTraitement.hasTopConnector())tmp=1;
											while((p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()) || enCoursDeTraitement.hasTopConnector()) {
												enCoursDeTraitement.turn();
												tmp++;
											}
											if(!p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector() && !enCoursDeTraitement.hasTopConnector())tmp=1;
											while((!p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector()) || enCoursDeTraitement.hasTopConnector()) {
												enCoursDeTraitement.turn();
												tmp++;
											}
									}
									else if (p_tmp==grid.leftNeighbor(enCoursDeTraitement)) {
										//System.out.println("J'ai des voisins fixes 2");
										if(p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector() && !enCoursDeTraitement.hasTopConnector())tmp=1;
										while((p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()) || enCoursDeTraitement.hasTopConnector()) {
											enCoursDeTraitement.turn();
											tmp++;
										}
										if(!p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector() && !enCoursDeTraitement.hasTopConnector())tmp=1;
										while((!p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector()) || enCoursDeTraitement.hasTopConnector()) {
											enCoursDeTraitement.turn();
											tmp++;
										}
									}
								}
								else {
									if (p_tmp==grid.rightNeighbor(enCoursDeTraitement)) {
									//	System.out.println("J'ai des voisins fixes 1");
										if(p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector() && !enCoursDeTraitement.hasBottomConnector())tmp=1;
										while((p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()) || enCoursDeTraitement.hasBottomConnector()) {
											enCoursDeTraitement.turn();
											tmp++;
										}
										if(!p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector() && !enCoursDeTraitement.hasBottomConnector())tmp=1;
										while((!p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector()) || enCoursDeTraitement.hasBottomConnector()) {
											enCoursDeTraitement.turn();
											tmp++;
										}
									}
									else if (p_tmp==grid.leftNeighbor(enCoursDeTraitement)) {
									//	System.out.println("J'ai des voisins fixes 2");
										if(p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector() && !enCoursDeTraitement.hasBottomConnector())tmp=1;
										while((p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()) || enCoursDeTraitement.hasBottomConnector()) {
											enCoursDeTraitement.turn();
											tmp++;
										}
										if(!p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector() && !enCoursDeTraitement.hasBottomConnector())tmp=1;
										while((!p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector()) || enCoursDeTraitement.hasBottomConnector()) {
											enCoursDeTraitement.turn();
											tmp++;
										}
									}
								}
								if(tmp!=0) enCoursDeTraitement.setFixed(true);
							}
					}
				}
						else if(enCoursDeTraitement.getType()==PieceType.ONECONN) {
						//	System.out.println("je suis sur une ligne de type oneconn "+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
							int tmpp=0;
							if(grid.listOfFixedNeighbours(enCoursDeTraitement).size()==3) {
								int north=0;
								int south=0;
								int east=0;
								int west=0;
								for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
									if(p_tmp == grid.leftNeighbor(enCoursDeTraitement)) {
										 west++;
										if(p_tmp.hasRightConnector()) west++;
									}
									if(p_tmp == grid.topNeighbor(enCoursDeTraitement)) {
										north++;
										if(p_tmp.hasBottomConnector()) north++;
									}
									if(p_tmp == grid.bottomNeighbor(enCoursDeTraitement)) {
										 south++;
										if(p_tmp.hasTopConnector()) south++;
									}
									if(p_tmp == grid.rightNeighbor(enCoursDeTraitement)) {
										 east++;
										if(p_tmp.hasLeftConnector()) east++;
									}
								}
								if(west==1 && north==1 && east==1) while(!enCoursDeTraitement.hasBottomConnector()) {enCoursDeTraitement.turn(); tmpp++;}
								if(south==1 && north==1 && east==1) while(!enCoursDeTraitement.hasLeftConnector()) {enCoursDeTraitement.turn();tmpp++; }
								if(west==1 && south==1 && east==1) while(!enCoursDeTraitement.hasTopConnector()) {enCoursDeTraitement.turn(); tmpp++; }
								if(west==1 && north==1 && south==1) while(!enCoursDeTraitement.hasRightConnector()) {enCoursDeTraitement.turn(); tmpp++;}
								}
							if(tmpp!=0) { enCoursDeTraitement.setFixed(true); break;}
							if(grid.listOfFixedNeighbours(enCoursDeTraitement)!=null && !enCoursDeTraitement.isFixed()) {
								//System.out.println("J'ai des voisins fixes");
								for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
									int tmp=0;
									if(enCoursDeTraitement.getPosY()==0) { //premiere ligne
										if (p_tmp==grid.rightNeighbor(enCoursDeTraitement)) {
										//	System.out.println("J'ai des voisins fixes 1");
											if(p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector() && !enCoursDeTraitement.hasTopConnector())tmp=1;
												while((p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()) || enCoursDeTraitement.hasTopConnector()) {
													enCoursDeTraitement.turn();
													tmp++;
												}
												/*if(!p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector() && enCoursDeTraitement.hasTopConnector())tmp=1;
												while(!p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector() && enCoursDeTraitement.hasTopConnector()) {
													enCoursDeTraitement.turn();
													tmp++;
												}*/
										}
										else if (p_tmp==grid.leftNeighbor(enCoursDeTraitement)) {
											//System.out.println("J'ai des voisins fixes 2");
											if(p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector() && !enCoursDeTraitement.hasTopConnector())tmp=1;
											while((p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()) || enCoursDeTraitement.hasTopConnector()) {
												enCoursDeTraitement.turn();
												tmp++;
											}
											/*
											if(!p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector() && !enCoursDeTraitement.hasTopConnector())tmp=1;
											while(!p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector() && enCoursDeTraitement.hasTopConnector()) {
												enCoursDeTraitement.turn();
												tmp++;
											}*/
										}
										else {
										//	System.out.println("J'ai des voisins fixes 2");
											if(p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector())tmp=1;
											while((p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()) || enCoursDeTraitement.hasTopConnector()) {
												enCoursDeTraitement.turn();
												tmp++;
											}/*
											if(!p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector())tmp=1;
											while(!p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector() && enCoursDeTraitement.hasTopConnector()) {
												enCoursDeTraitement.turn();
												tmp++;
											}*/
										}
										//if(tmp!=0) enCoursDeTraitement.setFixed(true);
										//if (grid.listOfFixedNeighbours(enCoursDeTraitement).size()==2 && !enCoursDeTraitement.isFixed()) while(!enCoursDeTraitement.hasBottomConnector()) {enCoursDeTraitement.turn();tmp++;}

									}
									else { //derniere ligne
										if (p_tmp==grid.rightNeighbor(enCoursDeTraitement)) {
										//	System.out.println("J'ai des voisins fixes 1");
											if(p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector() && !enCoursDeTraitement.hasBottomConnector())tmp=1;
											while((p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()) || enCoursDeTraitement.hasBottomConnector()) {
												enCoursDeTraitement.turn();
												tmp++;
											}/*
											if(!p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector() && !enCoursDeTraitement.hasBottomConnector())tmp=1;
											while(!p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector() && enCoursDeTraitement.hasBottomConnector()) {
												enCoursDeTraitement.turn();
												tmp++;
											}*/
										}
										else if (p_tmp==grid.leftNeighbor(enCoursDeTraitement)) {
										//	System.out.println("J'ai des voisins fixes 2");
											if(p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector() && !enCoursDeTraitement.hasBottomConnector())tmp=1;
											while((p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()) || enCoursDeTraitement.hasBottomConnector()) {
												enCoursDeTraitement.turn();
												tmp++;
											}/*
											if(!p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector() && !enCoursDeTraitement.hasBottomConnector())tmp=1;
											while(!p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector() && enCoursDeTraitement.hasBottomConnector()) {
												enCoursDeTraitement.turn();
												tmp++;
											}*/
										}
										else {
										//	System.out.println("J'ai des voisins fixes 2");
											if(p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector())tmp=1;
											while((p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()) || enCoursDeTraitement.hasBottomConnector()) {
												enCoursDeTraitement.turn();
												tmp++;
											}/*
											if(!p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector())tmp=1;
											while(!p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector() && enCoursDeTraitement.hasBottomConnector()) {
												enCoursDeTraitement.turn();
												tmp++;
											}*/
										}
										//if(tmp!=0) enCoursDeTraitement.setFixed(true);
										//if (grid.listOfFixedNeighbours(enCoursDeTraitement).size()==2 && !enCoursDeTraitement.isFixed()) while(!enCoursDeTraitement.hasBottomConnector()) {enCoursDeTraitement.turn();tmp++;}

									}
									if(tmp!=0) enCoursDeTraitement.setFixed(true);
								}
						}
				//	System.out.println("je suis sur une ligne de type l nouveau "+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
				}

			//		System.out.println("je suis la meme piece bar ou type 1"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
				}
				//Je suis sur le bord colonne
				else if(enCoursDeTraitement.getPosX()==0 || enCoursDeTraitement.getPosX()==grid.getHeight()-1) {
					if (enCoursDeTraitement.getType()==PieceType.BAR) {
				//		System.out.println("je suis une piece bar"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
						enCoursDeTraitement.setOrientation(0);
						enCoursDeTraitement.setFixed(true);
						pile.pop();
					}
					else if (enCoursDeTraitement.getType()==PieceType.TTYPE) {
				//		System.out.println("je suis une piece TTYpe"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
						if (enCoursDeTraitement.getPosX()==0) {
							enCoursDeTraitement.setOrientation(1);
							enCoursDeTraitement.setFixed(true);
							pile.pop();
						}
						else {
							enCoursDeTraitement.setOrientation(3);
							enCoursDeTraitement.setFixed(true);
							pile.pop();
						}
					}
					else if(enCoursDeTraitement.getType()==PieceType.LTYPE) {
					//	System.out.println("je suis une piece de type l sur une colonne"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
						if(grid.listOfFixedNeighbours(enCoursDeTraitement)!=null) {
						//	System.out.println("je rentre dans boucle");
							for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
								int tmp=0;
								if(enCoursDeTraitement.getPosX()==0) {//premiere colonne
								if (p_tmp==grid.topNeighbor(enCoursDeTraitement)) {
								//	System.out.println("je rentre dans boucle 2");
									if(p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasLeftConnector())tmp=1;
									while((p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()) ||enCoursDeTraitement.hasLeftConnector()) {
										enCoursDeTraitement.turn();
										tmp++;
									}
									if(!p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector() &&!enCoursDeTraitement.hasLeftConnector())tmp=1;
									while((!p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector()) ||enCoursDeTraitement.hasLeftConnector()){
										enCoursDeTraitement.turn();
										tmp++;
									}
								}
								else if (p_tmp==grid.bottomNeighbor(enCoursDeTraitement)) {
								//	System.out.println("je rentre dans boucle 3");
									if(p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector()&&!enCoursDeTraitement.hasLeftConnector())tmp=1;
									while((p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()) ||enCoursDeTraitement.hasLeftConnector()) {
										enCoursDeTraitement.turn();
										tmp++;
									}
									if(!p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()&&!enCoursDeTraitement.hasLeftConnector())tmp=1;
									while((!p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector()) ||enCoursDeTraitement.hasLeftConnector()) {
										enCoursDeTraitement.turn();
										tmp++;
									}
								}
								}
								if(enCoursDeTraitement.getPosX()==grid.getHeight()-1) {//derniere colonne
									if (p_tmp==grid.topNeighbor(enCoursDeTraitement)) {
									//	System.out.println("je rentre dans boucle 2");
										if(p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector()&&!enCoursDeTraitement.hasRightConnector())tmp=1;
										while((p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()) ||enCoursDeTraitement.hasRightConnector()) {
											enCoursDeTraitement.turn();
											tmp++;
										}
										if(!p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()&&!enCoursDeTraitement.hasRightConnector())tmp=1;
										while((!p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector()) || enCoursDeTraitement.hasRightConnector()) {
											enCoursDeTraitement.turn();
											tmp++;
										}
									}
									else if (p_tmp==grid.bottomNeighbor(enCoursDeTraitement)) {
									//	System.out.println("je rentre dans boucle 3");
										if(p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector()&&!enCoursDeTraitement.hasRightConnector())tmp=1;
										while((p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()) ||enCoursDeTraitement.hasRightConnector()) {
											enCoursDeTraitement.turn();
											tmp++;
										}
										if(!p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()&&!enCoursDeTraitement.hasRightConnector())tmp=1;
										while((!p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector()) ||enCoursDeTraitement.hasRightConnector()) {
											enCoursDeTraitement.turn();
											tmp++;
										}
									}
									}
								if(tmp!=0) enCoursDeTraitement.setFixed(true);
							}

						//	System.out.println("je suis le meme piece de type l sur une colonne"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
						}

					}
					else if(enCoursDeTraitement.getType()==PieceType.ONECONN) {
						//System.out.println("je suis un bord colonne de type OneCONN"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
						/*int tmpp=0;
						if(grid.listOfFixedNeighbours(enCoursDeTraitement).size()==3) {
							int north=0;
							int south=0;
							int east=0;
							int west=0;
							for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
								if(p_tmp == grid.leftNeighbor(enCoursDeTraitement)) {
									 west++;
									if(p_tmp.hasRightConnector()) west++;
								}
								if(p_tmp == grid.topNeighbor(enCoursDeTraitement)) {
									north++;
									if(p_tmp.hasBottomConnector()) north++;
								}
								if(p_tmp == grid.bottomNeighbor(enCoursDeTraitement)) {
									 south++;
									if(p_tmp.hasTopConnector()) south++;
								}
								if(p_tmp == grid.rightNeighbor(enCoursDeTraitement)) {
									 east++;
									if(p_tmp.hasLeftConnector()) east++;
								}
							}
							if(west==1 && north==1 && east==1) while(!enCoursDeTraitement.hasBottomConnector()) {enCoursDeTraitement.turn(); tmpp++;}
							if(south==1 && north==1 && east==1) while(!enCoursDeTraitement.hasLeftConnector()) {enCoursDeTraitement.turn();tmpp++; }
							if(west==1 && south==1 && east==1) while(!enCoursDeTraitement.hasTopConnector()) {enCoursDeTraitement.turn(); tmpp++; }
							if(west==1 && north==1 && south==1) while(!enCoursDeTraitement.hasRightConnector()) {enCoursDeTraitement.turn(); tmpp++;}
							}
						if(tmpp!=0) { enCoursDeTraitement.setFixed(true); break;}*/
						if ( !enCoursDeTraitement.isFixed()) {
							for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
								int tmp=0;
								if(enCoursDeTraitement.getPosX()==0) {//premiere colonne
								if (p_tmp==grid.rightNeighbor(enCoursDeTraitement)) {
									if(p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()) tmp++;
									while((p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()) || enCoursDeTraitement.hasLeftConnector()) {
										enCoursDeTraitement.turn();
										tmp++;
									}
									/*//if(!p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()) tmp++;
									while((!p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector()) || enCoursDeTraitement.hasLeftConnector()){
										enCoursDeTraitement.turn();
										//tmp++;
									}*/
								}
								else if (p_tmp==grid.topNeighbor(enCoursDeTraitement)) {
									if (p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector() &&!enCoursDeTraitement.hasLeftConnector())tmp++;
									while((p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()) ||enCoursDeTraitement.hasLeftConnector()) {
										enCoursDeTraitement.turn();
										tmp++;
									}
									/*
									while(!p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector()) {
										enCoursDeTraitement.turn();
									}*/
								}
								else {
									if (p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector() &&!enCoursDeTraitement.hasLeftConnector()) tmp++;
									while((p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()) ||enCoursDeTraitement.hasLeftConnector()) {
										enCoursDeTraitement.turn();
										tmp++;
									}/*
									while(!p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector()) {
										enCoursDeTraitement.turn();
									}*/
								}
								//if(tmp!=0) enCoursDeTraitement.setFixed(true);
								}
								else if(enCoursDeTraitement.getPosX()==grid.getWidth()-1) {//derniere colonne
									 if (p_tmp==grid.leftNeighbor(enCoursDeTraitement)) {
										if (p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector())tmp++;
										while((p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()) || enCoursDeTraitement.hasRightConnector()) {
											enCoursDeTraitement.turn();
											tmp++;
										}/*
										while(!p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector()) {
											enCoursDeTraitement.turn();
										}*/
									}
									else if (p_tmp==grid.topNeighbor(enCoursDeTraitement)) {
										if (p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector() && !enCoursDeTraitement.hasRightConnector())tmp++;
										while((p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()) || enCoursDeTraitement.hasRightConnector()) {
											enCoursDeTraitement.turn();
											tmp++;
										}/*
										while(!p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector()) {
											enCoursDeTraitement.turn();
										}*/
									}
									else {
										if (p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector() && !enCoursDeTraitement.hasRightConnector()) tmp++;
										while((p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()) || enCoursDeTraitement.hasRightConnector()) {
											enCoursDeTraitement.turn();
											tmp++;
										}/*
										while(!p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector()) {
											enCoursDeTraitement.turn();
										}*/
										}
								}
								if(tmp!=0) enCoursDeTraitement.setFixed(true);
							}
						}
						//System.out.println("je suis le meme bord ligne de type oneCOnn"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
					}
					//System.out.println("je suis la meme piece bar ou type 2"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
				}
				//je suis au milieu
				else if (grid.hasAtLeast1FixedNeighbour(enCoursDeTraitement) && !enCoursDeTraitement.isFixed()) {
					//System.out.println("Jai au moins un voisin de fix�");
					if(enCoursDeTraitement.getType()==PieceType.BAR ) {
					//	System.out.println("je suis une piece Bar "+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
						for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
							int tmp=0;
						//	System.out.println("Je rentre bien dans la boucle 1");
							if (p_tmp == grid.leftNeighbor(enCoursDeTraitement)){
							//	System.out.println("Je rentre bien dans la boucle 2");
								if(!p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()) tmp=1;
								while(!p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector()) {
									enCoursDeTraitement.turn();
									//System.out.println("1");
									tmp=1;
								}
								if(p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector()) tmp=1;
								while(p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()) {
									enCoursDeTraitement.turn();
									//System.out.println("1");
									tmp=1;
								}
							}
							else if (p_tmp == grid.rightNeighbor(enCoursDeTraitement)){
							//	System.out.println("Je rentre bien dans la boucle 3");
								if(!p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()) tmp=1;
								while(!p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector()) {
									enCoursDeTraitement.turn();
								//	System.out.println("2");
									tmp=1;
								}
								if(p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector()) tmp=1;
								while(p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()) {
									enCoursDeTraitement.turn();
									//System.out.println("2");
									tmp=1;
								}
							}
							else if (p_tmp == grid.bottomNeighbor(enCoursDeTraitement)){
							//	System.out.println("Je rentre bien dans la boucle 4");
								if(!p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()) tmp=1;
								while(!p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector()) {
									enCoursDeTraitement.turn();
								//	System.out.println("3");
									tmp=1;
								}
								if(p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector()) tmp=1;
								while(p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()) {
									enCoursDeTraitement.turn();
								//	System.out.println("3");
									tmp=1;
								}
							}
							else {
							//	System.out.println("Je rentre bien dans la boucle 5");
								if(!p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()) tmp=1;
								while(!p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector()) {
								//	System.out.println("dfbdfbkdf");
									enCoursDeTraitement.turn();
									//System.out.println("4");
									tmp=1;
								}
								if(p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector()) tmp=1;
								while(p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()) {
								//	System.out.println("dfbdfbkdf");
									enCoursDeTraitement.turn();
								//	System.out.println("4");
									tmp=1;
								}
							}
							if(tmp!=0) enCoursDeTraitement.setFixed(true);
						}
						///System.out.println("je suis la meme piece bar ou t"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
					}
					else if(enCoursDeTraitement.getType()==PieceType.TTYPE ) { //Piece type TTYPE dans milieu
						if(grid.listOfFixedNeighbours(enCoursDeTraitement).size()==3) {
							int tmpp=0;
							int north=0;
							int south=0;
							int east=0;
							int west=0;
							for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
								if(p_tmp == grid.leftNeighbor(enCoursDeTraitement)) {
									 west++;
									if(p_tmp.hasRightConnector()) west++;
								}
								if(p_tmp == grid.topNeighbor(enCoursDeTraitement)) {
									north++;
									if(p_tmp.hasBottomConnector()) north++;
								}
								if(p_tmp == grid.bottomNeighbor(enCoursDeTraitement)) {
									 south++;
									if(p_tmp.hasTopConnector()) south++;
								}
								if(p_tmp == grid.rightNeighbor(enCoursDeTraitement)) {
									 east++;
									if(p_tmp.hasLeftConnector()) east++;
								}
							}
							if(west==2 && north==2 && east==2) while(enCoursDeTraitement.hasBottomConnector()) {enCoursDeTraitement.turn(); tmpp++;}
							if(south==2 && north==2 && east==2) while(enCoursDeTraitement.hasLeftConnector()) {enCoursDeTraitement.turn();tmpp++; }
							if(west==2 && south==2 && east==2) while(enCoursDeTraitement.hasTopConnector()) {enCoursDeTraitement.turn(); tmpp++; }
							if(west==2 && north==2 && south==2) while(enCoursDeTraitement.hasRightConnector()) {enCoursDeTraitement.turn(); tmpp++;}
							if(tmpp!=0) enCoursDeTraitement.setFixed(true);
						}
						if(enCoursDeTraitement.isFixed()) break;
					//	System.out.println("je suis une piece Ttype "+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
						for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
							int tmp=0;
						//	System.out.println("Je rentre bien dans la boucle 1");
							if (p_tmp == grid.leftNeighbor(enCoursDeTraitement)){
							//	System.out.println("Je rentre bien dans la boucle 2");
								if(!p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()) tmp=1;
								while(!p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector()) {
									enCoursDeTraitement.turn();
								//	System.out.println("1");
									tmp=1;
								}
							}
							else if (p_tmp == grid.rightNeighbor(enCoursDeTraitement)){
								//System.out.println("Je rentre bien dans la boucle 3");
								if(!p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()) tmp=1;
								while(!p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector()) {
									enCoursDeTraitement.turn();
								//	System.out.println("2");
									tmp=1;
								}
							}
							else if (p_tmp == grid.bottomNeighbor(enCoursDeTraitement)){
							//	System.out.println("Je rentre bien dans la boucle 4");
								if(!p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()) tmp=1;
								while(!p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector()) {
									enCoursDeTraitement.turn();
								//	System.out.println("3");
									tmp=1;
								}
							}
							else {
								//System.out.println("Je rentre bien dans la boucle 5");
								if(!p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()) tmp=1;
								while(!p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector()) {
								//	System.out.println("dfbdfbkdf");
									enCoursDeTraitement.turn();
								//	System.out.println("4");
									tmp=1;
								}
							}
							if(tmp!=0) enCoursDeTraitement.setFixed(true);
						}
					}
					else if(enCoursDeTraitement.getType()==PieceType.ONECONN) {
					//	System.out.println("je suis une piece one conn"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
						int tmpp=0;
						if(grid.listOfFixedNeighbours(enCoursDeTraitement).size()==3) {
							int north=0;
							int south=0;
							int east=0;
							int west=0;
							for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
								if(p_tmp == grid.leftNeighbor(enCoursDeTraitement)) {
									 west++;
									if(p_tmp.hasRightConnector()) west++;
								}
								if(p_tmp == grid.topNeighbor(enCoursDeTraitement)) {
									north++;
									if(p_tmp.hasBottomConnector()) north++;
								}
								if(p_tmp == grid.bottomNeighbor(enCoursDeTraitement)) {
									 south++;
									if(p_tmp.hasTopConnector()) south++;
								}
								if(p_tmp == grid.rightNeighbor(enCoursDeTraitement)) {
									 east++;
									if(p_tmp.hasLeftConnector()) east++;
								}
							}
							if(west==1 && north==1 && east==1) while(!enCoursDeTraitement.hasBottomConnector()) {enCoursDeTraitement.turn(); tmpp++;}
							if(south==1 && north==1 && east==1) while(!enCoursDeTraitement.hasLeftConnector()) {enCoursDeTraitement.turn();tmpp++; }
							if(west==1 && south==1 && east==1) while(!enCoursDeTraitement.hasTopConnector()) {enCoursDeTraitement.turn(); tmpp++; }
							if(west==1 && north==1 && south==1) while(!enCoursDeTraitement.hasRightConnector()) {enCoursDeTraitement.turn(); tmpp++;}
							}
						if(tmpp!=0) { enCoursDeTraitement.setFixed(true); break;}
						for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
					//		System.out.println("ici");
							int tmp=0;
							if (p_tmp == grid.leftNeighbor(enCoursDeTraitement)){
						//		System.out.println("ici2");
								if(p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector())tmp++;
								while(p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()) {
									enCoursDeTraitement.turn();
									tmp=1;
								}
							}
							else if (p_tmp == grid.rightNeighbor(enCoursDeTraitement)){
							//	System.out.println("ici3");
								if (p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()) tmp++;
								while(p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()) {
									enCoursDeTraitement.turn();
									tmp=1;
								}
							}
							else if (p_tmp == grid.bottomNeighbor(enCoursDeTraitement)){
							//	System.out.println("ici4");
								if (p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()) tmp++;
								while(p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()) {
									enCoursDeTraitement.turn();
									tmp=1;
								}
							}
							else {
							//	System.out.println("ici5");
								if (p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()) tmp++;
								while(p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()) {
									enCoursDeTraitement.turn();
									tmp=1;
								}
							}
							if (tmp!=0) enCoursDeTraitement.setFixed(true);
						}
					}
					else if(enCoursDeTraitement.getType()==PieceType.LTYPE) {
					//	System.out.println("je suis une piece LTYPE"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());
						int tmpp=0;
						if(grid.listOfFixedNeighbours(enCoursDeTraitement).size()==2) {
							int north=0;
							int south=0;
							int east=0;
							int west=0;
							for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
								if(p_tmp == grid.leftNeighbor(enCoursDeTraitement)) {
									 west++;
									if(p_tmp.hasRightConnector()) west++;
								}
								if(p_tmp == grid.topNeighbor(enCoursDeTraitement)) {
									north++;
									if(p_tmp.hasBottomConnector()) north++;
								}
								if(p_tmp == grid.bottomNeighbor(enCoursDeTraitement)) {
									 south++;
									if(p_tmp.hasTopConnector()) south++;
								}
								if(p_tmp == grid.rightNeighbor(enCoursDeTraitement)) {
									 east++;
									if(p_tmp.hasLeftConnector()) east++;
								}
							}
							if((north==2 && east==2) || (east==2 && south==1)|| (west==1 && north==2)) while(!enCoursDeTraitement.hasRightConnector() && !enCoursDeTraitement.hasTopConnector()) {enCoursDeTraitement.turn(); tmpp++; }
							else if((south==2 && east==2)|| (east==2 && north==1)|| (south==2 && west==1)) while(!enCoursDeTraitement.hasRightConnector() && !enCoursDeTraitement.hasBottomConnector()) {enCoursDeTraitement.turn(); tmpp++;}
							else if((west==2 && south==2)|| (east==1 && south==2) || (west==2 && north==1)) while(!enCoursDeTraitement.hasBottomConnector() && !enCoursDeTraitement.hasLeftConnector()) {enCoursDeTraitement.turn();tmpp++; }
							else if((west==2 && north==2)|| (west==2 && south==1)|| (east==1 && north==2)) while(!enCoursDeTraitement.hasLeftConnector() && !enCoursDeTraitement.hasTopConnector()) {enCoursDeTraitement.turn();tmpp++; }
						}
						if(tmpp!=0) { enCoursDeTraitement.setFixed(true); break;}
						if(grid.listOfFixedNeighbours(enCoursDeTraitement).size()==3 && !enCoursDeTraitement.isFixed()) {
							int north=0;
							int south=0;
							int east=0;
							int west=0;
							for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
								if(p_tmp == grid.leftNeighbor(enCoursDeTraitement)) {
									 west++;
									if(p_tmp.hasRightConnector()) west++;
								}
								if(p_tmp == grid.topNeighbor(enCoursDeTraitement)) {
									north++;
									if(p_tmp.hasBottomConnector()) north++;
								}
								if(p_tmp == grid.bottomNeighbor(enCoursDeTraitement)) {
									 south++;
									if(p_tmp.hasTopConnector()) south++;
								}
								if(p_tmp == grid.rightNeighbor(enCoursDeTraitement)) {
									 east++;
									if(p_tmp.hasLeftConnector()) east++;
								}
							}
							if((north==2 && east==2) || (east==2 && south==1)|| (west==1 && north==2)) while(!enCoursDeTraitement.hasRightConnector() && !enCoursDeTraitement.hasTopConnector()) {enCoursDeTraitement.turn(); tmpp++; }
							else if((south==2 && east==2)|| (east==2 && north==1)|| (south==2 && west==1)) while(!enCoursDeTraitement.hasRightConnector() && !enCoursDeTraitement.hasBottomConnector()) {enCoursDeTraitement.turn(); tmpp++;}
							else if((west==2 && south==2)|| (east==1 && south==2) || (west==2 && north==1)) while(!enCoursDeTraitement.hasBottomConnector() && !enCoursDeTraitement.hasLeftConnector()) {enCoursDeTraitement.turn();tmpp++; }
							else if((west==2 && north==2)|| (west==2 && south==1)|| (east==1 && north==2)) while(!enCoursDeTraitement.hasLeftConnector() && !enCoursDeTraitement.hasTopConnector()) {enCoursDeTraitement.turn();tmpp++; }
						}
						if(tmpp!=0) { enCoursDeTraitement.setFixed(true); break;}
						//if(grid.listOfFixedNeighbours(enCoursDeTraitement).size()==3) {
						/*for(Piece p_tmp:grid.listOfFixedNeighbours(enCoursDeTraitement)) {
							System.out.println("ici");
							int tmp=0;
							if (p_tmp == grid.leftNeighbor(enCoursDeTraitement)){
								System.out.println("ici2");
								if(p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector())tmp++;
								while(p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector()) {
									enCoursDeTraitement.turn();
									tmp=1;
								}
								if(!p_tmp.hasRightConnector() && !enCoursDeTraitement.hasLeftConnector())tmp++;
								while(!p_tmp.hasRightConnector() && enCoursDeTraitement.hasLeftConnector()) {
									enCoursDeTraitement.turn();
									tmp=1;
								}
							}
							else if (p_tmp == grid.rightNeighbor(enCoursDeTraitement)){
								System.out.println("ici3");
								if(p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector())tmp++;
								while(p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector()) {
									enCoursDeTraitement.turn();
									tmp=1;
								}
								if(!p_tmp.hasLeftConnector() && !enCoursDeTraitement.hasRightConnector())tmp++;
								while(!p_tmp.hasLeftConnector() && enCoursDeTraitement.hasRightConnector()) {
									enCoursDeTraitement.turn();
									tmp=1;
								}
							}
							else if (p_tmp == grid.bottomNeighbor(enCoursDeTraitement)){
								System.out.println("ici4");
								if (p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector())tmp++;
								while(p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector()) {
									enCoursDeTraitement.turn();
									tmp=1;
								}
								if (!p_tmp.hasTopConnector() && !enCoursDeTraitement.hasBottomConnector())tmp++;
										while(!p_tmp.hasTopConnector() && enCoursDeTraitement.hasBottomConnector()) {
											enCoursDeTraitement.turn();
											tmp=1;
										}
							}
							else {
								System.out.println("ici5");
								if (p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector())tmp++;
								while(p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector()) {
									enCoursDeTraitement.turn();
									tmp=1;
								}
								if (!p_tmp.hasBottomConnector() && !enCoursDeTraitement.hasTopConnector())tmp++;
								while(!p_tmp.hasBottomConnector() && enCoursDeTraitement.hasTopConnector()) {
									enCoursDeTraitement.turn();
									tmp=1;
								}
							}
							if (tmp!=0) enCoursDeTraitement.setFixed(true);
						}*/
					//	System.out.println("je suis la meme piece LTYPE"+enCoursDeTraitement+" fixee?" +enCoursDeTraitement.isFixed());

					}
				}
			}
		//System.out.println("au final: "+enCoursDeTraitement+" fixe? "+enCoursDeTraitement.isFixed());
		Piece nextPiece = null;
		if(i==grid.getHeight()-1 && j ==grid.getWidth()-1) {
			nextPiece=depart;
				//jeux++;
		}
			//Add next piece to stack
		else if (grid.getNextPiece(enCoursDeTraitement)!=null) {
			nextPiece=grid.getNextPiece(enCoursDeTraitement);
			/*while(nextPiece!=null && nextPiece.isFixed()==true) {
				nextPiece=grid.getNextPiece(nextPiece);
			}*/
			//jeux++;
		}
		if(nextPiece!=null) {pile.push(nextPiece);
		jeux++;}
		if (jeux>300) {return null;}
		}
		return null;
	}

	public static void main(String[] args) {

		// To be implemented

	}
}
