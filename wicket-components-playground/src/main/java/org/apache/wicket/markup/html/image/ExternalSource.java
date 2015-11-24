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

import java.util.Arrays;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.Image.Cors;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

/**
 * A component which displays external images within a picture tag.
 * 
 * @see org.apache.wicket.markup.html.image.Source
 * 
 * @author Tobias Soloschenko
 *
 */
public class ExternalSource extends ExternalImage
{

	private static final long serialVersionUID = 1L;

	private String media = null;

	/**
	 * Creates an external source
	 * 
	 * @param id
	 *            the component id
	 * @param src
	 *            the source URL
	 * @param srcSet
	 *            a list of URLs placed in the srcset attribute
	 */
	public ExternalSource(String id, String... srcSet)
	{
		super(id, Args.notNull(srcSet, "srcSet")[0], Arrays.copyOfRange(srcSet, 1, srcSet.length));
	}

	/**
	 * Creates an external source
	 * 
	 * @param id
	 *            the component id
	 * @param src
	 *            the model source URL
	 * @param srcSet
	 *            a model list of URLs placed in the srcset attribute
	 */
	public ExternalSource(String id, IModel<?>... srcSet)
	{
		super(id, Args.notNull(srcSet, "srcSet")[0], Arrays.copyOfRange(srcSet, 1, srcSet.length));
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
	}

	/**
	 * Sets the media attribute information
	 *
	 * @param media
	 *            the media attribute information
	 */
	public void setMedia(String media)
	{
		this.media = media;
	}

	/**
	 * Gets the media attribute information
	 *
	 * @return the media attribute information
	 */
	public String getMedia()
	{
		return media;
	}

	/**
	 * Unsupported for source tag
	 */
	@Override
	public void setCrossOrigin(Cors crossorigin)
	{
		throw new UnsupportedOperationException(
			"It is not allowed to set the crossorigin attribute for source tag");
	}

	/**
	 * Unsupported for source tag
	 */
	@Override
	public final Cors getCrossOrigin()
	{
		return null;
	}
}
