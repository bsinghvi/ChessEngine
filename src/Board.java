import java.util.ArrayList;

public class Board {
	String[][] b;
	ArrayList<String> removed;
	int king1r;
	int king1c;
	int king2r;
	int king2c;
	
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
		king1r = 0;
		king1c = 4;
		king2r = 7;
		king2c = 4;
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
		   if(b[u][v]=="A KING  ") {
			   king1r = u;
			   king1c = v;
		   }
		   else if(b[u][v]=="B KING  ") {
			   king2r = u;
			   king2c = v;
		   }
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
	public boolean checkMate(int kingR, int kingC) {
		if(checkSurrounding(kingR,kingC)==true) {
			int orig = parsePlayer(kingR,kingC);
			int sum = 8;
			for(int i=-1; i<=1; i++) {
				for(int j=-1; j<=1; j++) {
					int checkR = kingR + i;
					int checkC = kingC + j;
					if(checkR>7 || checkR<0 || checkC>7 || checkC<0) {
						sum-=1;
					}
					else {
						int piece = parsePlayer(checkR,checkC);
						if(piece == orig) {
							sum -=1;
						}
					}
				}
			}
			for(int i = kingC-1; i<=kingC+1; i+=2) {
				if(i>=0 && i<8) {
					for(int j=kingC-1; j<=kingC+1; j++) {
						if(j>=0 && j<=7) {
							if(j!=kingC || i==kingC) {
								if(checkSurrounding(j, i)) {
									sum-=1;
								}
							}
						}
					}
				}
			}
			System.out.println(sum);
			if(sum==0) {
				return true;
			}
		}
		return false;
	}
	// Method checks if there is an opposing piece that could remove it
	// Return true if there is one
	public boolean checkSurrounding(int r, int c) {
		// Parse which player's piece it is
		int orig = parsePlayer(r,c);
		
		// Following for loops run check if there are any opposing pieces, 
		// and if so can it remove the piece in question
		for(int i=0; i<8; i++) {
			int c1 = (c+i)%8;
			int curr = parsePlayer(r,c1);
			if(orig !=-1 && curr != orig) {
				if(b[r][c1].contains("ROOK") || b[r][c1].contains("QUEEN") ) {
					return true;
				}
			}
		}
		for(int i=0; i<8; i++) {
			int r1 = (r+i)%8;
			int curr = parsePlayer(r1,c);
			if(orig !=-1 && curr != orig) {
				if(b[r1][c].contains("ROOK") || b[r1][c].contains("QUEEN") ) {
					return true;
				}
			}
		}
		for(int i=0; i<8; i++) {
			int r1 = (r+i)%8;
			int c1 = (c+i)%8;
			int curr = parsePlayer(r1,c1);
			if(orig !=-1 && curr != orig) {
				if(b[r1][c1].contains("BISHOP") || b[r1][c1].contains("QUEEN") ) {
					return true;
				}
			}
			r1 = (r+i)%8;
			c1 = (((c-i)%8) + 8)%8;
			curr = parsePlayer(r1,c1);
			if(orig !=-1 && curr != orig) {
				if(b[r1][c1].contains("BISHOP") || b[r1][c1].contains("QUEEN") ) {
					return true;
				}
			}
			r1 = (((r-i)%8) + 8)%8;
			c1 = (c+i)%8;
			curr = parsePlayer(r1,c1);
			if(orig !=-1 && curr != orig) {
				if(b[r1][c1].contains("BISHOP") || b[r1][c1].contains("QUEEN") ) {
					return true;
				}
			}
			r1 = (((r-i)%8) + 8)%8;
			c1 = (((c-i)%8) + 8)%8;
			curr = parsePlayer(r1,c1);
			if(orig !=-1 && curr != orig) {
				if(b[r1][c1].contains("BISHOP") || b[r1][c1].contains("QUEEN") ) {
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
			if(r-2 <=7 && r-2 >=0) {
				if(b[r-2][c+1].contains("A PAWN") || b[r+2][c-1].contains("A PAWN")) {
					return true;
				}
			}
		}
		return false;
	}
	
}
