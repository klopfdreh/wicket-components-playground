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
package org.apache.wicket.markup.html.media;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.PartWriterCallback;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * This resource reference is used to provide content based on Java NIO FileSystem API.<br>
 * <br>
 * To implement a mime type detection refer to the documentation of
 * {@link java.nio.file.Files#probeContentType(Path)} and provide an implementation for
 * java.nio.file.spi.FileTypeDetector in the META-INF/services folder for jars or in the
 * /WEB-INF/classes/META-INF/services folder for webapps
 * 
 * @author Tobias Soloschenko
 */
public class FileSystemResourceReference extends ResourceReference
{
	private static final long serialVersionUID = 1L;

	private Path path;

	/**
	 * Creates a file system resource reference based on the given path
	 * 
	 * @param path
	 *            the path to create the resource reference (the name is used to expose the data)
	 */
	public FileSystemResourceReference(Path path)
	{
		super(path.getFileName().toString());
		this.path = path;
	}

	@Override
	public IResource getResource()
	{
		return new FileSystemResource(path);
	}

	private class FileSystemResource extends AbstractResource
	{
		private static final long serialVersionUID = 1L;

		private Path path;

		public FileSystemResource(Path path)
		{
			this.path = path;
		}

		@Override
		protected ResourceResponse newResourceResponse(Attributes attributes)
		{
			try
			{
				long size = Files.readAttributes(path, BasicFileAttributes.class).size();
				ResourceResponse resourceResponse = new ResourceResponse();
				resourceResponse.setContentType(getMimeType() != null ? getMimeType()
					: Files.probeContentType(path));
				resourceResponse.setAcceptRange(ContentRangeType.BYTES);
				resourceResponse.setContentLength(size);
				RequestCycle cycle = RequestCycle.get();
				Long startbyte = cycle.getMetaData(CONTENT_RANGE_STARTBYTE);
				Long endbyte = cycle.getMetaData(CONTENT_RANGE_ENDBYTE);
				resourceResponse.setWriteCallback(new PartWriterCallback(
					Files.newInputStream(path), size, startbyte, endbyte));
				return resourceResponse;
			}
			catch (IOException e)
			{
				throw new WicketRuntimeException(
					"An error occured while processing the media resource response", e);
			}
		}
	}

	/**
	 * Used to apply a custom mime type without implementing a mime type detection
	 * 
	 * @return the mime type
	 */
	protected String getMimeType()
	{
		return null;
	}
}
