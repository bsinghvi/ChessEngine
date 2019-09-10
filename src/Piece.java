import java.util.List;

public abstract class Piece {
	int row; 
	int column;
	int player;
	String name;
	
	public void setPos(int r, int c) {
		row = r;
		column = c;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPlayer() {
		return player;
	}
	
	public int[] getPos() {
		int[] pos = new int[2];
		pos[0] = row; 
		pos[1] = column;
		return pos;
	}
	
	public abstract List<String> getMoves(ChessBoard b);
	
	public boolean canMove(int u, int v, ChessBoard b) {
		List<String> moves = getMoves(b);
		return moves.contains(convertToHash(u,v));
	}

	public boolean validMove(int u, int v, ChessBoard b) {
		return false;
	}
	
	public boolean locateCollisions(int u, int v, ChessBoard b) {
		return false;
	}
	
	/**
	 * Converts the specific row and column values to the "hash" of a piece
	 * @param int row and column - ex: 1,0
	 * @return String hash - ex: for 1,0, it would return "A1", as 0th column is represented by A and 1st row is represented by 1
	 */
	public String convertToHash(int row, int column) {
		int r = row;
		char c = (char)(column + 65);
		String hash = c + "" + r;
		return hash;
	}
	
}
