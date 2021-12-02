package ru.stqa.pft.contacts.tests;

import org.testng.annotations.*;

public class CreateContact extends TestBase {

  @Test
  public void testCreateContact() throws Exception {
    app.gotoAddNewContact();
    app.fillContactForm(new ContactData("Попов", "Кувшиновка", "Павел", "+79111111112", "mail@gmail.ru"));
    app.clickEnter();
    app.returnHomePage();
    app.logout();
  }
}
