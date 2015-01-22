package org.apache.wicket.markup.html.media;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class Application extends WebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return MediaTagsTestPage.class;
	}
	
	@Override
	protected void init()
	{
		super.init();
		MediaUtils.init();
	}
}
