package org.apache.wicket.markup.html.media;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.media.MediaComponent.Cors;
import org.apache.wicket.markup.html.media.audio.Audio;
import org.apache.wicket.markup.html.media.video.Video;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;

public class MediaTagsTestPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public MediaTagsTestPage()
	{
		PageParameters pageParameters = new PageParameters();
		pageParameters.set("test", "test");
		Audio audio = new Audio("audio", new MediaStreamingResourceReference(
			MediaTagsTestPage.class, "dummyAudio.mp3"), pageParameters);
		audio.setAutoplay(true);
		audio.setControls(true);
		audio.setCrossorigin(Cors.use_credentials);
		audio.setLoop(true);
		audio.setMuted(true);
		audio.setStartTime("5");
		audio.setEndTime("10");
		add(audio);

		Video video = new Video("video", new MediaStreamingResourceReference(
			MediaTagsTestPage.class, "dummyVideo.m4a"));
		PageParameters pageParameters2 = new PageParameters();
		pageParameters2.add("test2", "test2");
		video.setPoster(new PackageResourceReference(MediaTagsTestPage.class, "dummyPoster.jpg"),
			pageParameters2);
		video.setWidth(500);
		video.setHeight(400);
		add(video);
	}

}
