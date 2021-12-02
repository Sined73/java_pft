package ru.stqa.pft.contacts.modal;

public class ContactData {
  private final String lastname;
  private final String address;
  private final String firstname;
  private final String mobile;
  private final String email;

  public ContactData(String lastname, String address, String firstname, String mobile, String email) {
    this.lastname = lastname;
    this.address = address;
    this.firstname = firstname;
    this.mobile = mobile;
    this.email = email;
  }

  public String getLastname() {
    return lastname;
  }

  public String getAddress() {
    return address;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getMobile() {
    return mobile;
  }

  public String getEmail() {
    return email;
  }
}
