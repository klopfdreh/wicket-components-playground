package org.wicketstuff.html5.shape.example;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.html5.shape.Circle;
import org.wicketstuff.html5.shape.ShapeBuilder;
import org.wicketstuff.html5.shape.ShapeBuilder.Orientation;

public class ShapeCircleExamplePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public ShapeCircleExamplePage()
	{
		add(new ShapeBuilder("shapeleft").shape(new Circle("30%"))
			.transition(new Circle("50%"))
			.useWidth("500px")
			.useHeight("500px"));
		add(new ShapeBuilder("shaperight").shape(new Circle("30%"))
			.transition(new Circle("50%"))
			.useWidth("500px")
			.useHeight("500px")
			.orientation(Orientation.right));
	}

}
