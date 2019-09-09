import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

	public Knight(int r, int c, Player x) {
		row = r;
		column = c;
		player = x.number;
		if(player==0) {
			name = "A KNIGHT";
		}
		else {
			name = "B KNIGHT";
		}
	}

	/**
	 * Knights are the only pieces that move in non-linear fashion, therefore differently written checks must be used
	 * @param u - intended row of piece
	 * @param v - intended col of piece
	 * @return true if it's valid movement of the knight
	 */
	@Override
	public boolean validMove(int u, int v, ChessBoard b) {
		if(row+2 == u) {
			if(column-1 == v || column+1 == v) {
				return !locateCollisions(u, v, b);
			}
		}
		else if(row-2 == u) {
			if(column-1 == v || column+1 == v) {
				return !locateCollisions(u, v, b);
			}
		}
		else if(row+1 == u) {
			if(column-2 == v || column+2 == v) {
				return !locateCollisions(u, v, b);
			}
		}
		else if(row-1 == u) {
			if(column-2 == v || column+2 == v) {
				return !locateCollisions(u, v, b);
			}
		}
		return false;
	}
	@Override
	public boolean locateCollisions(int u, int v, ChessBoard b) {
		String destination = convertToHash(u,v);
		if(player == 0) {
			if(b.piecesA.containsKey(destination)) {
				return true;
			}
		}
		else if(player == 1) {
			if(b.piecesB.containsKey(destination)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public List<String> getMoves(ChessBoard b) {
		ArrayList<String> moves = new ArrayList<String>();
		for(int r=-2; r<=2; r++) {
			for(int c=-2; c<=2; c++) {
				if(r!=row && c!=column && validMove(r+row, c+column, b)) {
					moves.add(convertToHash(r+row, c+column));
				}
			}
		}
		return moves;
	}

}
