package tests;

import org.testng.annotations.Test;

public class GroupDeletetionTests extends TestBase {

  @Test
  public void testGroupDeletetion() throws Exception {
    app.gotoGroupPage();
    app.selectGroup();
    app.deleteSelectedGroups();
    app.returnToGroupPage();
  }
}
