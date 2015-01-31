/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * A WebRTC component to create a video conference based on SimpleWebRTC.
 * 
 * http://www.html5rocks.com/en/tutorials/webrtc/basics/<br>
 * https://github.com/andyet/signalmaster<br>
 * https://github.com/HenrikJoreteg/SimpleWebRTC<br>
 * https://github.com/HenrikJoreteg/webrtc.js
 * 
 * @author Tobias Soloschenko
 *
 */
public abstract class WebRTC extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;

	private Boolean debug;

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
		if (debug != null && debug)
		{
			response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(
				WebRTC.class, "simplewebrtc.bundle.js")));
		}
		else
		{
			response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(
				WebRTC.class, "latest.js")));
		}
		String initializejs = new Scanner(WebRTC.class.getResourceAsStream("initialize.js")).useDelimiter(
			"\\A")
			.next();
		initializejs = String.format(initializejs, getLocalVideoId(), getSocketIOUrl(),
			getRoomName(), getMarkupId(), getMarkupId());
		response.render(JavaScriptReferenceHeaderItem.forScript(initializejs, getMarkupId() +
			"script"));
		response.render(CssReferenceHeaderItem.forReference(new PackageResourceReference(
			WebRTC.class, "WebRTC.css")));
	}

	/**
	 * Gets the local video id of the video element which is going to display the local video
	 * recording
	 * 
	 * @return the id of the local video
	 */
	public abstract String getLocalVideoId();

	/**
	 * Gets the room name to which the users are joining
	 * 
	 * @return the room name
	 */
	public abstract String getRoomName();

	/**
	 * Gets the url of the signal server based on socket.io. Important the URL must not contain a
	 * namespace like /socket.io at the end, because the signal server then never calls the connect
	 * event.
	 * 
	 * @return the url of the socket io server
	 */
	public abstract String getSocketIOUrl();

	/**
	 * If the debug mode is enabled - this is causing the uncompressed version of the javascript
	 * files is going to be loaded
	 * 
	 * @return if the debug mode is enabled
	 */
	public Boolean isDebug()
	{
		return debug;
	}

	/**
	 * Sets the debug mode to be enabled - this is causing the uncompressed version of the
	 * javascript files is going to be loaded
	 * 
	 * @param debug
	 *            if the debug mode is enabled
	 */
	public void setDebug(Boolean debug)
	{
		this.debug = debug;
	}

}
