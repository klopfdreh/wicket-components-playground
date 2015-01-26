package org.wicketstuff.html5.shape;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

/**
 * Creates a shape by the given shape types. The initial with / height is set to 100%, margin is set
 * to 0
 * 
 * @author Tobias Soloschenko
 *
 */
public class Shape extends WebMarkupContainer
{

	private static final long serialVersionUID = 1L;

	private String width = "100%";

	private String height = "100%";

	private String margin = "0";

	private ShapeType shapeType = null;

	private ShapeType transitionShapeType = null;

	private Orientation orientation = Orientation.left;

	/**
	 * Creates a shape at the position of the given id
	 * 
	 * @param id
	 *            the it to position the shape
	 */
	public Shape(String id)
	{
		super(id);
		this.setOutputMarkupId(true);
	}

	/**
	 * Creates a shape at the position of the given id
	 * 
	 * @param id
	 *            the it to position the shape
	 * @param model
	 *            the model this shape contains
	 */
	public Shape(String id, IModel<?> model)
	{
		super(id, model);
		this.setOutputMarkupId(true);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("class", this.getMarkupId());
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		String shapeDefinition = "shape-outside: " + this.shapeType.getName() + "(" +
			this.shapeType.getValues() + ");";
		String css = String.format("." + this.getMarkupId() + "{ float: %s %s %s %s ",
			this.getOrientation().name() + ";", this.getDimension(), "-webkit-" + shapeDefinition,
			shapeDefinition);
		String transitionCss = "";
		if (this.transitionShapeType != null)
		{
			css += " transition: " +
				(this.transitionShapeType.getTransitionTime() != null
					? this.transitionShapeType.getTransitionTime() + ";" : "1s;");
			String shapeTransitionDefinition = "shape-outside: " +
				this.transitionShapeType.getName() + "(" + this.transitionShapeType.getValues() +
				");";
			transitionCss = String.format("." + this.getMarkupId() + ":hover{ %s %s }", "-webkit-" +
				shapeTransitionDefinition, shapeTransitionDefinition);
		}
		css += "}";
		response.render(CssHeaderItem.forCSS(css + " " + transitionCss, this.getMarkupId() + "css"));
	}

	/**
	 * Gets the dimension of the shape as string - height and width
	 * 
	 * @return the dimension settings as string
	 */
	protected String getDimension()
	{
		return "min-height: " + this.getHeight() + "; height:" + this.getHeight() + "; width: " +
			this.getWidth() + ";";
	}

	/**
	 * Gets the with of the shape
	 * 
	 * @return the with of the shape
	 */
	public String getWidth()
	{
		return this.width;
	}

	/**
	 * Uses the given width for the shape
	 * 
	 * @param width
	 *            the width to use
	 * @return the shape
	 */
	public Shape useWidth(String width)
	{
		this.width = width;
		return this;
	}

	/**
	 * Gets the height of the shape
	 * 
	 * @return the height of the shape
	 */
	public String getHeight()
	{
		return this.height;
	}

	/**
	 * Uses the given height for the shape
	 * 
	 * @param height
	 *            the height to use
	 * @return the shape
	 */
	public Shape useHeight(String height)
	{
		this.height = height;
		return this;
	}

	/**
	 * Gets the maring of the shape
	 * 
	 * @return the margin of the shape
	 */
	public String getMargin()
	{
		return margin;
	}

	/**
	 * Uses the given margin for the shape
	 * 
	 * @param margin
	 *            the margin to use
	 * @return the shape
	 */
	public Shape withMargin(String margin)
	{
		this.margin = margin;
		return this;
	}

	/**
	 * Gets the shape type the shape is using
	 * 
	 * @return the shape type
	 */
	public ShapeType getShapeType()
	{
		return this.shapeType;
	}

	/**
	 * Uses the given shape type
	 * 
	 * @param shapeType
	 *            the shape type
	 * @return the shape
	 */
	public Shape shapeType(ShapeType shapeType)
	{
		this.shapeType = shapeType;
		return this;
	}

	/**
	 * Gets the transition shape type the shape is using
	 * 
	 * @return the transition shape type
	 */
	public ShapeType getTransitionShapeType()
	{
		return this.transitionShapeType;
	}

	/**
	 * Uses the given transition shape type
	 * 
	 * @param transitionShapeType
	 *            the transition shape type
	 * @return the shape
	 */
	public Shape transitionShapeType(ShapeType transitionShapeType)
	{
		this.transitionShapeType = transitionShapeType;
		return this;
	}

	/**
	 * The orientation the shape is positioned
	 * 
	 * @return the orientation
	 */
	public Orientation getOrientation()
	{
		return this.orientation;
	}

	/**
	 * Uses the given orientation
	 * 
	 * @param orientation
	 *            the orientation to use
	 * @return the shape
	 */
	public Shape orientation(Orientation orientation)
	{
		this.orientation = orientation;
		return this;
	}

	public enum Orientation
	{
		left, right;
	}
}
