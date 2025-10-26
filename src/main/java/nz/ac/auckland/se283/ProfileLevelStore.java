package nz.ac.auckland.se283;

import static nz.ac.auckland.se283.Statics.*;

import java.util.HashMap;
import java.util.Map;

public class ProfileLevelStore implements LevelStore {
  private final Map<String, Integer> levelData = new HashMap<>();
  private final Map<String, Integer> pointsData = new HashMap<>();

  @Override
  public void saveLevel(String accountId, int level) {
    if (accountId == null || accountId.isEmpty()) {
      throw new IllegalArgumentException(INVALID_ACCOUNT_ID);
    }
    levelData.put(accountId, level);
  }

  @Override
  public int loadLevel(String accountId) {
    if (accountId == null || accountId.isEmpty()) {
      throw new IllegalArgumentException(INVALID_ACCOUNT_ID);
    }

    return levelData.getOrDefault(accountId, MIN_LEVEL);
  }

  @Override
  public void savePoints(String accountId, int points) {
    if (accountId == null || accountId.isEmpty()) {
      throw new IllegalArgumentException(INVALID_ACCOUNT_ID);
    }
    pointsData.put(accountId, points);
  }

  @Override
  public int loadPoints(String accountId) {
    if (accountId == null || accountId.isEmpty()) {
      throw new IllegalArgumentException(INVALID_ACCOUNT_ID);
    }
    return pointsData.getOrDefault(accountId, 0);
  }
}
