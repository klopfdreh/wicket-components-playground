package wicket.components.playground;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

public class Source extends Image {

    
    private static final long serialVersionUID = 1L;
    
    private String minWidth;

    protected Source(final String id,String minWidth) {
	super(id);
	this.minWidth=minWidth;
    }

    public Source(final String id, final ResourceReference resourceReference,String minWidth) {
	this(id, resourceReference, null,minWidth);
    }

    public Source(final String id, final ResourceReference resourceReference,
	    PageParameters resourceParameters,String minWidth) {
	super(id);
	setImageResourceReference(resourceReference, resourceParameters);
	this.minWidth=minWidth;
    }

    public Source(final String id, final IResource imageResource,String minWidth) {
	super(id);
	setImageResource(imageResource);
	this.minWidth=minWidth;
    }

    public Source(final String id, final IModel<?> model,String minWidth) {
	super(id, model);
	this.minWidth=minWidth;
    }

    public Source(final String id, final String string,String minWidth) {
	this(id, new Model<String>(string),minWidth);
    }
    
    @Override
    protected void onComponentTag(ComponentTag tag) {
	tag.setName("img");
        super.onComponentTag(tag);
        if(minWidth != null){            
            tag.put("media","(min-width: "+minWidth+")");
        }
        tag.put("srcset", tag.getAttribute("src"));
        tag.remove("src");
        tag.setName("source");
    }
}
