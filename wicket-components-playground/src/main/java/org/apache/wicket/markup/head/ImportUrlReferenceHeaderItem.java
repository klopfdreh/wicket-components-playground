package org.apache.wicket.markup.head;

import org.apache.wicket.request.Response;
import org.apache.wicket.request.UrlUtils;
import org.apache.wicket.request.cycle.RequestCycle;

public class ImportUrlReferenceHeaderItem extends CssUrlReferenceHeaderItem
{

	public ImportUrlReferenceHeaderItem(String url, String media, String condition)
	{
		super(url, media, condition);
	}
	
	@Override
	public void render(Response response)
	{
		internalRenderCSSReference(response,
			UrlUtils.rewriteToContextRelative(getUrl(), RequestCycle.get()), getMedia(),
			getCondition(),true);
	}

	@Override
	public String toString()
	{
		return "ImportUrlReferenceHeaderItem(" + getUrl() + ")";
	}

}
