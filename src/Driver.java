import java.util.Scanner;

public class Driver {
	static Board chess = new Board();
	static ValidMove v = new ValidMove();
	
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		String move = "";
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
			if(chess.checkMate(chess.king2r, chess.king2c)) {
				System.out.println("CHECKMATE");
				break;
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
			if(chess.checkMate(chess.king1r, chess.king1c)) {
				System.out.println("CHECKMATE");
				break;
			}
		}
	}
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
	
	public static boolean checkPiece(int r1, int c1, int r2, int c2) {
		if(chess.b[r1][c1].contains("ROOK")) {
			return v.validRook(r1, c1, r2, c2);
		}
		else if(chess.b[r1][c1].contains("KNIGHT")) {
			return v.validKnight(r1, c1, r2, c2);
		}
		else if(chess.b[r1][c1].contains("BISHOP")) {
			return v.validBishop(r1, c1, r2, c2);
		}
		else if(chess.b[r1][c1].contains("QUEEN")) {
			return v.validQueen(r1, c1, r2, c2);
		}
		else if(chess.b[r1][c1].contains("KING")) {
			return v.validKing(r1, c1, r2, c2);
		}
		else if(chess.b[r1][c1].contains("PAWN")) {
			return v.validPawn(r1, c1, r2, c2);
		}
		return false;
	}
	public static boolean checkMove(String move, int p) {
		String[] m = move.split(",");
		int c1 = ((int)(m[0].charAt(0))-65);
		int r1 = Integer.parseInt(m[0].charAt(1)+"")-1;
		int c2 = ((int)(m[1].charAt(0))-65);
		int r2 = Integer.parseInt(m[1].charAt(1)+"")-1;
		System.out.println(chess.parsePlayer(c1, r1));
		if(chess.parsePlayer(r1, c1) != p) {
			System.out.println("Not a valid move! You're moving the opponents's piece!");
			return false;
		}
		// System.out.println(chess.b[r1][c1]+ r1 + " " + c1);
		if(checkPiece(r1, c1, r2, c2)) {
			int checked = chess.changePos(r1, c1, r2, c2);
			if(checked==0) {
				System.out.println("Not a valid move!");
				return false;
			}
		}
		else {
			System.out.println("Not a valid move!");
			return false;
		}
		return true;
	}
	
}
