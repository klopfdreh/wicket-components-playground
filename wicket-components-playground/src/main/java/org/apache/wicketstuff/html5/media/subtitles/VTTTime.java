package org.apache.wicketstuff.html5.media.subtitles;

public class VTTTime
{
	private int h = 0;
	private int m = 0;
	private int s = 0;
	private int mi = 0;

	public int getH()
	{
		return h;
	}

	public VTTTime setH(int h)
	{
		this.h = h;
		return this;
	}

	public int getM()
	{
		return m;
	}

	public VTTTime setM(int m)
	{
		this.m = m;
		return this;
	}

	public int getS()
	{
		return s;
	}

	public VTTTime setS(int s)
	{
		this.s = s;
		return this;
	}

	public int getMi()
	{
		return mi;
	}

	public VTTTime setMi(int mi)
	{
		this.mi = mi;
		return this;
	}

	public String getRepresentation()
	{
		return String.format("%02d:%02d:%02d.%03d", h, m, s, mi);
	}
}
