package gods;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import gods.Board.Board;
import gods.Board.Terrain;
import gods.Entities.Building;
import gods.Entities.GameType;
import gods.Entities.Unit;
import gods.Game.Game;
import gods.Game.MoveValidator;

class Test1 {
	Board testBoard;
	Game testGame;

	@BeforeEach
	void setup() {
		testBoard = new Board(10, 10);
		testGame = new Game(testBoard);
		testGame.addUnit(0, 0, new Unit(GameType.SPEAR, Color.RED));
		testGame.addUnit(0, 4, new Unit(GameType.SPEAR, Color.RED));
		testGame.addUnit(1, 1, new Unit(GameType.SWORD, Color.BLUE));
	}

	@Test
	void checkUnits() {
		assertEquals(testBoard.getUnitAt(0, 0).getType(), GameType.SPEAR);
		assertEquals(testBoard.getUnitAt(1, 1).getType(), GameType.SWORD);
	}

	@Test
	void moveUnit() {
		testGame.moveUnit(0, 0, 1, 0);
		assertEquals(testBoard.getUnitAt(1, 0).getType(), GameType.SPEAR);
	}

	@Test
	void terrainIsPlain() {
		assertEquals(testBoard.getTerrainAt(0, 0), Terrain.Plain);
	}

	@Test
	void checkColor() {
		assertEquals(testBoard.getUnitAt(0, 0).getColor(), Color.RED);
	}

	@ParameterizedTest
	@MethodSource("moveLimitProvider")
	void moveLimit(int tr, int tc, int fr, int fc, Color color) {
		assertEquals(false, MoveValidator.moveIsValid(tr, tc, fr, fc, testBoard, color));
	}

	static Stream<Arguments> moveLimitProvider() {
		return Stream.of(
				// Too far
				Arguments.of(0, 0, 8, 0, Color.RED),
				// Too far
				Arguments.of(0, 0, 4, 4, Color.RED),
				// Cannot move onto another unit
				Arguments.of(0, 0, 0, 4, Color.RED),
				// Out of bounds
				Arguments.of(-1, 0, 0, 0, Color.RED),
				// Out of bounds
				Arguments.of(0, 12, 0, 0, Color.RED));
	}

	@ParameterizedTest
	@MethodSource("validAttackProvider")
	void validAttack(int tr, int tc, int fr, int fc, Color color) {
		testGame.moveUnit(0, 0, 1, 0);
		assertEquals(false, MoveValidator.attackIsValid(tr, tc, fr, fc, testBoard, color));
	}

	static Stream<Arguments> validAttackProvider() {
		return Stream.of(
				// Out of bounds
				Arguments.of(-1, 0, 0, 0, Color.RED),
				// Out of bounds
				Arguments.of(0, 12, 0, 0, Color.RED),
				//null unit attacks
				Arguments.of(0, 0, 1, 0, Color.RED),
				//attack null unit
				Arguments.of(1, 0, 2, 0, Color.RED),
				//attack out of range
				Arguments.of(0, 4, 1, 1, Color.RED));
	}
	
	@Test
	void testAttack()
	{
		System.out.println("--------Battle------");
		testGame.moveUnit(0, 0, 1, 0);
		testGame.attackUnit(1, 0, 1, 1);
		testGame.endTurn();
		testGame.endTurn();
		testGame.attackUnit(1, 0, 1, 1);
		testGame.endTurn();
		testGame.endTurn();
		testGame.attackUnit(1, 0, 1, 1);
		testGame.endTurn();
		assertNull(testGame.getBoard().getUnitAt(1, 1));
		System.out.println("---------End Battle--------");
	}
	
	@Test
	void testAttack1()
	{
		System.out.println("--------Battle1------");
		testGame.moveUnit(0, 0, 1, 0);
		testGame.endTurn();
		testGame.attackUnit(1, 1, 1, 0);
		testGame.endTurn();
		testGame.endTurn();
		testGame.attackUnit(1, 1, 1, 0);
		testGame.endTurn();
		testGame.endTurn();
		testGame.attackUnit(1, 1, 1, 0);
		testGame.endTurn();
		assertNull(testGame.getBoard().getUnitAt(1, 0));
		System.out.println("---------End Battle1--------");
	}
	
	@Test
	void testVillagerActions()
	{
		Unit v = new Unit(GameType.VILLAGER, Color.RED);
		assertEquals(3, v.getActions().size());
	}
	
	@Test
	void testBuildings()
	{
		testBoard.setBuilding(0, 0, new Building(GameType.TOWN_HALL, Color.RED));
		assertEquals(GameType.TOWN_HALL, testBoard.getBuildingAt(0, 0).getType());
	}
	
	@Test
	void testGameBuilding()
	{
//		testGame.addBuilding(0, 0, new Building(GameType.TOWN_HALL, Color.RED));
//		testGame.printBoard();
		testGame.addUnit(2, 2, new Unit(GameType.VILLAGER, Color.RED));
		testGame.build(2, 2, GameType.TOWN_HALL);
		testGame.endTurn();
		testGame.endTurn();
		testGame.moveUnit(2, 2, 2, 4);
		testGame.printBoard();
		testGame.train(2, 2, GameType.VILLAGER);
		assertEquals(GameType.VILLAGER, testGame.getBoard().getUnitAt(2, 2).getType());
	}

}
