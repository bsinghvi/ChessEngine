import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class King extends Piece {

	public King(int r, int c, Player x) {
		row = r;
		column = c;
		player = x.number;
		if(player ==0) {
			name = "A KING  ";
		}
		else {
			name = "B KING  ";
		}
	}

	/**
	 * The King move is checked by seeing if the row and column changes by only 1 space
	 * @param u - intended row of piece
	 * @param v - intended col of piece
	 * @return true if it's valid movement of the King
	 */
	
	@Override
	public boolean validMove(int u, int v, ChessBoard b) {
		if(Math.abs(u-row)<=1 && Math.abs(v-column) <=1) {
			return !locateCollisions(u,v,b);
		}
		return false;
	}

	@Override
	public boolean locateCollisions(int u, int v, ChessBoard b) {
		String destination = convertToHash(u,v);
		if(player == 0) {
			if(b.piecesA.containsKey(destination)) {
				return isCheck(u,v,b);
			}
		}
		else if(player == 1) {
			if(b.piecesB.containsKey(destination)) {
				return isCheck(u,v,b);
			}
		}
		return false;
	}
	@Override
	public List<String> getMoves(ChessBoard b) {
		System.out.println("sdf");
		ArrayList<String> moves = new ArrayList<String>();
		for(int u=row-1; u<=row+1; u++) {
			for(int v=column-1; v<=column+1; v++) {
				if(u>=0 && u<=7 && v>=0 && v<=7) {
					if(validMove(u,v,b)) {
						moves.add(convertToHash(u,v));
					}
				}
			}
		}
		return moves;
	}
	public boolean isCheck(int u,int v,ChessBoard b) {
		HashMap<String,Piece> pieces;
		if(player==0) {
			pieces = b.piecesB;
		}
		else {
			pieces = b.piecesA;
		}
		for(String s: pieces.keySet()) {
			System.out.println(pieces.get(s).name);
			if(pieces.get(s).canMove(u, v, b)) {
				return true;
			}
		}
		return false;
	}
	public boolean isCheckMate(ChessBoard b) {
		if(getMoves(b).isEmpty()) {
			return true;
		}
		return false;
	}

}
