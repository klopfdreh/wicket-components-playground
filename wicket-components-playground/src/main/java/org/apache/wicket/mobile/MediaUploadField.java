package org.apache.wicket.mobile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IOUtils;

public class MediaUploadField extends FileUploadField
{

	private static final long serialVersionUID = -1852799458979511170L;

	public enum Type
	{
		IMAGE("image/*", "camera"), VIDEO("video/*", "camcorder"), AUDIO("audio/*", "microphone");

		private String accept;

		private String capture;

		private Type(String accept, String capture)
		{
			this.accept = accept;
			this.capture = capture;
		}

		public String getAccept()
		{
			return accept;
		}

		public String getCapture()
		{
			return capture;
		}
	}

	private Type type;

	private Image image;

	public MediaUploadField(String id, Type type)
	{
		super(id);
		this.type = type;
		setOutputMarkupId(true);
		setOutputMarkupPlaceholderTag(true);
	}

	public MediaUploadField(String id, IModel<? extends List<FileUpload>> model, Type type)
	{
		super(id, model);
		this.type = type;
		setOutputMarkupId(true);
		setOutputMarkupPlaceholderTag(true);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		// Default handling for component tag
		super.onComponentTag(tag);

		// Capture attribute must be set
		tag.put("capture", type.getCapture());

		// The accept type to get the representation of
		tag.put("accept", type.getAccept());
	}

	public void setImage(Image image)
	{
		if (type != Type.IMAGE)
		{
			throw new WicketRuntimeException("The type of the MediaUploadField is not IMAGE");
		}
		image.setOutputMarkupId(true);
		this.image = image;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		if (type == Type.IMAGE && image != null)
		{
			response.render(JavaScriptReferenceHeaderItem.forReference(Application.get()
				.getJavaScriptLibrarySettings()
				.getJQueryReference()));

			InputStream javaScriptStream = null;
			try
			{
				javaScriptStream = MediaUploadField.class.getResourceAsStream("MediaUploadField.js");
				String javaScript = IOUtils.toString(javaScriptStream);
				javaScript = javaScript.replaceAll("%\\(imageid\\)", image.getMarkupId());
				javaScript = javaScript.replaceAll("%\\(mediauploadfieldid\\)", getMarkupId());
				response.render(JavaScriptReferenceHeaderItem.forScript(javaScript, "script_" +
					getMarkupId()));
			}
			catch (IOException ioe)
			{
				throw new WicketRuntimeException("Error while reading the MediaUploadField.js", ioe);
			}
			finally
			{
				IOUtils.closeQuietly(javaScriptStream);
			}
		}
	}
}
