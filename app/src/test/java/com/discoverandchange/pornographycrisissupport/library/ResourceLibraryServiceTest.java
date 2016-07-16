package com.discoverandchange.pornographycrisissupport.library;

import static android.test.MoreAsserts.assertNotEqual;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.discoverandchange.pornographycrisissupport.quiz.Quiz;

import org.apache.commons.lang3.Range;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


/**
 * Unit tests for ResourceLibraryService.
 */
public class ResourceLibraryServiceTest {

  @Test
  public void testGetResourcesForQuizIsEmptyOnInitialLoad() {

    ResourceLibraryService service = new ResourceLibraryService();
    List<LibraryResource> resources = service.getResourcesForQuiz(new Quiz(10));

    assertNotNull("Resource list should have come back", resources);
    assertTrue("Resource list should have be empty", resources.isEmpty());

    List<LibraryResource> resourcesMildCravings = service.getResourcesForQuiz(new Quiz(2));

    assertNotNull("Resource list should have come back", resourcesMildCravings);
    assertTrue("Resource list should have be empty", resourcesMildCravings.isEmpty());
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
    service.addResources(Range.between(1, 3), mild);
    service.addResources(Range.between(4, 6), moderate);
    service.addResources(Range.between(7, 10), severe);

    List<LibraryResource> mildCheck = service.getResourcesForQuiz(new Quiz(3));
    List<LibraryResource> moderateCheck = service.getResourcesForQuiz(new Quiz(6));
    List<LibraryResource> severeCheck = service.getResourcesForQuiz(new Quiz(10));

    runResourceCheck(mild, mildCheck, mild.size(), "Mild");
    runResourceCheck(moderate, moderateCheck, moderate.size(), "Moderate");
    runResourceCheck(severe, severeCheck, severe.size(), "Severe");
  }

  @Test
  public void testRegisterObserver() {
    ResourceLibraryService service = new ResourceLibraryService();
    LibraryServiceObserver observer = mock(LibraryServiceObserver.class);
    service.registerObserver(observer);

    service.addResource(Range.between(1, 3), mock(LibraryResource.class));
    service.markResourcesLoaded();
    verify(observer, times(1)).resourcesLoaded(service);
  }

  @Test
  public void testRemoveObserver() {
    ResourceLibraryService service = new ResourceLibraryService();
    LibraryServiceObserver observer1 = mock(LibraryServiceObserver.class);
    LibraryServiceObserver observer2 = mock(LibraryServiceObserver.class);
    service.registerObserver(observer1);
    service.registerObserver(observer2);

    service.removeObserver(observer1);

    service.addResource(Range.between(1, 3), mock(LibraryResource.class));
    service.markResourcesLoaded();
    verify(observer1, times(0)).resourcesLoaded(service);
    verify(observer2, times(1)).resourcesLoaded(service);
  }

  private void runResourceCheck(List<LibraryResource> resources, List<LibraryResource> check,
                                int size, String type) {
    assertEquals(type + " resources should have been added", check.size(), size);
    for (int i = 0; i < resources.size(); i++) {
      assertTrue(type + " contains this resources", check.contains(resources.get(i)));

    }
  }
}
