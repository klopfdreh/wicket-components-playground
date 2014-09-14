/**
 * Iterates over each droped file and sends it to the server.
 */
var transactionid = guid();
$.each(evt.originalEvent.dataTransfer.files, function(index) {
	var file = this;
	$.ajax({
		type : 'POST',
		url : '%s&fileName=' + encodeURIComponent(file.name) + '&dropid='+transactionid+'&id=' + guid(),
		data : file,
		contentType : false,
		processData : false
	});
});