package com.discoverandchange.pornographycrisissupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;

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


  @Test
  public void testContactNetwork() {
    // Given a mocked Context inject it into the object under test...

    String phone = "888-888-8888";
    String id = "1";
    String first = "John";
    String last = "Jacob";

    SupportNetworkService service = new SupportNetworkService(mManager);
    String phoneSrcAddress = service.getDevicePhoneNumber();
    String defaultCrisisMessage = service.getDefaultMessage();
    service.addSupportContact(first, last, id,  phone);
    service.contactNetwork();

    verify(mManager, times(1)).sendTextMessage(phone, phoneSrcAddress, defaultCrisisMessage, null, null);
  }

  @Test
  public void testAddSupportNetworkContact() {

    SupportNetworkService service = new SupportNetworkService(mManager);
    SupportContact contact = service.addSupportContact("John", "Jacob", "1", "888-888-8888");
    assertThat("contact should have been added", contact, notNullValue());

    List<SupportContact> contactList = service.getSupportContactList();
    contactList.get(0).equals(contact);
  }
}