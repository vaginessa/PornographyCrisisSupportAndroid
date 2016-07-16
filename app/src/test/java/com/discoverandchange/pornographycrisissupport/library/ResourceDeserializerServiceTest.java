package com.discoverandchange.pornographycrisissupport.library;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.discoverandchange.pornographycrisissupport.library.json.ResourceDeserializer;

import org.apache.commons.lang3.Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;



/**
 * Unit tests for ResourceDeserializerService.
 */
@RunWith(MockitoJUnitRunner.class)
public class ResourceDeserializerServiceTest {

  @Test
  public void testRegisterDeserializer() {
    ResourceDeserializerService service = new ResourceDeserializerService();

    ResourceDeserializer videoDeserializer = mock(ResourceDeserializer.class);
    service.registerDeserializer("Video", videoDeserializer);

    ResourceDeserializer checkDeserializer = service.getDeserializerForType("Video");
    assertThat("deserializer returned for type", checkDeserializer, is(videoDeserializer));
  }

  @Test
  /**
   * We can't test the string deserialization as android requires JSONObject to be mocked
   * so we test with a bunch of mock JSONObjects.  Unfortunately this pretty heavily ties this test
   * to the internal workings of the implementation.
   */
  public void testDeserialize() throws JSONException {

    JSONObject mockJsonMapping = mock(JSONObject.class);
    JSONArray mockRangeList = mock(JSONArray.class);
    JSONObject mockRange = mock(JSONObject.class);
    JSONArray mockResourceList = mock(JSONArray.class);
    JSONObject mockJsonVideo = mock(JSONObject.class);

    when(mockJsonMapping.getJSONArray("ranges")).thenReturn(mockRangeList);
    when(mockRangeList.length()).thenReturn(1);
    when(mockRangeList.getJSONObject(0)).thenReturn(mockRange);
    when(mockRange.optInt("start", 0)).thenReturn(1);
    when(mockRange.optInt("end", 0)).thenReturn(3);
    when(mockRange.getJSONArray("resources")).thenReturn(mockResourceList);
    when(mockResourceList.length()).thenReturn(1);
    when(mockResourceList.getJSONObject(0)).thenReturn(mockJsonVideo);
    when(mockJsonVideo.getString("type")).thenReturn("Video");
    when(mockJsonVideo.getString("url"))
        .thenReturn("https://www.discoverandchange.com/wp-content/uploads/2016/02/"
            + "DiscoverAndChangeIntroVideo.mp4");

    ResourceDeserializerService service = new ResourceDeserializerService();
    ResourceDeserializer videoDeserializer = mock(ResourceDeserializer.class);
    LibraryResource mockVideo = mock(LibraryResource.class);
    service.registerDeserializer("Video", videoDeserializer);
    when(videoDeserializer.deserialize(any(JSONObject.class))).thenReturn(mockVideo);

    Map<Range, List<LibraryResource>> resourcesByRange = service.deserialize(mockJsonMapping);

    assertThat("resources should contain one range", resourcesByRange.size(), is(1));
    Range<Integer> range = resourcesByRange.keySet().iterator().next();
    assertThat("Range should start at one", range.getMinimum(), is(1));
    assertThat("Range should end at three", range.getMaximum(), is(3));

    List<LibraryResource> libraryResources = resourcesByRange.get(range);
    assertThat("Range should contain one resource", libraryResources.size(), is(1));
    assertThat("Range should contain one video resource", libraryResources.get(0), is(mockVideo));
  }


}
