import java.util.ArrayList;
import java.util.List;

import Old.Board;

public class Pawn extends Piece {

	public int direction; 
	public Pawn(int r, int c, Player x) {
		row = r;
		column = c;
		player = x.number;
		if(player==0) {
			name = "A PAWN  ";
			direction = 1;
		}
		else {
			name = "B PAWN  ";
			direction = -1;
		}
	}

	@Override
	public boolean validMove(int u, int v, ChessBoard b) {
		if(row==1 || row==6) {
			if(Math.abs(u-row)<=2 && column==v) {
				return !locateCollisions(u,v,b);
			}
		}
		else if(Math.abs(u-row)==1 && column==v) {
			return !locateCollisions(u,v,b);
		}
		else if(Math.abs(u-row)==1 && Math.abs(v-column)==1) {
			return locateCollisions(u,v,b);
		}
		return false;
	}
	
	@Override
	public boolean locateCollisions(int u, int v, ChessBoard b) {
		String destination = convertToHash(u,v);
		if(player ==0) {
			if(b.piecesB.containsKey(destination)) {
				return true;
			}
		}
		if(player == 1) {
			if(b.piecesA.containsKey(destination)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> getMoves(ChessBoard b) {
		ArrayList<String> moves = new ArrayList<String>();
		int r1 = row + direction; 
		for(int i=-1; i<=1; i++) {
			int col = column + i;
			if(validMove(r1, col, b)) {
				moves.add(convertToHash(r1, col));
			}
		}
		if(validMove(r1+1, column, b)) {
			moves.add(convertToHash(r1, column));
		}
		return moves;
	}

}
