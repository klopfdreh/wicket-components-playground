package org.apache.wicket.markup.html.image;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * A component which displays localizable image resources within a picture tag.
 * 
 * @see org.apache.wicket.markup.html.image.Image
 * @author Tobias Soloschenko
 * 
 */
public class Source extends Image {

	private static final long serialVersionUID = 1L;

	private String media;

	/**
	 * @see org.apache.wicket.markup.html.image.Image
	 */
	protected Source(final String id) {
		super(id);
	}

	/**
	 * @see org.apache.wicket.markup.html.image.Image
	 */
	public Source(final String id, final ResourceReference... resourceReferences) {
		this(id, null, resourceReferences);
	}

	/**
	 * @see org.apache.wicket.markup.html.image.Image
	 */
	public Source(final String id, PageParameters resourceParameters, final ResourceReference... resourceReferences) {
		super(id);
		this.setImageResourceReferences(resourceParameters, resourceReferences);
	}

	/**
	 * @see org.apache.wicket.markup.html.image.Image
	 */
	public Source(final String id, final IResource... imageResources) {
		super(id);
		this.setImageResources(imageResources);
	}

	/**
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public Source(final String id, final IModel<?> model) {
		super(id, model);
	}

	/**
	 * @see org.apache.wicket.markup.html.image.Image
	 */
	public Source(final String id, final String string) {
		this(id, new Model<String>(string));
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		tag.setName("img");
		super.onComponentTag(tag);
		tag.setName("source");
		if (this.media != null) {
			tag.put("media", this.media);
		}
	}

	/**
	 * Builds the srcset attribute and removes the src attribute
	 */
	@Override
	protected void buildSrcAndSrcSetTag(ComponentTag tag, boolean renderSrcAttribute) {
		super.buildSrcAndSrcSetTag(tag, false);
	}

	/**
	 * @param media
	 *            the media attribute
	 */
	public void setMedia(String media) {
		this.media = media;
	}
}
