package com.company;

import express.Express;
import express.middleware.Middleware;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Express app = new Express();
        Database db = new Database();

        // req = Request, res = Response
        app.get("/hello-world", (req, res) -> {
            res.send("Hello World");
        });

        app.get("/about", (req, res) -> {
            res.send("This is the about page");
        });

        app.get("/rest/users", (req, res) -> {
            List<User> users = db.getUsers();

            res.json(users);
        });

        app.get("/rest/users/:id", (req, res) -> {
            int id = Integer.parseInt(req.getParam("id"));

            User user = db.getUserById(id);

            res.json(user);
        });

        app.post("/rest/users", (req, res) -> {
            User user = (User) req.getBody(User.class);

            System.out.println(user.toString());

            db.createUser(user);

            res.send("post OK");
        });

        try {
            app.use(Middleware.statics(Paths.get("src/www").toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        app.listen(3000); // defaults to port 80
        System.out.println("Server started on port 3000");
    }
}
