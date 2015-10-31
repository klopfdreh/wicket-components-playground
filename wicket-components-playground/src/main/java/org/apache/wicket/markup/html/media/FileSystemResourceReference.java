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
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * This resource reference is used to provide a reference to a resource based on Java NIO FileSystem
 * API.<br>
 * <br>
 * To implement a mime type detection refer to the documentation of
 * {@link java.nio.file.Files#probeContentType(Path)} and provide an implementation for
 * java.nio.file.spi.FileTypeDetector in the META-INF/services folder for jars or in the
 * /WEB-INF/classes/META-INF/services folder for webapps<br>
 * <br>
 * You can optionally override {@link #getMimeType()} to provide an inline mime type detection,
 * which is preferred to the default detection.
 * 
 * @author Tobias Soloschenko
 */
public class FileSystemResourceReference extends ResourceReference
{
	private static final long serialVersionUID = 1L;

	private Path path;

	private static Map<URI, FileSystem> fileSystemURIs = new HashMap<URI, FileSystem>();

	/**
	 * Creates a file system resource reference based on the given path
	 * 
	 * @param name
	 *            the name of the resource reference to expose data
	 * @param path
	 *            the path to create the resource reference
	 */
	public FileSystemResourceReference(String name, Path path)
	{
		super(name);
		this.path = path;
	}

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

	/**
	 * Creates a new {@link org.apache.wicket.markup.html.media.FileSystemResource} and applies the
	 * path to it.
	 */
	@Override
	public IResource getResource()
	{
		return getFileSystemResource();
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

	/**
	 * Gets the file system resource to be used for the resource reference
	 * 
	 * @return the file system resource to be used for the resource reference
	 */
	protected FileSystemResource getFileSystemResource()
	{
		FileSystemResource fileSystemResource = new FileSystemResource(path)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected String getMimeType() throws IOException
			{
				String mimeType = FileSystemResourceReference.this.getMimeType();
				return mimeType != null ? mimeType : super.getMimeType();
			}
		};
		return fileSystemResource;
	}

	/**
	 * Creates a path and a file system (if required) based on the given URI
	 * 
	 * @param uri
	 *            the uri to create the file system and the path of
	 * @return the path of the file in the file system
	 * @throws IOException
	 *             if the file system could'nt be created
	 * @throws URISyntaxException
	 *             if the URI has no valid syntax
	 */
	public static Path getPath(URI uri) throws IOException, URISyntaxException
	{
		String uriString = uri.toString();
		int indexOfExclamationMark = uriString.indexOf('!');
		if (indexOfExclamationMark == -1)
		{
			return Paths.get(uri);
		}
		String zipFile = uriString.substring(0, indexOfExclamationMark);
		FileSystem fileSystem = fileSystemURIs.get(uri);
		if (fileSystem == null)
		{
			Map<String, String> env = new HashMap<>();
			env.put("create", "true");
			fileSystem = FileSystems.newFileSystem(new URI(zipFile), env);
			fileSystemURIs.put(uri, fileSystem);
		}
		String fileName = uriString.substring(uriString.indexOf('!') + 1);
		return fileSystem.getPath(fileName);
	}
}
