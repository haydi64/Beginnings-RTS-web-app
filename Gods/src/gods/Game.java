package gods;

public class Game {

	private Board theBoard;
	private GameState state;

	public Game(Board board) {
		theBoard = board;
		state = new GameState(PlayerColor.RED, PlayerColor.BLUE);
	}

	public void moveUnit(int fromRow, int fromColumn, int toRow, int toColumn) {
		boolean validMove;
		try {
			validMove = MoveValidator.moveIsValid(fromRow, fromColumn, toRow, toColumn, theBoard,
					state.getCurrentPlayer());
		} catch (InvalidMoveException e) {
			e.printStackTrace();
			validMove = false;
		}
		if (validMove) {
			Unit fromUnit = theBoard.getUnitAt(fromRow, fromColumn);
			theBoard.setUnit(toRow, toColumn, fromUnit);
			theBoard.setUnit(fromRow, fromColumn, null);
			fromUnit.setMoved(true);
		}
	}

	public void attackUnit(int fromRow, int fromColumn, int toRow, int toColumn) {
		try {
			MoveValidator.attackIsValid(fromRow, fromColumn, toRow, toColumn, theBoard, state.getCurrentPlayer());
			Unit fromUnit = theBoard.getUnitAt(fromRow, fromColumn);
			Unit toUnit = theBoard.getUnitAt(toRow, toColumn);
			AttackResult result = fromUnit.attack(toUnit);
			if (result == AttackResult.AttackerDead)
			{
				theBoard.setUnit(fromRow, fromColumn, null);
				state.removeUnit(fromUnit);
			} else if (result == AttackResult.DefenderDead)
			{
				theBoard.setUnit(toRow, toColumn, null);
				state.removeUnit(toUnit);
			}
			fromUnit.setAttacked(true);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
	}

	public void endTurn() {
		state.hasPlayerLost();  //returns a boolean
		state.nextPlayer();
		if(state.playersLeft() == 1)
		{
			gameOver();
		}
	}
	
	public void addUnit(int row, int column, Unit unit) {
		theBoard.setUnit(row, column, unit);
		state.addUnit(unit);
	}
	
	public void gameOver()
	{
		System.out.println("Player " + state.getCurrentPlayer().toString() + " wins");
	}

	public void printBoard()
	{
		System.out.println("Turn Number: " + state.getTurnNumber() + " Player: " + state.getCurrentPlayer().toString());
		theBoard.printBoard();
	}
	
	public Board getBoard() {
		return theBoard;
	}
	
	public PlayerColor getCurrentPlayer()
	{
		return state.getCurrentPlayer();
	}
}
