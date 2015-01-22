package org.apache.wicket.markup.html.media;

import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MediaTagsTest
{
	private WicketTester wicketTester;

	@Before
	public void setup()
	{
		wicketTester = new WicketTester(new Application());
	}

	@Test
	public void testAudioTagIsRenderedRight()
	{
		wicketTester.startPage(MediaTagsTestPage.class);
		String lastResponseAsString = wicketTester.getLastResponse().getDocument();
		TagTester createTagByAttribute = TagTester.createTagByAttribute(lastResponseAsString,
			"audio");
		Assert.assertTrue(createTagByAttribute.hasAttribute("autoplay"));
		Assert.assertTrue(createTagByAttribute.hasAttribute("controls"));
		Assert.assertTrue(createTagByAttribute.hasAttribute("loop"));
		Assert.assertTrue(createTagByAttribute.hasAttribute("muted"));
		Assert.assertEquals("user-credentials", createTagByAttribute.getAttribute("crossorigin"));
		String attribute = createTagByAttribute.getAttribute("src");
		Assert.assertTrue("The time period is set right in the src attribute",
			attribute.contains("#t=5,10"));
		Assert.assertTrue("page parameter is in the url of the src attribute",
			attribute.contains("test=test"));
	}

	@Test
	public void testVideoTagIsRenderedRight()
	{
		wicketTester.startPage(MediaTagsTestPage.class);
		String lastResponseAsString = wicketTester.getLastResponse().getDocument();
		TagTester createTagByAttribute = TagTester.createTagByAttribute(lastResponseAsString,
			"video");
		String attribute = createTagByAttribute.getAttribute("poster");
		Assert.assertTrue("page parameter is in the url of the poster",
			attribute.contains("test2=test2"));
		String attributesrc = createTagByAttribute.getAttribute("src");
		Assert.assertTrue("video url is in the src attribute",
			attributesrc.contains("dummyVideo.m4a"));
		Assert.assertEquals("500", createTagByAttribute.getAttribute("width"));
		Assert.assertEquals("400", createTagByAttribute.getAttribute("height"));
	}

	@Test
	public void testextendedVideoTagIsRenderedRight()
	{
		wicketTester.startPage(MediaTagsExtendedTestPage.class);
		String lastResponseAsString = wicketTester.getLastResponse().getDocument();
		TagTester createTagByAttribute = TagTester.createTagByAttribute(lastResponseAsString,
			"video");
		Assert.assertTrue(createTagByAttribute.hasChildTag("source"));
		Assert.assertTrue(createTagByAttribute.hasChildTag("track"));
		
		
		TagTester sourceTag = TagTester.createTagByAttribute(lastResponseAsString, "source");
		Assert.assertEquals("video/mp4", sourceTag.getAttribute("type")); 
		Assert.assertEquals("screen and (device-width:500px)", sourceTag.getAttribute("media")); 
		Assert.assertEquals("http://www.mytestpage.xc/video.m4a", sourceTag.getAttribute("src")); 
		
		TagTester trackTag = TagTester.createTagByAttribute(lastResponseAsString, "track");
		
		Assert.assertTrue(trackTag.getAttribute("src").contains("dummySubtitles")); 
		Assert.assertEquals("subtitles", trackTag.getAttribute("kind")); 
		Assert.assertEquals("Subtitles of video", trackTag.getAttribute("label")); 
		Assert.assertEquals("default", trackTag.getAttribute("default")); 
		Assert.assertEquals("de", trackTag.getAttribute("srclang")); 
	}
}
