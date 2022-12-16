function increase_page(count) {
    let num = Number(document
        .getElementById("page_num").innerText);
    let url_string = window.location.href;
    let url = new URL(url_string);
    let page_val = url.searchParams.get("page");
    if (page_val != null) {
        url.searchParams.set("page", "" + (num + count));
    } else {
        url.searchParams.append("page", "" + (num + count));
    }
    window.location.href = url.href;
}