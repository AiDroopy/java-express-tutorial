package com.company;

public class BlogPost {
  private int id;
  private String title;
  private String content;
  private long timestamp;
  private String imageUrl;

  public BlogPost() { }

  public BlogPost(String title, String content, long timestamp) {
    this.title = title;
    this.content = content;
    this.timestamp = timestamp;
  }

  public BlogPost(String title, String content, long timestamp, String imageUrl) {
    this.title = title;
    this.content = content;
    this.timestamp = timestamp;
    this.imageUrl = imageUrl;
  }

  public BlogPost(int id, String title, String content, long timestamp, String imageUrl) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.timestamp = timestamp;
    this.imageUrl = imageUrl;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
