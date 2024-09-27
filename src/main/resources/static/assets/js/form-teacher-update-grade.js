let sno = localStorage.getItem("sno");
let cno = parseInt(localStorage.getItem("courseId"));
$.ajax({
    url:"/student/get_grade_special",
    method:"get",
    data:{
        sno:sno,
        cno:cno
    },
    success:function (res){
        if(res.status == 200){
            res = res.data;
            $("#name").val(res.sname);
            $("#cname").val(res.cname);
            $("#html5-number-input").val(res.testGrade);
            $("#html5-number-input1").val(res.generalGrade);
            $("#html5-number-input2").val(res.finalGrade);
        }
        else{
            alert(res.inf);
            history.back();
        }
    }
})

$("#student-submit").click(function (){
    let testGrade = $("#html5-number-input").val();
    let generalGrade = $("#html5-number-input1").val();
    let finalGrade = $("#html5-number-input2").val();


    $.ajax({
        url:"/student/updateGrade",
        method:"post",
        data:{
            cno:cno,
            sno:sno,
            testGrade:testGrade,
            generalGrade:generalGrade,
            finalGrade:finalGrade
        },
        success:function (res){
            alert(res.inf);
            if(res.status == 200){
                window.location.href = "/table-teacher-selectStudent.html";
            }
        }
    })
})