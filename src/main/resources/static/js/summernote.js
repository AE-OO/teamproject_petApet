uploadSummernoteImageFile = (file, el) => {
    data = new FormData();
    data.append("file", file);
    $.ajax({
        data: data,
        type: "POST",
        url: "/product/image",
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (data) {
            console.log(data)
            $(el).summernote('editor.insertImage', data.url);
        }
    });
}

$(document).ready(function(){
    $('#summernoteContent').summernote({
        tabsize: 2,
        fontSizes: ['8', '9', '10', '11', '12', '14', '18', '24', '36'],
        toolbar: [
            ['font', ['fontname', 'fontsize', 'fontsizeunit']],
            ['fontstyle', ['bold', 'italic', 'underline', 'strikethrough', 'forecolor', 'backcolor', 'superscript', 'subscript', 'clear']],
            ['style', ['style']],
            ['paragraph', ['paragraph', 'height', 'ul', 'ol']],
            ['insert', ['table', 'hr', 'link', 'picture', 'video']],
            ['codeview'],
        ],
        minHeight: 500,
        maxHeight: null,
        focus: true,
        resize: false,
        callbacks: {
            onImageUpload: function (files, editor, welEditable) {
                //             // 파일 업로드(다중업로드를 위해 반복문 사용)
                for (var i = files.length - 1; i >= 0; i--) {
                    uploadSummernoteImageFile(files[i],
                        this);
                }
            }
        }
    })

    $("#submitBtn").click(function () {
        if($("#summernoteTitle").val() ==='' || $("#summernoteTitle").val() === null){
            alert("제목을 작성해주세요");
            return;
        }else if($("#summernoteContent").val() === '' || $("#summernoteContent").val() === null){
            alert("내용을 작성해주세요");
            return;
        } else{
            $("#submitBtn").attr("type", "submit");
        }
    })
})