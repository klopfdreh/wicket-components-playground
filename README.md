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
		    String fileName, ServletInputStream fileServletInputStream,
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
});
```

HTML:
<pre>
&lt;div wicket:id="dropable" style="width:100px;height:100px;border:1px solid black;"&gt;&lt;/div&gt;
</pre>

If the user uploads several files a request is made for every file to the handleReponse-Method.

<b>!!! The fileServletInputStream is closed after the handleReponse-Method is complete - so you don't have to be worried about that. !!!</b>

TODO: Prevent multiple drops at the same time to one HTML5DropablePanel
