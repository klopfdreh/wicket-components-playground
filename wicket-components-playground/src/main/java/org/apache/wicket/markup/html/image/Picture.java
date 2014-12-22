package org.apache.wicket.markup.html.image;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

/**
 * A component which displays localizable image resources within source and image elements as responsive image.
 * 
 * @author Tobias Soloschenko
 */
public class Picture extends WebMarkupContainer {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a picture component
	 * 
	 * @param id
	 *            the id of the picture component
	 */
	public Picture(String id) {
		super(id);
	}

	/**
	 * Creates a picture component
	 * 
	 * @param id
	 *            the id of the picture component
	 * @param model
	 *            the component's model
	 */
	public Picture(String id, IModel<?> model) {
		super(id, model);
	}

	/**
	 * builds the component tag and checks the tag to be a picture
	 */
	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		this.checkComponentTag(tag, "picture");
	}

	/**
	 * Adds an image
	 * 
	 * @param image
	 *            the image to add
	 */
	public void addImage(Image image) {
		this.add(image);
	}

	/**
	 * Adds a source
	 * 
	 * @param source
	 *            the source to add
	 */
	public void addSource(Source source) {
		this.add(source);
	}
}
