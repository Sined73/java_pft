package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemove extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData()
              .withName("test65")
              .withHeader("test81")
              .withFooter("test71"));
    }
    if (app.db().contacts().size() == 0) {
      Groups groups = app.db().groups();
      app.goTo().homePage();
      app.contact().create(new ContactData().withFirstname("Bulgakov")
              .withLastname("Michail")
              .withAddress("Moscow, Flowers street, 15, ap.78")
              .withMobilePhone("8926354785")
              .withEmail("voland666@mail.ru")
              .inGroup(groups.iterator().next()));
    }
  }
  @Test
  public void testContactRemoveFromGroup() {
    app.goTo().homePage();
    ContactData addedContact = selectContacts();
    Groups before = addedContact.getGroups();
    GroupData groupToAdd = selectGroups(addedContact);
    app.contact().removeContactFromGroup(addedContact, groupToAdd);
    Contacts contacts = app.db().contacts();
    Groups after = null;
    for (ContactData contact : contacts) {
      if (contact.getId() == addedContact.getId()) {
        after = contact.getGroups();
      }
    }
    assertThat(after, equalTo(before.without(groupToAdd)));
  }

  public GroupData selectGroups(ContactData contact) {
    return contact.getGroups().iterator().next();
  }

  public ContactData selectContacts() {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    int i = contacts.size();
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() > 0) {
        return contact;
      }
      if (contact.getGroups().size() == 0) {
        i = i - 1;
      }
    }
    if (i == 0) {
      app.contact().create(new ContactData()
              .withFirstname("Gorky")
              .withLastname("Maxim")
              .withAddress("Moscow, Arbat street, 18, ap.30")
              .withMobilePhone("89055555555")
              .withEmail("dno54@mail.ru")
              .inGroup(groups.iterator().next()));
      Contacts contacts2 = app.db().contacts();
      for (ContactData contact2 : contacts2) {
        if (contact2.getGroups().size() > 0) {
          return contact2;
        }
      }
      contacts = contacts2;
    }
    return contacts.iterator().next();
  }
}