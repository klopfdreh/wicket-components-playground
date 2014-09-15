/**
 * Iterates over each droped file and sends it to the server.
 */
var dropid = guid();
$.each(evt.originalEvent.dataTransfer.files, function(index) {
	var file = this;
	var fileid = guid();
	
	file.fileid=fileid;
	file.dropid=dropid;
	
	// getStartUploadClientScript
	%s;
	
	$.ajax({
		type : 'POST',
		url : '%s&fileName=' + encodeURIComponent(file.name) + '&dropid='+dropid+'&fileid=' + fileid,
		data : file,
		contentType : false,
		processData : false,
		
		success:function(response){
			var file = this.data
			// getFinishedUploadClientScript
			%s;
		}
	});
});