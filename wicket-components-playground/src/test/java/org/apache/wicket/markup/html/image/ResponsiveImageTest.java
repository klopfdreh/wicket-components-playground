package org.apache.wicket.markup.html.image;

import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResponsiveImageTest
{

	private WicketTester wicketTester;

	@Before
	public void setup()
	{
		wicketTester = new WicketTester(new Application());
	}

	@Test
	public void testSrcSetIsNotAvailableOnDefaultUsage()
	{
		wicketTester.startPage(ImageTestPage.class);
		String lastResponseAsString = wicketTester.getLastResponse().getDocument();
		TagTester createTagByAttribute = TagTester.createTagByAttribute(lastResponseAsString, "img");
		Assert.assertFalse(createTagByAttribute.hasAttribute("srcset"));
	}

	@Test
	public void testPictureTagIsRenderedRight()
	{
		wicketTester.startPage(ImagePictureTestPage.class);
		String lastResponseAsString = wicketTester.getLastResponse().getDocument();
		TagTester pictureTagTester = TagTester.createTagByAttribute(lastResponseAsString, "picture");
		Assert.assertTrue(pictureTagTester.hasChildTag("img"));
		Assert.assertTrue(pictureTagTester.hasChildTag("source"));
		TagTester sourceTagTester = TagTester.createTagByAttribute(lastResponseAsString, "source");
		Assert.assertTrue(sourceTagTester.hasAttribute("media"));
		Assert.assertEquals("(min-width: 650px)", sourceTagTester.getAttribute("media"));
		Assert.assertEquals("(min-width: 50em) 33vw", sourceTagTester.getAttribute("sizes"));
	}

	@Test
	public void testImageTagIsRenderedWithXValuesAndSrcSet()
	{
		wicketTester.startPage(ImageSrcSetTestPage.class);
		String lastResponseAsString = wicketTester.getLastResponse().getDocument();
		TagTester imgTagTester = TagTester.createTagByAttribute(lastResponseAsString, "img");
		Assert.assertTrue(imgTagTester.hasAttribute("src"));
		Assert.assertTrue(imgTagTester.hasAttribute("srcset"));
		String attribute = imgTagTester.getAttribute("srcset");
		String[] srcSetElements = attribute.split(",");
		int i = 0;
		for (String srcSetElement : srcSetElements)
		{
			if (i == 0)
			{
				Assert.assertTrue(srcSetElement.endsWith("320w"));
			}
			if (i == 1)
			{
				Assert.assertTrue(srcSetElement.endsWith("2x"));
			}
			if (i == 2)
			{
				Assert.assertTrue(srcSetElement.endsWith("900w"));
			}
			i++;
		}
		Assert.assertEquals("(min-width: 50em) 33vw,(min-width: 28em) 50vw,100vw",
			imgTagTester.getAttribute("sizes"));
	}

}
