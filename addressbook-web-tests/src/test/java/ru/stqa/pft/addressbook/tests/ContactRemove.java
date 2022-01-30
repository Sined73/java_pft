package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
  public void testDeleteContactFromGroup() {
    ContactData contact = selectContact();
    GroupData groupForDel = selectedGroup(contact);
    Groups before = contact.getGroups();
    app.goTo().homePage();
    app.contact().selectGroupForDelete(groupForDel.getId());
    app.contact().removeContactFromGroup(contact, groupForDel.getId());
    ContactData contactsAfter = selectContactById(contact);
    Groups after = contactsAfter.getGroups();
    assertThat(after, equalTo(before.without(groupForDel)));
  }

  private ContactData selectContactById(ContactData contact) {
    Contacts contactById = app.db().contacts();
    return contactById.iterator().next().withId(contact.getId());
  }

  private GroupData selectedGroup(ContactData deleteContact) {
    ContactData contact = selectContactById(deleteContact);
    Groups deletedContact = contact.getGroups();
    return deletedContact.iterator().next();
  }

  private ContactData selectContact() {
    Contacts contacts = app.db().contacts();
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() > 0) {
        return contact;
      }
    }
    ContactData addContact = app.db().contacts().iterator().next();
    app.contact().addContactInGroup(addContact, app.db().groups().iterator().next());
    return addContact;
  }
}