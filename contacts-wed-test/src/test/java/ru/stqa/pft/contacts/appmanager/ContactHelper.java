package ru.stqa.pft.contacts.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.contacts.modal.ContactData;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void clickEnter() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void returnHomePage() {
    click(By.linkText("home"));
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());
  }

  public void selectContact(String number) {
    click(By.id(number));
  }

  public void clickDelete() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void confirmWindow() {

    wd.switchTo().alert().accept();

  }
}
