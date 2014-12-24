/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.markup.html.image;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.image.resource.LocalizedImageResource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * An Image component displays localizable image resources.
 * <p>
 * For details of how Images load, generate and manage images, see
 * {@link LocalizedImageResource}.
 * 
 * The first ResourceReference / ImageResource is used for the src attribute
 * within the img tag, all following are applied to the srcset. If
 * setXValues(String... values) is used the values are set behind the srcset
 * elements in the order they are given to the setXValues(String... valus)
 * method. The separated values in the sizes attribute are set with
 * setSizes(String... sizes)
 * 
 * @see NonCachingImage
 * 
 * @author Jonathan Locke
 * @author Tobias Soloschenko
 * 
 */
public class Image extends WebComponent implements IResourceListener {
	private static final long serialVersionUID = 1L;

	/** The image resources this image component references */
	private final List<LocalizedImageResource> localizedImageResources = new ArrayList<LocalizedImageResource>() {
		private static final long serialVersionUID = 1L;
		{
			this.add(new LocalizedImageResource(Image.this));
		}
	};

	/** The x values to be used within the srcset */
	private final List<String> xValues = new ArrayList<String>();

	/** The sizes of the responsive images */
	private final List<String> sizes = new ArrayList<String>();

	/**
	 * This constructor can be used if you override {@link #getImageResourceReference()} or {@link #getImageResource()}
	 * 
	 * @param id
	 */
	protected Image(final String id) {
		super(id);
	}

	/**
	 * Constructs an image from an image resourcereference. That resource reference will bind its resource to the current SharedResources.
	 * 
	 * If you are using non sticky session clustering and the resource reference is pointing to a Resource that isn't guaranteed to be on every
	 * server, for example a dynamic image or resources that aren't added with a IInitializer at application startup. Then if only that resource is
	 * requested from another server, without the rendering of the page, the image won't be there and will result in a broken link.
	 * 
	 * @param id
	 *            See Component
	 * @param resourceReferences
	 *            The shared image resource
	 */
	public Image(final String id, final ResourceReference... resourceReferences) {
		this(id, null, resourceReferences);
	}

	/**
	 * Constructs an image from an image resourcereference. That resource reference will bind its resource to the current SharedResources.
	 * 
	 * If you are using non sticky session clustering and the resource reference is pointing to a Resource that isn't guaranteed to be on every
	 * server, for example a dynamic image or resources that aren't added with a IInitializer at application startup. Then if only that resource is
	 * requested from another server, without the rendering of the page, the image won't be there and will result in a broken link.
	 * 
	 * @param id
	 *            See Component
	 * @param resourceReference
	 *            The shared image resource
	 * @param resourceParameters
	 *            The resource parameters
	 */
	public Image(final String id, PageParameters resourceParameters, final ResourceReference... resourceReferences) {
		super(id);
		this.setImageResourceReferences(resourceParameters, resourceReferences);
	}

	/**
	 * Constructs an image directly from an image resource.
	 * 
	 * This one doesn't have the 'non sticky session clustering' problem that the ResourceReference constructor has. But this will result in a non
	 * 'stable' url and the url will have request parameters.
	 * 
	 * @param id
	 *            See Component
	 * 
	 * @param imageResource
	 *            The image resource
	 */
	public Image(final String id, final IResource... imageResources) {
		super(id);
		this.setImageResources(imageResources);
	}

	/**
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public Image(final String id, final IModel<?> model) {
		super(id, model);
	}

	/**
	 * @param id
	 *            See Component
	 * @param string
	 *            Name of image
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public Image(final String id, final String string) {
		this(id, new Model<String>(string));
	}

	/**
	 * @see org.apache.wicket.IResourceListener#onResourceRequested()
	 */
	@Override
	public void onResourceRequested() {
		for (LocalizedImageResource localizedImageResource : this.localizedImageResources) {
			localizedImageResource.onResourceRequested(null);
		}
	}

	/**
	 * @param imageResource
	 *            The new ImageResource to set.
	 */
	public void setImageResource(final IResource imageResource) {
		this.localizedImageResources.clear();
		this.setLocalizedImageResource(imageResource);
	}

	/**
	 * 
	 * @param imageResources
	 *            the new ImageResource to set.
	 */
	public void setImageResources(final IResource... imageResources) {
		this.localizedImageResources.clear();
		for (IResource imageResource : imageResources) {
			this.setLocalizedImageResource(imageResource);
		}
	}

	/**
	 * @param imageResource
	 *            the image resource to set
	 */
	private void setLocalizedImageResource(final IResource imageResource) {
		LocalizedImageResource localizedImageResource = new LocalizedImageResource(this);
		localizedImageResource.setResource(imageResource);
		this.localizedImageResources.add(localizedImageResource);
	}

	/**
	 * @param resourceReference
	 *            The shared ImageResource to set.
	 */
	public void setImageResourceReference(final ResourceReference resourceReference) {
		this.localizedImageResources.clear();
		this.setLocalizedImageResourceReference(resourceReference);
	}

	/**
	 * @param resourceReference
	 *            The shared ImageResource to set.
	 * @param parameters
	 *            Set the resource parameters for the resource.
	 */
	public void setImageResourceReferences(final PageParameters parameters, final ResourceReference... resourceReferences) {
		this.localizedImageResources.clear();
		for (ResourceReference resourceReference : resourceReferences) {
			this.setLocalizedImageResourceReference(resourceReference);
		}
	}

	/**
	 * @param values
	 *            the x values to be used in the srcset
	 */
	public void setXValues(String... values) {
		this.xValues.clear();
		this.xValues.addAll(Arrays.asList(values));
	}

	/**
	 * @param sizes
	 *            the sizes to be used in the size
	 */
	public void setSizes(String... sizes) {
		this.sizes.clear();
		this.sizes.addAll(Arrays.asList(sizes));
	}

	/**
	 * @param resourceReference
	 *            the resource reference to set
	 */
	private void setLocalizedImageResourceReference(final ResourceReference resourceReference) {
		LocalizedImageResource localizedImageResource = new LocalizedImageResource(this);
		localizedImageResource.setResourceReference(resourceReference);
		this.localizedImageResources.add(localizedImageResource);
	}

	/**
	 * @see org.apache.wicket.Component#setDefaultModel(org.apache.wicket.model.IModel)
	 */
	@Override
	public Component setDefaultModel(IModel<?> model) {
		// Null out the image resource, so we reload it (otherwise we'll be
		// stuck with the old model.
		for (LocalizedImageResource localizedImageResource : this.localizedImageResources) {
			localizedImageResource.setResourceReference(null);
			localizedImageResource.setResource(null);
		}
		return super.setDefaultModel(model);
	}

	/**
	 * @return Resource returned from subclass
	 */
	protected IResource getImageResource() {
		if (this.localizedImageResources.size() == 0) {
			return null;
		}
		LocalizedImageResource localizedImageResource = this.localizedImageResources.get(0);
		if (localizedImageResource == null) {
			return null;
		} else {
			return localizedImageResource.getResource();
		}
	}

	/**
	 * @return ResourceReference returned from subclass
	 */
	protected ResourceReference getImageResourceReference() {
		if (this.localizedImageResources.size() == 0) {
			return null;
		}
		LocalizedImageResource localizedImageResource = this.localizedImageResources.get(0);
		if (localizedImageResource == null) {
			return null;
		} else {
			return localizedImageResource.getResourceReference();
		}
	}

	/**
	 * @see org.apache.wicket.Component#initModel()
	 */
	@Override
	protected IModel<?> initModel() {
		// Images don't support Compound models. They either have a simple
		// model, explicitly set, or they use their tag's src or value
		// attribute to determine the image.
		return null;
	}

	/**
	 * @see org.apache.wicket.Component#onComponentTag(ComponentTag)
	 */
	@Override
	protected void onComponentTag(final ComponentTag tag) {
		super.onComponentTag(tag);
		if(tag.getName().equals("source")){	
		    this.buildSrcAndSrcSetTag(tag, false);
		}else{
		    this.checkComponentTag(tag, "img");
		    if(localizedImageResources.size()==1){			
			this.buildSingleSrcTag(tag);
			
		    }else{
			this.buildSrcAndSrcSetTag(tag, true);
		    }
		}
		tag.remove("single");
	}

	/**
	 * Builds the src and srcset tag if multiple localizedImageResources are found
	 * 
	 * @param tag
	 *            the component tag
	 */
	protected void buildSrcAndSrcSetTag(final ComponentTag tag, boolean renderSrcAttribute) {
		int srcSetPosition = 0;
		String firstSrcValue = "";
		for (LocalizedImageResource localizedImageResource : this.localizedImageResources) {
			localizedImageResource.setSrcAttribute(tag);

			if (this.shouldAddAntiCacheParameter()) {
				this.addAntiCacheParameter(tag);
			}

			if (renderSrcAttribute) {
				if (srcSetPosition > 0) {
					String srcset = tag.getAttribute("srcset");
					String xValue = this.xValues.size() > srcSetPosition - 1 && this.xValues.get(srcSetPosition - 1) != null ? " "
							+ this.xValues.get(srcSetPosition - 1) : "";
					tag.put("srcset", (srcset != null ? srcset + " , " : "") + tag.getAttribute("src") + xValue);
					tag.put("src", firstSrcValue);
				} else {
					firstSrcValue = tag.getAttribute("src");
				}
			} else {
				String srcset = tag.getAttribute("srcset");
				String xValue = this.xValues.size() > srcSetPosition && this.xValues.get(srcSetPosition) != null ? " " + this.xValues.get(srcSetPosition) : "";
				tag.put("srcset", (srcset != null ? srcset + " , " : "") + tag.getAttribute("src") + xValue);
				tag.remove("src");
			}
			srcSetPosition++;
		}

		this.buildSizesAttribute(tag);
	}

	/**
	 * Builds a single src tag if only one localizedImageResource is found
	 * 
	 * @param tag
	 *            the component tag
	 */
	private void buildSingleSrcTag(final ComponentTag tag) {
		final IResource resource = this.getImageResource();
		if (resource != null) {
			this.localizedImageResources.get(0).setResource(resource);
		}
		final ResourceReference resourceReference = this.getImageResourceReference();
		if (resourceReference != null) {
			this.localizedImageResources.get(0).setResourceReference(resourceReference);
		}
		this.localizedImageResources.get(0).setSrcAttribute(tag);

		if (this.shouldAddAntiCacheParameter()) {
			this.addAntiCacheParameter(tag);
		}
	}

	/**
	 * builds the sizes attribute of the img tag
	 * 
	 * @param tag
	 *            the component tag
	 */
	private void buildSizesAttribute(final ComponentTag tag) {
		String sizes = "";
		for (String size : this.sizes) {
			sizes += size + ",";
		}
		int lastIndexOf = sizes.lastIndexOf(",");
		if (lastIndexOf != -1) {
			sizes = sizes.substring(0, lastIndexOf);
		}
		if (!"".equals(sizes)) {
			tag.put("sizes", sizes);
		}
	}

	/**
	 * Adding an image to {@link org.apache.wicket.ajax.AjaxRequestTarget} most of the times mean that the image has changes and must be re-rendered.
	 * <p>
	 * With this method the user may change this default behavior for some of her images.
	 * </p>
	 * 
	 * @return {@code true} to add the anti cache request parameter, {@code false} - otherwise
	 */
	protected boolean shouldAddAntiCacheParameter() {
		return this.getRequestCycle().find(AjaxRequestTarget.class) != null;
	}

	/**
	 * Adds random noise to the url every request to prevent the browser from caching the image.
	 * 
	 * @param tag
	 */
	protected final void addAntiCacheParameter(final ComponentTag tag) {
		String url = tag.getAttributes().getString("src");
		url = url + (url.contains("?") ? "&" : "?");
		url = url + "antiCache=" + System.currentTimeMillis();

		tag.put("src", url);
	}

	/**
	 * @see org.apache.wicket.Component#getStatelessHint()
	 */
	@Override
	protected boolean getStatelessHint() {
		if (this.localizedImageResources.size() == 1) {
			return (this.getImageResource() == null || this.getImageResource() == this.localizedImageResources.get(0).getResource())
					&& this.localizedImageResources.get(0).isStateless();
		} else {
			boolean stateless = false;
			for (LocalizedImageResource localizedImageResource : this.localizedImageResources) {
				if ((this.getImageResource() == null || this.getImageResource() == localizedImageResource.getResource())
						&& localizedImageResource.isStateless()) {
					stateless = true;
				}
			}
			return stateless;
		}
	}

	/**
	 * @see org.apache.wicket.Component#onComponentTagBody(MarkupStream, ComponentTag)
	 */
	@Override
	public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
	}

	@Override
	public boolean canCallListenerInterface(Method method) {
		boolean isResource = method != null && IResourceListener.class.isAssignableFrom(method.getDeclaringClass());
		if (isResource && this.isVisibleInHierarchy()) {
			// when the image data is requested we do not care if this component is enabled in
			// hierarchy or not, only that it is visible
			return true;
		} else {
			return super.canCallListenerInterface(method);
		}
	}
}
