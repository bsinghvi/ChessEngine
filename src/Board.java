import java.util.ArrayList;

public class Board {
	String[][] b;
	ArrayList<String> removed;
	
	public Board() {
		 removed = new ArrayList<String>();
		 b = new String[][]{ {"A ROOK  ", "A KNIGHT", "A BISHOP", "A QUEEN ", "A KING  ", "A BISHOP", "A KNIGHT", "A ROOK  "},
							 {"A PAWN  ", "A PAWN  ", "A PAWN  ", "A PAWN  ", "A PAWN  ", "A PAWN  ", "A PAWN  ", "A PAWN  "},
							 {"EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   "},
							 {"EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   "},
							 {"EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   "},
							 {"EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   ", "EMPTY   "},
							 {"B PAWN  ", "B PAWN  ", "B PAWN  ", "B PAWN  ", "B PAWN  ", "B PAWN  ", "B PAWN  ", "B PAWN  "}, 
							 {"B ROOK  ", "B KNIGHT", "B BISHOP", "B QUEEN ", "B KING  ", "B BISHOP", "B KNIGHT", "B ROOK  "} };
	}
	/**
	 * Determine which player the piece belongs to
	 * @param r - row of piece
	 * @param c - col of piece
	 * @return
	 */
	public int parsePlayer(int r, int c) {
		if(b[r][c] == "EMPTY   ") {
			return -1;
		}
		else if(b[r][c].contains("A ")) {
			return 0;
		}
		return 1;
	}
	/**
	 * Return if the moving of piece is valid
	 * @param u - row of piece 1
	 * @param v - col of piece 1
	 * @param r - intended row
	 * @param c - intended col
	 * @return 1 if successful
	 */
	public int changePos(int u, int v, int r, int c) {
		if(b[r][c] != "EMPTY   ") {
		   b[r][c] = b[u][v];
		   b[u][v] = "EMPTY   ";
		   return 1;
		}
		else if(parsePlayer(u,v) != parsePlayer(r,c)) {
			if(b[r][c] != "EMPTY   ") {
				System.out.println(b[r][c] + " was removed");
				removed.add(b[r][c]);
			}
			b[r][c] = b[u][v];
			b[u][v] = "EMPTY   ";
			return 1;
		}
		return 0;
	}
	public boolean checkMate() {
		
		return false;
	}
	public boolean checkSurrounding(int r, int c) {
		int orig = parsePlayer(r,c);
		for(int i=0; i<8; i++) {
			c = (c+i)%8;
			int curr = parsePlayer(r,c);
			if(orig !=-1 && curr != orig) {
				if(b[r][c].contains("ROOK") || b[r][c].contains("QUEEN") ) {
					return true;
				}
			}
		}
		for(int i=0; i<8; i++) {
			r = (r+i)%8;
			int curr = parsePlayer(r,c);
			if(orig !=-1 && curr != orig) {
				if(b[r][c].contains("ROOK") || b[r][c].contains("QUEEN") ) {
					return true;
				}
			}
		}
		for(int i=0; i<8; i++) {
			int r1 = (r+i)%8;
			int r2 = (c+i)%8;
			int curr = parsePlayer(r,c);
			if(orig !=-1 && curr != orig) {
				if(b[r][c].contains("BISHOP") || b[r][c].contains("QUEEN") ) {
					return true;
				}
			}
			r1 = (r+i)%8;
			r2 = (c-i)%8;
			curr = parsePlayer(r,c);
			if(orig !=-1 && curr != orig) {
				if(b[r][c].contains("BISHOP") || b[r][c].contains("QUEEN") ) {
					return true;
				}
			}
			r1 = (r-i)%8;
			r2 = (c+i)%8;
			curr = parsePlayer(r,c);
			if(orig !=-1 && curr != orig) {
				if(b[r][c].contains("BISHOP") || b[r][c].contains("QUEEN") ) {
					return true;
				}
			}
			r1 = (r-i)%8;
			r2 = (c-i)%8;
			curr = parsePlayer(r,c);
			if(orig !=-1 && curr != orig) {
				if(b[r][c].contains("BISHOP") || b[r][c].contains("QUEEN") ) {
					return true;
				}
			}
		}
		if(orig==0) {
			if(r+2 <=7) {
				if(b[r+2][c+1].contains("B PAWN") || b[r+2][c-1].contains("B PAWN")) {
					return true;
				}
			}
		}
		if(orig==1) {
			if(r-2 <=7) {
				if(b[r-2][c+1].contains("A PAWN") || b[r+2][c-1].contains("A PAWN")) {
					return true;
				}
			}
		}
		return false;
	}
	
}
