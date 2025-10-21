package nz.ac.auckland.se283;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static nz.ac.auckland.se283.Statics.*;

public class ExplainMistakeIntegrationTest {
    

  @Test
  void userInputsTooManyCommands_emptyHistory_InvalidCommandOutput() {
    Response response = new SimpleResponse();
    MistakeHandling mh = new MistakeHandling(response);

    String out = mh.handleCommand(MOVE + " " + LEFT + " " + RIGHT);
    assertEquals(INVALID_COMMAND, out);
  }

  
    
}




