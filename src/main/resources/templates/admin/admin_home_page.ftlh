<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Главная страница редактора</title>
    <script src="pages.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>


<div>

    <div>Посты</div>

    <#if posts??>
        <#list posts as post>
            <div>

                <div>
                    <label>Название ${post.title}</label>
                </div>

                <div>
                    <label>Дата: ${post.date}</label>
                </div>

                <div>
                    <label>Описание: ${post.description}</label>
                </div>

                <div>
                    <label>Количество репортов: ${post.reportCount}</label>
                </div>

                <div>
                    <label>Тип репорта: ${post.reportType}</label>
                </div>

                <button onclick="document.location.href = '/admin/check_post?postId=${post.id}'">
                    Проверить пост
                </button>

            </div>
        </#list>
    </#if>

    <div>
        <#if page!=0>
            <button onclick="increase_page(-1)">Назад</button>
        </#if>
        <button id="page_num">${page}</button>
        <#if posts?size = 5>
            <button onclick="increase_page(1)">Далее</button>
        </#if>
    </div>

</div>


</body>
</html>