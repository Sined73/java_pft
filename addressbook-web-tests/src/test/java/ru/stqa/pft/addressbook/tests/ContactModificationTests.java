package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() throws IOException {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      Properties properties = new Properties();
      String target = System.getProperty("target", "local");
      properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
      app.contact().create(new ContactData().withFirstname(properties.getProperty("contact.name"))
              .withLastname(properties.getProperty("contact.lastname"))
              .withAddress(properties.getProperty("contact.address"))
              .withMobilePhone(properties.getProperty("contact.mobile"))
              .withEmail(properties.getProperty("contact.email")));
      app.goTo().homePage();
    }
  }
  @Test
  public void testContactModification() throws IOException {
    Properties properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId())
            .withFirstname(properties.getProperty("contactrename.name"))
            .withLastname(properties.getProperty("contactrename.lastname"))
            .withAddress(properties.getProperty("contactrename.address"))
            .withMobilePhone(properties.getProperty("contactrename.mobile"))
            .withEmail(properties.getProperty("contactrename.email"));
    app.contact().modify(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }
}
