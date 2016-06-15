package com.discoverandchange.pornographycrisissupport;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
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
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;

import junit.framework.Assert;

import java.util.List;


/**
 * documentation for mocking here: http://site.mockito.org/mockito/docs/1.10.19/org/mockito/Mockito.html
 * Created by snielson on 6/6/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SupportNetworkServiceTest {

  private static final int FAKE_SCORE = 5;

  @Mock
  SmsManager mManager;

  @Mock
  Context mContext;

  @Test
  public void testInitLoadsData() {
    int idColumn = 0;
    int nameColumn = 1;
    int phoneColumn = 2;
    int crisisColumn = 3;

    String name = "John";
    String phone = "801 855-8555";
    String id = "1";
    Cursor supportNetworkCursor = mock(Cursor.class);
    ContentResolver resolver = mock(ContentResolver.class);
    Uri contentUri = mock(Uri.class);
    SupportNetworkService service = new SupportNetworkService(mContext, mManager, contentUri);
    when(mContext.getContentResolver()).thenReturn(resolver);

    when(resolver.query(contentUri,
        SupportContactOpenHelper.ALL_COLUMNS, null, null, null))
        .thenReturn(supportNetworkCursor);

    when(supportNetworkCursor.getCount()).thenReturn(1);
    when(supportNetworkCursor.getColumnIndex(SupportContactOpenHelper.COLUMN_NAME_ID))
        .thenReturn(idColumn);
    when(supportNetworkCursor.getColumnIndex(SupportContactOpenHelper.COLUMN_NAME_NAME))
        .thenReturn(nameColumn);
    when(supportNetworkCursor.getColumnIndex(SupportContactOpenHelper.COLUMN_NAME_PHONE))
        .thenReturn(phoneColumn);
    when(supportNetworkCursor.getColumnIndex(SupportContactOpenHelper.COLUMN_NAME_IS_CRISIS))
        .thenReturn(crisisColumn);
    when(supportNetworkCursor.getString(idColumn)).thenReturn(id);
    when(supportNetworkCursor.getString(nameColumn)).thenReturn(name);
    when(supportNetworkCursor.getString(phoneColumn)).thenReturn(phone);
    when(supportNetworkCursor.getInt(crisisColumn)).thenReturn(1);
    when(supportNetworkCursor.isAfterLast()).thenReturn(true); // only do it once.
    // init the service and let's verify we can get the contact list.
    service.init();

    List<SupportContact> list = service.getSupportContactList();
    assertThat("List should have loaded one contact from the database", list, notNullValue());
    assertThat("List should have loaded one contact from the database", list.size(), is(1));
    SupportContact contact = list.get(0);
    assertThat("id should have been loaded", contact.getContactID(), is(id));
    assertThat("name should have been loaded", contact.getName(), is(name));
    assertThat("phone should have been loaded", contact.getPhoneNumber(), is(phone));
    assertThat("isCrisis should have been set", contact.isCrisisContact(), is(true));
  }


  @Test
  public void testContactNetwork() {
    // Given a mocked Context inject it into the object under test...

    String phone = "888-888-8888";
    String id = "1";
    String name = "John Jacob";

    SupportNetworkService service = new SupportNetworkService(mContext, mManager, mock(Uri.class));
    String phoneSrcAddress = service.getDevicePhoneNumber();
    String defaultCrisisMessage = service.getDefaultMessage();
    service.addSupportContact(new SupportContact(id, name, phone));
    service.contactNetwork();

    verify(mManager, times(1)).sendTextMessage(phone, phoneSrcAddress, defaultCrisisMessage, null, null);
  }

  @Test
  public void testAddSupportNetworkContactWithParameters() {

    SupportNetworkService service = new SupportNetworkService(mContext, mManager, mock(Uri.class));
    SupportContact contact = service.addSupportContact("John", "Jacob", "1", "888-888-8888");
    assertThat("contact should have been added", contact, notNullValue());

    List<SupportContact> contactList = service.getSupportContactList();
    assertThat("Contact list should have at least one item in it.", contactList.isEmpty(), is(false));
    contactList.get(0).equals(contact);
  }

  @Test
  public void testAddSupportNetworkContactWithObject() {

    SupportNetworkService service = new SupportNetworkService(mContext, mManager, mock(Uri.class));
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
    SupportNetworkService service = new SupportNetworkService(mContext, mManager, mock(Uri.class));
    SupportContact contact = service.addSupportContact("John", "Jacob", contactID, "888-888-8888");

    SupportContact contactRemoved = service.removeSupportContact(contactID);
    assertTrue("The contact has been removed", contactRemoved != null);
    List<SupportContact> contactList = service.getSupportContactList();

    assertFalse("The contactID is no longer present in the list", contactList.contains(contactRemoved));
  }

  @Test
  public void sendSMSTestMessage() {
    String phone = "888-888-8888";
    String first = "Joe";
    String last = "Smith";
    String id = "1";

    SupportNetworkService service = new SupportNetworkService(mContext, mManager, mock(Uri.class));
    String phoneSrcAddress = service.getDevicePhoneNumber();
    String testMessage = "This is a test message.";
    service.addSupportContact(first, last, id,  phone);
    service.sendSMSTestMessage(testMessage);

    verify(mManager, times(1)).sendTextMessage(phone, phoneSrcAddress, testMessage, null, null);
  }

}
