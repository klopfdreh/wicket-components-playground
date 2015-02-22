package org.apache.wicketstuff.html5.media.subtitles;

public class VTTItem
{

	public enum Vertical
	{
		vertical("vertical"), vertical_lr("vertical-lr");

		private String verticalName;

		Vertical(String verticalName)
		{
			this.verticalName = verticalName;
		}

		public String getVerticalName()
		{
			return verticalName;
		}

		public void setVerticalName(String verticalName)
		{
			this.verticalName = verticalName;
		}

	}

	public enum Alignment
	{
		start, middle, end
	}

	private String line;

	private String position;

	private String size;

	private Alignment alignment;

	private Vertical vertical;

	private VTTTime startTime;

	private VTTTime stopTime;

	private String text;

	public StringBuilder getRepresentation()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(getStartTime().getRepresentation());
		builder.append(" --> ");
		builder.append(getStopTime().getRepresentation());
		if (getAlignment() != null)
		{
			builder.append(" align:");
			builder.append(getAlignment().name());
		}
		if (getVertical() != null)
		{
			builder.append(" vertical:");
			builder.append(getVertical().getVerticalName());
		}
		if (getSize() != null)
		{
			builder.append(" size:");
			builder.append(getSize());
		}
		if (getLine() != null)
		{
			builder.append(" line:");
			builder.append(getLine());
		}
		if (getPosition() != null)
		{
			builder.append(" position:");
			builder.append(getPosition());
		}
		builder.append(System.getProperty("line.separator"));
		builder.append(getText());
		return builder;
	}

	public String getLine()
	{
		return line;
	}

	public VTTItem setLine(String line)
	{
		this.line = line;
		return this;
	}

	public String getPosition()
	{
		return position;
	}

	public VTTItem setPosition(String position)
	{
		this.position = position;
		return this;
	}

	public String getSize()
	{
		return size;
	}

	public VTTItem setSize(String size)
	{
		this.size = size;
		return this;
	}

	public Alignment getAlignment()
	{
		return alignment;
	}

	public VTTItem setAlignment(Alignment alignment)
	{
		this.alignment = alignment;
		return this;
	}

	public Vertical getVertical()
	{
		return vertical;
	}

	public VTTItem setVertical(Vertical vertical)
	{
		this.vertical = vertical;
		return this;
	}

	public VTTTime getStartTime()
	{
		return startTime;
	}

	public VTTItem setStartTime(VTTTime startTime)
	{
		this.startTime = startTime;
		return this;
	}

	public VTTTime getStopTime()
	{
		return stopTime;
	}

	public VTTItem setStopTime(VTTTime stopTime)
	{
		this.stopTime = stopTime;
		return this;
	}

	public String getText()
	{
		return text;
	}

	public VTTItem setText(String text)
	{
		this.text = text;
		return this;
	}
}
