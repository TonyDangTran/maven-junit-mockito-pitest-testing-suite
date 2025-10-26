package nz.ac.auckland.se283;

import static nz.ac.auckland.se283.Statics.*;

public class SimpleResponse implements Response {
  @Override
  public String explainMistake(String command) {
    // checks for null or blank input.
    if (command == null || command.isBlank()) {
      return EMPTY_INPUT;
    }
    // trims and uppercases the input command, checks if valid.
    String inputCommand = command.trim().toUpperCase();
    if (inputCommand.equals(MOVE) || inputCommand.equals(TURN) || inputCommand.equals(STOP)) {
      // returns two possible pre-determined messages.
      return COMMAND_ACCEPTED;
    }
    return INVALID_COMMAND;
  }
}
