package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("Denis", "Kateev", "Moscow city", "+79111111111", "mail@mail.ru", "Test1"), true);
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().goToHomePage();
    app.getSessionHelper().logout();
  }
}
