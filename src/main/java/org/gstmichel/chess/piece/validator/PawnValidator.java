package org.gstmichel.chess.piece.validator;

import org.gstmichel.chess.Board;
import org.gstmichel.chess.util.PositionUtil;

public abstract class PawnValidator extends PieceValidator{
	
	public boolean isEndPositionValid(Board board, int positionA, int positionB, long [] validationArray){
		return Long.bitCount(
				PositionUtil.POSITIONS[positionA] |
				(PositionUtil.POSITIONS[positionB] &
				validationArray[positionA]) & 
				~board.getCurrent()) == 2;
	}
	
	public boolean isBFree(Board board, int positionB){
		long valid = PositionUtil.POSITIONS[positionB] & (board.getOpponent());
		
		return Long.bitCount(valid) == 0;
	}
	
	public boolean isBEnemy(Board board, int positionB){
		long valid = 0;
		
		if(board.enPassant != -1 && positionB == board.enPassant){
			if(board.enPassant < 24){
				valid = PositionUtil.POSITIONS[board.enPassant + 8] & board.getOpponent();
			} else {
				valid = PositionUtil.POSITIONS[board.enPassant - 8] & board.getOpponent();
			}
		} else {
			valid = PositionUtil.POSITIONS[positionB] & board.getOpponent();
		}
		
		return Long.bitCount(valid) == 1;
	}
}