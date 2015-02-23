package org.apache.wicketstuff.html5.media.subtitles;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.io.Streams;

/**
 * Generates the VTT content by the given VTTGen. The content might be internationalized with
 * Wicket's i18n functionality.
 * 
 * @author Tobias Soloschenko
 */
public abstract class VTTResourceReference extends ResourceReference
{

	private static final long serialVersionUID = 1L;

	public VTTResourceReference(String name)
	{
		super(name);
	}

	@Override
	public IResource getResource()
	{
		return new AbstractResource()
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected ResourceResponse newResourceResponse(Attributes attributes)
			{
				ResourceResponse resourceResponse = new ResourceResponse();
				resourceResponse.setContentType("text/vtt");
				resourceResponse.setTextEncoding("UTF-8");
				resourceResponse.setWriteCallback(new WriteCallback()
				{
					@Override
					public void writeData(Attributes attributes) throws IOException
					{
						OutputStream outputStream = attributes.getResponse().getOutputStream();
						Streams.copy(new ByteArrayInputStream(getWebVtt().create().getBytes()),
							outputStream);
					}
				});
				return resourceResponse;
			}
		};
	}

	/**
	 * Used to get the VTTGen to created the content with
	 * 
	 * @return the VTTGen to create the VTT content with
	 */
	public abstract WebVtt getWebVtt();

}
