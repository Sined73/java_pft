package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    if (!app.getNavigationHelper().isThereAModButton()) {
      app.getContactHelper().createContact(new ContactData("Denis", "Kateev", "Moscow city",
              "+79111111111", "mail@mail.ru", "[none]"));
      app.getNavigationHelper().goToHomePage();
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getNavigationHelper().gotoModificateContact(before.size() - 1);
    app.getContactHelper().fillContactForm(new ContactData("Алёша", "Попович", "Ростов",
            "+79111155112", "mail2@gmail.ru", "[none]"), false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().goToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());
    app.getSessionHelper().logout();
  }
}
