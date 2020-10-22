let users = [];

async function getUsers() {
    let result = await fetch('/rest/users');
    users = await result.json();

    console.log(users);

    renderUsers();
}

function renderUsers() {
    let userList = document.querySelector("#user-list");

    userList.innerHTML = "";

    for(let user of users) {
        let userLi = `
            <li>
                id: ${user.id} <br>
                name: ${user.name} <br>
                age: ${user.age} 
            </li> <br>
        `;

        userList.innerHTML += userLi;
    }

}

async function createUser() {
    let user = {
        name: "Cassandra",
        age: 48
    }

    let result = await fetch("/rest/users", {
        method: "POST",
        body: JSON.stringify(user)
    });

    console.log(await result.text())
}