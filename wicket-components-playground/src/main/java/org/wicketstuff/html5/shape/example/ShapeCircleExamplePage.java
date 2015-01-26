package org.wicketstuff.html5.shape.example;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.html5.shape.CircleShapeType;
import org.wicketstuff.html5.shape.Shape;
import org.wicketstuff.html5.shape.Shape.Orientation;

public class ShapeCircleExamplePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public ShapeCircleExamplePage()
	{
		add(new Shape("shapeleft").shapeType(new CircleShapeType("30%"))
			.transitionShapeType(new CircleShapeType("50%"))
			.useWidth("500px")
			.useHeight("500px"));
		add(new Shape("shaperight").shapeType(new CircleShapeType("30%"))
			.transitionShapeType(new CircleShapeType("50%"))
			.useWidth("500px")
			.useHeight("500px")
			.orientation(Orientation.right));
	}

}
