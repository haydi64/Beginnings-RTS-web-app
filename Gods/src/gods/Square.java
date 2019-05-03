package gods;

public class Square
{
	private final int column;
	private final int row;

	/**
	 * Default constructor
	 */
	public Square(int row, int column)
	{
		this.row = row;
		this.column = column;
	}

	/**
	 * gets the Manhattan distance from this square to another.
	 * 
	 * @param to
	 *            the square that is being compared with
	 * @return an integer for the number of squares away
	 */
	public int getManDistance(Square to)
	{
		return Math.abs(this.row - to.row) + Math.abs(this.column - to.column);
	}

	/**
	 * @return the column
	 */
	public int getColumn()
	{
		return column;
	}

	/**
	 * @return the row
	 */
	public int getRow()
	{
		return row;
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Square)) {
			return false;
		}
		Square other = (Square) obj;
		if (column != other.column) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}

}
