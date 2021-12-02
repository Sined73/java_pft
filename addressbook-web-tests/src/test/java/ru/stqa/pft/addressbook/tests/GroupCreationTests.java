package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GruopData;

public class GroupCreationTests extends tests.TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.gotoGroupPage();
    app.initGroupCreation();
    app.fillGroupForm(new GruopData("Test1", "Test2", "Test3"));
    app.submitGroupCreation();
    app.returnToGroupPage();
  }
}
