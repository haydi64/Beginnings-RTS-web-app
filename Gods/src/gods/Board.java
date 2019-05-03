package gods;

import java.util.HashMap;
import java.util.Map;

public class Board
{

	private final int rows, columns;
	private Map<Square, Unit> map;

	public Board(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		map = new HashMap<Square, Unit>();

//		for (int i = 0; i < rows; i++)
//			for (int j = 0; j < columns; j++)
//				map.put(new Square(i, j), new Unit(UnitType.NONE));
	}
	
	public void addUnit(int row, int column, Unit unit)
	{
		map.put(new Square(row, column), unit);
	}

	public Unit getUnitAt(int row, int column)
	{
		return map.get(new Square(row, column));
	}
	
	public Terrain getTerrainAt(int row, int column)
	{
		return Terrain.Plain;
	}
	
	public void printBoard()
	{
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++)
			{
				Unit unit = map.get(new Square(i, j));
				if(unit == null) System.out.print("_____ ");
				else unit.print();
			}
			System.out.print('\n');
		}
	}
}
