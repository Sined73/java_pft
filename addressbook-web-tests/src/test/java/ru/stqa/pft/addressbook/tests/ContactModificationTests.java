package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    int before = app.getContactHelper().getContactCount();
    if (!app.getNavigationHelper().isThereAModButton()) {
      app.getContactHelper().createContact(new ContactData("Denis", "Kateev", "Moscow city",
              "+79111111111", "mail@mail.ru", "Test1"), true);
      app.getNavigationHelper().goToHomePage();
    }
    app.getNavigationHelper().gotoModificateContact();
    app.getContactHelper().fillContactForm(new ContactData("Алёша", "Попович", "Ростов",
            "+79111155112", "mail2@gmail.ru", "[none]"), false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().goToHomePage();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before);
    app.getSessionHelper().logout();
  }
}
