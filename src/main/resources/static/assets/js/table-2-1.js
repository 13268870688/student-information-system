

    $("#fuzzy-search-btn").click(function (){
    let key = $("#fuzzy-input").val().trim().replace(/\s/g,"");
    let ls = key.split("=",2);
    if(ls.length > 1) {
    localStorage.setItem("key", ls[0]);
    localStorage.setItem("value",ls[1]);
    window.location.href = "/student/all?pageNum=1&pageSize=9&key=" + ls[0] + "&value=" + ls[1];
}
    else{
    localStorage.setItem("key", "");
    localStorage.setItem("value",ls[0]);
    window.location.href = "/student/all?pageNum=1&pageSize=9" + "&value=" + ls[0];
}

});

    $("#filter-button").click(
        function (){
            filterFunction("/student/all?pageNum=1&pageSize=9");
        }
    )

