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

import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportNetworkService;


/**
 * documentation for mocking here: http://site.mockito.org/mockito/docs/1.10.19/org/mockito/Mockito.html
 * Created by snielson on 6/6/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleMockTest {

  private static final int FAKE_SCORE = 5;

  @Mock
  SmsManager mManager;


  @Test
  public void testContactNetwork() {
    // Given a mocked Context inject it into the object under test...
//
//

    SupportNetworkService service = new SupportNetworkService(mManager);
    service.contactNetwork();


    verify(mManager, times(1)).sendTextMessage("8018558888", "8018558888", "some message", null, null);
    verify(mManager, times(1)).sendTextMessage("8018888888", "8018888888", "some message", null, null);
    verify(mManager, times(1)).sendTextMessage("8019999999", "8019999999", "some message", null, null);
  }
}
