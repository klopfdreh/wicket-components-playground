package org.apache.wicket.markup.html.image;

import java.io.DataInputStream;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.crypt.Base64;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * The canvas image is used to embed the complete image content within a HTML document.
 * 
 * @author Tobias Soloschenko
 * 
 */
public class CanvasImage extends WebComponent
{

	private static final long serialVersionUID = 1L;

	private PackageResourceReference packageResourceReference;

	/**
	 * Creates an canvas image
	 * 
	 * @param id
	 *            the id
	 * @param packageResourceStream
	 *            the package resource stream of the image
	 */
	public CanvasImage(String id, PackageResourceReference packageResourceReference)
	{
		this(id, null, packageResourceReference);
	}

	/**
	 * Creates an canvas image
	 * 
	 * @param id
	 *            the id
	 * @param model
	 *            the model of the canvas image
	 * @param packageResourceStream
	 *            the package resource stream of the image
	 */
	public CanvasImage(String id, IModel<?> model, PackageResourceReference packageResourceReference)
	{
		super(id, model);
		this.packageResourceReference = packageResourceReference;
	}

	/**
	 * Renders the complete image tag with the base64 encoded content.
	 */
	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		checkComponentTag(tag, "img");
		super.onComponentTag(tag);

		IResourceStream packageResourceStream = packageResourceReference.getResource()
			.getResourceStream();
		if (packageResourceStream != null)
		{
			try
			{
				StringBuilder builder = new StringBuilder();
				builder.append("data:");
				builder.append(packageResourceStream.getContentType());
				builder.append(";base64,");
				byte[] bytes = new byte[(int)packageResourceStream.length().bytes()];
				DataInputStream dataInputStream = new DataInputStream(
					packageResourceStream.getInputStream());
				dataInputStream.readFully(bytes);
				builder.append(Base64.encodeBase64String(bytes));
				tag.put("src", builder.toString());
			}
			catch (Exception e)
			{
				throw new WicketRuntimeException(
					"An error occured while reading the package resource stream", e);
			}
		}
		else
		{
			// If the package resource stream is not set create an empty image
			tag.put("src", "#");
			tag.put("style", "display:none;");
		}
	}
}
