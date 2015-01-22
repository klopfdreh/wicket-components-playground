package org.apache.wicket.markup.html.image;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class Application extends WebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return ImageTestPage.class;
	}
}
