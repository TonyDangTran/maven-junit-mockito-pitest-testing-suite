package nz.ac.auckland.se283;

import static nz.ac.auckland.se283.Statics.*;

import java.util.Stack;

public class MistakeHandling {
  private Stack<String> history = new Stack<>();
  private final Response response;
  private String direction;
  private String action;

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
    String[] parts = command.trim().split("\\s+");
    if (parts.length != 2) {
      return false;
  }

    action = parts[0].toUpperCase();
    direction = parts[1].toUpperCase();

    return isValidAction(action) || isValidDirection(direction);
}

private boolean isValidAction(String action) {
    if ( action.equals(MOVE) || action.equals(TURN) || action.equals(STOP)) 
    {
      return true;
    }
    return false;
}

  private boolean isValidDirection(String command) {
    if ((direction.equals(LEFT))
        || (direction.equals(RIGHT))
        || (direction.equals(FORWARDS))
        || (direction.equals(BACKWARDS))) {
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
