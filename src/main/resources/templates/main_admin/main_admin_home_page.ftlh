<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Главная страница пользователя</title>

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
    <div>Админы</div>

    <#if admins??>
        <#list admins as admin>
            <div onclick="go_read(${admin.userId})">

                <div>
                    <label>Имя админа ${admin.username}</label>
                </div>


            </div>
        </#list>
    </#if>

</div>


</body>
</html>