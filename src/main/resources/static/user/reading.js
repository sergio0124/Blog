function http_post_json(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.responseType = 'json';
    xmlHttp.send(inputData);
    return [xmlHttp.response, xmlHttp.status];
}

function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.response, xmlHttp.status];
}


function like_tap(element, postId) {
    let color = element.style.backgroundColor;

    let body = {
        "id": postId
    }
    let json = JSON.stringify(body);
    let responce = http_post("like", json);
    let like_count_elem = document.getElementById("like_count");
    if (responce[1] != 200) {
        alert(responce[0]);
    } else {
        if (color == 'blue') {
            element.style.backgroundColor = 'red';
            like_count_elem.innerText = '' + (Number(like_count_elem.innerText) + 1);
        } else if (color == 'red') {
            element.style.backgroundColor = 'blue';
            like_count_elem.innerText = '' + (Number(like_count_elem.innerText) - 1);
        }
    }
}


function load_comments() {
    let pageCount = Number(document.getElementById("").textContent);
    document.getElementById("").textContent = '' + (pageCount + 1);
    let data = {
        "postId": post_id,
        "page": pageCount + 1
    };
    let json = JSON.stringify(data);
    let code = http_post_json("user/load_comments", json)[0];

    let comments_container = document
        .getElementById("comments_container");
    code.forEach(function (iter, i, code) {
        let private_cont = document.createElement("div");
        let username_div = document.createElement("div");
        let text_div = document.createElement("div");

        username_div.textContent = iter.user.username;
        text_div.textContent = iter.text;
        private_cont.appendChild(username_div);
        private_cont.appendChild(text_div);

        comments_container.appendChild(private_cont);
    })

    let button_load = document.getElementById("load_comments");
    if (comments_container.childElementCount % 10 != 0) {
        button_load.style.display = "none";
    }
}