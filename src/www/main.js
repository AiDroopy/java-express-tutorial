let posts = [];
getPosts()

async function getPosts() {
    let result = await fetch('/rest/posts');
    posts = await result.json();

    console.log(posts);

    renderPosts();
}

function renderPosts() {
    let postList = document.querySelector("#post-list");

    // clear list before update
    postList.innerHTML = "";

    for(let post of posts) {
        let date = new Date(post.timestamp).toLocaleString();

        let postLi = `
            <li>
                <img src="${post.imageUrl}" alt="post-image">
                id: ${post.id} <br>
                published: ${date} <br>
                <h3>${post.title}</h3>

                <p>${post.content}</p>
            </li> <br>
        `;

        postList.innerHTML += postLi;
    }

}

async function createPost(e) {
    e.preventDefault();

    // upload image with FormData
    let files = document.querySelector('input[type=file]').files;
    let formData = new FormData();

    for(let file of files) {
        formData.append('files', file, file.name);
    }

    // upload selected files to server
    let uploadResult = await fetch('/api/file-upload', {
        method: 'POST',
        body: formData
    });

    // get the uploaded image url from response
    let imageUrl = await uploadResult.text();

    let titleInput = document.querySelector('#title');
    let contentInput = document.querySelector('#content');

    // create a post object containing values from inputs
    // and the uploaded image url
    let post = {
        title: titleInput.value,
        content: contentInput.value,
        imageUrl: imageUrl
    }

    let result = await fetch("/rest/posts", {
        method: "POST",
        body: JSON.stringify(post)
    });

    posts.push(post)

    console.log(await result.text())
}