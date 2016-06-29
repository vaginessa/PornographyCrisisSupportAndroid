package com.discoverandchange.pornographycrisissupport;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.discoverandchange.pornographycrisissupport.db.SupportContactTable;
import com.discoverandchange.pornographycrisissupport.db.SupportContactStorageSystem;
import com.discoverandchange.pornographycrisissupport.supportnetwork.SupportContact;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by snielson on 6/15/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SupportContactStorageSystemTest {

  @Mock
  Context context;

  @Mock
  Uri contentUri;

  @Test
  public void testRetrieveSupportContactsFromStorage() {

    int idColumn = 0;
    int nameColumn = 1;
    int phoneColumn = 2;
    int crisisColumn = 3;

    String name = "John";
    String phone = "801 855-8555";
    String id = "1";

    Cursor supportNetworkCursor = mock(Cursor.class);
    ContentResolver resolver = mock(ContentResolver.class);
    SupportContactStorageSystem storageSystem = new SupportContactStorageSystem(context, contentUri);
    when(context.getContentResolver()).thenReturn(resolver);

    when(resolver.query(contentUri, SupportContactTable.ALL_COLUMNS, null, null, null))
        .thenReturn(supportNetworkCursor);

    when(supportNetworkCursor.getCount()).thenReturn(1);
    when(supportNetworkCursor.getColumnIndex(SupportContactTable.COLUMN_NAME_ID))
        .thenReturn(idColumn);
    when(supportNetworkCursor.getColumnIndex(SupportContactTable.COLUMN_NAME_NAME))
        .thenReturn(nameColumn);
    when(supportNetworkCursor.getColumnIndex(SupportContactTable.COLUMN_NAME_PHONE))
        .thenReturn(phoneColumn);
    when(supportNetworkCursor.getColumnIndex(SupportContactTable.COLUMN_NAME_IS_CRISIS))
        .thenReturn(crisisColumn);
    when(supportNetworkCursor.getString(idColumn)).thenReturn(id);
    when(supportNetworkCursor.getString(nameColumn)).thenReturn(name);
    when(supportNetworkCursor.getString(phoneColumn)).thenReturn(phone);
    when(supportNetworkCursor.getInt(crisisColumn)).thenReturn(1);
    when(supportNetworkCursor.isAfterLast()).thenReturn(true); // only do it once.

    List<SupportContact> list = storageSystem.retrieveSupportContactsFromStorage();

    assertThat("List should have loaded one contact from the database", list, notNullValue());
    assertThat("List should have loaded one contact from the database", list.size(), is(1));
    SupportContact contact = list.get(0);
    assertThat("id should have been loaded", contact.getContactId(), is(id));
    assertThat("name should have been loaded", contact.getName(), is(name));
    assertThat("phone should have been loaded", contact.getPhoneNumber(), is(phone));
    assertThat("isCrisis should have been set", contact.isCrisisContact(), is(true));
  }
}
