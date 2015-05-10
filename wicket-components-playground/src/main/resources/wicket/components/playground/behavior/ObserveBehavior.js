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
var %sObserve = {};
$("#%s").bind("%s",function(event){
	function simpleKeys (original) {
	  return Object.keys(original).reduce(function (obj, key) {
	    obj[key] = typeof original[key] === 'object' ? '{ }' : original[key];
	    return obj;
	  }, {});
	}
	var selfRef = $(this);
	%sObserve.event = event;
	if(!%sObserve.registered){
		Object.observe(%sObserve,function(changes){
			changes.forEach(function(change){
				var jsonData = window.JSON.stringify({
					value: selfRef.text(),
					html: selfRef.html(),
					oldEvent: change.oldValue ? simpleKeys(change.oldValue):"", 
					newEvent: simpleKeys(%sObserve.event)
				});
				Wicket.Ajax.post({'u':'%s',ep:{data: jsonData}});
			});
		});
		%sObserve.registered=true;
	}
});

