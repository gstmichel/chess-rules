package org.gstmichel.chess.rules;

import org.gstmichel.chess.Board;
import org.gstmichel.chess.piece.Color;
import org.gstmichel.chess.piece.PieceEnum;
import org.gstmichel.chess.util.PositionUtil;

import java.util.ArrayList;
import java.util.List;

public class BoardManager {

	public Board board;
	public List<String> history = new ArrayList<String>();
	public boolean ended = false;
	
	public boolean debug = false;
	
	public BoardManager(Board board){
		this.board = board;
	}
	
	public boolean playTurn(int positionA, int positionB){
		PieceEnum piece = board.getPieceAt(positionA);
		Board child = piece.move(board, positionA, positionB); 
		if(child != null){
			history.add(board.turn + " - " + PositionUtil.translatePosition(positionA) + " => " + PositionUtil.translatePosition(positionB));
			board = child;
			
			return true;
		}
		return false;
	}
	
	
	public void endIt(){
		board = new Board(	board.PAWN, board.ROOK, board.KNIGHT, board.BISHOP, board.QUEEN, board.KING,  
							board.WHITE, board.BLACK, 
							board.blackCastlingA, board.blackCastlingH, board.whiteCastlingA, board.whiteCastlingH, 
							board.enPassant, board.turn == Color.WHITE ? Color.BLACK : Color.WHITE, board.nbTurn50);
		
		ended = true;
		showHistory();
		System.out.println("Game done in " + history.size() + " shot.");
	}
	
	public void showHistory(){
		for(String current : history)
			System.out.println(current);
	}
	
	//For Puzzle... revisit
//	public void addPiece(PieceEnum p, String position, boolean white){
//		int pos = PositionUtil.translatePosition(position);
//		
//		switch(p){
//			case KING :	board.KING = board.KING | PositionUtil.POSITIONS[pos];
//						break;
//			case QUEEN : board.QUEEN = board.QUEEN | PositionUtil.POSITIONS[pos];
//						break;
//			case BISHOP : board.BISHOP = board.BISHOP | PositionUtil.POSITIONS[pos];
//						break;
//			case KNIGHT : board.KNIGHT = board.KNIGHT | PositionUtil.POSITIONS[pos];
//						break;
//			case ROCK : board.ROOK = board.ROOK | PositionUtil.POSITIONS[pos];
//						break;
//			case BLACK_PAWN : 
//			case WHITE_PAWN : board.PAWN = board.PAWN | PositionUtil.POSITIONS[pos];
//						break;
//		}
		
		//Set the AI Color by setting the board one...
//		if(	(white && board.turn == Color.WHITE) ||
//			(!white && board.turn() == Color.BLACK))
//			board.getCurrent() = board.getCurrent() | PositionUtil.POSITIONS[pos];
//		else
//			board.getOpponent() = board.getOpponent() | PositionUtil.POSITIONS[pos];
			
//	}
}