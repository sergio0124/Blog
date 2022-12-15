<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Поиск</title>

    <script src="read_post.js"></script>
    <script src="search.js"></script>
</head>
<body>

<div>
    <button onclick="document.location.href = '/user/info'">Информация об аккаунте</button>
</div>

<div>
    <label for="search">Поиск: </label><input type="text" id="search_input">
    <button onclick="search()">Поиск</button>
</div>

<div>
    <div>Посты</div>

    <#if posts??>
        <#list posts as post>
            <div>

                <#if post.creator_name??>
                    <div>
                        <label>Имя издателя ${post.creator_name}</label>
                    </div>
                </#if>

                <div>
                    <label>Название ${post.title}</label>
                </div>

                <div>
                    <label>Дата: ${post.date}</label>
                </div>

                <div>
                    <label>Описание: ${post.description}</label>
                </div>

                <button onclick="read_post(${post.id})">Читать пост</button>

            </div>
        </#list>
    </#if>

</div>


</body>
</html>