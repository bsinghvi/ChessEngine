import java.util.Scanner;

public class Driver {
	static Board chess = new Board();
	
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		String move = "";
		String checkK = "";
		printBoard();
		while(true) {
			boolean check = false;
			while(check==false) {
				System.out.println("Player 1: Where do you want to move your piece to? Denote starting position such as E6, and ending such as A1, as 'E6,A1' ");
				move = kb.nextLine();
				if(checkMove(move, 0)) {
					check = true;
				}
			}
			printBoard();
			checkK = chess.checkKing(chess.king2r, chess.king2c);
			if(checkK != "false") {
				System.out.println(checkK);
				if(checkK == "Checkmate") {
					break;
				}
			}
			check = false;
			while(check==false) {
				System.out.println("Player 2: Where do you want to move your piece to? Denote starting position such as E6, and ending such as A1, as 'E6,A1' ");
				move = kb.nextLine();
				if(checkMove(move, 1)) {
					check = true;
				}
			}
			printBoard();
			checkK = chess.checkKing(chess.king1r, chess.king1c);
			if(checkK != "false") {
				System.out.println(checkK);
				if(checkK == "Checkmate") {
					break;
				}
			}
		}
		kb.close();
	}
	
	/*
	 * Prints the board using for loops and text manipulation
	 */
	public static void printBoard() {
		System.out.print("\t");
		for(int j=65; j<73;j++) 
		{
			System.out.print((char)(j) + "         ");
		}
		System.out.println("\n");
		for(int i=0; i<8; i++)
		{
			int row = i+1;
			System.out.print(row + "\t");
			for(int j=0; j<8;j++)
			{
				System.out.print(chess.b[i][j] + "  "); 
			}
			System.out.println("   " + row + "\n");
		}
		System.out.print("\t");
		for(int j=65; j<73;j++)
		{
			System.out.print((char)(j) + "         ");
		}
		System.out.println("");
		System.out.println("");
	}
	
	/*
	 * Main method for checking if the move is valud. 
	 * Coordinates with the Board class to use its following methods:
	 * parsePlayer - to check which player is moving the piece
	 * checkPiece - to check if the piece can move the way it's been asked to
	 */
	public static boolean checkMove(String move, int p) {
		if(move.length()!=5 || move.charAt(2)!=',') {
			System.out.println("Your specified move wasn't written correctly!");
			return false;
		}
		String[] m = move.split(",");
		int r1 = Integer.parseInt(m[0].charAt(1)+"")-1;
		int c1 = ((int)(m[0].charAt(0))-65);
		if(r1 >=0 && r1 <8 && c1>=65 && c1<=72) {
			System.out.println("You did not enter the pieces initial location right");
			return false;
		}
		if(r1 < 0 || r1 >=8 || c1<65 || c1>=72) {
			System.out.println("You did not enter the pieces initial location right");
			return false;
		}
		int r2 = Integer.parseInt(m[1].charAt(1)+"")-1;
		int c2 = ((int)(m[1].charAt(0))-65);
		if(r2 < 0 || r2 >=8 || c2<65 || c2>=72) {
			System.out.println("You did not enter the piece's destination location right");
			return false;
		}
		if(chess.parsePlayer(r1, c1) != p) {
			if(chess.parsePlayer(r1, c1)==-1) {
				System.out.println("Not a valid move! There's no piece there!");
			}
			else {
				System.out.println("Not a valid move! You're moving the opponents's piece!");
			}
			return false;
		}
		// Check if the piece can move in the way it has been asked to
		if(checkPiece(r1, c1, r2, c2,p)) {
			// Check if the piece can move into the spot it's getting into
			int checked = chess.changePos(r1, c1, r2, c2);
			if(checked == 0) {
				System.out.println("Not a valid move! You're trying to move into a spot which contains your own piece!");
				return false;
			}
		}
		else {
			System.out.println("Not a valid move! The piece you selected doesn't move that way");
			return false;
		}
		return true;
	}
	
	
	/**
	 * Method called by checkMove 
	 * Determines what piece it is and called the corresponding method in the Board class...
	 * ...to check if the piece moves in the way it's asked
	 * @param r1 - initial row
	 * @param c1 - initial column
	 * @param r2 - destination row
	 * @param c2 - destination column
	 * @param p - Whose piece it is, 0 for Player 1, 1 for player 2
	 * @return whether it's a valid move or not
	 */
	public static boolean checkPiece(int r1, int c1, int r2, int c2, int p) {
		if(chess.b[r1][c1].contains("ROOK")) {
			return chess.validRook(r1, c1, r2, c2);
		}
		else if(chess.b[r1][c1].contains("KNIGHT")) {
			return chess.validKnight(r1, c1, r2, c2);
		}
		else if(chess.b[r1][c1].contains("BISHOP")) {
			return chess.validBishop(r1, c1, r2, c2);
		}
		else if(chess.b[r1][c1].contains("QUEEN")) {
			
			return chess.validQueen(r1, c1, r2, c2);
		}
		else if(chess.b[r1][c1].contains("KING")) {
			return chess.validKing(r1, c1, r2, c2);
		}
		else if(chess.b[r1][c1].contains("PAWN")) {
			int spot = chess.parsePlayer(r2,c2);
			if(spot != p && spot != -1) {
				return chess.validPawn(r1, c1, r2, c2, p, 1);
			}
			return chess.validPawn(r1, c1, r2, c2, p, 0);
		}
		return false;
	}
}
