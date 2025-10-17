package nz.ac.auckland.se283;

import static nz.ac.auckland.se283.Statics.*;

public class SimpleResponse implements Response {
  @Override
  public String explainMistake(String command) {
    if (command == null || command.isBlank()) {
      return EMPTY_INPUT;
    }
    String inputCommand = command.trim().toUpperCase();
    if (inputCommand.equals(MOVE) || inputCommand.equals(TURN) || inputCommand.equals(STOP)) {
      return COMMAND_ACCEPTED;
    }
    return INVALID_COMMAND;
  }
}
