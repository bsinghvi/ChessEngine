import java.util.Arrays;
import java.util.HashMap;

public class ChessBoard {

	HashMap<String,Piece> piecesA;
	HashMap<String,Piece> piecesB;
	Player A;
	Player B;

	public ChessBoard(Player a, Player b) {
		A = a;
		B = b;
		piecesA = initializeBoard(A);
		piecesB = initializeBoard(B);
	}

	public HashMap<String, Piece> initializeBoard(Player x) {
		HashMap<String, Piece> pieces = new HashMap<String, Piece>();
		String[] col = new String[]{"A","B","C","D","E", "F", "G", "H"};
		int row = 0;
		if(x.number == 1) {
			row +=7;
		}

		pieces.put(col[0] + row, new Rook(row, 0, x));
		pieces.put(col[7] + row, new Rook(row, 7, x));

		pieces.put(col[1] + row, new Knight(row, 1, x));
		pieces.put(col[6] + row, new Knight(row, 6, x));

		pieces.put(col[2] + row, new Bishop(row, 2, x));
		pieces.put(col[5] + row, new Bishop(row, 5, x));

		pieces.put(col[3] + row, new Queen(row, 3, x));
		pieces.put(col[4] + row, new King(row, 4, x));

		if(x.number==0) {
			row = 1;
		}
		else {
			row = 6;
		}
		for(int i=0; i<=7; i++) {
			pieces.put(col[i]+row, new Pawn(row, i, x));
		}

		return pieces;
	}
	/**
	 * Determine which player the piece belongs to
	 * @param r - row of piece
	 * @param c - col of piece
	 * @return -1 if empty, 0 if player A's piece, 1 if player B's piece
	 */
	public int parsePlayer(String location) {
		/*
		 * System.out.println(location + "!");
		 * for(String key:piecesA.keySet()) {
			System.out.println(key + ":" + piecesA.get(key).name );
		}
		 */
		if(piecesA.containsKey(location)) {
			return 0;
		}
		if(piecesB.containsKey(location)) {
			return 1;
		}
		return -1;
	}

	public boolean changePosition(String location, String destination, int u, int v, int p) {
		Piece x;
		int[] dest = convertToSpace(destination);
		if(p==0) {
			x = piecesA.get(location);
		}
		else {
			x = piecesB.get(location);
		}
		if(x.validMove(dest[0], dest[1], this)) {
			x.setPos(dest[0], dest[1]);
			if(p == 0) {
				piecesA.put(destination, x);
				piecesA.remove(location);
				if(piecesB.containsKey(destination)) {
					piecesB.remove(destination);
				}
			}
			if(p == 1) {
				piecesB.put(destination, x);
				piecesB.remove(location);
				if(piecesA.containsKey(destination)) {
					piecesA.remove(destination);
				}
			}
			return true;
		}
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
	/**
	 * Converts the "hash" of a piece to the specific row and column values
	 * @param hash - ex: "A1"
	 * @return int[] - ex: for "A1", it would return [1][0], as A is the 0th column and 1 represents the first row
	 */
	public int[] convertToSpace (String hash) {
		int row = Integer.parseInt(hash.charAt(1)+"");
		int column = ((int)(hash.charAt(0))-65);
		int[] space = new int[]{row, column};
		return space;
	}
}
