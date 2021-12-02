package ru.stqa.pft.contacts.tests;

import org.testng.annotations.*;
import ru.stqa.pft.contacts.modal.ContactData;

public class CreateContact extends TestBase {

  @Test
  public void testCreateContact() throws Exception {
    app.getNavigationHelper().gotoAddNewContact();
    app.getContactHelper().fillContactForm(new ContactData("Попов", "Кувшиновка", "Павел", "+79111111112", "mail@gmail.ru"));
    app.getContactHelper().clickEnter();
    app.getContactHelper().returnHomePage();
    app.getSessionHelper().logout();
  }
}
