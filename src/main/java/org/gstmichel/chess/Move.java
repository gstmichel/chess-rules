package org.gstmichel.chess;

import java.util.ArrayList;
import java.util.List;

import org.gstmichel.chess.util.BoardUtil;
import org.gstmichel.chess.util.PositionUtil;

public class Move{
	private int posA, posB, value = 0;
	private List<Move> subMove;
	
	public Move(int posA, int posB){
		this.posA = posA;
		this.posB = posB;
	}
	
	public Move(Board begin, Board end){
		
		long movedPiece = 0;
		long originalPiece = 0;
		
		movedPiece = end.getOpponent() & ~begin.getCurrent();
		originalPiece = begin.getCurrent() & ~end.getOpponent();
		
		//King moved
		if(Long.bitCount(movedPiece) == 2){
			movedPiece = movedPiece & ~(PositionUtil.POSITIONS[3] | PositionUtil.POSITIONS[5] | 
										PositionUtil.POSITIONS[59] | PositionUtil.POSITIONS[61]);
			originalPiece = originalPiece & ~(	PositionUtil.POSITIONS[0] | PositionUtil.POSITIONS[7] | 
												PositionUtil.POSITIONS[56] | PositionUtil.POSITIONS[63]);
		}
		
		this.posA = BoardUtil.getSinglePosition(originalPiece);
		this.posB = BoardUtil.getSinglePosition(movedPiece);
	}
	
	public int getPosA(){
		return posA;
	}
	
	public int getPosB(){
		return posB;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	public List<Move> getMove(){
		if(subMove == null)
			subMove = new ArrayList<Move>();
		
		return subMove;
	}
	
	public int getMoveQte(){
		int result = getMove().size();
		for(Move temp : getMove()){
			result += temp.getMoveQte();
		}
		return result;
	}
}