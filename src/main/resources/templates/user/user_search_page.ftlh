<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Поиск</title>

    <script language="JavaScript">
        function go_read(id) {
            document.location.href = "/creator/read_post?id=" + id;
        }

        function go_search() {

        }
    </script>
</head>
<body>

<div>
    <button onclick="document.location.href = '/user/info'">Информация об аккаунте</button>
</div>

<div>
    <label for="search">Поиск: </label><input type="text" id="search">
    <button onclick="go_search()">Поиск</button>
</div>

<div>
    <div>Посты</div>

    <#if posts??>
        <#list posts as post>
            <div onclick="go_read(${post.id})">

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


            </div>
        </#list>
    </#if>

</div>


</body>
</html>