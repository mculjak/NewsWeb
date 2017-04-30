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
} 


$(document).ready(function() {
	var id = getUrlParameter("id");
    $.ajax({
        url: "api/articles/"+id
    }).then(function(data) {
    	var article = data;
    	var panel = $(".panel.panel-default");
    	var heading = $('<div>', {class: "panel-heading"});
		panel.append(heading);
		var glyph = $('<span>', {class: "glyphicon glyphicon-star"});
		heading.append(glyph);
		heading.append(" NewsWeb Article");

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
		heading.append(labels);

		var body = $('<div>', {class: "panel-body"});
		var title = $('<h3>', {id: "article-title"});
		title.append(article.title);
		body.append(title);
		var text = $('<div>');
		text.append(article.text);
		body.append(text);
		
		var linkToOrig = $('<div>', {class: "full-article-link"});
		var link = $('<a>', {href: article.url});
		link.append("Read original article");
		linkToOrig.append(link);
		body.append(linkToOrig);
		panel.append(body);
    	
    });
});