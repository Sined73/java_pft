package ru.stqa.pft.contacts.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void gotoAddNewContact() {
    click(By.linkText("add new"));
  }

  public void gotoModificateContact() {
    click(By.xpath("//img[@alt='Edit']"));
  }
}
