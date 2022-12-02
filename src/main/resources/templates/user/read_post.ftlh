<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Чтение поста</title>

</head>
<body>

<div>

    <#if post??>
        <div id="post_id" style="display: none">${post.id}</div>
    </#if>


    <div>
        <label for="title">Название:</label><input id="title"
                <#if post??>
                    value="${post.title}"
                </#if>
        >
    </div>

    <div>
        <label for="description">Описание:</label>
        <div maxlength="250" id="description">
            <#if post??>
                ${post.description}
            </#if>
        </div>
    </div>

    <div>
        <label for="text">Текст:</label>
        <div maxlength="10000" id="text">
            <#if post??>
                ${post.text}
            </#if>
        </div>
    </div>


    <div>
        <button id="like_button">Лайк</button>
        <label>post.likesCount</label>
    </div>

    <div>
        <button id="report_button">Репорт</button>
    </div>

    <div id="comments_container">
        <#if comments??>
            <#list comments as comment>
                <div>
                    <div>${comment.username}</div>
                    <div>${comment.text}</div>
                </div>
            </#list>
        </#if>
    </div>

    <button id="load_comments">Подгрузить комментарии+</button>

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