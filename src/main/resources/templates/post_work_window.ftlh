<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Создание поста</title>

</head>
<body>

<div>

    <#if post??>
        <div>${post.id}</div>
    </#if>

    <div>
        <button id="save_button">Сохранить</button>
    </div>

    <div>
        <label for="title">Название:</label><input id="title"
                <#if post??>
                    value="${post.title}"
                </#if>
        >
    </div>

    <div>
        <label for="description">Описание:</label>
        <textarea maxlength="250" id="description">
        <#if post??>
            ${post.description}
        </#if>
    </textarea>
    </div>

    <div>
        <label for="text">Текст:</label>
        <textarea maxlength="10000" id="text">
        <#if post??>
            ${post.text}
        </#if>
    </textarea>
    </div>

    <div>
        <label for="image">Загрузить изображение: </label>
        <input type="file" id="image_load" accept=".png,.jpeg">
    </div>

    <label>Изображения:</label>
    <div id="image_container">
        <#if post??>
            <#list post.postImages as img_src>
                <div class="img_cont">
                    <img class="loaded_img" src="${img_src}">
                    <button onchange="delete_image(this)">Удалить изображение</button>
                </div>
            </#list>
        </#if>
    </div>

</div>


<script language="JavaScript">
    image_container = document.getElementById("image_container");
    save_button = document.getElementById("save_button");
    image_load = document.getElementById("image_load");

    function http_post(theUrl, inputData) {
        let xmlHttp = new XMLHttpRequest();
        xmlHttp.open("POST", theUrl, false);
        xmlHttp.setRequestHeader("Content-Type", "application/json");
        xmlHttp.send(inputData);
        return [xmlHttp.responseText, xmlHttp.status];
    }

    image_load.addEventListener("change", function (event) {
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
        delete_button.setAttribute("text", "Удалить изображение");

        private_cont.appendChild(loaded_img);
        private_cont.appendChild(delete_button);

        image_container.appendChild(private_cont);

        reader.readAsDataURL(selectedFile);
    });

    function delete_image(node) {
        let parent = node.parents(".img_cont")
        parent.parentNode.removeChild(parent);
    }

    save_button.addEventListener("click", function () {
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
            postImageSet.push(pngUrl);
        }

        let data = {
            "title": title.value,
            "text": text.value,
            "description": description.value,
            "postImages": postImageSet
        };
        let json = JSON.stringify(data);

        http_post("/creator/work_on_post", json);
    });
</script>

</body>
</html>