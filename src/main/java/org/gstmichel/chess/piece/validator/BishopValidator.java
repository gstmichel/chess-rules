package org.gstmichel.chess.piece.validator;

import java.util.Arrays;

import org.gstmichel.chess.Board;
import org.gstmichel.chess.util.BoardUtil;
import org.gstmichel.chess.util.PositionUtil;

public class BishopValidator extends PieceValidator{

	public static final long [] MOVE = {
		0x8040201008040200L,
		0x0080402010080500L,
		0x0000804020110A00L,
		0x0000008041221400L,
		0x0000000182442800L,
		0x0000010204885000L,
		0x000102040810A000L,
		0x0102040810204000L,

		0x4020100804020002L,
		0x8040201008050005L,
		0x00804020110A000AL,
		0x0000804122140014L,
		0x0000018244280028L,
		0x0001020488500050L,
		0x0102040810A000A0L,
		0x0204081020400040L,
		
		0x2010080402000204L,
		0x4020100805000508L,
		0x804020110A000A11L,
		0x0080412214001422L,
		0x0001824428002844L,
		0x0102048850005088L,
		0x02040810A000A010L,
		0x0408102040004020L,

		0x1008040200020408L,
		0x2010080500050810L,
		0x4020110A000A1120L,
		0x8041221400142241L,
		0x0182442800284482L,
		0x0204885000508804L,
		0x040810A000A01008L,
		0x0810204000402010L,
		
		0x0804020002040810L,
		0x1008050005081020L,
		0x20110A000A112040L,
		0x4122140014224180L,
		0x8244280028448201L,
		0x0488500050880402L,
		0x0810A000A0100804L,
		0x1020400040201008L,
		
		0x0402000204081020L,
		0x0805000508102040L,
		0x110A000A11204080L,
		0x2214001422418000L,
		0x4428002844820100L,
		0x8850005088040201L,
		0x10A000A010080402L,
		0x2040004020100804L,
		
		0x0200020408102040L,
		0x0500050810204080L,
		0x0A000A1120408000L,
		0x1400142241800000L,
		0x2800284482010000L,
		0x5000508804020100L,
		0xA000A01008040201L,
		0x4000402010080402L,

		0x0002040810204080L,
		0x0005081020408000L,
		0x000A112040800000L,
		0x0014224180000000L,
		0x0028448201000000L,
		0x0050880402010000L,
		0x00A0100804020100L,
		0x0040201008040201L
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