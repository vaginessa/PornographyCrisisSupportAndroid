package com.discoverandchange.pornographycrisissupport.library;

import android.app.Activity;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.LibraryResourceActivityRegistry;

import org.junit.Test;
import org.mockito.Matchers;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by snielson on 6/22/16.
 */
public class LibraryResourceActivityRegistryTest {

  @Test
  public void testRegisterResource() {
    LibraryResourceActivityRegistry registry = LibraryResourceActivityRegistry.getInstance();

    Class<? extends Activity> activity = registry.getActivityForResource(LibraryResource.class);
    assertThat("there should be no registered activities here", activity, nullValue());

    registry.registerResource(LibraryResource.class, Activity.class);
    activity = registry.getActivityForResource(LibraryResource.class);
    assertThat("We should have gotten back the registered class", activity, notNullValue());
  }
}
