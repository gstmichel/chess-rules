package org.gstmichel.chess.util;

import java.util.Arrays;

import org.gstmichel.chess.Board;
import org.gstmichel.chess.piece.Color;
import org.gstmichel.chess.piece.PieceEnum;


public class BoardUtil {

	public static void showBoard(long board){
		String input = fill(Long.toBinaryString(board));
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < 8; i++){
			String line = input.substring(8*i, 8*i+8);
			for(int j = 7; j != -1; j--){
				sb.append(line.charAt(j));
			}
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
		System.out.println("--------");
	}
	
	public static String fill(String board){
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i <= 64 - board.length(); i++){
			sb.append("0");
		}
		sb.append(board);
		
		return sb.toString();
	}
	
	public static void showBoard(Board board){
		String[] b = getStringBoard(board);
		
		System.out.println("+-+--+--+--+--+--+--+--+--+");
		System.out.println("+       "+Thread.currentThread().getName()+"        +");
		System.out.println("+-+--+--+--+--+--+--+--+--+");
		System.out.println("+8+"+b[56]+"+"+b[57]+"+"+b[58]+"+"+b[59]+"+"+b[60]+"+"+b[61]+"+"+b[62]+"+"+b[63]+"+");
		System.out.println("+-+--+--+--+--+--+--+--+--+");
		System.out.println("+7+"+b[48]+"+"+b[49]+"+"+b[50]+"+"+b[51]+"+"+b[52]+"+"+b[53]+"+"+b[54]+"+"+b[55]+"+");
		System.out.println("+-+--+--+--+--+--+--+--+--+");
		System.out.println("+6+"+b[40]+"+"+b[41]+"+"+b[42]+"+"+b[43]+"+"+b[44]+"+"+b[45]+"+"+b[46]+"+"+b[47]+"+");
		System.out.println("+-+--+--+--+--+--+--+--+--+");
		System.out.println("+5+"+b[32]+"+"+b[33]+"+"+b[34]+"+"+b[35]+"+"+b[36]+"+"+b[37]+"+"+b[38]+"+"+b[39]+"+");
		System.out.println("+-+--+--+--+--+--+--+--+--+");
		System.out.println("+4+"+b[24]+"+"+b[25]+"+"+b[26]+"+"+b[27]+"+"+b[28]+"+"+b[29]+"+"+b[30]+"+"+b[31]+"+");
		System.out.println("+-+--+--+--+--+--+--+--+--+");
		System.out.println("+3+"+b[16]+"+"+b[17]+"+"+b[18]+"+"+b[19]+"+"+b[20]+"+"+b[21]+"+"+b[22]+"+"+b[23]+"+");
		System.out.println("+-+--+--+--+--+--+--+--+--+");
		System.out.println("+2+"+b[8]+"+"+b[9]+"+"+b[10]+"+"+b[11]+"+"+b[12]+"+"+b[13]+"+"+b[14]+"+"+b[15]+"+");
		System.out.println("+-+--+--+--+--+--+--+--+--+");
		System.out.println("+1+"+b[0]+"+"+b[1]+"+"+b[2]+"+"+b[3]+"+"+b[4]+"+"+b[5]+"+"+b[6]+"+"+b[7]+"+");
		System.out.println("+-+--+--+--+--+--+--+--+--+");
		System.out.println("+ +A +B +C +D +E +F +G +H +");
		System.out.println("+-+--+--+--+--+--+--+--+--+");
	}
	
	public static String[] getStringBoard(Board board){
		long WHITE = board.turn == Color.WHITE ? board.getCurrent() : board.getOpponent();
		long BLACK = board.turn == Color.WHITE ? board.getOpponent() : board.getCurrent();
		
		String [] b = {
				"  ","  ","  ","  ","  ","  ","  ","  ",
				"  ","  ","  ","  ","  ","  ","  ","  ",
				"  ","  ","  ","  ","  ","  ","  ","  ",
				"  ","  ","  ","  ","  ","  ","  ","  ",
				"  ","  ","  ","  ","  ","  ","  ","  ",
				"  ","  ","  ","  ","  ","  ","  ","  ",
				"  ","  ","  ","  ","  ","  ","  ","  ",
				"  ","  ","  ","  ","  ","  ","  ","  ",
		};
		
		for(int i : getPositionOnBoard(WHITE & board.PAWN)){
			b[i] = "wp";
		}
		for(int i : getPositionOnBoard(WHITE & board.ROOK)){
			b[i] = "wr";
		}
		for(int i : getPositionOnBoard(WHITE & board.KNIGHT)){
			b[i] = "wn";
		}
		for(int i : getPositionOnBoard(WHITE & board.BISHOP)){
			b[i] = "wb";
		}
		for(int i : getPositionOnBoard(WHITE & board.QUEEN)){
			b[i] = "wq";
		}
		for(int i : getPositionOnBoard(WHITE & board.KING)){
			b[i] = "wk";
		}
		for(int i : getPositionOnBoard(BLACK & board.PAWN)){
			b[i] = "bp";
		}
		for(int i : getPositionOnBoard(BLACK & board.ROOK)){
			b[i] = "br";
		}
		for(int i : getPositionOnBoard(BLACK & board.KNIGHT)){
			b[i] = "bn";
		}
		for(int i : getPositionOnBoard(BLACK & board.BISHOP)){
			b[i] = "bb";
		}
		for(int i : getPositionOnBoard(BLACK & board.QUEEN)){
			b[i] = "bq";
		}
		for(int i : getPositionOnBoard(BLACK & board.KING)){
			b[i] = "bk";
		}
		
		return b;
	}
	
	/**
	 * Performance : 10M by seconds
	 */
	public static int[] getPositionOnBoard(long bitboard){
		int[] temp = new int[32];
		
		int index = 0;
		while(bitboard != 0){
			temp[index] = Long.numberOfTrailingZeros(bitboard);
			bitboard -= Long.lowestOneBit(bitboard);
			index ++;
		}
		
		return Arrays.copyOf(temp, index);
	}
	
	public static Board[] getMove(Board board){
		long bitboard = board.getCurrent();
		Board[] m = new Board[1000];
		
		int index = 0;
		while(bitboard != 0){
			int pos = Long.numberOfTrailingZeros(bitboard);
			for(Board current : BoardUtil.getPieceAt(board, pos).getMove(board, pos)){
				m[index] = current;
				index ++;
			}
			bitboard -= Long.lowestOneBit(bitboard);
		}
		
		return Arrays.copyOf(m, index);
	}
	
	public static Board[] getAttack(Board board){
		long bitboard = board.getCurrent();
		Board[] m = new Board[500];
		
		int index = 0;
		while(bitboard != 0){
			int pos = Long.numberOfTrailingZeros(bitboard);
			for(Board current : BoardUtil.getPieceAt(board, pos).getAttack(board, pos)){
				m[index] = current;
				index ++;
			}
			bitboard -= Long.lowestOneBit(bitboard);
		}
		
		return Arrays.copyOf(m, index);
	}
	
	public static int getSinglePosition(long bitboard){
		if(Long.bitCount(bitboard) != 1)
			throw new IllegalArgumentException("The bint board must contain one and only one bit in \"on\" possition.");
		return Long.numberOfTrailingZeros(bitboard);
	}
	
	private static PieceEnum getPieceOfColorAt(Board board, int position, long color){
		long pieceOrigine = PositionUtil.POSITIONS[position];
		
		if(Long.bitCount(color & pieceOrigine & board.PAWN) == 1)
			if(board.turn == Color.WHITE)
				return (pieceOrigine & board.getCurrent()) != 0 ? PieceEnum.WHITE_PAWN : PieceEnum.BLACK_PAWN;
			else if(board.turn == Color.BLACK)
				return (pieceOrigine & board.getCurrent()) != 0 ? PieceEnum.BLACK_PAWN : PieceEnum.WHITE_PAWN;
		if(Long.bitCount(color & pieceOrigine & board.ROOK) == 1)
			return PieceEnum.ROCK;
		if(Long.bitCount(color & pieceOrigine & board.KNIGHT) == 1)
			return PieceEnum.KNIGHT;
		if(Long.bitCount(color & pieceOrigine & board.BISHOP) == 1)
			return PieceEnum.BISHOP;
		if(Long.bitCount(color & pieceOrigine & board.QUEEN) == 1)
			return PieceEnum.QUEEN;
		if(Long.bitCount(color & pieceOrigine & board.KING) == 1)
			return PieceEnum.KING;
		
		return null;
	}
	
	public static PieceEnum getPieceAt(Board board, int position){
		return getPieceOfColorAt(board, position, board.getCurrent());
	}
	
	public static PieceEnum getPieceOpponentAt(Board board, int position){
		return getPieceOfColorAt(board, position, board.getOpponent());
	}
}