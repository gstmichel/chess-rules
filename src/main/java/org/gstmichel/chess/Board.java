package org.gstmichel.chess;

import org.gstmichel.chess.piece.Color;
import org.gstmichel.chess.piece.PieceEnum;
import org.gstmichel.chess.util.PositionUtil;


public class Board {

	public final long PAWN; 
	public final long ROOK;
	public final long KNIGHT;
	public final long BISHOP;
	public final long QUEEN;
	public final long KING;
	
	public final long WHITE;
	public final long BLACK;
	
	public final boolean blackCastlingA;
	public final boolean blackCastlingH;
	public final boolean whiteCastlingA;
	public final boolean whiteCastlingH;
	
	public final int enPassant;
	public final Color turn;
	public final int nbTurn50;
	
	public Board() {
		this.PAWN = 0x00FF00000000FF00L; 
		this.ROOK = 0x8100000000000081L;
		this.KNIGHT = 0x4200000000000042L;
		this.BISHOP = 0x2400000000000024L;
		this.QUEEN = 0x0800000000000008L;
		this.KING = 0x1000000000000010L;
		
		this.WHITE = 0x000000000000FFFFL;
		this.BLACK = 0xFFFF000000000000L;
		
		this.blackCastlingA = true;
		this.blackCastlingH = true;
		this.whiteCastlingA = true;
		this.whiteCastlingH = true;
		
		this.enPassant = -1;
		this.turn = Color.WHITE;
		
		this.nbTurn50 = 0;
	}
	
	public Board(	long pawn, long rook, long knight, long bishop, long queen, long king, 
					long white, long black,
					boolean blackA, boolean blackH, boolean whiteA, boolean whiteH,
					int passant, Color nextTurn, int cpt50) {
		
		PAWN = pawn; ROOK = rook; KNIGHT = knight; BISHOP = bishop; QUEEN = queen; KING = king;
		WHITE = white; BLACK = black;
		blackCastlingA = blackA; blackCastlingH = blackH;
		whiteCastlingA = whiteA; whiteCastlingH = whiteH;
		
		enPassant = passant;
		turn = nextTurn;
		nbTurn50 = cpt50;
	}
	
	
	
	public boolean isCastlingA(){
		return (turn == Color.WHITE ? whiteCastlingA : blackCastlingA);
	}
	
	public boolean isCastlingH(){
		return (turn == Color.WHITE ? whiteCastlingH : blackCastlingH);
	}
	
//	public Board clone(){
//		try {
//			Board b = (Board)super.clone();
//			return b;
//		} catch (CloneNotSupportedException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public PieceEnum getPieceAt(int position){
		long pieceOrigine = PositionUtil.POSITIONS[position];
		
		if(Long.bitCount(pieceOrigine & PAWN) == 1)
			if(Long.bitCount(pieceOrigine & WHITE) == 1)
				return PieceEnum.WHITE_PAWN;
			else 
				return PieceEnum.BLACK_PAWN;
		if(Long.bitCount(pieceOrigine & ROOK) == 1)
			return PieceEnum.ROCK;
		if(Long.bitCount(pieceOrigine & KNIGHT) == 1)
			return PieceEnum.KNIGHT;
		if(Long.bitCount(pieceOrigine & BISHOP) == 1)
			return PieceEnum.BISHOP;
		if(Long.bitCount(pieceOrigine & QUEEN) == 1)
			return PieceEnum.QUEEN;
		if(Long.bitCount(pieceOrigine & KING) == 1)
			return PieceEnum.KING;
		
		return null;
	}

	public long getCurrent() {
		return turn == Color.WHITE ? WHITE : BLACK;
	}
	
	public long getOpponent() {
		return turn == Color.WHITE ? BLACK : WHITE;
	}
	
	public Board move(int posA, int posB) {
		PieceEnum att = getPieceAt(posA);
		//PieceEnum def = getPieceAt(posB);
		
		long posNeg = ~(PositionUtil.POSITIONS[posA] | PositionUtil.POSITIONS[posB]);
		
		long tmpWhite = WHITE;
		long tmpBlack = BLACK;
		
		long tmpPawn = PAWN; 
		long tmpRook = ROOK;
		long tmpKnight = KNIGHT;
		long tmpBishop = BISHOP;
		long tmpQueen = QUEEN;
		long tmpKing = KING;
		
		//WHIPING FROM COLOR AND PIECE BOARD;
		tmpWhite &= posNeg;
		tmpBlack &= posNeg;
		tmpPawn &= posNeg;
		tmpRook &= posNeg;
		tmpKnight &= posNeg;
		tmpBishop &= posNeg;
		tmpQueen &= posNeg;
		tmpKing &= posNeg;
		
		//ADDING ON COLOR BOARD
		long posBBoard = PositionUtil.POSITIONS[posB];
		if(turn == Color.WHITE){
			tmpWhite |= posBBoard;
		} else {
			tmpBlack |= posBBoard;
		}
		
		boolean bA = blackCastlingA, 
				bH = blackCastlingH, 
				wA = whiteCastlingA, 
				wH = whiteCastlingH;
		
		int 	tmpPassant = enPassant,
				tmp50 = nbTurn50;
		
		//ADDING PIECE BOARD
		switch(att){
		case BLACK_PAWN	:
		case WHITE_PAWN	:
			//Promotion des Pions
			if(posB <= 7 || posB >= 56){
				tmpQueen |= posBBoard;
			} else {
				tmpPawn |= posBBoard;
			}
			
			tmp50 = 0;
			
			//Prendre en passant si c'est le cas
			if(enPassant == posB){
				if(posB < 24){
					tmpWhite &= ~(posBBoard << 8);
					tmpBlack &= ~(posBBoard << 8);
					tmpPawn &= ~(posBBoard << 8);
				} else if(posB > 39){
					tmpWhite &= ~(posBBoard >> 8);
					tmpBlack &= ~(posBBoard >> 8);
					tmpPawn &= ~(posBBoard >> 8);
				}
				
				tmpPassant = -1;
				
			//�tablire en passant
			} else {
				tmpPassant = -1;
				
				//Setter en passant si on as fait un coup double
				//En passant
				if(posB - posA == 16){
					//Blanc
					tmpPassant = posB - 8;
				} else if(posA - posB == 16){
					//Noir
					tmpPassant = posA - 8;
				}
			}
			
			break; //TODO : Promotion...
		case ROCK		:	
			tmpRook |= posBBoard; 
			
			if(bA || bH || wA || wH){
				if(posA == 0) wA = false;
				else if(posA == 7) wH = false;
				else if(posA == 56) bA = false;
				else if(posA == 63) bH = false;
			}
			
			break;
		case KNIGHT		:	tmpKnight |= posBBoard; break;
		case BISHOP		:	tmpBishop |= posBBoard; break;
		case QUEEN		:	tmpQueen |= posBBoard; break;
		case KING		:	
			tmpKing |= posBBoard;
			long posABoard = PositionUtil.POSITIONS[posA];
			if((posABoard >> 1) != posBBoard && (posABoard >> 2) == posBBoard){
				tmpRook ^= (posABoard >> 4);
				tmpRook |= (posABoard >> 1);
				if(turn == Color.WHITE){
					tmpWhite |= (posABoard >> 1);
					tmpWhite ^= (posABoard >> 4);
					wA = false; wH = false;
				} else {
					tmpBlack |= (posABoard >> 1);
					tmpBlack ^= (posABoard >> 4);
					bA = false; bH = false;
				}
			} else if((posABoard << 1) != posBBoard && (posABoard << 2) == posBBoard){
				tmpRook ^= (posABoard << 3);
				tmpRook |= (posABoard << 1);
				if(turn == Color.WHITE){
					tmpWhite |= (posABoard << 1);
					tmpWhite ^= (posABoard << 3);
					wA = false; wH = false;
				} else {
					tmpBlack |= (posABoard << 1);
					tmpBlack ^= (posABoard << 3);
					bA = false; bH = false;
				}
			}
			
			break;
		}
		
		Color tmpColor = turn == Color.WHITE ? Color.BLACK : Color.WHITE;
		
		return new Board(	tmpPawn,  tmpRook,  tmpKnight,  tmpBishop,  tmpQueen,  tmpKing,  
							tmpWhite,  tmpBlack, 
							bA, bH, wA, wH, 
							tmpPassant, tmpColor, tmp50 +1);
	}
}