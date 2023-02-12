$(document).ready(function () {
    let val = $('#productType').val();
    if (val !== "") {
        $('select option[value=' + val + ']').attr('selected', true);
    }
})

function setThumbnail(event) {
    let from = Array.from(event.target.files);
    $('#thumbnailImg').empty();
    $.each(from, function (idx, el) {
        const carouselHtml = `<div class="carousel-item" id="addImg${idx}"></div>`;
        $('#thumbnailImg').append(carouselHtml);


        var reader = new FileReader();
        reader.onload = function (event) {
            var img = document.createElement("img");
            img.setAttribute("src", event.target.result);
            img.setAttribute('style', 'max-width: 100%; height: auto; max-height: 30rem')
            document.querySelector("#addImg" + idx).appendChild(img);
        };
        reader.readAsDataURL(el);
        $('.carousel-inner > div:nth-child(1)').addClass('active');
    })
}

$(document).ready(function () {
    $('#productContent').summernote({
        tabsize: 2,
        placeholder: '내용을 작성하세요',
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

});

function uploadSummernoteImageFile(file, el) {
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
            $(el).summernote('editor.insertImage', data.url);
        }
    });
}

$(document).ready(function () {
    $("#discountRate").on("propertychange change paste input", function () {
        let price = fnReplace($("#productPrice").val());
        let discountRate = fnReplace($("#discountRate").val());
        if (discountRate > 99) {
            $('#disRateMessage').text('0-99까지의 값을 입력하세요');
        } else {
            $('#disRateMessage').empty();
        }
        $('#finalPrice').val(Math.ceil(price - (price * (discountRate / 100))));
    })
    $("#productPrice").on("propertychange change paste input", function () {
        let price = fnReplace($("#productPrice").val());
        let discountRate = fnReplace($("#discountRate").val());
        if (price > 9999999 || price < 1000) {
            $('#priceMessage').text('1,000원 이상, 9,999,999원 이하의 금액이여야 합니다');
        } else {
            $('#priceMessage').empty();
        }
        $('#finalPrice').val(Math.ceil(price - (price * (discountRate / 100))));
    })
    $("#productStock").on("propertychange change paste input", function () {
        let productStock = fnReplace($("#productStock").val());
        if (productStock > 9999 || productStock < 100) {
            $('#stockMessage').text('100-9999미만의 값을 입력하세요');
        } else {
            $('#stockMessage').empty();
        }
    })
})

function fnReplace(val) {
    var ret = 0;
    if (typeof val != "undefined" && val != null && val != "") {
        ret = Number(val.replace(/,/gi, ''));
    }
    return ret;
}

$('input[type=file]').change(function (file) {
    let files = file.target.files;
    for (const file of files) {
        let fileType = file.type.split('/')[0];
        if (fileType !== 'image') {
            alert('이미지 파일만 업로드 가능합니다.')
            $('input[type=file]').val("");
        }
    }
})