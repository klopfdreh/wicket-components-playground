package org.apache.wicket.markup.html.image;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;

public class ImageSrcSetTestPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public ImageSrcSetTestPage()
	{
		Image image2 = new Image("image2", new PackageResourceReference(this.getClass(),
			"small.jpg"), new PackageResourceReference(this.getClass(), "small.jpg"),
			new PackageResourceReference(this.getClass(), "medium.jpg"),
			new PackageResourceReference(this.getClass(), "large.jpg"));
		image2.setXValues("320w", "2x", "900w");
		image2.setSizes("(min-width: 50em) 33vw", "(min-width: 28em) 50vw", "100vw");
		this.add(image2);
	}
}
