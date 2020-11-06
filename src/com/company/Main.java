package com.company;

import express.Express;
import express.middleware.Middleware;
import org.apache.commons.fileupload.FileItem;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Express app = new Express();
        Database db = new Database();

        // req = Request, res = Response
        app.get("/rest/posts", (req, res) -> {
            List<BlogPost> posts = db.getPosts();
            res.json(posts);
        });

        app.get("/rest/posts/:id", (req, res) -> {
            int id = Integer.parseInt(req.getParam("id"));

            BlogPost post = db.getPostById(id);
            res.json(post);
        });

        app.post("/rest/posts", (req, res) -> {
            BlogPost post = (BlogPost) req.getBody(BlogPost.class);

            db.createPost(post);
            res.send("post OK");
        });

        app.post("/api/file-upload", (req, res) -> {
            String imageUrl = null;

            // extract the file from the FormData
            try {
                List<FileItem> files = req.getFormData("files");
                imageUrl = db.uploadImage(files.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // return "/uploads/image-name.jpg
            res.send(imageUrl);
        });

        // will serve both the html/css/js files and the uploads folder
        try {
            app.use(Middleware.statics(Paths.get("src/www").toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        app.listen(3000); // defaults to port 80
        System.out.println("Server started on port 3000");
    }
}
