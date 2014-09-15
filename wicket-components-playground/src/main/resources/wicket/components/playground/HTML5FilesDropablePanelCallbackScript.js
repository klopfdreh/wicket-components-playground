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
	
	var reader = new FileReader();
	reader.onload = (function(file) {
		return function(e) {
			Wicket.Ajax.post({'u':'%s&fileName=' + encodeURIComponent(file.name) + '&dropid='+file.dropid+'&fileid=' + file.fileid, ep:{data:btoa(reader.result)}, coh:[function(){
				// getFinishedUploadClientScript
				%s;
			}]});
		}
	})(file);
	reader.readAsBinaryString(file);
});