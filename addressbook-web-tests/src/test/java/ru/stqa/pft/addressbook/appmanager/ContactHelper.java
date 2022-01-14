package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactHelper extends HelperBase{

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    attach(By.name("photo"), contactData.getPhoto());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("phone2"), contactData.getSecondPhone());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());


        if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByIndex(0);
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
  }

//    if (creation) {
//      if (contactData.getGroups().size() > 0) {
//        Assert.assertTrue(contactData.getGroups().size() == 1);
//        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups()
//                .iterator().next().getName());
//      } else {
//        Assert.assertFalse(isElementPresent(By.name("new_group")));
//      }
//    }
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void submitContactModification() {
    click(By.xpath("//div[@id='content']/form/input[22]"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.xpath("//input[@value='" + id + "']")).click();
  }

  public void deleteSelectedContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void confirmWindow() {
    wd.switchTo().alert().accept();
  }

  public void selectGroupForAdd(GroupData group) {
    new Select(wd.findElement(By.xpath("//select[@name='to_group']"))).selectByValue(String.valueOf(group.getId()));
  }

  public void removeFromGroup() {
    wd.findElement(By.xpath("//input[@name='remove']")).click();
  }

  public void selectGroupForDelete(GroupData group) {
    new Select(wd.findElement(By.xpath("//select[@name='group']"))).selectByValue(String.valueOf(group.getId()));
  }

  public void addToGroup() {
    click(By.xpath("//input[@value='Add to']"));
  }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    contactCache = null;
  }

  public void gotoModificateContactById(int id) {
    if (isElementPresent(By.tagName("h1"))
            && wd.findElement(By.tagName("h1")).getText().equals("Edit / add address book entry")
            && isElementPresent(By.name("update"))) {
      return;
    }
    wd.findElement(By.xpath("//a[@href='edit.php?id=" + id + "']")).click();
  }

  public void modify(ContactData contact) {
    selectContactById(contact.getId());
    gotoModificateContactById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    contactCache = null;
  }

  public void addContactInGroup(ContactData contact, GroupData group) {
    selectContactById(contact.getId());
    selectGroupForAdd(group);
    addToGroup();
  }

  public void removeContactFromGroup(ContactData contact, GroupData group) {
    selectGroupForDelete(group);
    selectContactById(contact.getId());
    removeFromGroup();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public Contacts contactCache = null;

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContact();
    contactCache = null;
    confirmWindow();
  }

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name = 'entry']"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.xpath(".//td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String firstname = cells.get(2).getText();
      String lastname = cells.get(1).getText();
      String allPhones = cells.get(5).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      contactCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname)
              .withAddress(address).withAllEmails(allEmails).withAllPhones(allPhones));
    }
    return contactCache;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String secondPhone = wd.findElement(By.name("phone2")).getAttribute("value");;
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname).withAddress(address)
            .withEmail(email).withEmail2(email2).withEmail3(email3)
            .withHomePhone(home).withWorkPhone(work).withMobilePhone(mobile).withSecondPhone(secondPhone);
  }

  private void initContactModificationById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./..//.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();

//    wd.findElement(By.cssSelector(String.format("a[@href='edit.php?id=%s']", id))).click();

  }
}