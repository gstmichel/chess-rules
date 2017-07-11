package org.gstmichel.chess.piece;

public enum Color {
	WHITE,
	BLACK;
	
	public static Color getOpposite(Color color){
		return color == WHITE ? BLACK : WHITE;
	}
}
