package org.wicketstuff.html5.shape;

public interface ShapeType {

	String getName();

	String getValues();

	String getTransitionTime();

	ShapeType transitionTime(String transitionTime);
}
