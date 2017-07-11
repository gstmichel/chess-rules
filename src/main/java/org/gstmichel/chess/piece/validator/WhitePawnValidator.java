package org.gstmichel.chess.piece.validator;

import java.util.Arrays;

import org.gstmichel.chess.Board;
import org.gstmichel.chess.util.BoardUtil;

public class WhitePawnValidator extends PawnValidator{

	public static final long [] MOVE = {
		0x0L,0x0L,0x0L,0x0L,
		0x0L,0x0L,0x0L,0x0L,
		
		0x0000000001010000L,
		0x0000000002020000L,
		0x0000000004040000L,
		0x0000000008080000L,
		0x0000000010100000L,
		0x0000000020200000L,
		0x0000000040400000L,
		0x0000000080800000L,
		
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
		
		0x0000010000000000L,
		0x0000020000000000L,
		0x0000040000000000L,
		0x0000080000000000L,
		0x0000100000000000L,
		0x0000200000000000L,
		0x0000400000000000L,
		0x0000800000000000L,
		
		0x0001000000000000L,
		0x0002000000000000L,
		0x0004000000000000L,
		0x0008000000000000L,
		0x0010000000000000L,
		0x0020000000000000L,
		0x0040000000000000L,
		0x0080000000000000L,
		
		0x0100000000000000L,
		0x0200000000000000L,
		0x0400000000000000L,
		0x0800000000000000L,
		0x1000000000000000L,
		0x2000000000000000L,
		0x4000000000000000L,
		0x8000000000000000L,
		
		0x0L,0x0L,0x0L,0x0L,
		0x0L,0x0L,0x0L,0x0L,
	};
	
	public static final long [] ATTACK = {
		0x0L,0x0L,0x0L,0x0L,
		0x0L,0x0L,0x0L,0x0L,
		
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
		
		0x0002000000000000L,
		0x0005000000000000L,
		0x000A000000000000L,
		0x0014000000000000L,
		0x0028000000000000L,
		0x0050000000000000L,
		0x00A0000000000000L,
		0x0040000000000000L,
		
		0x0200000000000000L,
		0x0500000000000000L,
		0x0A00000000000000L,
		0x1400000000000000L,
		0x2800000000000000L,
		0x5000000000000000L,
		0xA000000000000000L,
		0x4000000000000000L,
		
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
		for(int positionB : BoardUtil.getPositionOnBoard(ATTACK[positionA] & ~board.PAWN)){
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