package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

import java.time.Duration;

public class ApplicationManager {

  protected WebDriver wd;

  private ContactHelper contactHelper;
  private SessionHelper sessionHelper;
  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;
  private final Browser browser;

  public ApplicationManager(Browser browser) {

    this.browser = browser;
  }

  public void init() {
    if (browser.equals(Browser.CHROME)) {
      wd = new ChromeDriver();
    } else if (browser.equals(Browser.FIREFOX)) {
      wd = new FirefoxDriver();
    } else if (browser.equals(Browser.EDGE)) {
      wd = new EdgeDriver();
    }
//    wd = new ChromeDriver();
//    wd = new FirefoxDriver();
//    wd = new EdgeDriver();
    wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    wd.get("http://localhost/addressbook/");
    groupHelper = new GroupHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    contactHelper = new ContactHelper(wd);
    sessionHelper.login("admin", "secret");
  }

  public void stop() {
    wd.quit();
  }

  public GroupHelper getGroupHelper() {
    return groupHelper;
  }

  public NavigationHelper getNavigationHelper() {
    return navigationHelper;
  }

  public ContactHelper getContactHelper() {
    return contactHelper;
  }

  public SessionHelper getSessionHelper() {
    return sessionHelper;
  }
}
