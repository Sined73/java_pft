package ru.stqa.pft.contacts.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper {
  private WebDriver wd;

  public NavigationHelper(WebDriver wd) {
    this.wd = wd;
  }

  public void gotoAddNewContact() {
    wd.findElement(By.linkText("add new")).click();
  }
}
