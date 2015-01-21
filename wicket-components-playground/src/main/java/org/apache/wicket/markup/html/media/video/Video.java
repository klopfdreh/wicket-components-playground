package org.apache.wicket.markup.html.media.video;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.media.MediaComponent;
import org.apache.wicket.markup.html.media.MediaStreamingResourceReference;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * A video media component to display videos.
 * 
 * @author Tobias Soloschenko
 */
public class Video extends MediaComponent {

	private static final long serialVersionUID = 1L;

	private ResourceReference poster;

	private Integer width;

	private Integer height;

	public Video(String id) {
		super(id);
	}

	public Video(String id, IModel<?> model) {
		super(id, model);
	}

	public Video(String id, MediaStreamingResourceReference mediaStreamingResourceReference) {
		super(id, mediaStreamingResourceReference);
	}

	public Video(String id, IModel<?> model, MediaStreamingResourceReference mediaStreamingResourceReference) {
		super(id, model, mediaStreamingResourceReference);
	}

	public Video(String id, MediaStreamingResourceReference mediaStreamingResourceReference, PageParameters pageParameters) {
		super(id, mediaStreamingResourceReference, pageParameters);
	}

	public Video(String id, IModel<?> model, MediaStreamingResourceReference mediaStreamingResourceReference, PageParameters pageParameters) {
		super(id, model, mediaStreamingResourceReference, pageParameters);
	}

	public Video(String id, String url) {
		super(id, url);
	}

	public Video(String id, IModel<?> model, String url) {
		super(id, model, url);
	}

	public Video(String id, String url, PageParameters pageParameters) {
		super(id, url, pageParameters);
	}

	public Video(String id, IModel<?> model, String url, PageParameters pageParameters) {
		super(id, model, url, pageParameters);
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		this.checkComponentTag(tag, "video");
		super.onComponentTag(tag);

		if (this.width != null) {
			tag.put("width", this.width);
		}

		if (this.height != null) {
			tag.put("height", this.height);
		}

		if (this.poster != null) {
			tag.put("poster", RequestCycle.get().urlFor(this.poster, null));
		}
	}

	/**
	 * The image to be displayed if the video isn't available
	 * 
	 * @return the resource reference of the image
	 */
	public ResourceReference getPoster() {
		return this.poster;
	}

	/**
	 * Sets the image to be displayed if the video isn't available
	 * 
	 * @param the
	 *            resource reference of the image used if the video isn't available
	 */
	public void setPoster(ResourceReference poster) {
		this.poster = poster;
	}

	/**
	 * Gets the width of the video area
	 * 
	 * @return the width of the video area
	 */
	public Integer getWidth() {
		return this.width;
	}

	/**
	 * Sets the width of the video area
	 * 
	 * @param width
	 *            the width of the video area
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * Gets the height of the video area
	 * 
	 * @return the height of the video area
	 */
	public Integer getHeight() {
		return this.height;
	}

	/**
	 * Sets the height of the video area
	 * 
	 * @param height
	 *            the height of the video area
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}
}
