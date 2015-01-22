package org.apache.wicket.markup.html.media;

import java.util.Locale;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * The track tag is used to provide subtitles, captions, descriptions, chapters, metadata to a video
 * media component
 * 
 * @author Tobias Soloschenko
 * 
 */
public class Track extends WebMarkupContainer
{

	private static final long serialVersionUID = 1L;

	private Kind kind;

	private String label;

	private Boolean defaultTrack;

	private Locale srclang;

	private ResourceReference resourceReference;

	private String url;

	private PageParameters pageParameters;

	public Track(String id)
	{
		super(id);
	}

	public Track(String id, IModel<?> model)
	{
		super(id, model);
	}

	public Track(String id, ResourceReference resourceReference)
	{
		this(id);
		this.resourceReference = resourceReference;
	}

	public Track(String id, IModel<?> model, ResourceReference resourceReference)
	{
		this(id, model);
		this.resourceReference = resourceReference;
	}

	public Track(String id, ResourceReference resourceReference, PageParameters pageParameters)
	{
		this(id);
		this.resourceReference = resourceReference;
		this.pageParameters = pageParameters;
	}

	public Track(String id, IModel<?> model, ResourceReference resourceReference,
		PageParameters pageParameters)
	{
		this(id, model);
		this.resourceReference = resourceReference;
		this.pageParameters = pageParameters;
	}

	public Track(String id, String url)
	{
		this(id);
		this.url = url;
	}

	public Track(String id, IModel<?> model, String url)
	{
		this(id, model);
		this.url = url;
	}

	public Track(String id, String url, PageParameters pageParameters)
	{
		this(id);
		this.url = url;
		this.pageParameters = pageParameters;
	}

	public Track(String id, IModel<?> model, String url, PageParameters pageParameters)
	{
		this(id, model);
		this.url = url;
		this.pageParameters = pageParameters;
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		this.checkComponentTag(tag, "track");
		super.onComponentTag(tag);

		if (this.resourceReference != null)
		{
			tag.put("src", RequestCycle.get().urlFor(this.resourceReference, this.pageParameters));
		}

		if (this.url != null)
		{
			tag.put("src", this.url);
		}

		if (this.kind != null)
		{
			tag.put("kind", this.kind.name());
		}

		if (this.label != null)
		{
			tag.put("label", this.label);
		}

		if (this.defaultTrack != null && this.defaultTrack)
		{
			tag.put("default", "default");
		}

		// if the srclang field is set use this, else if the
		// resource reference provides a locale use the language
		// of the resource reference
		if (this.srclang != null)
		{
			tag.put("srclang", this.srclang.getLanguage());
		}
		else if (this.resourceReference != null && this.resourceReference.getLocale() != null)
		{
			tag.put("srclang", this.resourceReference.getLocale().getLanguage());
		}
	}

	/**
	 * Gets the kind of the track belongs to the media component
	 * 
	 * @see {@link #setKind(Kind)}
	 * 
	 * @return the kind
	 */
	public Kind getKind()
	{
		return this.kind;
	}

	/**
	 * Sets the kind of the track belongs to the media component<br>
	 * <br>
	 * <b>subtitles</b>: Transcription or translation of the dialogue, suitable for when the sound
	 * is available but not understood (e.g. because the user does not understand the language of
	 * the media resource's soundtrack). Displayed over the video.<br>
	 * <br>
	 * <b>captions</b>: Transcription or translation of the dialogue, sound effects, relevant
	 * musical cues, and other relevant audio information, suitable for when the soundtrack is
	 * unavailable (e.g. because it is muted or because the user is deaf). Displayed over the video;
	 * labeled as appropriate for the hard-of-hearing.<br>
	 * <br>
	 * <b>descriptions</b>: Textual descriptions of the video component of the media resource,
	 * intended for audio synthesis when the visual component is unavailable (e.g. because the user
	 * is interacting with the application without a screen while driving, or because the user is
	 * blind). Synthesized as separate audio track.<br>
	 * <br>
	 * <b>chapters</b>: Chapter titles, intended to be used for navigating the media resource.
	 * Displayed as an interactive list in the user agent's interface.<br>
	 * <br>
	 * <b>metadata</b>: Tracks intended for use from script. Not displayed by the user agent.<br>
	 * <br>
	 * 
	 * @param the
	 *            kind
	 */
	public void setKind(Kind kind)
	{
		this.kind = kind;
	}

	/**
	 * The label for this track
	 * 
	 * @return the label
	 */
	public String getLabel()
	{
		return this.label;
	}

	/**
	 * Sets the label for this track
	 * 
	 * @param label
	 *            the label to be set
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * If the track is the default track
	 * 
	 * @return if the track is the default track
	 */
	public Boolean getDefaultTrack()
	{
		return this.defaultTrack != null ? this.defaultTrack : false;
	}

	/**
	 * Sets if this track is the default track
	 * 
	 * @param defaultTrack
	 *            if the track is the default track
	 */
	public void setDefaultTrack(Boolean defaultTrack)
	{
		this.defaultTrack = defaultTrack;
	}

	/**
	 * Gets the src lang
	 * 
	 * @return the src lang
	 */
	public Locale getSrclang()
	{
		return this.srclang;
	}

	/**
	 * Sets the src lang
	 * 
	 * @param srclang
	 *            the src lang to set
	 */
	public void setSrclang(Locale srclang)
	{
		this.srclang = srclang;
	}

	/**
	 * To be used for the kind attribute
	 */
	public enum Kind
	{
		subtitles, captions, descriptions, chapters, metadata
	}
}
