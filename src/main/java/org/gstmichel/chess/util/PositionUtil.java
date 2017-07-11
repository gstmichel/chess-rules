package org.gstmichel.chess.util;


public class PositionUtil {
	
	//A list of bords representing all position available
	public static final long[] POSITIONS = {
		(long) Math.pow(2,0),
		(long) Math.pow(2,1),
		(long) Math.pow(2,2),
		(long) Math.pow(2,3),
		(long) Math.pow(2,4),
		(long) Math.pow(2,5),
		(long) Math.pow(2,6),
		(long) Math.pow(2,7),
		(long) Math.pow(2,8),
		(long) Math.pow(2,9),
		(long) Math.pow(2,10),
		(long) Math.pow(2,11),
		(long) Math.pow(2,12),
		(long) Math.pow(2,13),
		(long) Math.pow(2,14),
		(long) Math.pow(2,15),
		(long) Math.pow(2,16),
		(long) Math.pow(2,17),
		(long) Math.pow(2,18),
		(long) Math.pow(2,19),
		(long) Math.pow(2,20),
		(long) Math.pow(2,21),
		(long) Math.pow(2,22),
		(long) Math.pow(2,23),
		(long) Math.pow(2,24),
		(long) Math.pow(2,25),
		(long) Math.pow(2,26),
		(long) Math.pow(2,27),
		(long) Math.pow(2,28),
		(long) Math.pow(2,29),
		(long) Math.pow(2,30),
		(long) Math.pow(2,31),
		(long) Math.pow(2,32),
		(long) Math.pow(2,33),
		(long) Math.pow(2,34),
		(long) Math.pow(2,35),
		(long) Math.pow(2,36),
		(long) Math.pow(2,37),
		(long) Math.pow(2,38),
		(long) Math.pow(2,39),
		(long) Math.pow(2,40),
		(long) Math.pow(2,41),
		(long) Math.pow(2,42),
		(long) Math.pow(2,43),
		(long) Math.pow(2,44),
		(long) Math.pow(2,45),
		(long) Math.pow(2,46),
		(long) Math.pow(2,47),
		(long) Math.pow(2,48),
		(long) Math.pow(2,49),
		(long) Math.pow(2,50),
		(long) Math.pow(2,51),
		(long) Math.pow(2,52),
		(long) Math.pow(2,53),
		(long) Math.pow(2,54),
		(long) Math.pow(2,55),
		(long) Math.pow(2,56),
		(long) Math.pow(2,57),
		(long) Math.pow(2,58),
		(long) Math.pow(2,59),
		(long) Math.pow(2,60),
		(long) Math.pow(2,61),
		(long) Math.pow(2,62),
		(long) Math.pow(2,63)+1};
	
	/**
	 * This Function will return a board representing the sliding move of a 
	 * piece.<br/><br/>
	 * <b>Performance :</b> About 0.506 seconds for 10M of execution
	 * 
	 * @param startingPos The starting position of the piece
	 * @param endingPos The final position of the piece
	 * @return A long representing the hexadecimal board of the sliding move 
	 */
	public static long getSlideBoard(int startingPos, int endingPos, boolean remove){
		
		//Initialisation of the board calculation variable
		int yMod = 0, xMod = 0; //Modifier to the X and Y axis while moving
		long board = 0x0L;		//Board representing the slide
		
		//the list of variable to calculate the modifier for x and y axis;
		int modS = startingPos % 8,
			modE = endingPos % 8,
			divS = startingPos / 8,
			divE = endingPos / 8;
		
		//Calculating the modifier on the X axis
		if(modS > modE)		{ xMod = -1; }
		else if(modS < modE){ xMod =  1; }

		//Calculating the modifier on the Y axis
		if(divS > divE) 	{ yMod = -1; } 
		else if(divS < divE){ yMod =  1; }
		
		//Incrementaly add the position between start and stop to the final board
		while(startingPos != endingPos){
			startingPos = startingPos + xMod + (yMod * 8);
			board = board | POSITIONS[startingPos];
		}
		
		if(remove){
			//Removing the ending position
			board = board ^ POSITIONS[endingPos];
		}
		
		return board;
	}
	
	public static long getSlideBoard(int startingPos, int endingPos){
		return getSlideBoard(startingPos, endingPos, true);
	}
	
	/**
	 * This function translate an Alphanumeric position in numeric one in 
	 * such a way that A1=0, B1=1, C1=2... H8 = 63<br/><br/>
	 * <b>Performance :</b> About 0.625 seconds for 10M of execution
	 * 
	 * @param pos The alphanumeric position
	 * @return The numeric position corresponding
	 */
	public static int translatePosition(String pos) {
		int result = (Integer.parseInt(pos.substring(1)) - 1) * 8;
		if(result > 8*7 || result < 0) throw new IllegalArgumentException("The 2nd character must be from 1 to 8");
		
		switch(pos.charAt(0)){
			case 'A' : return result;
			case 'B' : return result + 1;
			case 'C' : return result + 2;
			case 'D' : return result + 3;
			case 'E' : return result + 4;
			case 'F' : return result + 5;
			case 'G' : return result + 6;
			case 'H' : return result + 7;
		}
		
		throw new IllegalArgumentException("The 1st value must be from A to H");
	}
	
	public static String translatePosition(int position) {
		switch(position % 8){
			case 0 : return "A" + (position / 8 + 1);
			case 1 : return "B" + (position / 8 + 1);
			case 2 : return "C" + (position / 8 + 1);
			case 3 : return "D" + (position / 8 + 1);
			case 4 : return "E" + (position / 8 + 1);
			case 5 : return "F" + (position / 8 + 1);
			case 6 : return "G" + (position / 8 + 1);
			case 7 : return "H" + (position / 8 + 1);
		}
		
		return null;
	}
	
	public static long getBoardForPositions(int[] lst){
		long board = 0;
		for(int i : lst){
			board = board | POSITIONS[i];
		}
		return board;
	}
}