<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Чтение поста</title>
    <script src="reading.js"></script>
    <script src="comment.js"></script>
    <script src="report.js"></script>
</head>
<body>

<div>

    <#if post??>
    <div id="post_id" style="display: none">${post.id}</div>

    <div id="comment_page" style="display: none">0</div>

    <div>
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


        <div></div>
        <div id="image_container">
            <#list post.postImages as image>
                <div class="img_cont">
                    <img class="loaded_img" src="${image.image}">
                </div>
            </#list>
        </div>

        <div>
            <button id="like_button"
                    style="background-color: <#if liked=true>red<#else>blue</#if>"
                    onclick="like_tap(this, ${post.id})">Лайк
            </button>
            <label id="like_count">${post.likesCount}</label>
        </div>

        <div>
            <button id="report_button" onclick="go_report(${post.id})">Репорт</button>
        </div>

        <div id="comments_container">
            <label>Комментарии:</label>
            <#if post.comments??>
                <#list post.comments as comment>
                    <div>
                        <div>${comment.user.username}</div>
                        <div>${comment.creationDate}</div>
                        <#if user.id = comment.user.id>

                            <div>
                                <label for="comment_text">Текст:</label><input id="comment_text"
                                                                               value="${comment.text}">
                                <button onclick="update_comment(this, ${comment.id})">
                                    Сохранить изменения
                                </button>
                            </div>
                            <div>
                                <button onclick="delete_comment(this,${comment.id})">
                                    Удалить комментарий
                                </button>
                            </div>

                        <#else>
                            <div>${comment.text}</div>
                        </#if>
                    </div>
                </#list>
            </#if>
        </div>

        <div>
            <label for="own_comment_text">Текст комментария:</label>
            <input type="text" id="own_comment_text">
            <button onclick="send_comment()">Сохранить комментарий</button>
        </div>

        <#if post.comments??>
            <#if post.comments?size = 10>
                <button id="load_comments"
                        onclick="load_comments(${user.id})">Подгрузить комментарии
                </button>
            </#if>
        </#if>

        <#else>
            <label>Пост с таким id не найден</label>
        </#if>

    </div>

</div>
</body>
</html>