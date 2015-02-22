package org.apache.wicketstuff.html5.media.subtitles;

import org.apache.wicketstuff.html5.media.subtitles.VTTItem.Alignment;
import org.apache.wicketstuff.html5.media.subtitles.VTTItem.Vertical;

public class VTTGeneratorTest
{

	public static void main(String[] args)
	{
		VTTGen vttgen = new VTTGen();

		vttgen.addItem(new VTTItem().setStartTime(new VTTTime().setS(1))
			.setStopTime(new VTTTime().setS(2))
			.setAlignment(Alignment.start)
			.setText("Hello"));
		vttgen.addItem(new VTTItem().setStartTime(new VTTTime().setS(2))
			.setStopTime(new VTTTime().setS(3))
			.setText("Hello2"));
		vttgen.addItem(new VTTItem().setStartTime(new VTTTime().setS(4))
			.setStopTime(new VTTTime().setS(5))
			.setText("Hello3")
			.setVertical(Vertical.vertical_lr)
			.setPosition("20%")
			.setSize("60%")
			.setLine("0"));

		System.out.println(vttgen.generate());

	}
}
