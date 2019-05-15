package gods.Board;

import java.util.HashMap;
import java.util.Map;
import gods.Entities.Building;
import gods.Entities.Unit;

public class Board {

	private final int rows, columns;
	private Map<Square, Unit> unitMap;
	private Map<Square, Building> buildingMap;

	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		unitMap = new HashMap<Square, Unit>();
		buildingMap = new HashMap<Square, Building>();
	}

	public void setUnit(int row, int column, Unit unit) {
		unitMap.put(new Square(row, column), unit);
	}

	public Unit getUnitAt(int row, int column) {
		return unitMap.get(new Square(row, column));
	}

	public void setBuilding(int row, int column, Building building) {
		buildingMap.put(new Square(row, column), building);
	}

	public Building getBuildingAt(int row, int column) {
		return buildingMap.get(new Square(row, column));
	}

	public Terrain getTerrainAt(int row, int column) {
		return Terrain.Plain;
	}

	public boolean squaresInBounds(Square... squares) {
		boolean inBounds = true;
		for (Square square : squares) {
			if (square.getRow() >= rows || square.getRow() < 0 || square.getColumn() >= columns
					|| square.getColumn() < 0)
				inBounds = false;
		}
		return inBounds;
	}

	public void printBoard() {
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
}
