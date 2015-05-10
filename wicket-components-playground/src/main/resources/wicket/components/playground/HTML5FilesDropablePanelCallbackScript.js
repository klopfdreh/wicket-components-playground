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