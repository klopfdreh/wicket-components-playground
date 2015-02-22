package org.apache.wicketstuff.html5.media.subtitles;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class VTTGen
{

	private List<VTTItem> items = new ArrayList<VTTItem>();

	public static final SimpleDateFormat VTT_TIME = new SimpleDateFormat("HH:mm:ss.SSS");

	public void addItem(VTTItem vttItem)
	{
		items.add(vttItem);
	}

	public void removeItem(VTTItem vttItem)
	{
		items.remove(vttItem);
	}

	public void removeItem(int index)
	{
		items.remove(index);
	}

	public String generate()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("WEBVTT");
		builder.append("\n\n");
		for (int i = 0; i < items.size(); i++)
		{
			builder.append(i + 1);
			builder.append("\n");
			builder.append(items.get(i).getRepresentation());
			builder.append("\n\n");
		}
		return builder.toString();
	}
}
