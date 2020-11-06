package com.company;

import express.utils.Utils;
import org.apache.commons.fileupload.FileItem;

import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.sql.*;
import java.time.Instant;
import java.util.List;

public class Database {

    private Connection conn;

    public Database() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:express.db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String uploadImage(FileItem image) {
        // the uploads folder in the "www" directory is accessible from the website
        // because the whole "www" folder gets served, with all its content

        // get filename with file.getName()
        String imageUrl = "/uploads/" + image.getName();

        // open an ObjectOutputStream with the path to the uploads folder in the "www" directory
        try (var os = new FileOutputStream(Paths.get("src/www" + imageUrl).toString())) {
            // get the required byte[] array to save to a file
            // with file.get()
            os.write(image.get());
        } catch (Exception e) {
            e.printStackTrace();
            // if image is not saved, return null
            return null;
        }

        return imageUrl;
    }

    // replace whole entity in database with updated post.
    // the post must have an ID
    public void updatePost(BlogPost post) {
        // validate post ID (present and exists in database)

        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE posts SET title = ?, content = ?, timestamp = ?, imageUrl = ? WHERE id = ?");
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getContent());
            stmt.setLong(3, post.getTimestamp());
            stmt.setString(4, post.getImageUrl());
            stmt.setInt(5, post.getId());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BlogPost> getPosts() {
        List<BlogPost> posts = null;

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM blog_posts");
            ResultSet rs = stmt.executeQuery();

            BlogPost[] usersFromRS = (BlogPost[]) Utils.readResultSetToObject(rs, BlogPost[].class);
            posts = List.of(usersFromRS);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }

    public BlogPost getPostById(int id) {
        BlogPost post = null;

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM blog_posts WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            // ResultSet is always an array of items
            BlogPost[] userFromRS = (BlogPost[]) Utils.readResultSetToObject(rs, BlogPost[].class);

            post = userFromRS[0];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return post;
    }

    public void createPost(BlogPost post) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO blog_posts (title, content, timestamp, imageUrl) VALUES(?, ?, ?, ?)");
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getContent());
            stmt.setLong(3, Instant.now().toEpochMilli());
            stmt.setString(4, post.getImageUrl());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
