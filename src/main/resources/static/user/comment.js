function send_comment() {
    let post_id = document
        .getElementById("post_id").textContent;
    let comment_text = document
        .getElementById("own_comment_text").value;

    let data = {
        "text": comment_text,
        "post": {
            "id": post_id
        }
    };
    let json = JSON.stringify(data);
    let response = http_post("save_comment", json);
    if (response[1] == 200) {
        window.location.reload();
    } else {
        alert(response[0]);
    }
}


function load_comments(user_id) {
    let pageCount = Number(document.getElementById("").textContent);
    document.getElementById("").textContent = '' + (pageCount + 1);
    let data = {
        "postId": post_id,
        "page": pageCount + 1
    };
    let json = JSON.stringify(data);
    let code = http_post_json("load_comments", json)[0];

    let comments_container = document
        .getElementById("comments_container");
    code.forEach(function (iter, i, code) {
        let private_cont = document.createElement("div");
        let username_div = document.createElement("div");
        let date_div = document.createElement("div");

        username_div.textContent = iter.user.username;
        date_div.textContent = iter.creationDate;
        private_cont.appendChild(username_div);
        private_cont.appendChild(date_div);

        if (iter.user.id == user_id) {
            let delete_button_div = document.createElement("div");
            let update_button_div = document.createElement("div");

            let delete_button = document.createElement("button");
            delete_button.textContent = "Удалить комментарий";
            delete_button.onclick = function () {
                delete_comment(delete_button, iter.id);
            }
            delete_button_div.appendChild(delete_button);

            let label = document.createElement("label");
            label.innerText = "Текст:";
            let input = document.createElement("input");
            input.id = "comment_text";
            input.value = iter.text;
            let save_button = document.createElement("button");
            save_button.textContent = "Сохранить изменения";
            save_button.onclick = function () {
                update_comment(save_button, iter.id);
            }
            update_button_div.appendChild(label);
            update_button_div.appendChild(input);
            update_button_div.appendChild(save_button);
            private_cont.appendChild(update_button_div);
            private_cont.appendChild(delete_button_div);
        } else {
            let text_div = document.createElement("div");
            text_div.textContent = iter.text;
            private_cont.appendChild(text_div);
        }

        comments_container.appendChild(private_cont);
    })
}


function update_comment(element, commentId) {
    let post_id = document
        .getElementById("post_id").textContent;
    let text = element.parentNode.querySelector('#comment_text').value;
    let data = {
        "id": commentId,
        "text": text,
        "post": {
            "id": post_id
        }
    };
    let json = JSON.stringify(data);
    let response = http_post("save_comment", json);

    if (response[1] == 200) {
        window.location.reload();
    } else {
        alert(response[0]);
    }
}


function delete_comment(element, commentId) {
    if (!confirm("Уверены, что хотите удалить комментарий?")) {
        return;
    }
    let data = {
        "id": commentId
    };
    let json = JSON.stringify(data);
    let response = http_post("delete_comment", json);

    if (response[1] == 200) {
        let parent = element.parentNode.parentNode;
        parent.parentNode.removeChild(parent);
    } else {
        alert(response[0]);
    }
}


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