package ru.stqa.pft.mantis.appmanager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.BrowserType;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
public class ApplicationManager {
  private final Properties properties;
  private WebDriver wd;
  private final String browser;
  private RegistrationHelper registrationHelper;
  private FtpHelper ftp;
  private MailHelper mailHelper;
  private JamesHelper jamesHelper;
  private DbHelper dbHelper;
  private SoapHelper soapHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }
  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
  }
  public void stop() {
    if (wd != null) {
      wd.quit();
    }
  }
  public HttpSession newSession() {
    return new HttpSession(this);
  }
  public String getProperty(String key) {
    return properties.getProperty(key);
  }
  public RegistrationHelper registration() {
    if (registrationHelper == null) {
      registrationHelper = new RegistrationHelper(this);
    }
    return registrationHelper;
  }
  public FtpHelper ftp() {
    if (ftp == null) {
      ftp = new FtpHelper(this);
    }
    return ftp;
  }
  public WebDriver getDriver() {
    if (wd == null) {
      switch (browser) {
        case BrowserType.CHROME:
          wd = new ChromeDriver();
          break;
        case BrowserType.FIREFOX:
          wd = new FirefoxDriver();
          break;
        case BrowserType.IE:
          wd = new InternetExplorerDriver();
          break;
        case BrowserType.EDGE:
          wd = new EdgeDriver();
          break;
        case BrowserType.OPERA:
          wd = new OperaDriver();
          break;
      }
      wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
      wd.get(properties.getProperty("web.baseUrl"));
    }
    return wd;
  }
  public MailHelper mail() {
    if (mailHelper == null) {
      mailHelper = new MailHelper(this);
    }
    return mailHelper;
  }
  public JamesHelper james() {
    if (jamesHelper == null) {
      jamesHelper = new JamesHelper(this);
    }
    return jamesHelper;
  }
  public DbHelper db() {
    if (dbHelper == null) {
      dbHelper = new DbHelper(this);
    }
    return dbHelper;
  }

  public SoapHelper soap() {
    if (soapHelper == null) {
      soapHelper = new SoapHelper(this);
    }
    return soapHelper;
  }

}