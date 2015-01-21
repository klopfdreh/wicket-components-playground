package org.apache.wicket.markup.html.media;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * The media component is used to provide basic functionality to the video and audo component. The
 * given media streaming resource reference supports Content-Ranges and other stuff to make the
 * audio and video playback smooth.
 * 
 * @author Tobias Soloschenko
 */
public abstract class MediaComponent extends WebMarkupContainer
{

	private static final long serialVersionUID = 1L;

	// use Boolean instead of elementary data types to get a lightweight component
	private Boolean autoplay;

	private Boolean loop;

	private Boolean muted;

	private Boolean controls;

	private Preload preload;

	private String startTime;

	private String endTime;

	private String mediaGroup;

	private PageParameters pageParameters;

	private MediaStreamingResourceReference mediaStreamingResourceReference;

	private String url;

	public MediaComponent(String id)
	{
		super(id);
	}

	public MediaComponent(String id, IModel<?> model)
	{
		super(id, model);
	}

	public MediaComponent(String id, MediaStreamingResourceReference mediaStreamingResourceReference)
	{
		this(id);
		this.mediaStreamingResourceReference = mediaStreamingResourceReference;
	}

	public MediaComponent(String id, IModel<?> model,
		MediaStreamingResourceReference mediaStreamingResourceReference)
	{
		this(id, model);
		this.mediaStreamingResourceReference = mediaStreamingResourceReference;
	}

	public MediaComponent(String id,
		MediaStreamingResourceReference mediaStreamingResourceReference,
		PageParameters pageParameters)
	{
		this(id);
		this.mediaStreamingResourceReference = mediaStreamingResourceReference;
		this.pageParameters = pageParameters;
	}

	public MediaComponent(String id, IModel<?> model,
		MediaStreamingResourceReference mediaStreamingResourceReference,
		PageParameters pageParameters)
	{
		this(id, model);
		this.mediaStreamingResourceReference = mediaStreamingResourceReference;
		this.pageParameters = pageParameters;
	}

	public MediaComponent(String id, String url)
	{
		this(id);
		this.url = url;
	}

	public MediaComponent(String id, IModel<?> model, String url)
	{
		this(id, model);
		this.url = url;
	}

	public MediaComponent(String id, String url, PageParameters pageParameters)
	{
		this(id);
		this.url = url;
		this.pageParameters = pageParameters;
	}

	public MediaComponent(String id, IModel<?> model, String url, PageParameters pageParameters)
	{
		this(id, model);
		this.url = url;
		this.pageParameters = pageParameters;
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);

		if (this.autoplay != null)
		{
			tag.put("autoplay", this.autoplay);
		}

		if (this.loop != null)
		{
			tag.put("loop", this.loop);
		}

		if (this.muted != null)
		{
			tag.put("muted", this.muted);
		}

		// Use getter of controls here because they should be displayed as default
		if (this.getControls() != null)
		{
			tag.put("controls", this.getControls());
		}

		if (this.preload != null)
		{
			tag.put("preload", this.preload.name());
		}

		// The time management is used to set the start / stop
		// time in seconds of the movie to be played back
		String timeManagement = "";
		if (this.startTime != null)
		{
			timeManagement = timeManagement += "#t=" + this.startTime +
				(this.endTime != null ? "," + this.endTime : "");
		}

		if (this.mediaStreamingResourceReference != null)
		{
			tag.put("src",
				RequestCycle.get()
					.urlFor(this.mediaStreamingResourceReference, this.pageParameters) +
					timeManagement);
		}

		if (this.url != null)
		{
			tag.put("src", this.url + timeManagement);
		}

		if (this.mediaGroup != null)
		{
			tag.put("mediagroup", this.mediaGroup);
		}
	}

	/**
	 * If the playback is autoplayed on load
	 * 
	 * @return If the playback is autoplayed on load
	 */
	public Boolean getAutoplay()
	{
		return this.autoplay != null ? this.autoplay : false;
	}

	/**
	 * Sets the playback to be autoplayed on load
	 * 
	 * @param If
	 *            the playback is autoplayed on load
	 */
	public void setAutoplay(Boolean autoplay)
	{
		this.autoplay = autoplay;
	}

	/**
	 * If the playback is looped
	 * 
	 * @return If the playback is looped
	 */
	public Boolean getLoop()
	{
		return this.loop != null ? this.loop : false;
	}

	/**
	 * Sets the playback to be looped
	 * 
	 * @param If
	 *            the playback is looped
	 */
	public void setLoop(Boolean loop)
	{
		this.loop = loop;
	}

	/**
	 * If the playback is muted initially
	 * 
	 * @return If the playback is muted initially
	 */
	public Boolean getMuted()
	{
		return this.muted != null ? this.muted : false;
	}

	/**
	 * Sets the playback muted initially
	 * 
	 * @param If
	 *            the playback is muted initially
	 */
	public void setMuted(Boolean muted)
	{
		this.muted = muted;
	}

	/**
	 * If the controls are going to be displayed
	 * 
	 * @return if the controls are going to displayed
	 */
	public Boolean getControls()
	{
		return this.controls != null ? this.controls : true;
	}

	/**
	 * Sets if the controls are going to be displayed
	 * 
	 * @param if the controls are going to displayed
	 */
	public void setControls(Boolean controls)
	{
		this.controls = controls;
	}

	/**
	 * The type of preload <br>
	 * <br>
	 * <b>none</b>: Hints to the user agent that either the author does not expect the user to need
	 * the media resource, or that the server wants to minimise unnecessary traffic.<br>
	 * <br>
	 * <b>metadata</b>: Hints to the user agent that the author does not expect the user to need the
	 * media resource, but that fetching the resource metadata (dimensions, first frame, track list,
	 * duration, etc) is reasonable.<br>
	 * <br>
	 * <b>auto</b>: Hints to the user agent that the user agent can put the user's needs first
	 * without risk to the server, up to and including optimistically downloading the entire
	 * resource.
	 * 
	 * @return the preload
	 */
	public Preload getPreload()
	{
		return this.preload != null ? this.preload : Preload.none;
	}

	/**
	 * Sets the type of preload <br>
	 * <br>
	 * <b>none</b>: Hints to the user agent that either the author does not expect the user to need
	 * the media resource, or that the server wants to minimise unnecessary traffic.<br>
	 * <br>
	 * <b>metadata</b>: Hints to the user agent that the author does not expect the user to need the
	 * media resource, but that fetching the resource metadata (dimensions, first frame, track list,
	 * duration, etc) is reasonable.<br>
	 * <br>
	 * <b>auto</b>: Hints to the user agent that the user agent can put the user's needs first
	 * without risk to the server, up to and including optimistically downloading the entire
	 * resource.
	 * 
	 * @param the
	 *            preload
	 */
	public void setPreload(Preload preload)
	{
		this.preload = preload;
	}

	/**
	 * Gets the position at which the media component starts the playback<br>
	 * <br>
	 * t=<b>10</b>,20<br>
	 * t=<b>npt:10</b>,20<br>
	 * <br>
	 * 
	 * t=<b>120s</b>,121.5s<br>
	 * t=<b>npt:120</b>,0:02:01.5<br>
	 * <br>
	 * 
	 * t=<b>smpte-30:0:02:00</b>,0:02:01:15<br>
	 * t=<b>smpte-25:0:02:00:00</b>,0:02:01:12.1<br>
	 * <br>
	 * 
	 * t=<b>clock:20090726T111901Z</b>,20090726T121901Z
	 * 
	 * @return the time at which position the media component starts the playback
	 */
	public String getStartTime()
	{
		return this.startTime;
	}

	/**
	 * Sets the position at which the media component starts the playback<br>
	 * <br>
	 * t=<b>10</b>,20<br>
	 * t=<b>npt:10</b>,20<br>
	 * <br>
	 * 
	 * t=<b>120s</b>,121.5s<br>
	 * t=<b>npt:120</b>,0:02:01.5<br>
	 * <br>
	 * 
	 * t=<b>smpte-30:0:02:00</b>,0:02:01:15<br>
	 * t=<b>smpte-25:0:02:00:00</b>,0:02:01:12.1<br>
	 * <br>
	 * 
	 * t=<b>clock:20090726T111901Z</b>,20090726T121901Z
	 * 
	 * @param the
	 *            time at which position the media component starts the playback
	 */
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * Gets the position at which the media component stops the playback<br>
	 * <br>
	 * t=10,<b>20</b><br>
	 * t=npt:10,<b>20</b><br>
	 * <br>
	 * 
	 * t=120s,<b>121.5s</b><br>
	 * t=npt:120,<b>0:02:01.5</b><br>
	 * <br>
	 * 
	 * t=smpte-30:0:02:00,<b>0:02:01:15</b><br>
	 * t=smpte-25:0:02:00:00,<b>0:02:01:12.1</b><br>
	 * <br>
	 * 
	 * t=clock:20090726T111901Z,<b>20090726T121901Z</b>
	 * 
	 * @return the time at which position the media component stops the playback
	 */
	public String getEndTime()
	{
		return this.endTime;
	}

	/**
	 * Sets the position at which the media component stops the playback<br>
	 * <br>
	 * t=10,<b>20</b><br>
	 * t=npt:10,<b>20</b><br>
	 * <br>
	 * 
	 * t=120s,<b>121.5s</b><br>
	 * t=npt:120,<b>0:02:01.5</b><br>
	 * <br>
	 * 
	 * t=smpte-30:0:02:00,<b>0:02:01:15</b><br>
	 * t=smpte-25:0:02:00:00,<b>0:02:01:12.1</b><br>
	 * <br>
	 * 
	 * t=clock:20090726T111901Z,<b>20090726T121901Z</b>
	 * 
	 * @param the
	 *            time at which position the media component stops the playback
	 */
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	/**
	 * Gets the media group.
	 * 
	 * @return the media group
	 */
	public String getMediaGroup()
	{
		return this.mediaGroup;
	}

	/**
	 * Sets the media group
	 * 
	 * @param mediaGroup
	 *            to be set
	 */
	public void setMediaGroup(String mediaGroup)
	{
		this.mediaGroup = mediaGroup;
	}

	/**
	 * To be used for the preload attribute
	 */
	public enum Preload
	{
		none, metadata, auto
	}

}
