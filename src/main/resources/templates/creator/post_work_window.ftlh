<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Создание поста</title>
    <script src="/creator/create_new_post.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<div>

    <div style="margin-bottom: 20px">
        <img onclick="window.location.href = '/'" src="../static/image/blog.png" alt="">
        <label>Пользователь ${user.username}</label>
        <button onclick="document.location.href = '/info'">Информация об аккаунте</button>
        <button onclick="document.location.href = '/logout'">Выйти из аккаунта</button>
    </div>
    <hr/>

    <#if post??>
        <div id="post_id" style="display: none">${post.id}</div>
    </#if>

    <div>
        <button onclick="save_post()">Сохранить</button>
    </div>

    <div>
        <label for="title">Название:</label><input id="title" <#if post??>value="${post.title}"</#if>">
    </div>

    <div>
        <label for="description">Описание:</label>
        <textarea maxlength="250" id="description"><#if post??>${post.description}</#if></textarea>
    </div>

    <div>
        <label for="text">Текст:</label>
        <textarea maxlength="10000" id="text"><#if post??>${post.text}</#if></textarea>
    </div>

    <div>
        <label for="image">Загрузить изображение: </label>
        <input type="file" onchange="load_image(event)" accept=".png,.jpeg,.jpg">
    </div>

    <label>Изображения:</label>
    <div id="image_container">
        <#if post??>
            <#list post.postImages as img_src>
                <div class="img_cont">
                    <#if img_src.id??>
                        <div style="display: none" class="hidden_id">${img_src.id}</div>
                    </#if>
                    <img class="loaded_img" src="${img_src.image}">
                    <button onclick="delete_image(this)">Удалить изображение</button>
                </div>
            </#list>
        </#if>
    </div>

</div>


</body>
</html>