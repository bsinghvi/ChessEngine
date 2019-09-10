import java.util.ArrayList;
import java.util.List;

import Old.Board;

public class Bishop extends Piece {

	public Bishop(int r, int c, Player x) {
		row = r;
		column = c;
		player = x.number;
		if(player==0) {
			name = "A BISHOP";
		}
		else {
			name = "B BISHOP";
		}
	}

	/**
	 * Bishops move linearly therefore the movement's slope can be checked.
	 * In order to avoid a division by zero error, ...
	 * ...it's necessary to check if the bishop is moved vertically or horizontally first.
	 * @param u - intended row of piece
	 * @param v - intended col of piece
	 * @return true if it's valid movement of the bishop 
	 */
	@Override
	public boolean validMove(int u, int v, ChessBoard b) {
		if(row==u && column != v) {
			return false;
		}
		if(row!=u && column == v) {
			return false;
		}
		if( Math.abs((column-v)/(row-u)) == 1) {
			return true;
		}
		return false;
	}
	@Override
	public boolean locateCollisions(int u, int v, ChessBoard b) {
		int diffC = 1;
		int diffR = 1;
		if(v<column) {
			diffC = -1;
		}
		if(u<row) {
			diffR = -1;
		}
		int checkR = row+diffR;
		int checkC = column+diffC;
		while(checkR!=u && checkC!=v) {
			String destination = convertToHash(checkR,checkC);
			if(b.piecesA.containsKey(destination) || b.piecesB.containsKey(destination) ) {
				return true;
			}
			else {
				checkR += diffR;
				checkC += diffC;
			}
		}
		if(b.piecesA.containsKey(convertToHash(u,v)) && player == 0) {
			return true;
		}
		else if(b.piecesB.containsKey(convertToHash(u,v)) && player == 1) {
			return true;
		}
		return false;
	}
	@Override
	public List<String> getMoves(ChessBoard b) {
		ArrayList<String> moves = new ArrayList<String>();
		for(int x = 0; x<=7; x++) {
			if(x!=row && validMove(x, column,b)) {
				moves.add(convertToHash(x,column));
			}
			if(x!=column && validMove(row, x, b)) {
				moves.add(convertToHash(row,x));
			}
		}
		return moves;
	}
}