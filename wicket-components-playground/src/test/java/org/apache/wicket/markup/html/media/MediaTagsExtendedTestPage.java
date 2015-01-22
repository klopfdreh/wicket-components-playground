package org.apache.wicket.markup.html.media;

import java.util.Locale;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.media.Track.Kind;
import org.apache.wicket.markup.html.media.video.Video;
import org.apache.wicket.request.resource.PackageResourceReference;

public class MediaTagsExtendedTestPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public MediaTagsExtendedTestPage()
	{

		Video video = new Video("video", new MediaStreamingResourceReference(
			MediaTagsTestPage.class, "dummyVideo.m4a"));
		
		// source tag
		Source source = new Source("source","http://www.mytestpage.xc/video.m4a");
		source.setMedia("screen and (device-width:500px)");
		source.setType("video/mp4");
		source.setDisplayType(true);
		video.add(source);
		
		// tack tag
		Track track = new Track("track", new PackageResourceReference(MediaTagsTestPage.class,"dummySubtitles.vtt"));
		track.setKind(Kind.subtitles);
		track.setLabel("Subtitles of video");
		track.setSrclang(Locale.GERMANY);
		track.setDefaultTrack(true);
		video.add(track);
		
		add(video);
	}

}
