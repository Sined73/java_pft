package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() {
    List<Object[]> list = new ArrayList<Object[]>();
    File photo = new File("src/test/resources/panda.png");
    File photo2 = new File("src/test/resources/mouse.png");
    File photo3 = new File("src/test/resources/cat.png");
    list.add(new Object[] {new ContactData().withFirstname("Alexey").withLastname("Katuev").withAddress("Санкт-Петербург," +
                    "пр-т Невский, д. 15, корп.15/4, кв. 2032").withMobilePhone("+79111111111")
            .withEmail("mail@mail.ru").withPhoto(photo)});
    list.add(new Object[] {new ContactData().withFirstname("Николай").withLastname("Басков").withAddress("Москва," +
                    "ул. Арбат , д. 15, корп.23, кв. 236").withMobilePhone("+79159563257")
            .withEmail("mail2@mail.ru").withPhoto(photo2)});
    list.add(new Object[] {new ContactData().withFirstname("Семен").withLastname("Горбунков").withAddress("Черноморск," +
                    "ул. Морская , д. 21, кв. 9").withMobilePhone("88264957496")
            .withEmail("mail2084@mail.ru").withPhoto(photo3)});
    return list.iterator();
  }

  @Test(dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
}
