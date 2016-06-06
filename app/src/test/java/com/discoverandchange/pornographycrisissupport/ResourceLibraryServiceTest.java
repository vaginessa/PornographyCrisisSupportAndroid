package com.discoverandchange.pornographycrisissupport;

import android.test.suitebuilder.annotation.SmallTest;

import com.discoverandchange.pornographycrisissupport.library.LibraryResource;
import com.discoverandchange.pornographycrisissupport.library.Range;
import com.discoverandchange.pornographycrisissupport.library.ResourceLibraryService;
import com.discoverandchange.pornographycrisissupport.quiz.Quiz;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static android.test.MoreAsserts.assertNotEqual;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by snielson on 6/6/16.
 */
public class ResourceLibraryServiceTest {

  public static final int MILD_DEFAULT_RESOURCE_SIZE = 3;
  public static final int MODERATE_DEFAULT_RESOURCE_SIZE = 4;
  public static final int SEVERE_DEFAULT_RESOURCE_SIZE = 5;

  @Test
  public void testGetResourcesForQuiz() {

    ResourceLibraryService service = new ResourceLibraryService();
    List<LibraryResource> resources = service.getResourcesForQuiz(new Quiz(10));

    assertNotNull("Resource list should have come back", resources);
    assertFalse("Resource list should have at least one resource", resources.isEmpty());
    assertEquals("Resource list should have at least 5 resources", 5, resources.size());

    List<LibraryResource> resourcesMildCravings = service.getResourcesForQuiz(new Quiz(2));

    assertNotNull("Resource list should have come back", resourcesMildCravings);
    assertFalse("Resource list should have at least one resource", resourcesMildCravings.isEmpty());
    assertEquals("Resource list should have at least 3 resources",
        MILD_DEFAULT_RESOURCE_SIZE, resourcesMildCravings.size());
    LibraryResource firstMildResource = resourcesMildCravings.get(0);
    LibraryResource firstSevereResource = resources.get(0);
    assertNotEqual("mild cravings should not have the same resources as severe", firstMildResource, firstSevereResource);
  }

  @Test
  public void testAddResources() {
    ResourceLibraryService service = new ResourceLibraryService();
    List<LibraryResource> mild = Arrays.asList(mock(LibraryResource.class),
        mock(LibraryResource.class), mock(LibraryResource.class));
    List<LibraryResource> moderate = Arrays.asList(mock(LibraryResource.class),
        mock(LibraryResource.class), mock(LibraryResource.class));
    List<LibraryResource> severe = Arrays.asList(mock(LibraryResource.class),
        mock(LibraryResource.class), mock(LibraryResource.class));
    service.addResources(new Range(1, 3), mild);
    service.addResources(new Range(4, 6), moderate);
    service.addResources(new Range(7, 10), severe);

    List<LibraryResource> mildCheck = service.getResourcesForQuiz(new Quiz(3));
    List<LibraryResource> moderateCheck = service.getResourcesForQuiz(new Quiz(6));
    List<LibraryResource> severeCheck = service.getResourcesForQuiz(new Quiz(10));

    runResourceCheck(mild, mildCheck, MILD_DEFAULT_RESOURCE_SIZE + mildCheck.size(), "Mild");
    runResourceCheck(moderate, moderateCheck, MODERATE_DEFAULT_RESOURCE_SIZE + moderateCheck.size(), "Moderate");
    runResourceCheck(severe, severeCheck, SEVERE_DEFAULT_RESOURCE_SIZE + mildCheck.size(), "Severe");
  }

  private void runResourceCheck(List<LibraryResource> resources, List<LibraryResource> check, int size, String type) {
    assertEquals(type + " resources should have been added", check.size(), size);
    for (int i = 0; i < resources.size(); i++) {
      assertTrue(type + " contains this resources", check.contains(resources.get(i)));

    }
  }
}
