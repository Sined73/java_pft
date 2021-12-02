package ru.stqa.pft.contacts;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class CreateContact extends TestBase {

  @Test
  public void testCreateContact() throws Exception {
    gotoAddNewContact();
    fillContactForm(new ContactData("Попов", "Кувшиновка", "Павел", "+79111111112", "mail@gmail.ru"));
    clickEnter();
    returnHomePage();
    logout();
  }

}
