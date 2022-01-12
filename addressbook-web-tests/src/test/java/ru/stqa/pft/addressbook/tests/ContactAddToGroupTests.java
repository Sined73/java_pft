package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ContactAddToGroupTests extends TestBase {

  @BeforeMethod
  public void insurePreconditions() throws IOException, IOException {
    Properties properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withFirstname(properties.getProperty("contact.name"))
              .withLastname(properties.getProperty("contact.lastName")));
    }
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName(properties.getProperty("group.name"))
              .withHeader(properties.getProperty("group.header"))
              .withFooter(properties.getProperty("group.footer")));
    }
  }
  @Test
  public void testAddContactInGroup() throws Exception {
    logger.info("Открыть стартовую страницу");
    app.goTo().homePage();
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    ContactData addedContact = contacts.iterator().next();
    GroupData groupToAdd = groups.iterator().next();
    app.contact().addContactInGroup(addedContact, groupToAdd);
    //logger.info("Выбрать группу, если групп нет, создать");
    //logger.info("Проверить список контактов в группе до добавления");
    //logger.info("Перейти ко всем контактам в группах");
    logger.info("Выбрать контакт");
    logger.info("Выбрать группу");
    logger.info("Нажать добавить");
    logger.info("Перейти на страницу выбранной группы");
    logger.info("проверить список после добавления");
  }
}
