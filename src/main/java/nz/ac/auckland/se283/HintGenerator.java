package nz.ac.auckland.se283;

import static nz.ac.auckland.se283.Statics.*;

import java.util.List;
import java.util.Random;

public class HintGenerator {
  private static final List<String> HINTS = List.of(HINT_1, HINT_2, HINT_3);

  private long lastCommandTime;
  private final Random random;
  private final Time clock;

  public HintGenerator(Random random, Time clock) {
    this.random = random;
    this.clock = clock;
    this.lastCommandTime = clock.getCurrentTime();
  }

  public void recordCommand() {
    this.lastCommandTime = clock.getCurrentTime();
  }

  public String checkForHint() {
    if (clock.getCurrentTime() - lastCommandTime >= 5000) {
      return HINTS.get(random.nextInt(HINTS.size()));
    }
    return null;
  }
}
