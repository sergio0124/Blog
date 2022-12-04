function search() {
    let search = document.getElementById("search_input").value;
    if (search.replace(/\s/g, '').length > 0) {
        document.location.href = "/search?searchRequest=" + search;
    }
}