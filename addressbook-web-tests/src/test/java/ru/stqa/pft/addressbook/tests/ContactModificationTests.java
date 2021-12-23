package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.getNavigationHelper().goToHomePage();
    if (!app.getNavigationHelper().isThereAModButton()) {
      app.getContactHelper().createContact(new ContactData("Denis", "Kateev", "Moscow city",
              "+79111111111", "mail@mail.ru", "[none]"));
      app.getNavigationHelper().goToHomePage();
    }
  }
  @Test
  public void testContactModification() {
    List<ContactData> before = app.getContactHelper().getContactList();
    int indexModContact = before.size() - 1;
    ContactData contact = new ContactData(before.get(before.size()-1).getId(), "Алёша", "Попович",
            "Ulianovsk", "89263517596", "dakateye@mail.ru", "[none]");
    app.getContactHelper().modifyContact(indexModContact, contact);
    app.getNavigationHelper().goToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(indexModContact);
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }


}
