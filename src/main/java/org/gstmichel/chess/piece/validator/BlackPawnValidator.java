package org.gstmichel.chess.piece.validator;

import java.util.Arrays;

import org.gstmichel.chess.Board;
import org.gstmichel.chess.util.BoardUtil;

public class BlackPawnValidator extends PawnValidator{

	public static final long [] MOVE = {
		0x0L,0x0L,0x0L,0x0L,
		0x0L,0x0L,0x0L,0x0L,
		
		0x0000000000000001L,
		0x0000000000000002L,
		0x0000000000000004L,
		0x0000000000000008L,
		0x0000000000000010L,
		0x0000000000000020L,
		0x0000000000000040L,
		0x0000000000000080L,
		
		0x0000000000000100L,
		0x0000000000000200L,
		0x0000000000000400L,
		0x0000000000000800L,
		0x0000000000001000L,
		0x0000000000002000L,
		0x0000000000004000L,
		0x0000000000008000L,
		
		0x0000000000010000L,
		0x0000000000020000L,
		0x0000000000040000L,
		0x0000000000080000L,
		0x0000000000100000L,
		0x0000000000200000L,
		0x0000000000400000L,
		0x0000000000800000L,
		
		0x0000000001000000L,
		0x0000000002000000L,
		0x0000000004000000L,
		0x0000000008000000L,
		0x0000000010000000L,
		0x0000000020000000L,
		0x0000000040000000L,
		0x0000000080000000L,
		
		0x0000000100000000L,
		0x0000000200000000L,
		0x0000000400000000L,
		0x0000000800000000L,
		0x0000001000000000L,
		0x0000002000000000L,
		0x0000004000000000L,
		0x0000008000000000L,
		
		0x0000010100000000L,
		0x0000020200000000L,
		0x0000040400000000L,
		0x0000080800000000L,
		0x0000101000000000L,
		0x0000202000000000L,
		0x0000404000000000L,
		0x0000808000000000L,
		
		0x0L,0x0L,0x0L,0x0L,
		0x0L,0x0L,0x0L,0x0L,
	};
	
	public static final long [] ATTACK = {
		0x0L,0x0L,0x0L,0x0L,
		0x0L,0x0L,0x0L,0x0L,
		
		0x0000000000000002L,
		0x0000000000000005L,
		0x000000000000000AL,
		0x0000000000000014L,
		0x0000000000000028L,
		0x0000000000000050L,
		0x00000000000000A0L,
		0x0000000000000040L,
		
		0x0000000000000200L,
		0x0000000000000500L,
		0x0000000000000A00L,
		0x0000000000001400L,
		0x0000000000002800L,
		0x0000000000005000L,
		0x000000000000A000L,
		0x0000000000004000L,
		
		0x0000000000020000L,
		0x0000000000050000L,
		0x00000000000A0000L,
		0x0000000000140000L,
		0x0000000000280000L,
		0x0000000000500000L,
		0x0000000000A00000L,
		0x0000000000400000L,
		
		0x0000000002000000L,
		0x0000000005000000L,
		0x000000000A000000L,
		0x0000000014000000L,
		0x0000000028000000L,
		0x0000000050000000L,
		0x00000000A0000000L,
		0x0000000040000000L,
		
		0x0000000200000000L,
		0x0000000500000000L,
		0x0000000A00000000L,
		0x0000001400000000L,
		0x0000002800000000L,
		0x0000005000000000L,
		0x000000A000000000L,
		0x0000004000000000L,
		
		0x0000020000000000L,
		0x0000050000000000L,
		0x00000A0000000000L,
		0x0000140000000000L,
		0x0000280000000000L,
		0x0000500000000000L,
		0x0000A00000000000L,
		0x0000400000000000L,
		
		0x0L,0x0L,0x0L,0x0L,
		0x0L,0x0L,0x0L,0x0L,
	};

	public boolean isValidMove(Board board, int positionA, int positionB, boolean validateCastling){
		if(positionA % 8 == positionB % 8){
			//D&eacute;placement
			return 	isEndPositionValid(board, positionA, positionB, MOVE) &&
					isSlideValid(board, positionA, positionB) &&
					isBFree(board, positionB);
		}
		
		//Attaque
		return 	isEndPositionValid(board, positionA, positionB, ATTACK) &&
				isBEnemy(board, positionB);
	}
	
	public Board[] getMove(Board board, int positionA){
		Board[] temp = new Board[4];
		int index = 0;
		for(int positionB : BoardUtil.getPositionOnBoard(MOVE[positionA] | ATTACK[positionA])){
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
		Board[] temp = new Board[4];
		int index = 0;
		for(int positionB : BoardUtil.getPositionOnBoard(ATTACK[positionA] & (board.getOpponent() & ~board.PAWN))){
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