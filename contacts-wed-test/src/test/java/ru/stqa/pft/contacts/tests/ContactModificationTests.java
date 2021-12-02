package ru.stqa.pft.contacts.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.contacts.modal.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification () {
    app.getNavigationHelper().gotoModificateContact();
    app.getContactHelper().fillContactForm(new ContactData("Попович", "Кемерово", "Алёша", "+79111155112", "mail2@gmail.ru"));
    app.getContactHelper().clickUpdate();
    app.getContactHelper().returnHomePage();
    app.getSessionHelper().logout();
  }
}
