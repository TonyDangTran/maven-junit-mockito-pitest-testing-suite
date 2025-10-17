package nz.ac.auckland.se283;

import static nz.ac.auckland.se283.Statics.*;

import java.util.Stack;

public class MistakeHandling {
  private Stack<String> history = new Stack<>();
  private final Response response;

  public MistakeHandling(Response response) {
    if (response == null) {
      throw new NullPointerException(NULL_RESPONSE);
    }

    this.response = response;
  }

  public void record(String command) {
    if (command.trim().isEmpty()) {
      throw new IllegalArgumentException(EMPTY_INPUT);
    }
    history.push(command);
  }

  public String undo() {
    if (history.isEmpty()) {
      throw new IllegalArgumentException(INVALID_UNDO);
    }
    return SUCESSFUL_UNDO + history.pop();
  }

  public String handleCommand(String command) {
    if (isValidCommand(command)) {
      return COMMAND_ACCEPTED;
    }
    return response.explainMistake(command);
  }

  private boolean isValidCommand(String command) {
    String normalized = command.trim().toLowerCase();
    if ((normalized.equals(MOVE)) || (normalized.equals(TURN)) || (normalized.equals(STOP))) {
      return true;
    }
    return false;
  }

  public void clear() {
    history.clear();
  }

  public int getSize() {
    return history.size();
  }
}
