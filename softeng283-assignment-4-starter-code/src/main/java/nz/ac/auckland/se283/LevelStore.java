package nz.ac.auckland.se283;

public interface LevelStore {

  void saveLevel(String accountId, int level);

  int loadLevel(String accountId);

  void savePoints(String accountId, int points);

  int loadPoints(String accountId);
}
