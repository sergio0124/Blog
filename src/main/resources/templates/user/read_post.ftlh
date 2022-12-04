<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Чтение поста</title>
    <script src="reading.js"></script>
</head>
<body>

<div>

    <#if post??>
        <div id="post_id" style="display: none">${post.id}</div>

        <div id="comment_page" style="display: none">0</div>

        <div>
            <label for="title">Название:</label><input id="title"
                    <#if post??>
                        value="${post.title}"
                    </#if>
            >
        </div>

        <div>
            <label for="description">Описание:</label>
            <div id="description">
                <#if post??>
                    ${post.description}
                </#if>
            </div>
        </div>

        <div>
            <label for="text">Текст:</label>
            <div id="text">
                <#if post??>
                    ${post.text}
                </#if>
            </div>
        </div>


        <div>
            <button id="like_button"
                    style="background-color: <#if liked=true>red<#else>blue</#if>"
                    onclick="like_tap(this, ${post.id})">Лайк
            </button>
            <label>post.likesCount</label>
        </div>

        <div>
            <button id="report_button">Репорт</button>
        </div>

        <div id="comments_container">
            <#if post.comments??>
                <#list post.comments as comment>
                    <div>
                        <div>${comment.user.username}</div>
                        <div>${comment.text}</div>
                    </div>
                </#list>
            </#if>
        </div>

        <#if post.comments??>
            <#if post.comments?size = 10>
                <button id="load_comments"
                        onclick="load_comments()">Подгрузить комментарии
                </button>
            </#if>
        </#if>

    <#else>
        <label>Пост с таким id не найден</label>
    </#if>

</div>


</body>
</html>