<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Оценка поста</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="ban.js"></script>
</head>
<body>

<#if message??>
    <div>${message}</div>
<#else>
    <div id="username" onclick="go_to_creator_page(${post.user.id})">
        ${post.user.username}
    </div>

    <div id="title">
        ${post.title}
    </div>

    <div>
        <div></div>
        <div id="description">
            ${post.description}
        </div>
    </div>

    <div>
        <div></div>
        <div id="text">
            ${post.text}
        </div>
    </div>


    <div>
        <label>Количество репортов: ${post.reportCount}</label>
    </div>

    <div>
        <label>Тип репорта: ${post.reportType}</label>
    </div>


    <div></div>
    <div id="image_container">
        <#list post.postImages as image>
            <div class="img_cont">
                <img class="loaded_img" src="${image.image}">
            </div>
        </#list>
    </div>


    <div>
        <button onclick="ban_post(${post.id})">Удалить пост</button>
        <button onclick="justify_post(${post.id})">Признать пост приемлемым</button>
        <button onclick="double_ban_post(${post.id})">Удалить пост и заблокировать пользователя</button>
    </div>


</#if>


</body>
</html>