package org.apache.wicketstuff.html5.media.subtitles;

import org.apache.wicketstuff.html5.media.subtitles.VttCue.Alignment;
import org.apache.wicketstuff.html5.media.subtitles.VttCue.Vertical;
import org.apache.wicketstuff.html5.media.subtitles.VttRegion.Scroll;

public class VTTGeneratorTest
{

	public static void main(String[] args)
	{
		WebVtt webvtt = new WebVtt();
		webvtt.setDescription("this is the description");
		webvtt.setNote("This is the note");
		VttRegion vttRegion = new VttRegion("fred", "40%", 3, "0%,100%", "10%,90%", Scroll.up);
		webvtt.addRegion(vttRegion);
		VttRegion vttRegion2 = new VttRegion("fred", "40%", 3, "0%,100%", "10%,90%", Scroll.up);
		webvtt.addRegion(vttRegion2);

		webvtt.addCue(new VttCue("1", new VttTime().setS(1), new VttTime().setS(2), "Hello").setAlignment(
			Alignment.start)
			.setVoiceSpan("test")
			.setNote("This is a note\nvdsvsd"));
		webvtt.addCue(new VttCue("2", new VttTime().setS(2), new VttTime().setS(3), "Hello2").setVoiceSpan(
			".test2")
			.setRegion(vttRegion));
		webvtt.addCue(new VttCue("3", new VttTime().setS(4), new VttTime().setS(5), "Hello3").setVertical(
			Vertical.rl)
			.setPosition("20%")
			.setSize("60%")
			.setLine("0"));
		System.out.println(webvtt.create());

	}
}
