package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class GroupDeletetionTests extends tests.TestBase {

  @Test
  public void testGroupDeletetion() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupPage();
  }
}
