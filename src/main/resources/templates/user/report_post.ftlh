<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Создание репорта</title>
    <script src="report.js"></script>
</head>
<body>

<div id="message">

</div>

<#if message??>
    <div>${message}</div>
<#else>

    <div>Вы создаёте репорт на пост:</div>
    <div>Название <i>${post.title}</i></div>
    <div>Описание <i>${post.description}</i></div>

    <div>Выберите причину:</div>
    <div>
        <select id="report_type_select">
            <#list reportTypes as rep>
                <option>${rep}</option>
            </#list>
        </select>
        <button onclick="send_report()">Сохранить</button>
    </div>
</#if>

</body>
</html>