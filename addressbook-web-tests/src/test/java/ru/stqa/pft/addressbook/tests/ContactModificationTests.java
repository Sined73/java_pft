package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification() {
    app.getNavigationHelper().gotoModificateContact();
    app.getContactHelper().fillContactForm(new ContactData("Попович", "Кемерово", "Алёша", "+79111155112", "mail2@gmail.ru", null), false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().goToHomePage();
    app.getSessionHelper().logout();
  }
}
