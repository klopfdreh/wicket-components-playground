package wicket.components.playground;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

/**
 * The responsive picture is used to provide images based on the resolution of
 * the current device / the viewport which is available. The last parameter of
 * the Source is the min width used in the media attribute of the source tag. It
 * is used this way:<br>
 * <br>
 * HTML:
 * 
 * <pre>
 * <code>
 * &lt;picture wicket:id="myResponsiveImage"&gt;
 * 	&lt;source wicket:id="sources" /&gt;
 * 	&lt;img wicket:id="fallback" /&gt;
 * &lt;/picture>
 * </code>
 * </pre>
 * 
 * Java:
 * 
 * <pre>
 * <code>
 * HTML5ResponivePicture html5ResponiveImagePicture = new HTML5ResponivePicture("myResponsiveImage");
 * html5ResponiveImagePicture.addFallbackImage(new Image("fallback", new PackageResourceReference(TestPage.class, "fallback.jpg")));
 * html5ResponiveImagePicture.addSource(new Source("large.jpg", new PackageResourceReference(TestPage.class, "large.jpg"),"56.25em"));
 * html5ResponiveImagePicture.addSource(new Source("medium.jpg", new PackageResourceReference(TestPage.class, "medium.jpg"),"37.5em"));
 * html5ResponiveImagePicture.addSource(new Source("small.jpg", new PackageResourceReference(TestPage.class, "small.jpg"),null));
 * add(html5ResponiveImagePicture);
 * </code>
 * </pre>
 * <b>Important: In FireFox 33 you have to enable it via flags - about:config &gt; dom.image.picture.enabled;true and dom.image.srcset.enabled;true</b>
 * <br><br>
 * 
 * @author tobiassoloschenko
 */
public class HTML5ResponivePicture extends WebMarkupContainer {

    private static final long serialVersionUID = 1L;

    private RepeatingView repeatingView;

    public HTML5ResponivePicture(String id) {
	super(id);
	add(repeatingView = new RepeatingView("sources"));
    }

    public HTML5ResponivePicture(String id, IModel<?> model) {
	super(id, model);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
	super.onComponentTag(tag);
	checkComponentTag(tag, "picture");
    }

    public void addFallbackImage(Image fallbackImage) {
	add(fallbackImage);
    }

    public void addSource(Source source) {
	repeatingView.add(source);
    }
}
