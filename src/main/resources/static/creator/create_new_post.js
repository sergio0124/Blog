function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.responseText, xmlHttp.status];
}

function load_image(event) {
    let selectedFile = event.target.files[0];
    let reader = new FileReader();

    let image_container = document.getElementById("image_container");
    let private_cont = document.createElement("div");
    private_cont.setAttribute("class", "img_cont");

    let loaded_img = document.createElement("img");
    loaded_img.setAttribute("class", "loaded_img");
    reader.onload = function (event) {
        loaded_img.src = event.target.result;
    };

    let delete_button = document.createElement("button");
    delete_button.innerHTML = "Удалить это изображение";
    delete_button.onclick = function(){delete_image(delete_button);}
    private_cont.appendChild(loaded_img);
    private_cont.appendChild(delete_button);

    image_container.appendChild(private_cont);

    reader.readAsDataURL(selectedFile);
}


function delete_image(node) {
    let parent = node.parentNode;
    parent.parentNode.removeChild(parent);
}

function save_post() {
    let post_id = null;
    if (document.getElementById("post_id") != null) {
        post_id = document.getElementById("post_id").textContent;
    }
    let title = document.getElementById("title");
    let description = document.getElementById("description");
    let text = document.getElementById("text");
    let images = document.getElementsByClassName("loaded_img");

    let postImageSet = []
    for (let i = 0; i < images.length; i++) {
        let element = images[i];
        let pngUrl = element.src;
        let id_element = element.parentNode.querySelector('.hidden_id');
        if (id_element == null) {
            postImageSet.push({
                "image": pngUrl
            });
            continue;
        }

        let image_id = Number(id_element.textContent);
        postImageSet.push({
            "id": image_id,
            "image": pngUrl
        });

    }

    let data = {
        "id": post_id,
        "title": title.value,
        "text": text.value,
        "description": description.value,
        "postImages": postImageSet
    };
    let json = JSON.stringify(data);

    let code = http_post("save_post", json);
    alert(code[0]);
    if (code[1] == 200) {
        document.location.href = "/";
    }
};


