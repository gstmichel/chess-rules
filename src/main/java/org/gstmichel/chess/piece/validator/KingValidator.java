package org.gstmichel.chess.piece.validator;

import java.util.Arrays;

import org.gstmichel.chess.Board;
import org.gstmichel.chess.piece.PieceEnum;
import org.gstmichel.chess.util.BoardUtil;
import org.gstmichel.chess.util.PositionUtil;

public class KingValidator extends PieceValidator{

	public static final long [] MOVE = {
		0x0000000000000302L,
		0x0000000000000705L,
		0x0000000000000E0AL,
		0x0000000000001C14L,
		0x0000000000003828L,
		0x0000000000007050L,
		0x000000000000E0A0L,
		0x000000000000C040L,
		
		0x0000000000030203L,
		0x0000000000070507L,
		0x00000000000E0A0EL,
		0x00000000001C141CL,
		0x0000000000382838L,
		0x0000000000705070L,
		0x0000000000E0A0E0L,
		0x0000000000C040C0L,
		
		0x0000000003020300L,
		0x0000000007050700L,
		0x000000000E0A0E00L,
		0x000000001C141C00L,
		0x0000000038283800L,
		0x0000000070507000L,
		0x00000000E0A0E000L,
		0x00000000C040C000L,
		
		0x0000000302030000L,
		0x0000000705070000L,
		0x0000000E0A0E0000L,
		0x0000001C141C0000L,
		0x0000003828380000L,
		0x0000007050700000L,
		0x000000E0A0E00000L,
		0x000000C040C00000L,
		
		0x0000030203000000L,
		0x0000070507000000L,
		0x00000E0A0E000000L,
		0x00001C141C000000L,
		0x0000382838000000L,
		0x0000705070000000L,
		0x0000E0A0E0000000L,
		0x0000C040C0000000L,
		
		0x0003020300000000L,
		0x0007050700000000L,
		0x000E0A0E00000000L,
		0x001C141C00000000L,
		0x0038283800000000L,
		0x0070507000000000L,
		0x00E0A0E000000000L,
		0x00C040C000000000L,
		
		0x0302030000000000L,
		0x0705070000000000L,
		0x0E0A0E0000000000L,
		0x1C141C0000000000L,
		0x3828380000000000L,
		0x7050700000000000L,
		0xE0A0E00000000000L,
		0xC040C00000000000L,
		
		0x0203000000000000L,
		0x0507000000000000L,
		0x0A0E000000000000L,
		0x141C000000000000L,
		0x2838000000000000L,
		0x5070000000000000L,
		0xA0E0000000000000L,
		0x40C0000000000000L
	};
	
	public static final long BLACK_CASTLING_A = 0x0400000000000000L;
	public static final long BLACK_CASTLING_H = 0x4000000000000000L;
	public static final long WHITE_CASTLING_A = 0x0000000000000004L;
	public static final long WHITE_CASTLING_H = 0x0000000000000040L;
	
	public boolean isValidMove(Board board, int positionA, int positionB, boolean validateCastling){
		return 	isEndPositionValid(board, positionA, positionB, validateCastling);
	}
	
	public boolean isEndPositionValid(Board board, int positionA, int positionB, boolean validateCastling){
		long valid = 0x0L;

		if((MOVE[BoardUtil.getSinglePosition(board.getOpponent() & board.KING)] & PositionUtil.POSITIONS[positionB]) != 0)
			return false;
		
		int castling = isCastling(board, positionB);
		
		if(castling != -1 && validateCastling){
			long occupied = board.getCurrent() | board.getOpponent();

			switch(castling){
				case 0 :
					if(Long.bitCount( occupied & (
							PositionUtil.POSITIONS[1] | 
							PositionUtil.POSITIONS[2] | 
							PositionUtil.POSITIONS[3])) != 0){
						return false;
					}
					
					if(!isCaslingPossible(board, 2, 3, 4)){
						return false;
					}
					valid = WHITE_CASTLING_A | PositionUtil.POSITIONS[positionA] | PositionUtil.POSITIONS[positionB] & MOVE[positionA] & ~board.getCurrent();
					break;
				case 7 :
					
					if(Long.bitCount( occupied & (
							PositionUtil.POSITIONS[5] | 
							PositionUtil.POSITIONS[6])) != 0){
						return false;
					}
					
					if(!isCaslingPossible(board, 4, 5, 6)){
						return false;
					}
					valid = WHITE_CASTLING_H | PositionUtil.POSITIONS[positionA] | PositionUtil.POSITIONS[positionB] & MOVE[positionA] & ~board.getCurrent();
					break;
				case 56 :
					if(Long.bitCount( occupied & (
							PositionUtil.POSITIONS[57] | 
							PositionUtil.POSITIONS[58] | 
							PositionUtil.POSITIONS[59])) != 0){
						return false;
					}
					if(!isCaslingPossible(board, 58, 59, 60)){
						return false;
					}
					valid = BLACK_CASTLING_A | PositionUtil.POSITIONS[positionA] | PositionUtil.POSITIONS[positionB] & MOVE[positionA] & ~board.getCurrent();
					break;
				case 63 :
					if(Long.bitCount( occupied & (
							PositionUtil.POSITIONS[61] | 
							PositionUtil.POSITIONS[62])) != 0){
						return false;
					}
					if(!isCaslingPossible(board, 60, 61, 62)){
						return false;
					}
					valid = BLACK_CASTLING_H | PositionUtil.POSITIONS[positionA] | PositionUtil.POSITIONS[positionB] & MOVE[positionA] & ~board.getCurrent();
					break;
			}
		} else {
			valid = PositionUtil.POSITIONS[positionA] | PositionUtil.POSITIONS[positionB] & MOVE[positionA] & ~(board.getOpponent() & board.KING) & ~board.getCurrent();
		}
		
		return Long.bitCount(valid) == 2;
	}
	
	public int isCastling(Board board, int positionB){
		if(Long.bitCount(KingValidator.WHITE_CASTLING_A & PositionUtil.POSITIONS[positionB]) == 1)
			return board.whiteCastlingA ? 0 : -1;
		else if(Long.bitCount(KingValidator.WHITE_CASTLING_H & PositionUtil.POSITIONS[positionB]) == 1)
			return board.whiteCastlingH ? 7 : -1;
		else if(Long.bitCount(KingValidator.BLACK_CASTLING_A & PositionUtil.POSITIONS[positionB]) == 1)
			return board.blackCastlingA ? 56 : -1;
		else if(Long.bitCount(KingValidator.BLACK_CASTLING_H & PositionUtil.POSITIONS[positionB]) == 1)
			return board.blackCastlingH ? 63 : -1;
		
		return -1;
	}
	
	private boolean isCaslingPossible(Board board, int pos1, int pos2, int pos3){
		for(int integer : BoardUtil.getPositionOnBoard(board.getOpponent())){
			PieceEnum piece = board.getPieceAt(integer);
			if(		piece.isValidMove(board, integer, pos1, false) ||
					piece.isValidMove(board, integer, pos2, false) ||
					piece.isValidMove(board, integer, pos3, false)){
				return false;
			}
		}
		
		return true;
	}
	
	public Board[] getMove(Board board, int positionA){
		Board[] temp = new Board[8];
		int index = 0;
		
		long moveBoard = MOVE[positionA];
		if(positionA == 4){
			if(board.isCastlingA())
				moveBoard = moveBoard | PositionUtil.POSITIONS[2];
			if(board.isCastlingH())
				moveBoard = moveBoard | PositionUtil.POSITIONS[6];
		} else if(positionA == 60){
			if(board.isCastlingA())
				moveBoard = moveBoard | PositionUtil.POSITIONS[58];
			if(board.isCastlingH())
				moveBoard = moveBoard | PositionUtil.POSITIONS[62];
		}
		
		for(int positionB : BoardUtil.getPositionOnBoard(moveBoard)){
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
		Board[] temp = new Board[8];
		int index = 0;
		
		long moveBoard = MOVE[positionA] & (board.getOpponent() & ~board.PAWN);
		if(positionA == 4){
			if(board.isCastlingA())
				moveBoard = moveBoard | PositionUtil.POSITIONS[2];
			if(board.isCastlingH())
				moveBoard = moveBoard | PositionUtil.POSITIONS[6];
		} else if(positionA == 60){
			if(board.isCastlingA())
				moveBoard = moveBoard | PositionUtil.POSITIONS[58];
			if(board.isCastlingH())
				moveBoard = moveBoard | PositionUtil.POSITIONS[62];
		}
		
		for(int positionB : BoardUtil.getPositionOnBoard(moveBoard)){
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