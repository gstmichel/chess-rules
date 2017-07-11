package org.gstmichel.chess;

import static org.junit.Assert.*;

import org.junit.Test;

import org.gstmichel.chess.piece.Color;
import org.gstmichel.chess.util.BoardUtil;
import org.gstmichel.chess.util.PositionUtil;

public class BoardTest {

	public void testWCaslingA(){
		Board b = new Board(0l,
				PositionUtil.POSITIONS[0] | PositionUtil.POSITIONS[7] | PositionUtil.POSITIONS[56] | PositionUtil.POSITIONS[63], 
				0, 0, 0, 
				PositionUtil.POSITIONS[4] | PositionUtil.POSITIONS[60],
				PositionUtil.POSITIONS[0] | PositionUtil.POSITIONS[4] | PositionUtil.POSITIONS[7],
				PositionUtil.POSITIONS[56] | PositionUtil.POSITIONS[60] | PositionUtil.POSITIONS[63],
				true, true, true, true, -1, Color.BLACK, 0);
		
		BoardUtil.showBoard(b);
		b = b.move(60, 62);
		BoardUtil.showBoard(b);
	}
	
	@Test public void enPassant(){
		Board b = new Board();
		
		BoardUtil.showBoard(b);
		b = b.move(8, 24);
		b = b.move(55, 39);
		b = b.move(24, 32);
		b = b.move(49, 33);
		b = b.move(32, 41);
		b = b.move(39, 31);
		b = b.move(14, 30);
		b = b.move(31, 22);
		BoardUtil.showBoard(b);
		
	}
}
