package wicket.components.playground;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This panel is able to handle HTML5 dropable events and receives the files
 * which are dropped to the panel's markup. The style of the area is done via
 * CSS in the markup. With the AjaxRequestTarget it's able to give a response to
 * the client, for example to show that the files has been uploaded.<br>
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
 * 		    String dropid, String id) {
 * 		// Handle the file
 * 	    }
 * 
 * 	    // The following methods are not required to be overridden - see the java doc which javascript variables are available
 * 
 * 	    {@literal @}Override
 *	    protected String getStartUploadClientScript() {
 *		return "alert('start'+file.dropid); alert('start'+file.fileid);"; 
 *	    };
 * 
 *	    {@literal @}Override
 *	    protected String getFinishedUploadClientScript() {
 * 		return "alert('finished'+file.dropid); alert('finished'+file.fileid);";
 *	    }
 * 	});
 * </pre></code>HTML: <code><pre>
 * &lt;div wicket:id="dropable" style="width:100px;height:100px;border:1px solid black;"&gt;&lt;/div&gt;
 * </pre></code> If the user uploads several files a request is made for every
 * file to the handleReponse-Method. <br>
 * <b>!!! The fileServletInputStream is closed after the handleReponse-Method is
 * complete - so you don't have to be worried about that. !!!</b>
 * 
 * @author tobiassoloschenko
 */
public abstract class HTML5FilesDropablePanel extends WebMarkupContainer {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(HTML5FilesDropablePanel.class);

    private HTML5FilesDropableAjaxEventBehavior html5FilesDropableAjaxEventBehavior;

    public HTML5FilesDropablePanel(String id) {
	super(id);
	add(html5FilesDropableAjaxEventBehavior = new HTML5FilesDropableAjaxEventBehavior());
    }

    public HTML5FilesDropablePanel(String id, IModel<?> model) {
	super(id, model);
	add(html5FilesDropableAjaxEventBehavior = new HTML5FilesDropableAjaxEventBehavior());
    }

    /**
     * Registers the event function if the dom is ready to the element with the
     * current id. dragover / drop event.
     */
    @Override
    public void renderHead(IHeaderResponse response) {
	super.renderHead(response);
	InputStream eventFunctionScript = null;
	try {
	    eventFunctionScript = HTML5FilesDropablePanel.class
		    .getResourceAsStream(HTML5FilesDropablePanel.class
			    .getSimpleName() + "EventFunctionScript.js");
	    String string = IOUtils.toString(eventFunctionScript);
	    String format = String.format(string, getMarkupId(),
		    html5FilesDropableAjaxEventBehavior.getCallbackScript());
	    response.render(OnDomReadyHeaderItem.forScript(format));
	} catch (IOException e) {
	    throw new IllegalStateException(
		    "The event function script couldn't be load.", e);
	} finally {
	    IOUtils.closeQuietly(eventFunctionScript);
	}
    }

    /**
     * Renders the callback script to the event function so that the files are
     * going to be send back to the server if dropped.
     */
    private class HTML5FilesDropableAjaxEventBehavior extends
	    AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = -2575324860936557552L;

	@Override
	public CharSequence getCallbackScript() {
	    InputStream callbackScript = null;
	    try {
		callbackScript = HTML5FilesDropablePanel.class
			.getResourceAsStream(HTML5FilesDropablePanel.class
				.getSimpleName() + "CallbackScript.js");

		String string = IOUtils.toString(callbackScript);
		return String.format(string, getStartUploadClientScript(),
			getCallbackUrl(), getFinishedUploadClientScript());
	    } catch (IOException e) {
		throw new IllegalStateException(
			"The callback script couldn't be load.", e);
	    }
	}

	/**
	 * Handles the request if the user droped files into the area of the
	 * panel. The filename is received from a parameter of the url which is
	 * encoded at client side / decoded at server side and the content from
	 * the data of the ajax call.
	 */
	@Override
	protected void respond(AjaxRequestTarget target) {
	    ServletInputStream fileServletInputStream = null;
	    try {
		HttpServletRequest httpServletRequest = (HttpServletRequest) RequestCycle
			.get().getRequest().getContainerRequest();
		String fileName = httpServletRequest.getParameter("fileName");
		String fileid = httpServletRequest.getParameter("fileid");
		String dropid = httpServletRequest.getParameter("dropid");
		fileServletInputStream = httpServletRequest.getInputStream();
		handleResponse(target, URLDecoder.decode(fileName, "UTF-8"),
			fileServletInputStream, dropid, fileid);
	    } catch (IOException e) {
		LOGGER.error("Error while receiving a droped file", e);
	    } finally {
		IOUtils.closeQuietly(fileServletInputStream);
	    }
	}
    }

    /**
     * Used to handle if a user dropped a file into the panels area. The stream
     * doesn't have to be closed.
     * 
     * @param target
     *            the target to give feedback to the user.
     * @param fileName
     *            the name of the file which has been received
     * @param fileServletInputStream
     *            the file which has been received
     * @param dropid
     *            a random id which is the same for a set of files which have
     *            been dropped into the panel's area
     * @param fileid
     *            a random file id
     */
    protected abstract void handleResponse(AjaxRequestTarget target,
	    String fileName, ServletInputStream fileServletInputStream,
	    String dropid, String fileid);

    /**
     * Overwrite this function to inject additional client code into the loop
     * for each file. This script will we executed before each file's ajax call.
     * This method can be used to add some information into the panel for
     * example, so that the user see's the file started uploading. <br>
     * <br>
     * The following variables are accessable in this script:<br>
     * <br>
     * <b>file</b> - the file object containing the data / the name of the file
     * (e.g. file.name)<br>
     * <b>file.dropid</b> - the drop id the ajax call was made<br>
     * <b>file.fileid</b> - the id of the file<br>
     * <b>evt</b> - the drop event
     * 
     * 
     * @return the script to be placed in the file loop
     */
    protected String getStartUploadClientScript() {
	return "";
    }

    /**
     * Overwrite this function to inject additional client code into the finish
     * method of the ajax call. THis method can be used to add some information
     * into the panel for example, so that the user see's the file finished
     * uploading.<br>
     * <br>
     * The following variables are accessable in this script:<br>
     * <br>
     * <b>file</b> - the file object containing the data / the name of the file
     * (e.g. file.name)<br>
     * <b>file.dropid</b> - the drop id the ajax call was made<br>
     * <b>file.fileid</b> - the id of the file<br>
     * <b>response</b> - the response wicket gives back to the client after the ajax call<br>
     * <b>evt</b> - the drop event
     * 
     * @return the script to be placed in the sucess method of the ajax upload
     */
    protected String getFinishedUploadClientScript() {
	return "";
    }
}
