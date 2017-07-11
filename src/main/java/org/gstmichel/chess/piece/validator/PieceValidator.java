package org.gstmichel.chess.piece.validator;

import org.gstmichel.chess.Board;
import org.gstmichel.chess.piece.PieceEnum;
import org.gstmichel.chess.util.BoardUtil;
import org.gstmichel.chess.util.PositionUtil;

public abstract class PieceValidator {
	
	public abstract boolean isValidMove(Board board, int positionA, int positionB, boolean validateCastling);
	
	public boolean isSlideValid(Board board, int positionA, int positionB){
		long slide = PositionUtil.getSlideBoard(positionA, positionB);
		int nbBits = Long.bitCount(slide);

		slide = (slide & ~(board.getCurrent() | board.getOpponent()));
		return Long.bitCount(slide) == nbBits;
	}
	
	public boolean isPositionBFree(Board board, int positionB){
		return board.getPieceAt(positionB) == null;
	}
	
	public Board isCheckSafe(Board board, int positionA, int positionB){
		Board child = board.move(positionA, positionB);
		
		for(int integer : BoardUtil.getPositionOnBoard(child.getCurrent())){
			PieceEnum piece = child.getPieceAt(integer);
			try{
				if(piece.isValidMove(child, integer, BoardUtil.getSinglePosition(child.getOpponent() & child.KING), true)){
					return null;
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		return child;
	}
	
	public static boolean isCheck(Board board){
		for(int integer : BoardUtil.getPositionOnBoard(board.getOpponent())){
			PieceEnum piece = board.getPieceAt(integer);
			
			if(piece.isValidMove(board, integer, BoardUtil.getSinglePosition(board.KING & board.getOpponent()), true)){
				return true;
			}
		}
		
		return false;
	}
	
	public abstract Board[] getMove(Board board, int positionA);
	
	public abstract Board[] getAttack(Board board, int positionA);
}