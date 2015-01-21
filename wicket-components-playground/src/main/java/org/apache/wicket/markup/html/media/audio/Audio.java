package org.apache.wicket.markup.html.media.audio;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.media.MediaComponent;
import org.apache.wicket.markup.html.media.MediaStreamingResourceReference;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * An audio media component to playback audio files.
 * 
 * @author Tobias Soloschenko
 * 
 */
public class Audio extends MediaComponent {

	private static final long serialVersionUID = 1L;

	public Audio(String id) {
		super(id);
	}

	public Audio(String id, IModel<?> model) {
		super(id, model);
	}

	public Audio(String id, MediaStreamingResourceReference mediaStreamingResourceReference) {
		super(id, mediaStreamingResourceReference);
	}

	public Audio(String id, IModel<?> model, MediaStreamingResourceReference mediaStreamingResourceReference) {
		super(id, model, mediaStreamingResourceReference);
	}

	public Audio(String id, MediaStreamingResourceReference mediaStreamingResourceReference, PageParameters pageParameters) {
		super(id, mediaStreamingResourceReference, pageParameters);
	}

	public Audio(String id, IModel<?> model, MediaStreamingResourceReference mediaStreamingResourceReference, PageParameters pageParameters) {
		super(id, model, mediaStreamingResourceReference, pageParameters);
	}

	public Audio(String id, String url) {
		super(id, url);
	}

	public Audio(String id, IModel<?> model, String url) {
		super(id, model, url);
	}

	public Audio(String id, String url, PageParameters pageParameters) {
		super(id, url, pageParameters);
	}

	public Audio(String id, IModel<?> model, String url, PageParameters pageParameters) {
		super(id, model, url, pageParameters);
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		this.checkComponentTag(tag, "audio");
		super.onComponentTag(tag);
	}
}
