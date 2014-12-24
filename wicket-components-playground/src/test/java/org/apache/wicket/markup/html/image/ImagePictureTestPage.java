package org.apache.wicket.markup.html.image;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;

public class ImagePictureTestPage extends WebPage {

    private static final long serialVersionUID = 1L;

    public ImagePictureTestPage() {
	Picture picture = new Picture("picture");

	Source large = new Source("sourcelarge", new PackageResourceReference(this.getClass(), "large.jpg"));
	large.setMedia("(min-width: 650px)");
	large.setSizes("(min-width: 50em) 33vw");
	picture.addSource(large);
	large.setOutputMarkupId(true);

	Source medium = new Source("sourcemedium",new PackageResourceReference(this.getClass(), "medium.jpg"));
	medium.setMedia("(min-width: 465px)");
	picture.addSource(medium);

	Image image3 = new Image("image3", new PackageResourceReference(this.getClass(), "small.jpg"));
	picture.addImage(image3);

	this.add(picture);
    }
}
