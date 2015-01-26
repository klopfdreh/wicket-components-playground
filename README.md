wicket-components-playground
============================

Playground for new Wicket components

HTML5FilesDropablePanel
------------------
This panel is able to handle HTML5 dropable events and receives the files which are dropped to the panel's markup. The style of the area is done via CSS in the markup. With the AjaxRequestTarget it's able to give a response to the client, for example to show that the files has been uploaded.

Example Implementation
Java:
```java
add(new HTML5FilesDropablePanel("dropable") {

	    private static final long serialVersionUID = 5131615920147559576L;

	    @Override
	    public void handleResponse(AjaxRequestTarget target,
		    String fileName, InputStream inputStream,
                    String dropid, String id) {
		    // Handle the file
	    }
	    
 	    // The following methods are not required to be overridden - 
 	    // see the java doc which javascript variables are available
 
 	    @Override
	    protected String getStartUploadClientScript() {
			return "alert('start'+file.dropid); alert('start'+file.fileid);"; 
	    };
 
	    @Override
	    protected String getFinishedUploadClientScript() {
 			return "alert('finished'+file.dropid); alert('finished'+file.fileid);";
	    }
	    
	    @Override
    	protected void handleFailure(AjaxRequestTarget target, String fileName,
	    	String dropid, String fileid) {
	    	// Handle error
	    }
});
```

HTML:
<pre>
&lt;div wicket:id="dropable" style="width:100px;height:100px;border:1px solid black;"&gt;&lt;/div&gt;
</pre>

If the user uploads several files a request is made for every file to the handleReponse-Method.

<b>!!! The fileServletInputStream is closed after the handleReponse-Method is complete - so you don't have to be worried about that. !!!</b>

TODO: Prevent multiple drops at the same time to one HTML5DropablePanel

Refactoring of Wicket's image for HTML5 responsive image
------------------

The responsive picture is used to provide images based on the resolution of the current device / the viewport which is available. 

Example Implementation for picture tag:

Java:
```java
	Picture picture = new Picture("picture");
	
	Source large = new Source("sourcelarge", new PackageResourceReference(this.getClass(), "large.jpg"));
	large.setMedia("(min-width: 650px)");
	picture.addSource(large);
	large.setOutputMarkupId(true);
	
	Source medium = new Source("sourcemedium", new PackageResourceReference(this.getClass(), "medium.jpg"));
	medium.setMedia("(min-width: 465px)");
	picture.addSource(medium);
	
	Image image3 = new Image("image3", new PackageResourceReference(this.getClass(), "small.jpg"));
	picture.addImage(image3);
	
	this.add(picture);
```

HTML:
<pre>
  &lt;picture wicket:id="picture"&gt;
     &lt;source wicket:id="sourcelarge" /&gt;
     &lt;source wicket:id="sourcemedium" /&gt;
     &lt;img wicket:id="image3" /&gt;
  &lt;/picture>
</pre>


Example Implementation of img tag with srcset and xvalues:

Java:
```java
	Image image2 = new Image("image2", 
	new PackageResourceReference(this.getClass(), "small.jpg"), 
	new PackageResourceReference(this.getClass(),"small.jpg"), 
	new PackageResourceReference(this.getClass(), "medium.jpg"), 
	new PackageResourceReference(this.getClass(), "large.jpg"));
	image2.setXValues("320w", "600w", "900w");
	this.add(image2);
```

HTML:
<pre>
	&lt;img wicket:id="image2"/&gt;
</pre>

Example implementation of img tag with overridden getImageResourceReference method:

Java:
```java
	Image image1 = new Image("image1", Model.of("Test")) {
		private static final long serialVersionUID = 1L;
	
		@Override
		protected ResourceReference getImageResourceReference() {
			return new PackageResourceReference(this.getClass(), "small.jpg");
		}
	};
	this.add(image1);
```

HTML:
<pre>
	&lt;img wicket:id="image1"/&gt;
</pre>

The following methods are new to the Wicket's image: setXValues(String... xvalues), setSizes(String... sizes) and the Source's media attribute can be set with setMedia(String media).

The x values are applied to the srcset element the way the values are given to the setXValues method.

<b>Important: In FireFox 33 you have to enable it via flags - about:config &gt; dom.image.picture.enabled;true and dom.image.srcset.enabled;true</b>

Further information: 
* http://responsiveimages.org/
* http://www.w3.org/html/wg/drafts/html/master/embedded-content.html#the-picture-element

Implementation in the Wicket core project requested:
* https://issues.apache.org/jira/browse/WICKET-5801

HTML5 Import with Wicket
------------------

With a little effort it is now possible to import html files with the link tag.

Example:

Java:
```java
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(CssHeaderItem.forImportUrl(
		getRequestCycle().urlFor(TestPage.class,null).toString()));
	}
```
Updated version with MetaDataHeaderItem:

Java:
```java
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(MetaDataHeaderItem.forImportLinkTag(TestPage.class));
	}
```

Further information: 
* http://www.html5rocks.com/en/tutorials/webcomponents/imports/
* http://w3c.github.io/webcomponents/spec/imports/

~~Implementation in the Wicket core project requested:~~
* ~~https://issues.apache.org/jira/browse/WICKET-5802~~

Integrated:
* https://github.com/apache/wicket/commit/bf662852eb35f623e4c90b2b2d11334a956b1159 (Wicket 7.0.0-M5)
* https://github.com/apache/wicket/commit/602f363969e57b13a0e611d48fcf2652209403e3 (Wicket 6.19.0)
* CSSHeaderItem will be untouched
* To use the HTML5 Import the methods are implemented in the HtmlImportHeaderItem instead of MetaDataHeaderItem

ObserveBehavior
------------------

A little behavior to track events:

Java:
```java
	Label label = new Label("label", "MyLabel");
	label.add(new ObserveBehavior() {
		private static final long serialVersionUID = 1L;

		@Override
		public void processResponse(AjaxRequestTarget target, String data) throws JSONException {
			System.err.println(this.getNewEventValueAsBoolean(data, "ctrlKey"));
			System.err.println(this.getNewEventValueAsInt(data, "clientX"));
			System.err.println(this.getText(data));
			System.err.println(this.getHTML(data));
			System.err.println(target);
		}

	}.forEvent(Event.mouseover));
	this.add(label);
```

HTML5 Video / Audio Tags
------------------

Implementation of the HTML5 audio and video tags. Here is a little example, the MediaStreamingResourceReference supports Content-Range header handling and range requests.

Java:
```java

	// In Applications init()
	MediaUtils.init();
	
	// First example
	Video video = new Video("video");
	Source source = new Source("source", new MediaStreamingResourceReference(this.getClass(), "big.mp4"));
	video.add(source);
	this.add(video);
	
	// Other Example
	Video video = new Video("video");
	Source source = new Source("source", new MediaStreamingResourceReference(this.getClass(), "VfE_html5.mp4"));
	video.add(source);
	Track track = new Track("track",new PackageResourceReference(this.getClass(), "subtitles.vtt"));
	track.setKind(Kind.subtitles);
	track.setSrclang(Locale.GERMAN);
	track.setDefaultTrack(true);
	video.add(track);
	this.add(video);
```

HTML:
<pre>
	&lt;video wicket:id="video"&gt;
		&lt;source wicket:id="source" /&gt;
	&lt;/video&gt;
	
	Other Example
	&lt;video wicket:id="video"&gt;
		&lt;source wicket:id="source" /&gt;
		&lt;track wicket:id="track" /&gt;
	&lt;/video&gt;
</pre>

Further information: 

* http://www.w3schools.com/html/html5_video.asp
* http://www.w3schools.com/html/html5_audio.asp

* http://dev.w3.org/html5/spec-author-view/video.html

Implementation in the Wicket core project requested:

* https://issues.apache.org/jira/browse/WICKET-5819

HTML5 Shapes
------------------

The shapes are currently not working within in FF and IE, but all other browser supports them.

Java:
```java

	add(new Shape("shapeleft").shapeType(new CircleShapeType("30%"))
		.transitionShapeType(new CircleShapeType("50%"))
		.useWidth("500px")
		.useHeight("500px"));
	add(new Shape("shaperight").shapeType(new CircleShapeType("30%"))
		.transitionShapeType(new CircleShapeType("50%"))
		.useWidth("500px")
		.useHeight("500px")
		.orientation(Orientation.right));
```

HTML:
<pre>
	&lt;div&gt;
		&lt;div wicket:id="shapeleft"&gt;&lt;/div&gt;
		&lt;div wicket:id="shaperight"&gt;&lt;/div&gt;
		... some text...
	&lt;/div&gt;
</pre>

Further information:
* http://www.html5rocks.com/en/tutorials/shapes/getting-started/
* http://dev.w3.org/csswg/css-shapes/