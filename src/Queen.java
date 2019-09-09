import java.util.List;

public class Queen extends Piece {

	public Queen(int r, int c, Player x) {
		row = r;
		column = c;
		player = x.number;
		if(player==0) {
			name = "A QUEEN ";
		}
		else {
			name = "B QUEEN ";
		}

	}

	/**
	 * The queen move is checked by employing the same checks as a rook and bishop 
	 * @param u - intended row of piece
	 * @param v - intended col of piece
	 * @return true if it's valid movement of the queen
	 */

	@Override
	public boolean validMove(int u, int v, ChessBoard b) {
		// Checks if Queen is moving columns
		if(row==u && column != v) {
			return !locateCollisions(u,v,b);
		}
		// Check if Queen is moving rows
		if(row!=u && column == v) {
			return !locateCollisions(u,v,b);
		}
		// Check if Queen is moving diagonally
		if( Math.abs((column-v)/(row-u)) == 1) {
			return !locateCollisions(u,v,b);
		}
		return false;
	}
	@Override
	public boolean locateCollisions(int u, int v, ChessBoard b) {
		String thisHash = convertToHash(row,column);
		if(u==row || v==column) {
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
		}
		else {
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
		}
		if(b.piecesA.containsKey(convertToHash(u,v)) && player==0) {
			return true;
		}
		if(b.piecesB.containsKey(convertToHash(u,v)) && player==1) {
			return true;
		}
		return false;
	}
	@Override
	public List<String> getMoves(ChessBoard b) {
		// TODO Auto-generated method stub
		return null;
	}

}
