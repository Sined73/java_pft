package ru.stqa.pft.contacts.tests;

import org.testng.annotations.Test;

public class DeleteContactTest extends TestBase{

  @Test
  public void testGroupModification() {
    app.getContactHelper().selectContact("16");
    app.getContactHelper().clickDelete();
    app.getContactHelper().confirmWindow();
    app.getContactHelper().returnHomePage();
    app.getSessionHelper().logout();
  }
}
