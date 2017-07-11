package org.gstmichel.chess.piece;

import org.gstmichel.chess.Board;
import org.gstmichel.chess.piece.validator.BishopValidator;
import org.gstmichel.chess.piece.validator.BlackPawnValidator;
import org.gstmichel.chess.piece.validator.KingValidator;
import org.gstmichel.chess.piece.validator.KnightValidator;
import org.gstmichel.chess.piece.validator.PieceValidator;
import org.gstmichel.chess.piece.validator.QueenValidator;
import org.gstmichel.chess.piece.validator.RookValidator;
import org.gstmichel.chess.piece.validator.WhitePawnValidator;

public enum PieceEnum {
	ROCK(new RookValidator()),
	BISHOP(new BishopValidator()),
	KNIGHT(new KnightValidator()),
	WHITE_PAWN(new WhitePawnValidator()),
	BLACK_PAWN(new BlackPawnValidator()),
	KING(new KingValidator()),
	QUEEN(new QueenValidator());
	
	private PieceValidator validator;
	
	private PieceEnum(PieceValidator validator){
		this.validator = validator;
	}
	
	public boolean isValidMove(Board board, int positionA, int positionB){
		return 	isValidMove(board, positionA, positionB, true);
	}
	
	public boolean isValidMove(Board board, int positionA, int positionB, boolean extraValidate){
		return 	validator.isValidMove(board, positionA, positionB, extraValidate);
	}
	
	public boolean isCheckSafe(Board board, int positionA, int positionB){
		return validator.isCheckSafe(board, positionA, positionB) != null;
	}
	
	public Board move(Board board, int positionA, int positionB){
//		int p = board.enPassant;
		
		if(		isValidMove(board, positionA, positionB) &&
				isCheckSafe(board, positionA, positionB)){
			return board.move(positionA, positionB);
			//Si la valeur enPassant existe mais n'as pas chang&eacute;, on la r&eacute;initialise
			//TODO : Add in board move
//			if(board.enPassant != -1 && p == board.enPassant){
//				board.enPassant = -1; // On reset enPassant	
//			}
		}
		
		return null;
	}
	
	//REPLACED by board.move(positionA, positionB);
//	public void moveNoCheck(Board board, int positionA, int positionB){
		//Si en passant date d'avant ce coup ci, on resette la valeur
//		if(	board.enPassant != -1 && //Si en passant est actif
//			((board.enPassant < 24 && board.turn == Color.WHITE) || 	//et qu'il est blanc, mais que noir viens de jouer
//			(board.enPassant > 39 && board.turn == Color.BLACK))){		//Ou qu'il est noir mais que blanc viens de jouer
//			board.enPassant = -1; // On reset enPassant	
//		}
//	}
	
	//TODO : Add Casteling to board.move...
	public Board castling(Board board, int positionA, int positionB){
		if(this == ROCK){
			return board.move(positionA, positionB);
		}
		
		return board;
	}
	
	public Board[] getMove(Board board, int positionA){
		return validator.getMove(board, positionA);
	}
	
	public Board[] getAttack(Board board, int positionA){
		return validator.getAttack(board, positionA);
	}
}