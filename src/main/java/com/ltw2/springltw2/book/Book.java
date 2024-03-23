package com.ltw2.springltw2.book;

import java.sql.Date;

public class Book {
	private int id;
	private String author;
	private String price;
	private String title;
	private String describes;
	private Date date;
	private int bookpage;
	private String category;
	private String imgSrc;
	
	
	
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Book(int id, String title,String author, String price, String describes, Date date,int bookpage, String category) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.price = price;
		this.describes = describes;
		this.date = date;
		this.bookpage = bookpage;
		this.category = category;
	}
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescribes() {
        return describes;
    }
    public void setDescribes(String describes) {
        this.describes = describes;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getBookpage() {
        return bookpage;
    }
    public void setBookpage(int bookpage) {
        this.bookpage = bookpage;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    
    
}

