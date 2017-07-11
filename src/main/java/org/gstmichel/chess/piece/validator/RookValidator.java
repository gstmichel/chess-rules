package org.gstmichel.chess.piece.validator;

import java.util.Arrays;

import org.gstmichel.chess.Board;
import org.gstmichel.chess.util.BoardUtil;
import org.gstmichel.chess.util.PositionUtil;

public class RookValidator extends PieceValidator{

	public static final long [] MOVE = {

		0x01010101010101FEL,
		0x02020202020202FDL,
		0x04040404040404FBL,
		0x08080808080808F7L,
		0x10101010101010EFL,
		0x20202020202020DFL,
		0x40404040404040BFL,
		0x808080808080807FL,

		0x010101010101FE01L,
		0x020202020202FD02L,
		0x040404040404FB04L,
		0x080808080808F708L,
		0x101010101010EF10L,
		0x202020202020DF20L,
		0x404040404040BF40L,
		0x8080808080807F80L,
		
		0x0101010101FE0101L,
		0x0202020202FD0202L,
		0x0404040404FB0404L,
		0x0808080808F70808L,
		0x1010101010EF1010L,
		0x2020202020DF2020L,
		0x4040404040BF4040L,
		0x80808080807F8080L,
		
		0x01010101FE010101L,
		0x02020202FD020202L,
		0x04040404FB040404L,
		0x08080808F7080808L,
		0x10101010EF101010L,
		0x20202020DF202020L,
		0x40404040BF404040L,
		0x808080807F808080L,
		
		0x010101FE01010101L,
		0x020202FD02020202L,
		0x040404FB04040404L,
		0x080808F708080808L,
		0x101010EF10101010L,
		0x202020DF20202020L,
		0x404040BF40404040L,
		0x8080807F80808080L,
		
		0x0101FE0101010101L,
		0x0202FD0202020202L,
		0x0404FB0404040404L,
		0x0808F70808080808L,
		0x1010EF1010101010L,
		0x2020DF2020202020L,
		0x4040BF4040404040L,
		0x80807F8080808080L,
		
		0x01FE010101010101L,
		0x02FD020202020202L,
		0x04FB040404040404L,
		0x08F7080808080808L,
		0x10EF101010101010L,
		0x20DF202020202020L,
		0x40BF404040404040L,
		0x807F808080808080L,
		
		0xFE01010101010101L,
		0xFD02020202020202L,
		0xFB04040404040404L,
		0xF708080808080808L,
		0xEF10101010101010L,
		0xDF20202020202020L,
		0xBF40404040404040L,
		0x7F80808080808080L
	};
	
	public boolean isValidMove(Board board, int positionA, int positionB, boolean validateCastling){
		return 	isEndPositionValid(board, positionA, positionB) &&
				isSlideValid(board, positionA, positionB);
	}
	
	public boolean isEndPositionValid(Board board, int positionA, int positionB){
		return Long.bitCount(
				PositionUtil.POSITIONS[positionA] | 
				PositionUtil.POSITIONS[positionB] & 
				MOVE[positionA] & 
				~board.getCurrent()) == 2;
	}
	
	public Board[] getMove(Board board, int positionA){
		Board[] temp = new Board[14];
		int index = 0;
		for(int positionB : BoardUtil.getPositionOnBoard(MOVE[positionA])){
			if(isValidMove(board, positionA, positionB, true)){
				Board b = isCheckSafe(board, positionA, positionB);
				if(b != null){
					temp[index] = b;
					index ++;
				}
			}
		}
		
		return Arrays.copyOf(temp, index);
	}
	
	public Board[] getAttack(Board board, int positionA){
		Board[] temp = new Board[14];
		int index = 0;
		for(int positionB : BoardUtil.getPositionOnBoard(MOVE[positionA] & (board.getOpponent() & ~board.PAWN))){
			if(isValidMove(board, positionA, positionB, true)){
				Board b = isCheckSafe(board, positionA, positionB);
				if(b != null){
					temp[index] = b;
					index ++;
				}
			}
		}
		
		return Arrays.copyOf(temp, index);
	}
}