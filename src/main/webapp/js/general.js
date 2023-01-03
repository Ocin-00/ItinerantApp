/**
 * 
 */
 $(document).ready(function() {/*
    var entityMap = {
		'&': '&amp;',
		'<': '&lt;',
		'>': '&gt;',
		'"': '&quot;',
		"'": '&#39;',
		'/': '&#x2F;',
		'`': '&#x60;',
		'=': '&#x3D;'
	};

	function escapeHtml (string) {
		return String(string).replace(/[&<>"'`=\/]/g, function (s) {
			return entityMap[s];
		});
	}
	/*	
	$("#buscarForm").submit(function() {
		let keyword = $("#keyword").val();
		let escapedKeyword = escapeHtml(keyword)
		$("#keyword").val(escapedKeyword);
	});*/
	/*
	var formElements = new Array();
	$("form :input").each(function(){
	    formElements.push($(this));
	 });
	//alert(formElements)
	
	$("form").submit(function() {
		for(input of formElements) {
			let keyword = input.val();
			let escapedKeyword = escapeHtml(keyword)
			input.val(escapedKeyword);
		}
	});*/
});