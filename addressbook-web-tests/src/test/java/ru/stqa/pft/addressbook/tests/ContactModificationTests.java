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
    app.goTo().homePage();
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData().withFirstname("Denis").withLastname("Kateev").withAddress("Moscow city").
              withMobile("+79111111111").withEmail("mail@mail.ru"));
      app.goTo().homePage();
    }
  }
  @Test
  public void testContactModification() {
    List<ContactData> before = app.contact().list();
    int indexModContact = before.size() - 1;
    ContactData contact = new ContactData().withId(before.get(indexModContact).getId()).withFirstname("Алёша").
            withLastname("Попович").withAddress("Ulianovsk").withMobile("89263517596").withEmail("dakateye@mail.ru");
    app.contact().modify(indexModContact, contact);
    app.goTo().homePage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(indexModContact);
    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }


}
