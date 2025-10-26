package nz.ac.auckland.se283;

import static nz.ac.auckland.se283.Statics.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HintGeneratorTest {
  private Random mockRandom;
  private Time mockClock;
  private HintGenerator hint;

  @BeforeEach
  void setup() {
    mockRandom = mock(Random.class);
    mockClock = mock(Time.class);
    when(mockRandom.nextInt(anyInt())).thenReturn(0);
  }

  @Test
  void checkForHint_When5SecondsPassed_ReturnsHint() {
    when(mockClock.getCurrentTime()).thenReturn(0L, 6000L);
    hint = new HintGenerator(mockRandom, mockClock);

    String result = hint.checkForHint();

    assertEquals(HINT_1, result);
  }

  @Test
  void checkForHint_WhenLessThan5Seconds_NoHintGenerated() {
    when(mockClock.getCurrentTime()).thenReturn(0L, 4000L);
    hint = new HintGenerator(mockRandom, mockClock);
    String result = hint.checkForHint();

    assertNull(result);
  }

  @Test
  void recordCommand_ResetsTimerBefore5SecondsAreUp_NoHintsGiven() {
    when(mockClock.getCurrentTime()).thenReturn(0L, 6000L, 6000L, 9000L);
    hint = new HintGenerator(mockRandom, mockClock);
    hint.checkForHint();
    hint.recordCommand();

    String result = hint.checkForHint();

    assertNull(result);
  }
}
