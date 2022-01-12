package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    logger.info("Загрузка контактов из файла .xml");
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    logger.info("Загрузка контактов из файла .json");
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType());
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test
  public void testContactCreation() throws Exception {
    logger.info("Открыть стартовую страницу");
    app.goTo().homePage();
    logger.info("Считать контакты «до» создания");
    Contacts before = app.db().contacts();
    logger.info("Перейти на страницу создания контактаи создать новый контакт");
    Groups groups = app.db().groups();
    Properties properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
    ContactData newContact = new ContactData().withFirstname(properties.getProperty("contact.name"))
            .withLastname(properties.getProperty("contact.lastname"))
            .withAddress(properties.getProperty("contact.address"))
            .withEmail(properties.getProperty("contact.email"))
            .withEmail2(properties.getProperty("contact.email2"))
            .withEmail3(properties.getProperty("contact.email3"))
            .withMobilePhone(properties.getProperty("contact.mobile"))
            .withWorkPhone(properties.getProperty("contact.workPhone"))
            .withHomePhone(properties.getProperty("contact.homePhone"))
            .withSecondPhone(properties.getProperty("contact.phone2"))
            .inGroup(groups.iterator().next());
    app.contact().create(newContact);
    logger.info("Вернуться на стартовую страницу");
    app.goTo().homePage();
    logger.info("Проверить, что список контактов увеличился на 1");
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    logger.info("Считать контакты «после» создания");
    Contacts after = app.db().contacts();
    logger.info("Проверить, что создался нужный контакт");
    assertThat(after, equalTo(
            before.withAdded(newContact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    verifyContactListInUi();
  }


}
