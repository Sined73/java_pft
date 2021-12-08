package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification() {
    if (! app.getNavigationHelper().isThereAModButton()) {
      app.getContactHelper().createContact(new ContactData("Denis", "Kateev", "Moscow city", "+79111111111", "mail@mail.ru", "Test1"), true);
      app.getNavigationHelper().goToHomePage();
    }
    app.getNavigationHelper().gotoModificateContact();
    app.getContactHelper().fillContactForm(new ContactData("Алёша", "Попович", "Ростов", "+79111155112", "mail2@gmail.ru", null), false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().goToHomePage();
    app.getSessionHelper().logout();
  }
}
