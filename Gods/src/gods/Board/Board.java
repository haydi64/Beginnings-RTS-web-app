package gods.Board;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gods.Direction;
import gods.Entities.Building;
import gods.Entities.Unit;
import gods.Game.MoveValidator;

public class Board
{

	private final int rows, columns;
	private Square selectedSquare;
	private Map<Square, Unit> unitMap;
	private Map<Square, Building> buildingMap;

	public Board(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		unitMap = new HashMap<Square, Unit>();
		buildingMap = new HashMap<Square, Building>();
		selectedSquare = new Square(0, 0);
	}

	public boolean squaresInBounds(Square... squares)
	{
		boolean inBounds = true;
		for (Square square : squares) {
			if (square.getRow() >= rows || square.getRow() < 0
					|| square.getColumn() >= columns || square.getColumn() < 0)
				inBounds = false;
		}
		return inBounds;
	}

	public void render(Graphics g)
	{
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++)
			{
				RenderObject.renderSquare(this, i, j, g, this.getTerrainAt(i, j));
				Building building = this.getBuildingAt(i, j);
				Unit unit = this.getUnitAt(i, j);
				
				if(building != null)
					RenderObject.renderUnit(i, j, building.getType(), g);
				if(unit != null)
					RenderObject.renderUnit(i, j, unit.getType(), g);
					
			}
		}
	}

	public void changeSelectedTile(Direction d)
	{
		int x = selectedSquare.getRow();
		int y = selectedSquare.getColumn();
		
		switch(d) {
			case UP:
				if(y - 1 >= 0)
					selectedSquare = new Square(x, y - 1);
				break;
			case DOWN:
				if(y + 1 < columns)
					selectedSquare = new Square(x, y + 1);
				break;
			case LEFT:
				if(x - 1 >= 0)
					selectedSquare = new Square(x - 1, y);
				break;
			case RIGHT:
				if(x + 1 < rows)
					selectedSquare = new Square(x + 1, y);
				break;
		}
	}

	public void setUnit(int row, int column, Unit unit)
	{
		unitMap.put(new Square(row, column), unit);
	}

	public Unit getUnitAt(int row, int column)
	{
		return unitMap.get(new Square(row, column));
	}

	public Unit getUnitAt(Square sq)
	{
		return unitMap.get(sq);
	}

	public void setBuilding(int row, int column, Building building)
	{
		buildingMap.put(new Square(row, column), building);
	}

	public Building getBuildingAt(int row, int column)
	{
		return buildingMap.get(new Square(row, column));
	}
	
	public Building getBuildingAt(Square sq)
	{
		return buildingMap.get(sq);
	}

	public Terrain getTerrainAt(int row, int column)
	{
		return Terrain.Plain;
	}

	public Square getSelectedSquare()
	{
		return selectedSquare;
	}

	public void setSelectedSquare(Square square)
	{
		this.selectedSquare = square;
	}

	public void printBoard()
	{
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Unit unit = unitMap.get(new Square(i, j));
				if (unit == null)
					System.out.print("------ ");
				else
					unit.print();
			}
			System.out.print('\n');
			for (int j = 0; j < columns; j++) {
				Building build = buildingMap.get(new Square(i, j));
				if (build == null)
					System.out.print("______ ");
				else
					build.print();
			}
			System.out.print('\n');
		}
	}

	public List<Square> squaresInAttackRange(Square square)
	{
		List<Square> squares = new ArrayList<Square>();
		Unit unit = getUnitAt(square);
		int range = unit.getRange();
		int startX = square.getRow() - range;
		int startY = square.getRow() - range;
		int endX = square.getRow() + range;
		int endY = square.getColumn() + range;
		startX = (startX < 0) ? 0 : startX;
		startY = (startY < 0) ? 0 : startY;
		endX = (endX >= rows) ? rows : endX;
		endY = (endY >= columns) ? columns : endY;
		for(int i = startX; i < rows; i++) {
			for(int j = startY; j < columns; j++) {
				Unit toUnit = getUnitAt(i, j);
				Square to = new Square(i, j);
				if(MoveValidator.attackIsValid(square, to, this, unit.getColor()))
					squares.add(new Square(i, j));
			}
		}
		return squares;
	}
}
