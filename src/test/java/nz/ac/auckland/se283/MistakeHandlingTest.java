package nz.ac.auckland.se283;

import static nz.ac.auckland.se283.Statics.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MistakeHandlingTest {
  private MistakeHandling mistakeHandle;
  private Response response;

  @BeforeEach
  void setup() {
    response = mock(Response.class);
    mistakeHandle = new MistakeHandling(response);
  }

  @Test
  public void undo_NoUndoHistory_InvalidUndo() {
    assertThrows(IllegalArgumentException.class, () -> mistakeHandle.undo());
  }

  @Test
  public void undo_WhenThereIsUndoHistory_SuccessfulUndo() {
    mistakeHandle.record(FORWARDS);
    String result = mistakeHandle.undo();
    assertTrue(result.contains(SUCESSFUL_UNDO + FORWARDS));
  }

  @Test
  public void undo_WhenThereIsTwoActions_UndoLastActionOnly() {
    mistakeHandle.record(FORWARDS);
    mistakeHandle.record(BACKWARDS);
    String result = mistakeHandle.undo();
    assertTrue(result.contains(SUCESSFUL_UNDO + BACKWARDS));
  }

  @Test
  public void undo_WhenThereIsMultipleActionsAndUndoAll_UndoSuccessfulButLastUndoFails() {
    mistakeHandle.record(FORWARDS);
    mistakeHandle.record(BACKWARDS);
    mistakeHandle.record(LEFT);
    mistakeHandle.undo();
    mistakeHandle.undo();
    mistakeHandle.undo();
    assertThrows(IllegalArgumentException.class, () -> mistakeHandle.undo());
  }

  @Test
  public void handleCommand_WhenInvalidCommand_AIProvidesShortExplanation() {
    String invalid = "jump";
    when(response.explainMistake(invalid)).thenReturn(INVALID_COMMAND);

    String result = mistakeHandle.handleCommand(invalid);

    verify(response, times(1)).explainMistake(invalid);
    assertTrue(result.split(" ").length < 20);
  }

  @Test
  public void clear_NoHistory_HistoryRemainsEmpty() {
    mistakeHandle.clear();
    assertEquals(0, mistakeHandle.getSize());
  }

  @Test
  public void clear_HasHistory_HistoryBecomesEmpty() {
    mistakeHandle.record(FORWARDS);
    mistakeHandle.record(BACKWARDS);
    mistakeHandle.clear();
    assertEquals(0, mistakeHandle.getSize());
  }

  @Test
  public void clear_HasHistoryThenCallUndo_InvalidUndo() {
    mistakeHandle.record(FORWARDS);
    mistakeHandle.record(BACKWARDS);
    mistakeHandle.clear();
    assertThrows(IllegalArgumentException.class, () -> mistakeHandle.undo());
  }

  @Test
  void constructor_WhenResponseIsNull_ThrowsNullPointerException() {
    assertThrows(NullPointerException.class, () -> new MistakeHandling(null));
  }

  @Test
  void handleCommand_WhenCommandIsNull_ThrowsNullPointerException() {
    Response mockResponse = mock(Response.class);
    MistakeHandling handler = new MistakeHandling(mockResponse);
    assertThrows(NullPointerException.class, () -> handler.handleCommand(null));
  }

  @Test
  void record_WhenCommandIsEmpty_ThrowsException() {
    Response mockResponse = mock(Response.class);
    MistakeHandling handler = new MistakeHandling(mockResponse);
    assertThrows(IllegalArgumentException.class, () -> handler.record("   "));
  }
  @Test
  public void isValidDirection_InvalidDirection_ReturnsFalse() {
    String invalidDirection = "UPWARDS";
    mistakeHandle.handleCommand("MOVE " + invalidDirection);
    boolean result = mistakeHandle.isValidDirection(invalidDirection);
    assertEquals(false, result);
  }
  @ParameterizedTest
  @ValueSource(strings = {LEFT,RIGHT,BACKWARDS,FORWARDS})
  public void isValidDirection_ValidDirection_ReturnsTrue(String input) {
    boolean result = mistakeHandle.isValidDirection(input);
    assertEquals(true, result);
  }
  @ParameterizedTest
  @ValueSource(strings = {MOVE, TURN})
  public void isValidAction_InputIsMove_ReturnsTrue(String input) {
    boolean result = mistakeHandle.isValidAction(input);
    assertEquals(true, result);
  }
  @Test
  public void isValidAction_InputIsStop_ReturnsTrue() {
    boolean result = mistakeHandle.isValidAction(STOP);
    assertEquals(true, result);
  }
  @Test
  public void isValidAction_InputIsInvalid_ReturnsFalse() {
    boolean result = mistakeHandle.isValidAction(LEFT);
    assertEquals(false, result);
  }

  @Test
  public void isValidCommand_InputIsStopOnly_ReturnsTrue() {
    boolean result = mistakeHandle.isValidCommand(STOP);
    assertEquals(true, result);
  }
  @Test
  public void handleCommand_CommandIsValid_ReturnsCommandAccepted() {
    String command = MOVE + " " + LEFT;
    String result = mistakeHandle.handleCommand(command);
    assertEquals(COMMAND_ACCEPTED, result);
  }
  @Test
  public void isValidCommand_OneActionIsValidOneDirectionIsInvalid_ReturnsFalse() {
    boolean result = mistakeHandle.isValidCommand(MOVE + " " + MOVE);
    assertEquals(false, result);
  }
  @Test
  public void isValidCommand_BothInputsnvalid_ReturnsFalse() {
    boolean result = mistakeHandle.isValidCommand(LEFT + " " + MOVE);
    assertEquals(false, result);
  }


}
