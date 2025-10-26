package nz.ac.auckland.se283;

import static nz.ac.auckland.se283.Statics.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LevelManagerTest {
  private LevelManager level;

  @BeforeEach
  void setup() {
    level = new LevelManager();
  }

  @Test
  public void getLevel_whenLevelIsLessThanMinimum_ThrowsIllegalArgumentException() {

    assertThrows(IllegalArgumentException.class, () -> level.setLevel(MIN_LEVEL - 1));
  }

  @ParameterizedTest
  @ValueSource(ints = {MIN_LEVEL, MAX_LEVEL})
  public void getLevel_whenLevelIsWithinRange_ValidLevel(int input) {

    level.setLevel(input);

    int actual = level.getLevel();
    assertEquals(input, actual);
  }

  @Test
  public void getLevel_whenLevelIsMoreThanMaximum_ThrowsIllegalArgumentException() {

    assertThrows(IllegalArgumentException.class, () -> level.setLevel(MAX_LEVEL + 1));
  }

  @Test
  public void completeLevel_whenCurrentLevelIsCompleted_IncreaseLevelByOne() {
    level.setLevel(MIN_LEVEL);
    level.completeLevel();
    assertEquals(2, level.getLevel());
  }

  @Test
  public void completeLevel_whenCurrentLevelIsAtMaximum_CongratulateUser() {
    level.setLevel(MAX_LEVEL);
    String message = level.completeLevel();
    assertTrue(message.contains(CONGRATULATORY_MESSAGE));
  }

  @Test
  public void completeLevel_whenCurrentLevelIsCompleted_OutputNextLevel() {
    level.setLevel(MIN_LEVEL);
    String message = level.completeLevel();
    assertTrue(message.contains(LEVEL_COMPLETED_MESSAGE));
  }

  @Test
  public void completeLevel_whenCurrentLevelIsCompleted_GiveReward() {
    level.setLevel(MIN_LEVEL);
    level.completeLevel();
    assertEquals(1, level.getPoints());
  }

  @Test
  public void completeLevel_SetLevelAgainToMin_PointsAreNotGivenAgain() {
    level.setLevel(MIN_LEVEL);
    level.completeLevel();
    level.setLevel(MIN_LEVEL);
    level.completeLevel();
    assertEquals(1, level.getPoints());
  }

  @Test
  void constructor_WhenStoreIsNull_ThrowsNullPointerException() {
    assertThrows(NullPointerException.class, () -> new LevelManager(null, "player1"));
  }

  @Test
  void constructor_WhenAccountIdIsNull_ThrowsNullPointerException() {
    LevelStore mockStore = mock(LevelStore.class);
    assertThrows(NullPointerException.class, () -> new LevelManager(mockStore, null));
  }

  @Test
  void setLevel_PointsBelowLevelThenSetLevelAgain_ReturnsOnePoint() {
    level.setLevel(2);
    int result = level.setPoints();
    assertEquals(1, result);
  }

  @Test
  void setPointsAndsetLevel_pointsEqualToLevel_returnsSameValue() {
    level.setLevel(1);
    level.setPoints();
    assertEquals(1, level.setPoints());
  }

  @Test
  void loadProgress_BlankAccount_returnsNoSavedMessage() {
    LevelManager blankAccount = new LevelManager();
    assertEquals(NO_SAVED_PROGRESS_MESSAGE, blankAccount.loadProgress());
  }
}
