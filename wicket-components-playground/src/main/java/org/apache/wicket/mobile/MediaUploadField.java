package org.apache.wicket.mobile;

import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;

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

	protected Type type;


	public MediaUploadField(String id, Type type)
	{
		this(id, null, type);
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
}
