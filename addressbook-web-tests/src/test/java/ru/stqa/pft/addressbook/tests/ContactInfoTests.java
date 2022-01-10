package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInfoTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() throws IOException {
    logger.info("Открыть стартовую страницу");
    app.goTo().homePage();
    logger.info("Проверка контактов на странице");
    if (app.contact().all().size() == 0) {
      Properties properties = new Properties();
      String target = System.getProperty("target", "local");
      properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
      logger.info("Контактов нет. Создать новый контакт");
      app.contact().create(new ContactData().withFirstname(properties.getProperty("contact.name"))
              .withLastname(properties.getProperty("contact.lastname"))
              .withAddress(properties.getProperty("contact.address"))
              .withMobilePhone(properties.getProperty("contact.mobile"))
              .withWorkPhone(properties.getProperty("contact.workPhone"))
              .withHomePhone(properties.getProperty("contact.homePhone"))
              .withSecondPhone(properties.getProperty("contact.workPhone2"))
              .withEmail(properties.getProperty("contact.email"))
              .withEmail2(properties.getProperty("contact.email2"))
              .withEmail3(properties.getProperty("contact.email3")));
      logger.info("Контакт создан");
    }
    logger.info("Перейти на стартовую страницу");
    app.goTo().homePage();
  }

  @Test
  public void testContactPhones() {
    logger.info("Перейти на стартовую страницу");
    app.goTo().homePage();
    logger.info("Выбрать контакт для проверки данных");
    ContactData contact = app.contact().all().iterator().next();
    logger.info("Просмотреть данные на странице редактирования");
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    logger.info("Сверить номера телефонов");
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    logger.info("Сверить Email");
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    logger.info("Сверить адрес");
    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(), contact.getSecondPhone())
            .stream().filter((s) -> ! s.equals(""))
            .map(ContactInfoTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String phone) {
    return phone.replaceAll("\\s","").replaceAll("[-()]","");
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((e) -> ! e.equals(""))
            .collect(Collectors.joining("\n"));
  }
}
