package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.IOException;

public class ContactRemove extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() throws IOException {
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withFirstname("Bulgakov")
              .withLastname("Michail")
              .withAddress("Moscow, Flowers street, 15, ap.78")
              .withMobilePhone("8926354785")
              .withEmail("voland666@mail.ru"));
    }
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData()
              .withName("test65")
              .withHeader("test81")
              .withFooter("test71"));
    }
  }
  @Test
  public void testContactRemoveFromGroup() {
    app.goTo().homePage();
    ContactData addedContact = selectContacts();
    GroupData groupToAdd = selectGroups(addedContact);
    app.contact().removeContactFromGroup(addedContact, groupToAdd);
  }

  public GroupData selectGroups(ContactData contact) {

    return contact.getGroups().iterator().next();
  }

  public ContactData selectContacts() {
    Contacts contacts = app.db().contacts();
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() > 0) {
        return contact;
      }
    }
    return contacts.iterator().next();
  }
}
