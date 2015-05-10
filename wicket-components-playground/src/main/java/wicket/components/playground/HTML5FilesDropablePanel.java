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
package wicket.components.playground;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.crypt.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This panel is able to handle HTML5 dropable events and receives the files which are dropped to
 * the panel's markup. The style of the area is done via CSS in the markup. With the
 * AjaxRequestTarget it's able to give a response to the client, for example to show that the files
 * has been uploaded.<br>
 * <br>
 * 
 * Example Implementation: <code><pre>
 * 	add(new HTML5FilesDropablePanel("dropable") {
 * 
 * 	    private static final long serialVersionUID = 5131615920147559576L;
 * 
 * 	    {@literal @}Override
 * 	    public void handleResponse(AjaxRequestTarget target,
 * 		    String fileName, ServletInputStream fileServletInputStream, 
 * 		    String dropid, String fileid) {
 * 		// Handle the file
 * 	    }
 * 
 * 	    // The following methods are not required to be overridden - 
 * 	    // see the java doc which javascript variables are available
 * 
 * 	    {@literal @}Override
 * 	    protected String getStartUploadClientScript() {
 * 		return "alert('start'+file.dropid); alert('start'+file.fileid);"; 
 * 	    };
 * 
 * 	    {@literal @}Override
 * 	    protected String getFinishedUploadClientScript() {
 * 		return "alert('finished'+file.dropid); alert('finished'+file.fileid);";
 * 	    }
 * 	});
 * </pre></code>HTML: <code><pre>
 * &lt;div wicket:id="dropable" style="width:100px;height:100px;border:1px solid black;"&gt;&lt;/div&gt;
 * </pre></code> If the user uploads several files a request is made for every file to the
 * handleReponse-Method. <br>
 * <b>!!! The fileServletInputStream is closed after the handleReponse-Method is complete - so you
 * don't have to be worried about that. !!!</b>
 * 
 * @author tobiassoloschenko
 */
public abstract class HTML5FilesDropablePanel extends WebMarkupContainer
{

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(HTML5FilesDropablePanel.class);

	private HTML5FilesDropableAjaxEventBehavior html5FilesDropableAjaxEventBehavior;

	public HTML5FilesDropablePanel(String id)
	{
		super(id);
		add(html5FilesDropableAjaxEventBehavior = new HTML5FilesDropableAjaxEventBehavior());
	}

	public HTML5FilesDropablePanel(String id, IModel<?> model)
	{
		super(id, model);
		add(html5FilesDropableAjaxEventBehavior = new HTML5FilesDropableAjaxEventBehavior());
	}

	/**
	 * Registers the event function if the dom is ready to the element with the current id. dragover
	 * / drop event.
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		InputStream eventFunctionScript = null;
		try
		{
			eventFunctionScript = HTML5FilesDropablePanel.class.getResourceAsStream(HTML5FilesDropablePanel.class.getSimpleName() +
				"EventFunctionScript.js");
			String string = IOUtils.toString(eventFunctionScript);
			String format = String.format(string, getMarkupId(),
				html5FilesDropableAjaxEventBehavior.getCallbackScript());
			response.render(OnDomReadyHeaderItem.forScript(format));
		}
		catch (IOException e)
		{
			throw new IllegalStateException("The event function script couldn't be load.", e);
		}
		finally
		{
			IOUtils.closeQuietly(eventFunctionScript);
		}
	}

	/**
	 * Renders the callback script to the event function so that the files are going to be send back
	 * to the server if dropped.
	 */
	private class HTML5FilesDropableAjaxEventBehavior extends AbstractDefaultAjaxBehavior
	{

		private static final long serialVersionUID = -2575324860936557552L;

		@Override
		public CharSequence getCallbackScript()
		{
			InputStream callbackScript = null;
			try
			{
				callbackScript = HTML5FilesDropablePanel.class.getResourceAsStream(HTML5FilesDropablePanel.class.getSimpleName() +
					"CallbackScript.js");

				String string = IOUtils.toString(callbackScript);
				return String.format(string, getStartUploadClientScript(), getCallbackUrl(),
					getFinishedUploadClientScript());
			}
			catch (IOException e)
			{
				throw new IllegalStateException("The callback script couldn't be load.", e);
			}
		}

		/**
		 * Handles the request if the user droped files into the area of the panel. The filename is
		 * received from a parameter of the url which is encoded at client side / decoded at server
		 * side and the content from the data of the ajax call.
		 */
		@Override
		protected void respond(AjaxRequestTarget target)
		{
			InputStream inputStream = null;
			String fileName = "";
			String fileid = "";
			String dropid = "";
			try
			{
				HttpServletRequest httpServletRequest = (HttpServletRequest)RequestCycle.get()
					.getRequest()
					.getContainerRequest();
				fileName = httpServletRequest.getParameter("fileName");
				fileid = httpServletRequest.getParameter("fileid");
				dropid = httpServletRequest.getParameter("dropid");
				String data = getRequestCycle().getRequest()
					.getPostParameters()
					.getParameterValue("data")
					.toString();
				if (data != null)
				{
					inputStream = new ByteArrayInputStream(Base64.decodeBase64(data.getBytes()));
					handleResponse(target, URLDecoder.decode(fileName, "UTF-8"), inputStream,
						dropid, fileid);
				}
				else
				{
					handleFailure(target, fileName, dropid, fileid);
					LOGGER.warn("The data could'nt be processed - it may be that the max post size is to low! Example for tomcat: maxPostSize in Connector settings.");
				}
			}
			catch (IOException e)
			{
				handleFailure(target, fileName, dropid, fileid);
				LOGGER.error("Error while receiving a droped file", e);
			}
			finally
			{
				IOUtils.closeQuietly(inputStream);
			}
		}
	}

	/**
	 * Used to handle if a user dropped a file into the panels area. The stream doesn't have to be
	 * closed.
	 * 
	 * @param target
	 *            the target to give feedback to the user.
	 * @param fileName
	 *            the name of the file which has been received
	 * @param inputStream
	 *            the file which has been received
	 * @param dropid
	 *            a random id which is the same for a set of files which have been dropped into the
	 *            panel's area
	 * @param fileid
	 *            a random file id
	 */
	protected abstract void handleResponse(AjaxRequestTarget target, String fileName,
		InputStream inputStream, String dropid, String fileid);

	/**
	 * If the file size was to big or the content could'nt be decoded
	 * 
	 * @param target
	 *            the target to give feedback to the user.
	 * @param fileName
	 *            the name of the file which has been received
	 * @param dropid
	 *            a random id which is the same for a set of files which have been dropped into the
	 *            panel's area
	 * @param fileid
	 *            a random file id
	 */
	protected void handleFailure(AjaxRequestTarget target, String fileName, String dropid,
		String fileid)
	{
		// To be overridden
	}

	/**
	 * Overwrite this function to inject additional client code into the loop for each file. This
	 * script will we executed before each file's ajax call. This method can be used to add some
	 * information into the panel for example, so that the user see's the file started uploading. <br>
	 * <br>
	 * The following variables are accessable in this script:<br>
	 * <br>
	 * <b>file</b> - the file object containing the data / the name of the file (e.g. file.name)<br>
	 * <b>file.dropid</b> - the drop id the ajax call was made<br>
	 * <b>file.fileid</b> - the id of the file<br>
	 * <b>evt</b> - the drop event<br>
	 * <br>
	 * <b>!!! Important - this script can also be used to prevent the ajax call to be processed by
	 * inserting a return;</b>
	 * 
	 * @return the script to be placed in the file loop
	 */
	protected String getStartUploadClientScript()
	{
		return "";
	}

	/**
	 * Overwrite this function to inject additional client code into the finish method of the ajax
	 * call. THis method can be used to add some information into the panel for example, so that the
	 * user see's the file finished uploading.<br>
	 * <br>
	 * The following variables are accessable in this script:<br>
	 * <br>
	 * <b>file</b> - the file object containing the data / the name of the file (e.g. file.name)<br>
	 * <b>file.dropid</b> - the drop id the ajax call was made<br>
	 * <b>file.fileid</b> - the id of the file<br>
	 * <b>evt</b> - the drop event
	 * 
	 * @return the script to be placed in the sucess method of the ajax upload
	 */
	protected String getFinishedUploadClientScript()
	{
		return "";
	}
}
