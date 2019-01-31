populateSelectOptionsWithAllOptions = function(elem, entity) {
	var url = contextPath + '/services/' + entity + '.smvc4';	
	$.ajax({ 
	    type: 'GET', 
	    url: url, 
	    dataType: 'json', 
	    cache: false,
	    success:  function(obj) {	
	    	var options = '<option value="All">All</option>';
			for (var i = 0; i < obj.length; i++) { 	
				options += '<option value="' + obj[i].id + '">' + obj[i].name + '</option>';
			} 

			if(options == '') {
				options += '<option value="00">None</option>';
			}
			
			$(elem).html(options); 
	    }, 
	    data: {},
	    async: false
	});
};

function searchReason(url){
	var team = jQuery("#teams").val();
	var reason = jQuery("#reason").val();
	var assocId = jQuery("#assocId").val();
	var status = jQuery("#status :selected").text();
	
	if(team == undefined){
		team = "";
	}
	
	if(reason == undefined){
		reason = "";
	}
	
	if(assocId == undefined){
		assocId = "";
	}
	
	if(status == undefined){
		status = "";
	}
	
	var strURL = contextPath + url + "?team="    + team 
	                               + "&reason="  + reason
	                               + "&assocId=" + assocId
	                               + "&status="  + status;
	
	jQuery("#grid").setGridParam({url:strURL,page:1});
	jQuery("#grid").trigger("reloadGrid");
	jQuery("#jqGridDetails").trigger("reloadGrid");
};

populateReasonSummaryBarGraph = function(elem, entity) {
	var url = contextPath + '/services/' + entity + '.smvc4';	
	$.ajax({ 
	    type: 'POST', 
	    url: url, 
	    dataType: 'json', 
	    cache: false,
	    success:function(response) {
	    			Morris.Bar({
	    				element: elem,
	    				data:response,
	    				xLabelMargin: 10,
	    				xkey: 'team',
	    				ykeys: ['completed', 'identified','investigating', 'unknown', 'noExamples'],
	    				ymax:120,
	    				barColors: ["#1E88E5", "#4CAF50","#009688", "#e60000", "#1565C0"],
	    				labels: ['Completed', 'Identified','Investigating', 'Unknown','No Examples']
	    			})
	    			.on('click', function(i, row){
	    				  console.log(i, row);
	    			});
	    		},
	    
	    data: {},
	    async: false
	});
};

populateAutomationSummaryBarGraph = function(elem, entity) {
	var url = contextPath + '/reasonSummary/' + entity + '.smvc4';	
	$.ajax({ 
	    type: 'POST', 
	    url: url, 
	    dataType: 'json', 
	    cache: false,
	    success:function(response) {
	    			Morris.Bar({
	    				element: elem,
	    				data:response,
	    				xLabelMargin: 10,
	    				xkey: 'team',
	    				ykeys: ['closed', 'inProgress','inReview'],
	    				barColors: ["#1E88E5", "#4CAF50","#009688"],
	    				labels: ['Closed', 'In Progress','In Review']
	    			})
	    			.on('click', function(i, row){
	    				  console.log(i, row);
	    			});
	    		},
	    
	    data: {},
	    async: false
	});
};

populateRsnCountDounutGraph = function(elem, entity) {
	var url = contextPath + '/services/' + entity + '.smvc4';	
	$.ajax({ 
	    type: 'POST', 
	    url: url, 
	    dataType: 'json', 
	    cache: false,
	    success:function(response) {
	    			Morris.Donut({
	    				element: elem,
	    				data:response,
	    				labelColor: '#29B6F6',
	    				fontSize:'medium',
	    				colors: ['#0091EA', '#00B0FF', '#40C4FF', '#80D8FF'],
	    			});
	    		},	    
	    data: {},
	    async: false
	});
};

populateReasonSummaryData = function() {
	var url = contextPath + '/reasonSummary/getRsnSummary.smvc4';	
	$.ajax({ 
	    type: 'POST',
	    async: false,
	    url: url, 
	    dataType: 'json', 
	    cache: false,
	    success: function(response){
	    			jQuery('#totalReasons').text(response[0].totalReasons);
	    			jQuery('#totalReasonsCompleted').text(response[0].totalReasonsCompleted);
	    			jQuery('#totalReasonsIdentified').text(response[0].totalReasonsIdentified);
	    			jQuery('#totalReasonsInvestigating').text(response[0].totalReasonsInvestigating);
	    			jQuery('#totalReasonsUnknown').text(response[0].totalReasonsUnknown);
	    			jQuery('#totalReasonNoExamples').text(response[0].totalReasonNoExamples);
	    			jQuery('#totalCmpltedRsns').text(response[0].totalCmpltedRsns);
	    			jQuery('#totalCmpltedCernerActnable').text(response[0].totalCmpltedCernerActnable);
	    			jQuery('#totalCmpltedClientActnable').text(response[0].totalCmpltedClientActnable);
	    			jQuery('#totalCmpltedUnknown').text(response[0].totalCmpltedUnknown);
	    			jQuery('#totalDACernerActnable').text(response[0].totalDACernerActnable);
	    			jQuery('#totalDAAvailable').text(response[0].totalDAAvailable);
	    			jQuery('#totalDAPendingEM').text(response[0].totalDAPendingEM);
	    			jQuery('#totalDAPendingRCM').text(response[0].totalDAPendingRCM);
	    			jQuery('#totalDAUnknown').text(response[0].totalDAUnknown);
	    		}
	});
};

populatePBITeamSummaryData = function() {
	var url = contextPath + '/reasonSummary/getPbiTeamSummary.smvc4';	
	$.ajax({ 
	    type: 'POST',
	    async: false,
	    url: url, 
	    dataType: 'json', 
	    cache: false,
	    success: function(response){
	    			jQuery('#balClearedAmt').text('$ ' + commaSeparateNumber(response[0].balClearedAmt));
	    			jQuery('#balClearedUnits').text(response[0].balClearedUnits);
	    			jQuery('#cshPstClearedAmt').text('$ ' + commaSeparateNumber(response[0].cshPstClearedAmt));
	    			jQuery('#cshPstClearedUnits').text(response[0].cshPstClearedUnits);
	    			jQuery('#encModClearedAmt').text('$ ' + commaSeparateNumber(response[0].encModClearedAmt));
	    			jQuery('#encModClearedUnits').text(response[0].encModClearedUnits);
	    			jQuery('#opsJobClearedAmt').text('$ ' + commaSeparateNumber(response[0].opsJobClearedAmt));
	    			jQuery('#opsJobClearedUnits').text(response[0].opsJobClearedUnits);
	    			jQuery('#susChrgClearedAmt').text('$ ' + commaSeparateNumber(response[0].susChrgClearedAmt));
	    			jQuery('#susChrgClearedUnits').text(response[0].susChrgClearedUnits);
	    		}
	});
};

populateReasonCardBarGraph = function(elem, entity){
	var url = contextPath + '/reasonSummary/' + entity + '.smvc4';	
	$.ajax({ 
	    type: 'POST', 
	    url: url, 
	    dataType: 'json', 
	    cache: false,
	    success:function(response) {
	    			jQuery('.charts-modal').on('show.bs.modal', function (event) {
	    				setTimeout(function(){
	    					Morris.Bar({
	    						element: elem,
	    						barGap:4,
	    						xLabelMargin: 10,
	    						data:response,
	    						xkey: 'team',
	    						ykeys: ['completed', 'identified','investigating', 'unknown', 'noExamples'],
	    						ymax:120,
	    						barColors: ["#1E88E5", "#4CAF50","#009688", "#e60000", "#1565C0"],
	    						labels: ['Completed', 'Identified','Investigating', 'Unknown','No Examples']
	    					});
	    					// When you open modal several times Morris charts over loading. So this is for destory to over loades Morris charts.
	    					// If you have better way please share it. 
	    					if(jQuery('#detailGraph').find('svg').length > 1){
	    						// Morris Charts creates svg by append, you need to remove first SVG
	    						jQuery('#detailGraph svg:first').remove();
	    						// Also Morris Charts created for hover div by prepend, you need to remove last DIV
	    						jQuery(".morris-hover:last").remove();
	    					}
	    					// Smooth Loading
	    					jQuery('.js-loading').addClass('hidden');
	    				},1000);
	    			});
	    		},
	    data: {},
	    async: false
	});
};

populateActnbleSmrydBarGraph = function(elem, entity){
	var url = contextPath + '/reasonSummary/' + entity + '.smvc4';	
	$.ajax({ 
	    type: 'POST', 
	    url: url, 
	    dataType: 'json', 
	    cache: false,
	    success:function(response) {
	    			jQuery('.charts-modal').on('show.bs.modal', function (event) {
	    				setTimeout(function(){
	    					Morris.Bar({
	    						element: elem,
	    						barGap:4,
	    						xLabelMargin: 10,
	    						data:response,
	    						xkey: 'team',
	    						ykeys: ['cernerActionableCnt', 'clientActionableCnt','unknownCnt'],
	    						ymax:120,
	    						barColors: ["#1E88E5", "#4CAF50","#009688"],
	    						labels: ['Cerner', 'Client', 'Unknown']
	    					});
	    					// When you open modal several times Morris charts over loading. So this is for destory to over loades Morris charts.
	    					// If you have better way please share it. 
	    					if(jQuery('#detailGraph').find('svg').length > 1){
	    						// Morris Charts creates svg by append, you need to remove first SVG
	    						jQuery('#detailGraph svg:first').remove();
	    						// Also Morris Charts created for hover div by prepend, you need to remove last DIV
	    						jQuery(".morris-hover:last").remove();
	    					}
	    					// Smooth Loading
	    					jQuery('.js-loading').addClass('hidden');
	    				},1000);
	    			});
	    		},
	    data: {},
	    async: false
	});
};

populateTopClients = function(){
	var url = contextPath + '/reasonSummary/getTopClients.smvc4';	
	$.ajax({ 
	    type: 'POST',
	    async: false,
	    url: url, 
	    dataType: 'json', 
	    cache: false,
	    success: function(response){
	    			jQuery('#firstClient').text(response[0].name);
	    			jQuery('#firstClientAmount').text('$ ' + commaSeparateNumber(response[0].id));
	    			jQuery('#secondClient').text(response[1].name);
	    			jQuery('#secondClientAmount').text('$ ' + commaSeparateNumber(response[1].id));
	    			jQuery('#thirdClient').text(response[2].name);
	    			jQuery('#thirdClientAmount').text('$ ' + commaSeparateNumber(response[2].id));
	    			jQuery('#fourthClient').text(response[3].name);
	    			jQuery('#fourthClientAmount').text('$ ' + commaSeparateNumber(response[3].id));
	    			jQuery('#fifthClient').text(response[4].name);
	    			jQuery('#fifthClientAmount').text('$ ' + commaSeparateNumber(response[4].id));
	    		}
	});
};

function commaSeparateNumber(val){
    while (/(\d+)(\d{3})/.test(val.toString())){
      val = val.toString().replace(/(\d+)(\d{3})/, '$1'+','+'$2');
    }
    return val;
}

/*================================ PROCESS TIP ================================*/

function searchProcessTips(url){
	var keywords = jQuery("#keywords").val();
	
	if(keywords == undefined){
		keywords = "";
	}
	
	keywords.split(" ").join("_")
	
	var strURL = contextPath + url + "?keywords="    + keywords;
	
	jQuery("#jqGrid").setGridParam({url:strURL,page:1});
	jQuery("#jqGrid").trigger("reloadGrid");
};

incrementViewCount = function(processId){
	var url = contextPath + "/processtip/updateViewCnt.smvc4?processTipId=" + processId;
	$.ajax({ 
	    type: 'POST', 
	    url: url, 
	    dataType: 'json', 
	    cache: false,
	    success:function(response) {},	    
	    data: {},
	    async: false
	});
};

incrementLikesCount = function(){
	var row = jQuery("#jqGrid").jqGrid('getGridParam','selrow');
	var processId = jQuery("#jqGrid").getCell(row, 'processTipId');
	var url = contextPath + "/processtip/updateLikesCnt.smvc4?processTipId=" + processId;
	$.ajax({ 
	    type: 'POST', 
	    url: url, 
	    dataType: 'json', 
	    cache: false,
	    success:function(response) {},	    
	    data: {},
	    async: false
	});
};

addCommentToTip = function(){
	var row = jQuery("#jqGrid").jqGrid('getGridParam','selrow');
	var processId = jQuery("#jqGrid").getCell(row, 'processTipId');
	var comment = jQuery("#ptComment").val();
	
	var url = contextPath + "/processtip/addComment.smvc4?processTipId=" + processId
															+ "&comment=" + comment;
	
	if(comment == null || comment == undefined || comment.trim()==""){
		jQuery("#textdiv").text("Please enter comment");
		jQuery( "#dialogSelectRow" ).dialog( {	title: 'Warning', modal: true, buttons: {"OK": function() {jQuery(this).dialog("close");}}});
	}else{
		$.ajax({ 
		    type: 'POST', 
		    url: url, 
		    dataType: 'json', 
		    cache: false,
		    success:function(response) {
		    	var result = eval('(' + response.responseText + ')');
				var errors = "";
				if (result.success == false) {
					for (var i = 0; i < result.message.length; i++) {
						errors +=  result.message[i] + "<br/>";
					}
				}else{
					jQuery("#dialog").text('Comment added successfully');
					jQuery("#dialog").dialog({title: 'Success', modal: true, buttons: {"Ok": function()  {jQuery(this).dialog("close");}}});
				}

		    },	    
		    data: {},
		    async: false
		});
	}
};

/*================================ PROCESS TIP LOGIN ================================*/

function loginProcessTips(url){
	var username = jQuery("#username").val();
	var password = jQuery("#password").val();
	alert(username);
/*	jQuery.ajax({ 
	    type: 'POST', 
	    url: url, 
	    dataType: { 'username' : username, 'password' : password },
	    cache: false,
	    success:function(response) {
	    	jQuery("#username").val($('#username').val());
	    	jQuery("#password").val($('#password').val());
	    },	    
	    data: {},
	    async: false
	    
	});
	*/
	
};
