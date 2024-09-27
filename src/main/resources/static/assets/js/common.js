if(localStorage.getItem("key") == null) localStorage.setItem("key","");
if(localStorage.getItem("value") == null) localStorage.setItem("value","");

if($("#page-num").attr("pageval") == "1") {
    $("#before-page").attr("disabled","true");
}

$(".menu-link").click(function (){
    localStorage.clear();
})

if(localStorage.getItem("key") == ""){
    $("#fuzzy-input").attr("value",localStorage.getItem("value"));
}
else{
    $("#fuzzy-input").attr("value",localStorage.getItem("key") + "=" + localStorage.getItem("value"));
}

$("#next-page").click(function (){
    let arr = window.location.href.split("pageNum=");
    let hr = parseInt(arr[1][0]);
    arr[1] = deleteNum(arr[1]);
    hr++;

    window.location.href = arr[0] + "pageNum=" + hr + arr[1];
});
$("#before-page").click(function (){
    let arr = window.location.href.split("pageNum=");
    let hr = parseInt(arr[1][0]);
    arr[1] = deleteNum(arr[1]);
    hr--;

    window.location.href = arr[0] + "pageNum=" + hr + arr[1];
})


function deleteNum(str){
    let lg = 0;
    for (let i = 0; i < str.length; i++) {
        if(parseInt(str[i])) lg++;
        else break;
    }
    str = str.substring(lg,str.length);
    return str;
}
function filterFunction(baseUrl){
    let minfilterList = [];
    let maxfilterList = [];
    let nameList = [];
    $(".min").each(function (key,value) {
        minfilterList[key] = $(value).val();
    })
    $(".max").each(function (key,value) {
        maxfilterList[key] = $(value).val();
    })
    $(".filter-label").each(function (key,value) {
        nameList[key] = $(value).text();
    })
    let url = baseUrl;
    for (let i = 0; i < nameList.length; i++) {
        if(minfilterList[i] == "" && maxfilterList[i] == ""){
            continue;
        }
        url = url + "&name=" + nameList[i];
        url = url + "&min=" + (minfilterList[i] != "" ? minfilterList[i]:"?");
        url = url + "&max=" + (maxfilterList[i] != "" ? maxfilterList[i]:"?");
    }

    window.location.href = url;


}






