image_container = document.getElementById("image_container")

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

    let private_cont = document.createElement("div");
    private_cont.setAttribute("class", "img_cont");

    let loaded_img = document.createElement("img");
    loaded_img.setAttribute("class", "loaded_img");
    reader.onload = function (event) {
        loaded_img.src = event.target.result;
    };

    let delete_button = document.createElement("button");
    delete_button.setAttribute("onchange", "delete_image(this)");
    delete_button.setAttribute("text", "Удалить это изображение");

    private_cont.appendChild(loaded_img);
    private_cont.appendChild(delete_button);

    image_container.appendChild(private_cont);

    reader.readAsDataURL(selectedFile);
}

function delete_image(node) {
    let parent = node.parents(".img_cont")
    parent.parentNode.removeChild(parent);
}

function save_data() {
    let title = document.getElementById("title");
    let description = document.getElementById("description");
    let text = document.getElementById("text");
    let images = document.getElementsByClassName("loaded_img");

    let canvas = document.createElement("canvas");
    let context = canvas.getContext('2d');

    let postImageSet = []
    for (let i = 0; i < images.length; i++) {
        let element = images[i];
        base_image = new Image();
        base_image.src = element.src;
        base_image.onload = function () {
            context.drawImage(base_image, 100, 100);
        }
        let pngUrl = canvas.toDataURL("image/jpeg");
        listUrl.push(pngUrl);
    }

    let data = {
        "title": title.value,
        "text": text.value,
        "description": description.value,
        "postImageSet": postImageSet
    };
    let json = JSON.stringify(data);

    http_post("/creator/new", data);
}


