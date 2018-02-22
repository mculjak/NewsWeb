package com.newsweb.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="article",uniqueConstraints=@UniqueConstraint(
		columnNames={"url"}
))
@NamedQueries({
    @NamedQuery(name="Article.getArticleForCategory",
    		query="select a from Article as a JOIN a.categories categories "
    				+ "WHERE categories IN (:categories) group by a.id having count(a) = (:numCategories)")
})
public class Article {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="url", nullable=false, length=1024)
	private String url;
	
	@Column(name="title", nullable=false, length=512)
	private String title;
	
	@Lob
	@Type(type = "org.hibernate.type.TextType")
	@Column(name="html", nullable=false)
	private String html;
	
	@Lob
	@Type(type = "org.hibernate.type.TextType")
	@Column(name="text", columnDefinition = "TEXT")
	private String text;
	
	@Column(name="summary", length=2048)
	private String summary;
	
	@Column(name="date", nullable=false)
	private Date date;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "article_categories", joinColumns = @JoinColumn(name = "article_id"))
	private List<String> categories;

	public Article() {}
	
	public Article(Long id, String url, String title, String text, String summary, List<String> categories, Date date) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		this.text = text;
		this.summary = summary;
		this.categories = categories;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
