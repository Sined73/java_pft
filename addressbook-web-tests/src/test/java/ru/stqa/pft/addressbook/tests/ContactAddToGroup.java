package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ContactAddToGroup extends TestBase {

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
  public void testContactAddToGroup() {
    app.goTo().homePage();
    ContactData addedContact = selectContacts();
    GroupData groupToAdd = selectGroups(addedContact);
    app.contact().addContactInGroup(addedContact, groupToAdd);
  }

  public GroupData selectGroups(ContactData contact) {
    Groups groups = app.db().groups();
    Set<GroupData> freeGroups = new HashSet<>(groups);
    freeGroups.removeAll(contact.getGroups());
    if (freeGroups.size() == 0) {
      app.goTo().groupPage();
      GroupData added = new GroupData();
      app.group().create(added
              .withName("TestingGroup286")
              .withHeader("TestingGroup200")
              .withFooter("TestingGroup002"));
      freeGroups.add(added);
      app.goTo().homePage();
      freeGroups.add(added);
      Groups reload = app.db().groups();
      Set<GroupData> afterReload = new HashSet<>(reload);
      freeGroups.removeAll(contact.getGroups());
      freeGroups = afterReload;
    }
    return freeGroups.iterator().next();
  }

  public ContactData selectContacts() {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() < groups.size()) {
        return contact;
      }
    }
    return contacts.iterator().next();
  }
}