package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ContactAddToGroup extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() throws IOException {
    Properties properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withFirstname(properties.getProperty("contact.name"))
              .withLastname(properties.getProperty("contact.lastname"))
              .withAddress(properties.getProperty("contact.address"))
              .withMobilePhone(properties.getProperty("contact.mobile"))
              .withEmail(properties.getProperty("contact.email")));
    }
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData()
              .withName(properties.getProperty("group.name"))
              .withHeader(properties.getProperty("group.header"))
              .withFooter(properties.getProperty("group.footer")));
    }
  }
}
