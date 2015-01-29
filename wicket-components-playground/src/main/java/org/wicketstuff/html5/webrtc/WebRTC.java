package org.wicketstuff.html5.webrtc;

import java.util.Scanner;

import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.resource.JQueryResourceReference;

/**
 * A WebRTC component based on SimpleWebRTC
 * 
 * https://github.com/HenrikJoreteg/SimpleWebRTC
 * 
 * @author Tobias Soloschenko
 *
 */
public abstract class WebRTC extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;

	public WebRTC(String id)
	{
		super(id);
		this.setOutputMarkupId(true);
		this.setOutputMarkupPlaceholderTag(true);
	}

	public WebRTC(String id, IModel<?> model)
	{
		super(id, model);
		this.setOutputMarkupId(true);
		this.setOutputMarkupPlaceholderTag(true);
	}

	@SuppressWarnings("resource")
	@Override
	public void renderHead(IHeaderResponse response)
	{	
		response.render(JavaScriptHeaderItem.forReference(JQueryResourceReference.get()));
		response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(
			WebRTC.class, "latest.js")));
		String initializejs = new Scanner(WebRTC.class.getResourceAsStream("initialize.js")).useDelimiter(
			"\\A")
			.next();
		initializejs = String.format(initializejs, getLocalVideoId(),
			getSocketIOUrl(), getRoomName(),getMarkupId(),getMarkupId());
		response.render(JavaScriptReferenceHeaderItem.forScript(initializejs,getMarkupId()+"script"));
		response.render(CssReferenceHeaderItem.forReference(new PackageResourceReference(WebRTC.class,"WebRTC.css")));
	}

	public abstract String getLocalVideoId();

	public abstract String getRoomName();

	public abstract String getSocketIOUrl();

}
