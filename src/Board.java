import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	String[][] b;
	HashMap<String, String> pieces;
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
	   pieces = new HashMap<String, String>();
	   for(int i=0; i<8; i++) {
		   for(int j=0; j<8; j++) {
			   if(b[i][j]!="EMPTY   ") {
				   pieces.put(convertToHash(i,j), b[i][j]);
			   }
		   }
	   }
	   for(String key : pieces.keySet()) {
		   System.out.println(key + " " + pieces.get(key));
	   }
	   
	}
	
	/**
	 * Converts the specific row and column values to the "hash" of a piece
	 * @param int row and column - ex: 1,0
	 * @return String hash - ex: for 1,0, it would return "A1", as 0th column is represented by A and 1st row is represented by 1
	 */
	public String convertToHash (int r, int c) {
		int row = r;
		char column = (char)(c + 65);
		String hash = column + "" + row;
		return hash;
	}
	
	/**
	 * Converts the "hash" of a piece to the specific row and column values
	 * @param hash - ex: "A1"
	 * @return int[] - ex: for "A1", it would return [1][0], as A is the 0th column and 1 represents the first row
	 */
	public int[] convertToSpace (String hash) {
		int row = Integer.parseInt(hash.charAt(1)+"")-1;
		int column = ((int)(hash.charAt(0))-65);
		int[] space = new int[]{row, column};
		return space;
		
	}
	/**
	 * Determine which player the piece belongs to
	 * @param r - row of piece
	 * @param c - col of piece
	 * @return -1 if empty, 0 if player A's piece, 1 if player B's piece
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
		if(b[r][c] == "EMPTY   ") {
			b[r][c] = b[u][v];
			b[u][v] = "EMPTY   ";
			if(b[r][c]=="A KING  ") {
				king1r = r;
				king1c = c;
			}
			else if(b[r][c]=="B KING  ") {
				king2r = r;
				king2c = c;
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
	public String checkKing(int kingR, int kingC) {
		if(checkSurrounding(kingR,kingC,parsePlayer(kingR,kingC))==true) {
			if(checkMate(kingR, kingC) == true) {
				return "Checkmate";
			}
			else {
				return "Check";
			}
		}
		return "false";
	}
	
	/**
	 * Method called when the king is in check as determined by the "checkKing" method
	 * Checks if the king in question is under checkmate or not
	 * Done by first checking all the possible places the king can move
	 * then checks if it's possible for the king to move to the pieces
	 * @param u - original row of piece
	 * @param v - original col of piece
	 * @param r - intended row of piece
	 * @param c - intended col of piece
	 * @return true if all the possible empty spaces would also result the king in check, therefore checkmate
	 */
	public boolean checkMate(int kingR, int kingC) {
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
		System.out.println(sum); 
		int a = 0;
		for(int i=-1; i<=1; i++) {
			for(int j=-1; j<=1; j++) {
				int checkR = kingR + i;
				int checkC = kingC + j;
				if(parsePlayer(checkR,checkC)==-1) {
					a +=1;
				}
			}
		}
		
		System.out.println(a);
		for(int i=-1; i<=1; i++) {
			for(int j=-1; j<=1; j++) {
				int checkR = kingR + i;
				int checkC = kingC + j;
				if(checkR<8 && checkR>=0 && checkC<8 && checkC>=0) {
					int x = parsePlayer(checkR,checkC);
					if(x!=orig) {
						if(x==-1) {
							int y = 1;
							if(orig==1) {
								y = 0;
							}
							if(checkSurrounding(checkR,checkC, y)) {
								sum+=1;
							}
						}
						else if(checkSurrounding(checkR,checkC,x)==false) {
							sum=-1;
						}
					}
				}		
			}
		}
		System.out.println(sum);
		if(sum==0) {
			return true;
		}
		return false;
	}

	/**
	 * This method checks if any pieces oppose the spot in question. 
	 * @param r - row of spot
	 * @param c - column of spot
	 * @param p - player designation (either a '0' for Player A, or '1' for Player B)
	 * @return true if there is a piece that can oppose the spot in question (or if one own's piece can move into the empty spot)
	 */
	public boolean checkSurrounding(int r, int c, int p) {
		// Parse which player's piece it is, called original because it's the reference to check other pieces with
		int orig = p;

		// Following for-loops run check if there are any opposing pieces, 
		// if so, return true
		for(int i=1; i<8; i++) {
			int c1 = c+i;
			if(c1<=7) {
				int curr = parsePlayer(r,c1);
				if(curr == orig) {
					break;
				}
				else if(curr != orig) {
					if(b[r][c1].contains("ROOK") || b[r][c1].contains("QUEEN") ) {
						return true;
					}
				}
			}
			else {
				break;
			}
		}
		for(int i=1; i<8; i++) {
			int r1 = r+i;
			if(r1<=7) {
				int curr = parsePlayer(r1,c);
				curr = parsePlayer(r1,c);
				if(curr == orig) {
					break;
				}
				else if(curr != orig) {
					if(b[r1][c].contains("ROOK") || b[r1][c].contains("QUEEN") ) {
						return true;
					}
				}
			}
			else {
				break;
			}
		}
		for(int i=1; i<8; i++) {
			int c1 = c-i;
			if(c1>=0) {
				int curr = parsePlayer(r,c1);
				if(curr == orig) {
					break;
				}
				else if(curr != orig) {
					if(b[r][c1].contains("ROOK") || b[r][c1].contains("QUEEN") ) {
						return true;
					}
				}
			}
			else {
				break;
			}
		}
		for(int i=1; i<8; i++) {
			int r1 = r-i;
			if(r1>=0) {
				int curr = parsePlayer(r1,c);
				if(curr == orig) {
					break;
				}
				if(curr != orig) {
					if(b[r1][c].contains("ROOK") || b[r1][c].contains("QUEEN") ) {
						return true;
					}
				}
			}
			else {
				break;
			}
		}
		for(int i=1; i<8; i++) {
			int r1 = r+i;
			int c1 = c+i;
			if(r1<=7 && r1>=0 && c1<=7 && c1>=0) {
				int curr = parsePlayer(r1,c1);
				if(curr == orig) {
					break;
				}
				else if(curr != orig) {
					if(b[r1][c1].contains("BISHOP") || b[r1][c1].contains("QUEEN") ) {
						return true;
					}
				}
			}
			else {
				break;
			}
		}
		for(int i=1; i<8; i++) {
			int r1 = r+i;
			int c1 = c-i;
			if(r1<=7 && r1>=0 && c1<=7 && c1>=0) {
				int curr = parsePlayer(r1,c1);
				if(curr == orig) {
					break;
				}
				else if(curr != orig) {
					if(b[r1][c1].contains("BISHOP") || b[r1][c1].contains("QUEEN") ) {
						return true;
					}
				}
			}
			else {
				break;
			}
		}
		for(int i=1; i<8; i++) {
			int r1 = r-i;
			int c1 = c+i;
			if(r1<=7 && r1>=0 && c1<=7 && c1>=0) {
				int curr = parsePlayer(r1,c1);
				if(curr == orig) {
					break;
				}
				else if(curr != orig) {
					if(b[r1][c1].contains("BISHOP") || b[r1][c1].contains("QUEEN") ) {
						return true;
					}
				}
			}
			else {
				break;
			}
		}
		for(int i=1; i<8; i++) {
			int r1 = r-i;
			int c1 = c-i;
			if(r1<=7 && r1>=0 && c1<=7 && c1>=0) {
				int curr = parsePlayer(r1,c1);
				if(curr == orig) {
					break;
				}
				else if(curr != orig) {
					if(b[r1][c1].contains("BISHOP") || b[r1][c1].contains("QUEEN") ) {
						return true;
					}
				}
			}
			else {
				break;
			}
		}
		for(int i=-2; i<3; i++) {
			if(i != 0 && r+i>=0 && r+i<=7) {
				if(i%2==0) {
					if(c+1<=7) {
						if(b[r+i][c+1].contains("KNIGHT") && parsePlayer(r+i,c+1)!=orig) {
							return true;
						}
					}
					if(c-1 >=0) {
						if(b[r+i][c-1].contains("KNIGHT") && parsePlayer(r+i,c-1)!=orig) {
							return true;
						}
					}
				}
				else {
					if(c+2<=7) {
						if(b[r+i][c+2].contains("KNIGHT") && parsePlayer(r+i,c+2)!=orig) {
							return true;
						}
					}
					if(c-2 >=0) {
						if(b[r+i][c-2].contains("KNIGHT") && parsePlayer(r+i,c-2)!=orig) {
							return true;
						}
					}
				}
			}
		}
		if(orig==0) {
			if(r-2 >=0 && r+2 <=7) {
				if(b[r+2][c+1].contains("B PAWN") || b[r+2][c-1].contains("B PAWN")) {
					return true;
				}
			}

		}
		if(orig==1) {
			if(r-2 >=0 && r+2 <=7) {
				if(b[r-2][c+1].contains("A PAWN") || b[r+2][c-1].contains("A PAWN")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the movement of the rook is linear (non-diagonal) and does not include any pieces in between 
	 * @param u - original row of piece
	 * @param v - original col of piece
	 * @param r - intended row of piece
	 * @param c - intended col of piece
	 * @return true if it's valid movement of the rook
	 */
	public boolean validRook(int u, int v, int r, int c) {
		// Checks if rook is moving columns
		if(u==r && v != c) {
			int max = c;
			int min = v; 
			if(c-v<0) {
				max = v;
				min = c;
			}
			for(int i=0; i<8; i++) {
				if(i>min && i<max) {
					if(b[u][i] != "EMPTY   ") {
						return false;
					}
				}
			}
			return true;
		}
		// Checks if rook is moving rows
		if(u!=r && v == c) {
			int max = r;
			int min = u; 
			if(r-u<0) {
				max = u;
				min = r;
			}
			for(int i=0; i<8; i++) {
				if(i>min && i<max) {
					if(b[i][v] != "EMPTY   ") {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	/**
	 * Knights are the only pieces that move in non-linear fashion, therefore different checks must be used
	 * @param u - original row of piece
	 * @param v - original col of piece
	 * @param r - intended row of piece
	 * @param c - intended col of piece
	 * @return true if it's valid movement of the knight
	 */
	public boolean validKnight(int u, int v, int r, int c) {
		if(u+2 == r) {
			if(v-1 == c || v+1 == c) {
				return true;
			}
		}
		else if(u-2 == r) {
			if(v-1 == c || v+1 == c) {
				return true;
			}
		}
		else if(u+1 == r) {
			if(v-2 == c || v+2 == c) {
				return true;
			}
		}
		else if(u-1 == r) {
			if(v-2 == c || v+2 == c) {
				return true;
			}
		}
		return false;
	}
	// 
	
	/**
	 * Bishops move linearly therefore the movement's slope can be checked
	 * In order to avoid a division by zero error, ...
	 * ...it's necessary to check if the bishop is moved which diagonal
	 * @param u - original row of piece
	 * @param v - original col of piece
	 * @param r - intended row of piece
	 * @param c - intended col of piece
	 * @return true if it's valid movement of the bishop 
	 */
	public boolean validBishop(int u, int v, int r, int c) {
		if(u==r && v != c) {
			return false;
		}
		if(u!=r && v == c) {
			return false;
		}
		if( Math.abs((c-v)/(r-u)) == 1) {
			int diffC = -1;
			int diffR = -1;
			if(c>v) {
				diffC = 1;
			}
			if(r>u) {
				diffR = 1;
			}
			int checkR = u+diffR;
			int checkC = v+diffC;
			while(checkR!=r && checkC!=c) {
				if(b[checkR][checkC] != "EMPTY   ") {
					return false;
				}
				else {
					checkR += diffR;
					checkC += diffC;
				}
			}
			return true;
		}
		return false;
	}
	// The queen move is checked by employing the same checks as a rook and bishop 
	public boolean validQueen(int u, int v, int r, int c) {
		if(u==r && v != c) {
			return validRook(u, v, r, c);
		}
		if(u!=r && v == c) {
			return validRook(u, v, r, c);
		}
		if( Math.abs((c-v)/(r-u)) == 1) {
			return validBishop(u, v, r, c);
		}
		return false;
	}
	// A king can only move to one spot
	public boolean validKing(int u, int v, int r, int c) {
		if(Math.abs(r-u)<=1 && Math.abs(c-v) <=1) {
			return true;
		}
		return false;
	}
	/**
	 * Check if the movement of the piece specified matches how a pawn moves
	 * @param u - original row of piece
	 * @param v - original col of piece
	 * @param r - intended row of piece
	 * @param c - intended col of piece
	 * @param p - which player's piece it is (used to determine which direction pawn is moving in.
	 * @param x - if the pawn is cutting the opponent's piece
	 * @return true if it's valid movement of the pawn
	 */
	public boolean validPawn(int u, int v, int r, int c, int p, int x) { 
		// p == 0 --> Player A's piece
		if(p==0) {
			if(x==1) {
				if(Math.abs(c-v) == 1) {
					if (r - u == 1) {
						return true;
					}
				}
			}
			else {
				// must be moving 'down' the board
				if(r>u) {
					// if the pawn is in initial position, then it is allowed to move between one or two spaces
					if(u==1) {
						if((r-u)<=2 && c==v) {
							return true;
						}
					}
					// otherwise pawn can only move 1 space
					else if (r-u==1){
						return true;
					}
				}
			}

		}
		// p == 1 --> Player's B's piece
		else if(p==1) {
			if(x==1) {
				if(Math.abs(c-v) == 1) {
					if (r - u == -1) {
						return true;
					}
				}
			}
			else {
				// must be moving 'up' the board
				if(r<u) {
					// if the pawn is in initial position, then it is allowed to move between one or two spaces
					if(u==6) {
						if(Math.abs(r-u)<=2 && c==v) {
							return true;
						}
					}
					// otherwise pawn can only move 1 space
					else if (r-u==-1){
						return true;
					}
				}
			}
		}
		return false;
	}
}
