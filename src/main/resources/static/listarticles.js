function getUrlParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
    return null;
} 


$(document).ready(function() {
	
	//search & filter:
    $('#searchButton').click(function() {
        var rex = new RegExp($('#searchInput').val(), 'i');
        $('.searchable div').hide();
        $('.searchable div').filter(function () {
            return rex.test($(this).text());
        }).show();
    });
	
	var page = getUrlParameter("page");
	var size = getUrlParameter("size");
	var url = "api/articles/";
	if (page == null) {
		page = 1;
	}
	if (size == null) {
		size = 10;
	}
	url += "?page="+page+"&size="+size;
    $.ajax({
        url: url
    }).then(function(data) {
    	var articles = $('#articles');
    	var listSize = 0;
    	
    	//list articles:
    	$.each(data, function(i, article) {
    		var panel = $('<div>', {class: "panel panel-default searchable"});
    		
    		var heading = $('<div>', {class: "panel-heading"});
    		panel.append(heading);
    		var headingCollapse = $('<a>', {"data-toggle": "collapse", "data-parent": "#articles", href: "#collapse"+i});
    		var glyph = $('<span>', {class: "glyphicon glyphicon-star"});
    		heading.append(glyph);
			headingCollapse.append(" " + article.id + " ");
    		headingCollapse.append(" "+article.title);
    		heading.append(headingCollapse);

    		var dateView = $('<span>', {class: "article-date"});
                		var artDate = new Date(article.date);
                		dateView.append(" "+artDate.toLocaleDateString());
                		heading.append(dateView);


    		var labels = $('<span>', {class: "category-labels"});
    		$.each(article.categories, function(c, category) {
    			var label = $('<span>', {class: "label label-default", id: "label-default"+c});
    			label.append(category);
    			labels.append(label);
    		});


			/*var inputForm = $('<form>');
			var divInput =  $('<div>', {class: "input-group"});
			var spanInput = $('<span>', {class: "input-group-addon"});
			spanInput.append("Text");
			divInput.append(spanInput);
			var input =  $('<input>', {class: "form-control", type: "text"});
			divInput.append(input);


			var divButton = $('<div>', {class: "input-group-btn"});
			var saveCategoryButton = $('<button>', {class: "btn btn-default", type: "submit"});
			saveCategoryButton.append("Save");
			divButton.append(saveCategoryButton);
			divInput.append(divButton);

			inputForm.append(divInput);
			heading.append(inputForm);*/

    		heading.append(labels);
    		

    		var collapse = $('<div>', {id: "collapse"+i, class: "panel-collapse collapse"});
    		var body = $('<div>', {class: "panel-body"});
    		var summary = $('<div>');
    		summary.append(article.summary);
    		body.append(summary);
    		
    		var linkToFull = $('<div>', {class: "full-article-link"});
    		var link = $('<a>', {href: "article.html?id="+article.id});
    		link.append("Full article");
    		linkToFull.append(link);
    		body.append(linkToFull);

            var linkToOriginal = $('<div>', {class: "original-article-link"});
            var linkOrig = $('<a>', {href: article.url});
            linkOrig.append("Link to original article");
            linkToOriginal.append(linkOrig);
            body.append(linkToOriginal);

    		collapse.append(body);
    		articles.append(panel);
    		articles.append(collapse);
    		listSize = i;
    	});
    	
    	//pagination:
    	var pagination = $('#pagination');
    	var prevPage = Number(page)-1;
    	var nextPage = Number(page)+1;

    	if (page == 1) {
    		var liPrev = $('<li>', {class: "disabled"});
    		var prev = $('<a>', {href: "#"});
    	} else {
    		var liPrev = $('<li>');
    		var prev = $('<a>', {href: "index.html?page="+prevPage+"&size="+size});
    	}
    	prev.append("Previous");
    	liPrev.append(prev);
    	
    	if (listSize+1 == size) {
    		var liNext = $('<li>');
        	var next = $('<a>', {href: "index.html?page="+nextPage+"&size="+size});
    	} else {
    		var liNext = $('<li>', {class: "disabled"});
        	var next = $('<a>', {href: "#"});
    	}
    	next.append("Next");
    	liNext.append(next);
    	pagination.append(liPrev);
    	pagination.append(liNext);
    	
    
    });
});
