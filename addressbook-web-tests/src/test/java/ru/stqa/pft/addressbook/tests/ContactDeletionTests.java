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

public class ContactDeletionTests extends TestBase{

  @BeforeMethod

  public void ensurePreconditions() throws IOException {
    logger.info("Открыть стартовую страницу");
    app.goTo().homePage();
    logger.info("Проверка контактов на странице");
    if (app.db().contacts().size() == 0) {
      Properties properties = new Properties();
      String target = System.getProperty("target", "local");
      properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
      logger.info("Контактов нет. Создать новый контакт");
      app.contact().create(new ContactData().withFirstname(properties.getProperty("contact.name"))
              .withLastname(properties.getProperty("contact.lastname"))
              .withAddress(properties.getProperty("contact.address"))
              .withMobilePhone(properties.getProperty("contact.mobile"))
              .withEmail(properties.getProperty("contact.email")));
      logger.info("Контакт создан");
    }
    logger.info("Перейти на стартовую страницу");
    app.goTo().homePage();
  }

  @Test
  public void testContactDeletion() {
    logger.info("Считать контакты «до» удаления");
    Contacts before = app.db().contacts();
    logger.info("Выбрать удаляемый контакт");
    ContactData deletedContact = before.iterator().next();
    logger.info("Удалить контакт");
    app.contact().delete(deletedContact);
    logger.info("Перейти на стартовую страницу");
    app.goTo().homePage();
    logger.info("Проверить, что список контактов уменьшился на 1");
    assertThat(app.contact().count(), equalTo(before.size() - 1));
    logger.info("Считать контакты «после» удаления");
    Contacts after = app.db().contacts();
    logger.info("Проверить, что удалился нужный контакт");
    assertThat(after, equalTo(before.without(deletedContact)));
  }
 }