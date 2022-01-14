package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroup extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
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
  public void testContactAddToGroup() {
    app.goTo().homePage();
    ContactData addedContact = selectContacts();
    Groups before = addedContact.getGroups();
    GroupData groupToAdd = selectGroups(addedContact);
    app.contact().addContactInGroup(addedContact, groupToAdd);
    Contacts contacts = app.db().contacts();
    Groups after = null;
    for (ContactData contact : contacts) {
      if (contact.getId() == addedContact.getId()) {
        after = contact.getGroups();
      }
    }
    assertThat(after, equalTo(before.withAdded(groupToAdd)));
  }

  public GroupData selectGroups(ContactData contact) {
    Groups groups = app.db().groups();
    Set<GroupData> freeGroups = new HashSet<>(groups);
    return freeGroups.iterator().next();
  }

  public ContactData selectContacts() {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    int i = contacts.size();
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() < groups.size()) {
        return contact;
      }
      if (contact.getGroups().size() == groups.size()) {
        i = i - 1;
      }
    }
    if (i == 0) {
      app.goTo().groupPage();
      Contacts contacts2 = app.db().contacts();
      GroupData added = new GroupData();
      app.group().create(added
              .withName("TestingGroup286")
              .withHeader("TestingGroup200")
              .withFooter("TestingGroup002"));
      app.goTo().homePage();
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
