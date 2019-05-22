package gods.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import gods.GameLoop;
import gods.Board.Board;
import gods.Board.RenderObject;
import gods.Board.Square;
import gods.Entities.Actions;
import gods.Entities.AttackResult;
import gods.Entities.Building;
import gods.Entities.GameObject;
import gods.Entities.GameType;
import gods.Entities.Unit;
import gods.View.BuildPopup;
import gods.View.ButtonState;
import gods.View.Direction;
import gods.View.MyPopupMenu;
import gods.View.TrainPopup;
import gods.View.UnitPopup;
import gods.save.Save;

/**
 * 
 * comment
 */
public class Game implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047979186423690212L;
	private Board theBoard;
	private GameState state;
	private MyPopupMenu popup;
	private Square savedSquare;
	private List<Square> possibleMoves;

	public Game(Board board)
	{
		theBoard = board;
		state = new GameState(PlayerColor.RED, PlayerColor.BLUE);
		popup = null;
		savedSquare = null;
		possibleMoves = new ArrayList<Square>();
	}

	public Game()
	{
		this(new Board(20, 20));
		this.addUnit(2, 2, new Unit(GameType.VILLAGER, PlayerColor.RED));
		this.addUnit(1, 2, new Unit(GameType.SWORD, PlayerColor.RED));
//		this.addUnit(3, 3, new Unit(GameType.SPEAR, PlayerColor.blue));
		this.addUnit(18, 18, new Unit(GameType.VILLAGER, PlayerColor.BLUE));
		this.addUnit(18, 17, new Unit(GameType.SWORD, PlayerColor.BLUE));
	}

	public void tryMove()
	{
		Square from = savedSquare;
		Square to = getSelectedSquare();

		boolean isValid = MoveValidator.moveIsValid(from.getRow(), from.getColumn(),
				to.getRow(), to.getColumn(), theBoard,
				state.getCurrentPlayer().getPlayerColor());
		if (isValid) {
			moveUnit(from.getRow(), from.getColumn(), to.getRow(), to.getColumn());
			setButtonState(ButtonState.Normal);
		} else {
			theBoard.setSelectedSquare(savedSquare);
			this.addPopup();
		}
	}

	public void moveUnit(int fromRow, int fromColumn, int toRow, int toColumn)
	{
		Unit fromUnit = theBoard.getUnitAt(fromRow, fromColumn);
		theBoard.setUnit(toRow, toColumn, fromUnit);
		theBoard.setUnit(fromRow, fromColumn, null);
		fromUnit.setMoved(true);
	}

	public void tryAttack()
	{
		Square from = savedSquare;
		Square to = getSelectedSquare();
		boolean isValid = MoveValidator.attackIsValid(from.getRow(),
				from.getColumn(), to.getRow(), to.getColumn(), theBoard,
				state.getCurrentPlayer().getPlayerColor());

		if (isValid) {
			attackUnit(from.getRow(), from.getColumn(), to.getRow(), to.getColumn());
			setButtonState(ButtonState.Normal);
		} else {
			theBoard.setSelectedSquare(savedSquare);
			this.addPopup();
		}
	}

	public void attackUnit(int fromRow, int fromColumn, int toRow, int toColumn)
	{
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
	}

	public void build(int row, int column, GameType gameType)
	{
		Unit unit = theBoard.getUnitAt(row, column);
		if (MoveValidator.buildIsValid(row, column, theBoard, gameType,
				state.getCurrentPlayer())) {
			Building building = unit.build(gameType);
			state.takeCost(building);
			addBuilding(row, column, building);
			unit.setAttacked(true);
			unit.setMoved(true);
		}
	}

	public void train(int row, int column, GameType gType)
	{
		Building building = theBoard.getBuildingAt(row, column);
		if (MoveValidator.trainIsValid(row, column, theBoard, gType,
				state.getCurrentPlayer())) {
			Unit unit = building.train(gType);
			state.takeCost(unit);
			addUnit(row, column, unit);
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
		state.gameOver();
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
		for(Square s: possibleMoves)
			RenderObject.renderOverlay(s.getRow(), s.getColumn(), g, state.getButtonState());
		if (popup != null)
			popup.render(g);
		if (state.isGameOver()) {
			//TEST
			g.setColor(Color.DARK_GRAY);
			g.fillRect(GameLoop.WIDTH / 2, GameLoop.HEIGHT, 100, 50);
			g.drawString("GAME OVER", GameLoop.WIDTH / 2 + 25, GameLoop.HEIGHT / 2 + 25);
		}
	}
	
	public void renderInfo(Graphics g)
	{
		state.renderPlayerInfo(g);
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
		Unit unit = theBoard.getUnitAt(selected);
		Building building = theBoard.getBuildingAt(selected);
		if (unit != null && unit.getPlayerColor() == state.getCurrentPlayer().getPlayerColor()) {
			popup = new UnitPopup(selected, unit);
			setButtonState(ButtonState.Popup);
		} else if (building != null
				&& building.getPlayerColor() == state.getCurrentPlayer().getPlayerColor()) {
			popup = new TrainPopup(selected, building);
			setButtonState(ButtonState.Popup);
		}
	}

	public void removePopup()
	{
		popup = null;
	}

	public void changePossibleTile(Direction dir)
	{
		theBoard.changePossibleTile(possibleMoves, dir);
	}

	public void cycleActions(Direction dir)
	{
		popup.cycleActions(dir);
	}

	public void selectAction()
	{
		if (popup == null) {
			System.out.print("popup was not there");
			return;
		}
		Actions action = Actions.stringToActions(popup.getCurrentItem());
		GameType type = GameType.stringToType(popup.getCurrentItem());
		Square selected = getSelectedSquare();
		if (action != null)
			newAction(action, selected);
		else if (type != null) {
			if (type.isUnit())
				this.train(selected.getRow(), selected.getColumn(), type);
			else
				this.build(selected.getRow(), selected.getColumn(), type);
			setButtonState(ButtonState.Normal);
		}
	}

	private void newAction(Actions action, Square selectedSquare)
	{
		//Move this function?
		switch (action) {
			case Cancel:
				setButtonState(ButtonState.Normal);
				break;
			case Move:
				savedSquare = selectedSquare;
				possibleMoves = theBoard.squaresInMoveRange(selectedSquare);
				setButtonState(ButtonState.MoveUnit);
				break;
			case Attack:
				savedSquare = selectedSquare;
				possibleMoves = theBoard.squaresInAttackRange(selectedSquare);
				if(possibleMoves.size() > 0)
					theBoard.setSelectedSquare(possibleMoves.get(0));
				setButtonState(ButtonState.AttackUnit);
				break;
			case Build:
				List<GameType> buildings = new ArrayList<GameType>();
				for (GameType type : GameType.values())
					if (!type.isUnit())
						buildings.add(type);
				popup = new BuildPopup(selectedSquare, buildings);
				break;
			case Train:
				break;
			default:
				break;
		}
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
		if (buttonState != ButtonState.Popup)
			popup = null;
		if (buttonState == ButtonState.Normal) {
			savedSquare = null;
			this.possibleMoves = new ArrayList<Square>();
		}
		state.setButtonState(buttonState);
	}
	public void save() {
		Save.save(this, "save.ser");
	}
}
