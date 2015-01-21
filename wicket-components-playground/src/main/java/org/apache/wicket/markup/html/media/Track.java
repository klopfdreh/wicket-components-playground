package org.apache.wicket.markup.html.media;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

public class Track extends WebMarkupContainer {

	private static final long serialVersionUID = 1L;

	private Kind kind;

	private String label;

	public Track(String id) {
		super(id);
	}

	public Track(String id, IModel<?> model) {
		super(id, model);
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		this.checkComponentTag(tag, "track");
		super.onComponentTag(tag);

		if (this.kind != null) {
			tag.put("kind", this.kind.name());
		}
		if (this.label != null) {
			tag.put("label", this.label);
		}
	}

	/**
	 * Gets the kind of the track belongs to the media component
	 * 
	 * @see {@link #setKind(Kind)}
	 * 
	 * @return the kind
	 */
	public Kind getKind() {
		return this.kind;
	}

	/**
	 * Sets the kind of the track belongs to the media component<br>
	 * <br>
	 * <b>subtitles</b>: Transcription or translation of the dialogue, suitable for when the sound is available but not understood (e.g. because the
	 * user does not understand the language of the media resource's soundtrack). Displayed over the video.<br>
	 * <br>
	 * <b>captions</b>: Transcription or translation of the dialogue, sound effects, relevant musical cues, and other relevant audio information,
	 * suitable for when the soundtrack is unavailable (e.g. because it is muted or because the user is deaf). Displayed over the video; labeled as
	 * appropriate for the hard-of-hearing.<br>
	 * <br>
	 * <b>descriptions</b>: Textual descriptions of the video component of the media resource, intended for audio synthesis when the visual component
	 * is unavailable (e.g. because the user is interacting with the application without a screen while driving, or because the user is blind).
	 * Synthesized as separate audio track.<br>
	 * <br>
	 * <b>chapters</b>: Chapter titles, intended to be used for navigating the media resource. Displayed as an interactive list in the user agent's
	 * interface.<br>
	 * <br>
	 * <b>metadata</b>: Tracks intended for use from script. Not displayed by the user agent.<br>
	 * <br>
	 * 
	 * @param the
	 *            kind
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	/**
	 * The label for this track
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Sets the label for this track
	 * 
	 * @param label
	 *            the label to be set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * To be used for the kind attribute
	 */
	public enum Kind {
		subtitles,
		captions,
		descriptions,
		chapters,
		metadata
	}
}
