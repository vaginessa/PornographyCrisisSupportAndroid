package com.discoverandchange.pornographycrisissupport;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;

import com.discoverandchange.pornographycrisissupport.db.SupportContactOpenHelper;
import com.discoverandchange.pornographycrisissupport.db.SupportContactProvider;
import com.discoverandchange.pornographycrisissupport.db.SupportContactStorageSystem;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;


/**
 * documentation for mocking here: http://site.mockito.org/mockito/docs/1.10.19/org/mockito/Mockito.html
 * Created by snielson on 6/6/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SupportNetworkServiceTest {

  @Mock
  SmsManager mManager;

  @Mock
  SupportContactStorageSystem storageSystem;

  @Test
  public void testInitLoadsData() {
    SupportNetworkService service = new SupportNetworkService(storageSystem, mManager);
    SupportContact contact = getFirstTestContact();
    List<SupportContact> stubContacts = new ArrayList<>();
    stubContacts.add(getFirstTestContact());

    when(storageSystem.retrieveSupportContactsFromStorage()).thenReturn(stubContacts);

    service.init();
    List<SupportContact> list = service.getSupportContactList();
    assertThat("List should have loaded one contact from the database", list, notNullValue());
    assertThat("List should have loaded one contact from the database", list.size(), is(1));
    SupportContact checkContact = list.get(0);
    assertThat("id should have been loaded", checkContact.getContactID(), is(contact.getContactID()));
    assertThat("name should have been loaded", checkContact.getName(), is(contact.getName()));
    assertThat("phone should have been loaded", checkContact.getPhoneNumber(), is(contact.getPhoneNumber()));
    assertThat("isCrisis should have been set", checkContact.isCrisisContact(), is(contact.isCrisisContact()));
  }


  @Test
  public void testContactNetwork() {
    // Given a mocked Context inject it into the object under test...
    SupportContact contact = getFirstTestContact();
    SupportContact contact2 = getSecondTestContact();

    SupportNetworkService service = new SupportNetworkService(storageSystem, mManager);
    String phoneSrcAddress = service.getDevicePhoneNumber();
    String defaultCrisisMessage = service.getDefaultMessage();
    service.addSupportContact(contact);
    service.addSupportContact(contact2);
    service.contactNetwork();

    verify(mManager, times(1)).sendTextMessage(contact.getPhoneNumber(), phoneSrcAddress,
        defaultCrisisMessage, null, null);
    verify(mManager, times(1)).sendTextMessage(contact2.getPhoneNumber(), phoneSrcAddress,
        defaultCrisisMessage, null, null);
  }

  @Test
  public void testAddSupportNetworkContactWithParameters() {

    SupportNetworkService service = new SupportNetworkService(storageSystem, mManager);
    SupportContact contact = service.addSupportContact("John Jacob", "1", "888-888-8888");
    assertThat("contact should have been added", contact, notNullValue());

    List<SupportContact> contactList = service.getSupportContactList();
    assertThat("Contact list should have at least one item in it.", contactList.isEmpty(), is(false));
    contactList.get(0).equals(contact);
  }

  @Test
  public void testAddSupportNetworkContactWithObject() {

    SupportNetworkService service = new SupportNetworkService(storageSystem, mManager);
    SupportContact contact = service.addSupportContact(new SupportContact("John Jacob", "1", "888-888-8888"));
    assertThat("contact should have been added", contact, notNullValue());

    List<SupportContact> contactList = service.getSupportContactList();
    assertThat("Contact list should have at least one item in it.", contactList.isEmpty(), is(false));
    contactList.get(0).equals(contact);
  }

  @Test
  public void removeSupportContact(){

    // Obtain the contactID that was given
    // Search the arrayList of contacts for the contact ID
    // If that contactID is not present, then it has been removed
    String contactID = "1";
    SupportNetworkService service = new SupportNetworkService(storageSystem, mManager);
    SupportContact contact = service.addSupportContact("John Jacob", contactID, "888-888-8888");

    SupportContact contactRemoved = service.removeSupportContact(contactID);
    assertEquals("The contact has been removed", contactRemoved, null);
    List<SupportContact> contactList = service.getSupportContactList();

    assertFalse("The contactID is no longer present in the list", contactList.contains(contactRemoved));

    SupportContact contactRemovedCheck = service.removeSupportContact(contactID);
    assertEquals("no contact should return when it's been removed", contactRemovedCheck, null);
  }

  @Test
  public void testGetCrisisSupportContact() {
    SupportNetworkService service = new SupportNetworkService(storageSystem, mManager);
    SupportContact crisis = new SupportContact("1", "John Jacob", "5555");
    crisis.setIsCrisisContact(true);
    SupportContact notCrisis = new SupportContact("2", "Jingleheimer Smith", "6666");
    service.addSupportContact(notCrisis);
    service.addSupportContact(crisis);

    SupportContact contact = service.getCrisisSupportContact();
    assertThat("Crisis contact should be returned", crisis, notNullValue());
    assertThat("Contact id should match", contact.getContactID(), is(crisis.getContactID()));
    assertThat("Contact name should match", contact.getName(), is(crisis.getName()));
    assertThat("Contact phone should match", contact.getPhoneNumber(), is(crisis.getPhoneNumber()));
    assertThat("Contact should have crisis setting", contact.isCrisisContact(), is(true));
  }

  @Test
  public void sendSMSTestMessageToSingleContact() {
    String phone = "888-888-8888";
    String name = "Joe Smith";
    String id = "1";

    SupportNetworkService service = new SupportNetworkService(storageSystem, mManager);
    String phoneSrcAddress = service.getDevicePhoneNumber();
    String testMessage = "This is a test message.";
    service.addSupportContact(name, id,  phone);
    service.sendSMSTestMessage(testMessage);

    verify(mManager, times(1)).sendTextMessage(phone, phoneSrcAddress, testMessage, null, null);
  }

  @Test
  public void sendSMSTestMessageToMultipleContacts() {

   SupportContact testContact = getFirstTestContact();
    SupportContact testContact2 = getSecondTestContact();

    SupportNetworkService service = new SupportNetworkService(storageSystem, mManager);
    String phoneSrcAddress = service.getDevicePhoneNumber();
    String testMessage = "This is a test message.";
    service.addSupportContact(testContact);
    service.addSupportContact(testContact2);

    service.sendSMSTestMessage(testMessage);
    verify(mManager, times(1)).sendTextMessage(testContact.getPhoneNumber(), phoneSrcAddress,
        testMessage, null, null);
    verify(mManager, times(1)).sendTextMessage(testContact2.getPhoneNumber(), phoneSrcAddress,
        testMessage, null, null);

  }

  private SupportContact getFirstTestContact() {
    String phone = "888-888-8888";
    String name = "Joe Smith";
    String id = "1";
    return new SupportContact(id, name, phone);
  }

  private SupportContact getSecondTestContact() {
    String secondPhone = "888-888-9999";
    String secondName = "Second Joe Smith";
    String secondId = "2";
    return new SupportContact(secondId, secondName, secondPhone);
  }

}
