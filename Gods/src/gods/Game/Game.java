package gods.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import gods.Direction;
import gods.InvalidMoveException;
import gods.PopupMenu;
import gods.Board.Board;
import gods.Board.Square;
import gods.Entities.Actions;
import gods.Entities.AttackResult;
import gods.Entities.Building;
import gods.Entities.GameObject;
import gods.Entities.GameType;
import gods.Entities.Unit;

public class Game
{

	private Board theBoard;
	private GameState state;
	private PopupMenu popup;
	private Square savedSquare;

	public Game(Board board)
	{
		theBoard = board;
		state = new GameState(PlayerColor.RED, PlayerColor.BLUE);
		popup = null;
		savedSquare = null;
	}

	public Game()
	{
		this(new Board(20, 20));
		this.addUnit(2, 2, new Unit(GameType.VILLAGER, PlayerColor.RED));
		this.addUnit(1, 2, new Unit(GameType.SWORD, PlayerColor.RED));
		this.addUnit(18, 18, new Unit(GameType.VILLAGER, PlayerColor.BLUE));
		this.addUnit(18, 17, new Unit(GameType.SWORD, PlayerColor.BLUE));
	}

	public void tryMove() {
		Square from = savedSquare;
		Square to = getSelectedSquare();
		
		boolean isValid = MoveValidator.moveIsValid(from.getRow(), from.getColumn(), to.getRow(), to.getColumn(), theBoard, state.getCurrentPlayer().getColor());
		if(isValid)
		{
			moveUnit(from.getRow(), from.getColumn(), to.getRow(), to.getColumn());
//			savedSquare = null;
			setButtonState(ButtonState.Normal);
		} else {
			theBoard.setSelectedSquare(savedSquare);
			this.addPopup();
		}
	}

	public void moveUnit(int fromRow, int fromColumn, int toRow, int toColumn)
	{
//		boolean validMove;
//		try {
//			validMove = MoveValidator.moveIsValid(fromRow, fromColumn, toRow,
//					toColumn, theBoard, state.getCurrentPlayer().getColor());
//		} catch (InvalidMoveException e) {
//			e.printStackTrace();
//			validMove = false;
//		}
//		if (validMove) {
		Unit fromUnit = theBoard.getUnitAt(fromRow, fromColumn);
		theBoard.setUnit(toRow, toColumn, fromUnit);
		theBoard.setUnit(fromRow, fromColumn, null);
		fromUnit.setMoved(true);
//		}
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
		if (MoveValidator.buildIsValid(row, column, theBoard, gameType,
				state.getCurrentPlayer())) {
			addBuilding(row, column, unit.build(gameType));
			unit.setAttacked(true);
			unit.setMoved(true);
		}
	}

	public void train(int row, int column, GameType gType)
	{
		Building building = theBoard.getBuildingAt(row, column);
		if (MoveValidator.trainIsValid(row, column, theBoard, gType,
				state.getCurrentPlayer())) {
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
		if (popup != null)
			popup.render(g);
	}

	public Square getSelectedSquare()
	{
		return theBoard.getSelectedSquare();
	}

	public void changeSelectedTile(Direction dir)
	{
		theBoard.changeSelectedTile(dir);
	}

	public void addPopup()
	{
		Square selected = getSelectedSquare();
//		GameObject obj = getGameObjectAt(selected.getRow(), selected.getColumn());
		Unit unit = theBoard.getUnitAt(selected.getRow(), selected.getColumn());
		if (unit != null) {
			popup = new PopupMenu(selected, unit, Color.gray);
			setButtonState(ButtonState.Popup);
		}
	}

	public void removePopup()
	{
		popup = null;
	}

	// public boolean hasPopup() {
	// return popup != null;
	// }

	public void cycleActions(Direction dir)
	{
		popup.cycleActions(dir);
	}

	public void selectAction()
	{
		if(popup == null)
		{
			System.out.print("popup was not there");
			return;
		}
		Actions action = popup.getAction();
		switch (action) {
			case Cancel:
				setButtonState(ButtonState.Normal);
				break;
			case Move:
				savedSquare = getSelectedSquare();
				setButtonState(ButtonState.MoveUnit);
				break;
			case Attack:
				savedSquare = getSelectedSquare();
				setButtonState(ButtonState.AttackUnit);
				break;
			case Build:
				break;
			case Train:
				break;
			default:
				break;
		}
		// Implement this through game state
		// Might have to have different popup menus
		// Could add a abstract popup menu
//		removePopup();
	}

	public GameObject getGameObjectAt(int row, int column)
	{
		GameObject obj;
		if (theBoard.getUnitAt(row, column) != null)
			obj = theBoard.getUnitAt(row, column);
		else
			obj = theBoard.getBuildingAt(row, column);
		return obj;
	}

	public ButtonState getButtonState()
	{
		return state.getButtonState();
	}
	
	private void setButtonState(ButtonState buttonState)
	{
		if(buttonState != ButtonState.Popup)
			popup = null;
		if(buttonState == ButtonState.Normal)
		{
			savedSquare = null;
		}
		state.setButtonState(buttonState);
	}
}
