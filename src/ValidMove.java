
public class ValidMove {
	/**
	 * Class checks if the move of the respective piece is valid or not
	 * @param u - row of piece 1
	 * @param v - col of piece 1
	 * @param r - intended row
	 * @param c - intended col
	 * @return true if move is valid, else false
	 */
	public ValidMove() {}
	
	public boolean validRook(int u, int v, int r, int c) {
		if(u==r && v != c) {
			return true;
		}
		if(u!=r && v == c) {
			return true;
		}
		return false;
	}
	
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
	
	public boolean validBishop(int u, int v, int r, int c) {
		if( (v-c)/(u-r) == 1) {
			return true;
		}
		return false;
	}
	
	public boolean validQueen(int u, int v, int r, int c) {
		if( (v-c)/(u-r) == 1) {
			return true;
		}
		if(u==r && v != c) {
			return true;
		}
		if(u!=r && v == c) {
			return true;
		}
		return false;
	}
	
	public boolean validKing(int u, int v, int r, int c) {
		if(Math.abs(r-u)<=1 && Math.abs(c-v) <=1) {
			return true;
		}
		return false;
	}
	
	public boolean validPawn(int u, int v, int r, int c) {
		if(u==2 || u==6) {
			if(Math.abs(r-u)<=2 && c==v) {
				return true;
			}
		}
		else if(Math.abs(r-u)==1 && c==v) {
			return true;
		}
		return false;
	}
	
}
