package org.apache.wicket.markup.html.image;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.Picture;
import org.apache.wicket.markup.html.image.Source;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public class ImageTestPage extends WebPage {

    private static final long serialVersionUID = 1L;

    public ImageTestPage() {
	Image image1 = new Image("image1", Model.of("Test")) {
	    private static final long serialVersionUID = 1L;

	    @Override
	    protected ResourceReference getImageResourceReference() {
		return new PackageResourceReference(this.getClass(), "small.jpg");
	    }
	};
	this.add(image1);
    }
}
