package nz.ac.auckland.se283;

import static nz.ac.auckland.se283.Statics.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProfileLevelStoreTest {
  private LevelStore store;

  @BeforeEach
  public void setup() {
    store = mock(LevelStore.class);
  }

  @Test
  public void saveLevel_ExitProgrammeWithinRange_SavesLevelForUser() {
    String accountId = "player1";
    LevelManager manager = new LevelManager(store, accountId);
    manager.setLevel(MIN_LEVEL);
    manager.saveProgress();

    verify(store, times(1)).saveLevel(accountId, MIN_LEVEL);
  }

  @Test
  public void loadProgress_WhenSavedLevelExists_UserLevelIsSetToSavedLevel() {

    String accountId = "player2";
    when(store.loadLevel(accountId)).thenReturn(MAX_LEVEL);
    LevelManager manager = new LevelManager(store, accountId);

    String message = manager.loadProgress();

    assertEquals(WELCOME_MESSAGE + MAX_LEVEL, message);
  }

  @Test
  public void loadProgress_NoSavedLevelExists_UserLevelIsSetToMinimumLevel() {

    String accountId = "player3";
    when(store.loadLevel(accountId)).thenReturn(MIN_LEVEL);
    LevelManager manager = new LevelManager(store, accountId);

    String message = manager.loadProgress();

    assertEquals(WELCOME_MESSAGE + MIN_LEVEL, message);
    verify(store, times(1)).loadLevel(accountId);
  }

  @Test
  public void loadProgress_SaveTwoAccountIdThenLoadEach_LevelsAreCorrect() {
    LevelStore store = new ProfileLevelStore();
    LevelManager manager1 = new LevelManager(store, "user1");
    LevelManager manager2 = new LevelManager(store, "user2");

    manager1.setLevel(MIN_LEVEL);
    manager2.setLevel(MAX_LEVEL);
    manager1.saveProgress();
    manager2.saveProgress();

    assertAll(
        () -> assertEquals(MIN_LEVEL, store.loadLevel("user1")),
        () -> assertEquals(MAX_LEVEL, store.loadLevel("user2")));
  }

  @Test
  public void loadProgress_SaveOneAccountIdThenLoadNoSavedLevelExists_LevelsAreCorrect() {
    LevelStore store = new ProfileLevelStore();
    LevelManager manager1 = new LevelManager(store, "user1");
    LevelManager manager2 = new LevelManager(store, "user2");

    manager1.setLevel(MIN_LEVEL);
    manager1.saveProgress();

    String message1 = manager1.loadProgress();
    String message2 = manager2.loadProgress();

    assertAll(
        () -> assertEquals(WELCOME_MESSAGE + MIN_LEVEL, message1),
        () -> assertEquals(WELCOME_MESSAGE + MIN_LEVEL, message2));
  }

  @Test
  public void loadProgress_SaveOneAccountIdWith2Points_PointsAreCorrect() {
    LevelStore store = new ProfileLevelStore();
    LevelManager manager1 = new LevelManager(store, "user1");

    manager1.setLevel(MIN_LEVEL);
    manager1.completeLevel();
    manager1.completeLevel();
    manager1.saveProgress();

    String message1 = manager1.loadProgress();

    assertAll(
        () -> assertEquals(WELCOME_MESSAGE + (MIN_LEVEL + 2), message1),
        () -> assertEquals(2, manager1.getPoints()));
  }

  @Test
  void saveLevel_WhenAccountIdIsNull_ThrowsIllegalArgumentException() {
    ProfileLevelStore store = new ProfileLevelStore();
    assertThrows(IllegalArgumentException.class, () -> store.saveLevel(null, MIN_LEVEL));
  }

  @Test
  void loadPoints_WhenAccountIdIsNull_ThrowsIllegalArgumentException() {
    ProfileLevelStore store = new ProfileLevelStore();
    assertThrows(IllegalArgumentException.class, () -> store.loadPoints(null));
  }
}
