package gods;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Test1
{
	Board testBoard;
	Game testGame;

	@BeforeEach
	void setup()
	{
		testBoard = new Board(10, 10);
		testBoard.addUnit(0, 0, new Unit(UnitType.SPEAR, UnitColor.RED));
		testBoard.addUnit(0, 4, new Unit(UnitType.SPEAR, UnitColor.RED));
		testBoard.addUnit(1, 1, new Unit(UnitType.SWORD, UnitColor.BLUE));
		testGame = new Game(testBoard);
	}
	
	
	@Test
	void checkUnits()
	{
		assertEquals(testBoard.getUnitAt(0, 0).getType(), UnitType.SPEAR);
		assertEquals(testBoard.getUnitAt(1, 1).getType(), UnitType.SWORD);
	}
	
	@Test
	void moveUnit()
	{
		testGame.moveUnit(0, 0, 1, 0);
		assertEquals(testBoard.getUnitAt(1, 0).getType(), UnitType.SPEAR);

		testBoard.printBoard();
	}
	
	@Test
	void terrainIsPlain()
	{
		assertEquals(testBoard.getTerrainAt(0, 0), Terrain.Plain);
	}
	
	@Test
	void checkColor()
	{
		assertEquals(testBoard.getUnitAt(0, 0).getColor(), UnitColor.RED);
	}
	
	@ParameterizedTest
	@MethodSource("moveLimitProvider")
	void moveLimit(int tr, int tc, int fr, int fc)
	{
		assertThrows(InvalidMoveException.class, () -> MoveValidator.moveIsValid(tr, tc, fr, fc, testBoard));
	}
	
	static Stream<Arguments> moveLimitProvider()
	{
		return Stream.of(Arguments.of(0, 0, 8, 0),
				Arguments.of(0, 0, 4, 4),
				Arguments.of(0, 0, 0, 4));
	}

}
