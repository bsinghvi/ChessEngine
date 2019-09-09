import java.util.Scanner;

public class Driver {
	static ChessBoard b;
	static Player black;
	static Player white;
	static String[][] board;
	
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		//Parsing and initializing Player classes
		System.out.println("Player 1, what's your name?" );
		String name = kb.nextLine();
		white = new Player(0, name);
		System.out.println("Awesome! " + name + " you'll be starting first and your piece color is white. \n");
		System.out.println("Player 2, what's your name?" );
		name = kb.nextLine();
		black = new Player(1, name);
		System.out.println("Awesome! " + name + " you'll be starting second and your piece color is black. \n");
		//Initializing ChessBoard
		b = new ChessBoard(white, black);
		//Printing the board for the first time
		printBoard();
		while(true) {
			boolean isMove = false; 
			String move = "";
			while(isMove==false) {
				System.out.println(white.name + ", where do you want to move your piece to? Denote starting position such as E6, and ending such as A1, as 'E6,A1' ");
				move = kb.nextLine();
				if(checkMove(move, white)) {
					isMove = true;
				}
			}
			printBoard();
			isMove = false;
			while(isMove==false) {
				System.out.println(black.name + ", where do you want to move your piece to? Denote starting position such as E6, and ending such as A1, as 'E6,A1' ");
				move = kb.nextLine();
				if(checkMove(move, black)) {
					isMove = true;
				}
			}
			printBoard();
		}
		//kb.close();
	}
	
	/*
	 * Main method for checking if the move is valud. 
	 * Coordinates with the Board class to use its following methods:
	 * parsePlayer - to check which player is moving the piece
	 * checkPiece - to check if the piece can move the way it's been asked to
	 */
	public static boolean checkMove(String move, Player x) {
		if(move.length()!=5 || move.charAt(2)!=',') {
			System.out.println("Your specified move wasn't written correctly!");
			return false;
		}
		String[] m = move.split(",");
		if(!Character.isLetter(m[0].charAt(0)) || !Character.isDigit(m[1].charAt(1))) {
			System.out.println("You did not enter the move in the specified format!");
		}
		int r1 = Integer.parseInt(m[0].charAt(1)+"")-1;
		int c1 = ((int)(m[0].charAt(0))-65);
		if(r1 < 0 || r1 >=8 || c1<0 || c1>=8) {
			System.out.println("You did not enter the pieces initial location right");
			return false;
		}
		if(!Character.isLetter(m[1].charAt(0)) || !Character.isDigit(m[1].charAt(1))) {
			System.out.println("You did not enter the move in the specified format!");
		}
		int r2 = Integer.parseInt(m[1].charAt(1)+"")-1;
		int c2 = ((int)(m[1].charAt(0))-65);
		if(r2 < 0 || r2 >=8 || c2<0 || c2>=8) {
			System.out.println("You did not enter the piece's destination location right");
			return false;
		}
		String convertedLocation = b.convertToHash(r1, c1);
		String convertedDestination = b.convertToHash(r2, c2);
		int p = b.parsePlayer(convertedLocation);
		if(p != x.number) {
			if(p ==-1) {
				System.out.println("Not a valid move! There's no piece there!");
			}
			else {
				System.out.println("Not a valid move! You're moving the opponents's piece!");
			}
			return false;
		}
		// Check if the piece can move into the spot it's getting into
		if(b.changePosition(convertedLocation,convertedDestination,r2,c2, p)) {
			System.out.println("Success! Piece moved.");
		}
		else {
			System.out.println("Not a valid move! The piece you selected can't move that way");
			return false;
		}
		return true;
	}
	
	public static void printBoard() {
		board = new String[8][8];
		for (String key: b.piecesA.keySet()) {
		    int row = b.piecesA.get(key).row;
		    int col = b.piecesA.get(key).column;
		    String name = b.piecesA.get(key).name;
		    board[row][col] = name;
		}
		for (String key: b.piecesB.keySet()) {
		    int row = b.piecesB.get(key).row;
		    int col = b.piecesB.get(key).column;
		    String name = b.piecesB.get(key).name;
		    board[row][col] = name;
		}
		
		System.out.print("\t");
		for(int j=65; j<73;j++) {
			System.out.print((char)(j) + "         ");
		}
		System.out.println("\n");
		for(int i=0; i<8; i++) {
			int row = i+1;
			System.out.print(row + "\t");
			for(int j=0; j<8;j++) {
				if(board[i][j] != null) {
					System.out.print(board[i][j] + "  "); 
				}
				else {
					System.out.print("EMPTY     ");
				}
			}
			System.out.println("   " + row + "\n");
		}
		System.out.print("\t");
		for(int j=65; j<73;j++) {
			System.out.print((char)(j) + "         ");
		}
		System.out.println("");
		System.out.println("");
	}
}
