package readingManagement;

public class View {
	private String id;
	private String title;
	private String genre;
	private String writer;
	private String publisher;
	private String start;
	private String end;
	private String text;

	public View (String id, String title,
			String genre, String writer,
			String publisher, String start,
			String end, String text){
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.writer = writer;
		this.publisher = publisher;
		this.start = start;
		this.end = end;
		this.text = text;
	}

	public String getId(){return id;}
	public void setId(String id){this.id = id;}

	public String getTitle(){return title;}
	public void setTitle(String title){this.title = title;}

	public String getGenre(){return genre;}
	public void setGenre(String genre){this.genre = genre;}

	public String getPublisher(){return publisher;}
	public void setPublisher(String publisher){this.publisher = publisher;}

	public String getWriter(){return writer;}
	public void setWriter(String writer){this.writer = writer;}

	public String getStart(){return start;}
	public void setStart(String start){this.start = start;}

	public String getEnd(){return end;}
	public void setEnd(String end){this.end = end;}

	public String getText(){return text;}
	public void setText(String text){this.text = text;}
}
