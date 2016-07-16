package com.discoverandchange.pornographycrisissupport;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.telephony.SmsManager;

import com.discoverandchange.pornographycrisissupport.db.SupportContactStorageSystem;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;




/**
 * Tests the support network service.
 * documentation for mocking here:
 * http://site.mockito.org/mockito/docs/1.10.19/org/mockito/Mockito.html
 */
@RunWith(MockitoJUnitRunner.class)
public class SupportNetworkServiceTest {

  @Mock
  SmsManager smsManager;

  @Mock
  SupportContactStorageSystem storageSystem;

  @Test
  public void testInitLoadsData() {
    SupportNetworkService service = new SupportNetworkService(storageSystem, smsManager);

    List<SupportContact> stubContacts = new ArrayList<>();
    stubContacts.add(getFirstTestContact());

    when(storageSystem.retrieveSupportContactsFromStorage()).thenReturn(stubContacts);

    service.init();
    List<SupportContact> list = service.getSupportContactList();
    assertThat("List should have loaded one contact from the database", list, notNullValue());
    assertThat("List should have loaded one contact from the database", list.size(), is(1));
    SupportContact checkContact = list.get(0);
    SupportContact contact = getFirstTestContact();
    assertThat("id should have been loaded", checkContact.getContactId(),
        is(contact.getContactId()));
    assertThat("name should have been loaded", checkContact.getName(), is(contact.getName()));
    assertThat("phone should have been loaded", checkContact.getPhoneNumber(),
        is(contact.getPhoneNumber()));
    assertThat("isCrisis should have been set", checkContact.isCrisisContact(),
        is(contact.isCrisisContact()));
  }


  @Test
  public void testContactNetwork() {
    // Given a mocked Context inject it into the object under test...
    SupportContact contact = getFirstTestContact();
    SupportContact contact2 = getSecondTestContact();

    SupportNetworkService service = new SupportNetworkService(storageSystem, smsManager);


    service.addSupportContact(contact);
    service.addSupportContact(contact2);
    service.contactNetwork();

    String phoneSrcAddress = service.getDevicePhoneNumber();
    String defaultCrisisMessage = service.getDefaultMessage();
    verify(smsManager, times(1)).sendTextMessage(contact.getPhoneNumber(), phoneSrcAddress,
        defaultCrisisMessage, null, null);
    verify(smsManager, times(1)).sendTextMessage(contact2.getPhoneNumber(), phoneSrcAddress,
        defaultCrisisMessage, null, null);
  }

  @Test
  public void testAddSupportNetworkContactWithParameters() {

    SupportNetworkService service = new SupportNetworkService(storageSystem, smsManager);
    SupportContact contact = service.addSupportContact("John Jacob", "1", "888-888-8888");
    assertThat("contact should have been added", contact, notNullValue());

    List<SupportContact> contactList = service.getSupportContactList();
    assertThat("Contact list should have at least one item in it.",
        contactList.isEmpty(), is(false));
    contactList.get(0).equals(contact);
  }

  @Test
  public void testAddSupportNetworkContactWithObject() {

    SupportNetworkService service = new SupportNetworkService(storageSystem, smsManager);
    SupportContact contact = service.addSupportContact(
        new SupportContact("John Jacob", "1", "888-888-8888"));
    assertThat("contact should have been added", contact, notNullValue());

    List<SupportContact> contactList = service.getSupportContactList();
    assertThat("Contact list should have at least one item in it.",
        contactList.isEmpty(), is(false));
    contactList.get(0).equals(contact);
  }

  @Test
  public void testAddSameIdUpdatesSupportContact() {
    SupportNetworkService service = new SupportNetworkService(storageSystem, smsManager);
    SupportContact contact = service.addSupportContact(
        new SupportContact("1", "John Jacob", "888-888-8888"));
    assertThat("contact should have been added", contact, notNullValue());

    SupportContact updatedContact = service.addSupportContact(
        new SupportContact("1", "Some other person", "888-888-8555"));
    List<SupportContact> contacts = service.getSupportContactList();
    SupportContact contactCheck = contacts.get(0);
    assertThat("Contact name should have been updated", contactCheck.getName(),
        is(updatedContact.getName()));
    assertThat("Contact name should have been updated", contactCheck.getName(),
        not(is(contact.getName())));
  }

  @Test
  public void testReplaceCrisisContact() {

    SupportContact crisisContact = getFirstTestContact();
    crisisContact.setIsCrisisContact(true);
    SupportNetworkService service = new SupportNetworkService(storageSystem, smsManager);
    service.addSupportContact(crisisContact);

    SupportContact crisisCheck = service.getCrisisSupportContact();
    assertThat("Crisis check should be contact we added", crisisCheck.getContactId(),
        is(crisisContact.getContactId()));

    SupportContact replaceCrisisContact = getSecondTestContact();
    replaceCrisisContact.setIsCrisisContact(true);
    service.addSupportContact(replaceCrisisContact);
    SupportContact replaceCrisisContactCheck = service.getCrisisSupportContact();
    assertThat("Crisis check should now be the new contact we added",
        replaceCrisisContactCheck.getContactId(), is(replaceCrisisContact.getContactId()));

    // grab our service list and make sure only one crisis contact is there
    List<SupportContact> contactList = service.getSupportContactList();
    for (SupportContact contact : contactList) {
      if (contact.isCrisisContact()
          && !(contact.getContactId().equals(replaceCrisisContactCheck.getContactId()))) {
        fail("multiple crisis contacts found, when there should only be one. Contact with id "
            + contact.getContactId());
      }
    }


  }

  @Test
  public void removeSupportContact() {

    // Obtain the contactID that was given
    // Search the arrayList of contacts for the contact ID
    // If that contactID is not present, then it has been removed
    String contactId = "1";
    SupportNetworkService service = new SupportNetworkService(storageSystem, smsManager);
    SupportContact contact = service.addSupportContact("John Jacob", contactId, "888-888-8888");

    SupportContact contactRemoved = service.removeSupportContact(contactId);
    assertEquals("The contact has been removed", contactRemoved, null);
    List<SupportContact> contactList = service.getSupportContactList();

    assertFalse("The contactID is no longer present in the list",
        contactList.contains(contactRemoved));

    SupportContact contactRemovedCheck = service.removeSupportContact(contactId);
    assertEquals("no contact should return when it's been removed", contactRemovedCheck, null);
  }

  @Test
  public void testGetCrisisSupportContact() {
    SupportNetworkService service = new SupportNetworkService(storageSystem, smsManager);
    SupportContact crisis = new SupportContact("1", "John Jacob", "5555");
    crisis.setIsCrisisContact(true);
    SupportContact notCrisis = new SupportContact("2", "Jingleheimer Smith", "6666");
    service.addSupportContact(notCrisis);
    service.addSupportContact(crisis);

    SupportContact contact = service.getCrisisSupportContact();
    assertThat("Crisis contact should be returned", crisis, notNullValue());
    assertThat("Contact id should match", contact.getContactId(), is(crisis.getContactId()));
    assertThat("Contact name should match", contact.getName(), is(crisis.getName()));
    assertThat("Contact phone should match", contact.getPhoneNumber(), is(crisis.getPhoneNumber()));
    assertThat("Contact should have crisis setting", contact.isCrisisContact(), is(true));
  }

  @Test
  public void sendSmsTestMessageToSingleContact() {
    String phone = "888-888-8888";
    String name = "Joe Smith";
    String id = "1";

    SupportNetworkService service = new SupportNetworkService(storageSystem, smsManager);
    String phoneSrcAddress = service.getDevicePhoneNumber();
    String testMessage = "This is a test message.";
    service.addSupportContact(name, id,  phone);
    service.sendSmsTestMessage(testMessage);

    verify(smsManager, times(1)).sendTextMessage(phone, phoneSrcAddress, testMessage, null, null);
  }

  @Test
  public void sendSmsTestMessageToMultipleContacts() {

    SupportContact testContact = getFirstTestContact();
    SupportContact testContact2 = getSecondTestContact();

    SupportNetworkService service = new SupportNetworkService(storageSystem, smsManager);

    String testMessage = "This is a test message.";
    service.addSupportContact(testContact);
    service.addSupportContact(testContact2);

    service.sendSmsTestMessage(testMessage);
    String phoneSrcAddress = service.getDevicePhoneNumber();
    verify(smsManager, times(1)).sendTextMessage(testContact.getPhoneNumber(), phoneSrcAddress,
        testMessage, null, null);
    verify(smsManager, times(1)).sendTextMessage(testContact2.getPhoneNumber(), phoneSrcAddress,
        testMessage, null, null);

  }

  /**
   * Returns a test support contact
   * @return The generated contact.
   */
  private SupportContact getFirstTestContact() {
    String phone = "888-888-8888";
    String name = "Joe Smith";
    String id = "1";
    return new SupportContact(id, name, phone);
  }

  /**
   * Returns a different test support contact.
   * @return The generated contact.
   */
  private SupportContact getSecondTestContact() {
    String secondPhone = "888-888-9999";
    String secondName = "Second Joe Smith";
    String secondId = "2";
    return new SupportContact(secondId, secondName, secondPhone);
  }

}
