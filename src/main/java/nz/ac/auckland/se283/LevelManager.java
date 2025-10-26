package nz.ac.auckland.se283;

import static nz.ac.auckland.se283.Statics.*;

import java.util.HashSet;
import java.util.Set;

public class LevelManager {

  private int level;
  private int points;
  private final LevelStore store;
  private final String accountId;
  private final Set<Integer> completedLevels = new HashSet<>();

  public LevelManager() {
    this.store = null;
    this.accountId = null;
    this.level = MIN_LEVEL;
    this.points = 0;
  }

  public LevelManager(LevelStore store, String accountId) {
    if (store == null || accountId == null) {
      throw new NullPointerException(INVALID_LEVEL_MANAGER_PARAMETERS);
    }
    this.store = store;
    this.accountId = accountId;
  }

  public void setLevel(int level) {
    if (level < MIN_LEVEL || level > MAX_LEVEL) {
      throw new IllegalArgumentException(INVALID_LEVEL_MESSAGE);
    }
    this.level = level;
  }

  public int getLevel() {
    return this.level;
  }

  // completeLevel() increments level by 1, when 100 is reached, congratulatory message is run and
  // no longer increments.
  public String completeLevel() {

    if (this.level == 100) { // congratulatory message
      return CONGRATULATORY_MESSAGE;
    }
    if (!completedLevels.contains(
        this.level)) { // if level hasn't been completed before, give points /reward.
      setPoints();
      completedLevels.add(this.level);
    }
    this.level += 1;

    return LEVEL_COMPLETED_MESSAGE + this.level;
  }

  public int getPoints() {
    return this.points;
  }

  public int setPoints() {
    if (this.points >= getLevel()) {
      return this.points;
    }
    return this.points += 1;
  }

  public void saveProgress() {
    if ((store != null) && (accountId != null)) {
      store.saveLevel(accountId, level);
      store.savePoints(accountId, points);
    }
  }

  public String loadProgress() {
    if ((store != null) && (accountId != null)) {
      int savedLevel = store.loadLevel(accountId);
      this.level = savedLevel;
      this.points = store.loadPoints(accountId);
      return WELCOME_MESSAGE + savedLevel;
    }
    return NO_SAVED_PROGRESS_MESSAGE;
  }
}
