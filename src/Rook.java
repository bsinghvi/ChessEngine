import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

	public Rook(int r, int c, Player x) {
		row = r;
		column = c;
		player = x.number;
		if(player==0) {
			name = "A ROOK  ";
		}
		else {
			name = "B ROOK  ";
		}
	}

	/**
	 * Checks if the rook is asked to move in a straight non-diagonal line
	 * @param u - intended row of piece
	 * @param v - intended col of piece
	 * @return true if it's valid movement of the rook
	 */

	@Override
	public boolean validMove(int u, int v, ChessBoard b) {
		// Checks if rook is moving columns
		if(row==u && column != v) {
			return !locateCollisions(u, v, b);
		}
		if(row!=u && column == v) {
			return !locateCollisions(u,v,b);
		}
		return false;
	}
	@Override
	public boolean locateCollisions(int u, int v, ChessBoard b) {
		String thisHash = convertToHash(row,column);
		if(u == row) {
			int lowC = column;
			int hiC = v;
			if(v<column) {
				lowC = v;
				hiC = column;
			}
			for(int c=lowC; c<hiC; c++) {
				String destination = convertToHash(row,c);
				if(!thisHash.equals(destination)) {
					if(b.piecesA.containsKey(destination) || b.piecesB.containsKey(destination)) {
						return true;
					}
				}
			}
		}
		else if(v==column) {
			int lowR = row;
			int hiR = u;
			if(u<row) {
				lowR = u;
				hiR = row;
			}
			for(int r=lowR; r<hiR; r++) {
				String destination = convertToHash(r,column);
				if(!thisHash.equals(destination)) {
					if(b.piecesA.containsKey(destination) || b.piecesB.containsKey(destination)) {
						return true;
					}
				}
			}
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
