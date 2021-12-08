package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().createContact(new ContactData("Denis", "Kateev", "Moscow city", "+79111111111", "mail@mail.ru", "Test1"), true);
    app.getNavigationHelper().goToHomePage();
    app.getSessionHelper().logout();
  }
}
