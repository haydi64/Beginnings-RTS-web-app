package gods.Game;

import java.awt.Graphics;
import gods.Direction;
import gods.InvalidMoveException;
import gods.Board.Board;
import gods.Board.Square;
import gods.Entities.AttackResult;
import gods.Entities.Building;
import gods.Entities.GameType;
import gods.Entities.Unit;

public class Game
{

	private Board theBoard;
	private GameState state;

	public Game(Board board)
	{
		theBoard = board;
		state = new GameState(PlayerColor.RED, PlayerColor.BLUE);
	}

	public Game()
	{
		theBoard = new Board(20, 20);
		state = new GameState(PlayerColor.RED, PlayerColor.BLUE);
		this.addUnit(2, 2, new Unit(GameType.VILLAGER, PlayerColor.RED));
		this.addUnit(1, 2, new Unit(GameType.SWORD, PlayerColor.RED));
		this.addUnit(18, 18, new Unit(GameType.VILLAGER, PlayerColor.BLUE));
		this.addUnit(18, 17, new Unit(GameType.SWORD, PlayerColor.BLUE));
	}

	public void moveUnit(int fromRow, int fromColumn, int toRow, int toColumn)
	{
		boolean validMove;
		try {
			validMove = MoveValidator.moveIsValid(fromRow, fromColumn, toRow,
					toColumn, theBoard, state.getCurrentPlayer().getColor());
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

	public void attackUnit(int fromRow, int fromColumn, int toRow, int toColumn)
	{
		try {
			MoveValidator.attackIsValid(fromRow, fromColumn, toRow, toColumn,
					theBoard, state.getCurrentPlayer().getColor());
			Unit fromUnit = theBoard.getUnitAt(fromRow, fromColumn);
			Unit toUnit = theBoard.getUnitAt(toRow, toColumn);
			AttackResult result = fromUnit.attack(toUnit);
			if (result == AttackResult.AttackerDead) {
				theBoard.setUnit(fromRow, fromColumn, null);
				state.removeGameObject(fromUnit);
			} else if (result == AttackResult.DefenderDead) {
				theBoard.setUnit(toRow, toColumn, null);
				state.removeGameObject(toUnit);
			}
			fromUnit.setAttacked(true);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
	}

	public void build(int row, int column, GameType gameType)
	{
		Unit unit = theBoard.getUnitAt(row, column);
		if (MoveValidator.buildIsValid(row, column, theBoard, gameType, state.getCurrentPlayer())) {
			addBuilding(row, column, unit.build(gameType));
			unit.setAttacked(true);
			unit.setMoved(true);
		}
	}

	public void train(int row, int column, GameType gType)
	{
		Building building = theBoard.getBuildingAt(row, column);
		if (MoveValidator.trainIsValid(row, column, theBoard, gType, state.getCurrentPlayer())) {
			addUnit(row, column, building.train(gType));
		}
	}

	public void endTurn()
	{
		state.hasPlayerLost(); // returns a boolean
		state.nextPlayer();
		if (state.playersLeft() <= 1) {
			gameOver();
		}
	}

	public void addUnit(int row, int column, Unit unit)
	{
		theBoard.setUnit(row, column, unit);
		state.addGameObject(unit);
	}

	public void addBuilding(int row, int column, Building building)
	{
		theBoard.setBuilding(row, column, building);
		state.addGameObject(building);
	}

	public void gameOver()
	{
		System.out
				.println("Player " + state.getCurrentPlayer().toString() + " wins");
	}

	public void printBoard()
	{
		System.out.println("Turn Number: " + state.getTurnNumber() + " Player: "
				+ state.getCurrentPlayer().toString());
		theBoard.printBoard();
	}

	public Board getBoard()
	{
		return theBoard;
	}

	public void render(Graphics g)
	{
		theBoard.render(g);
	}

	public Square getSelectedSquare()
	{
		return theBoard.getSelectedSquare();
	}

	public void changeSelectedTile(Direction dir)
	{
		theBoard.changeSelectedTile(dir);
	}
}
