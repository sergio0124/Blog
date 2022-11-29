<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Создание поста</title>
</head>
<body>

<div>
    <button onchange="save_data()">Сохранить</button>

    <label for="title">Название:</label><input id="title">

    <label for="description">Описание:</label>
    <textarea maxlength="250" id="description"></textarea>

    <label for="text">Текст:</label>
    <textarea maxlength="10000" id="text"></textarea>

    <label for="image">Загрузить изображение: </label>
    <input type="image" id="image_load" alt="" onchange="load_image(event)">

    <label>Изображения:</label>
    <div id="image_container"></div>

</div>

<script type="text/javascript" src="create_new_post.js"></script>

</body>
</html>