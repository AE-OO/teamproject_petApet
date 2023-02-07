let productId = $('.productTitle').attr('data-id');

$('.productImgModalBtn').click(function () {
    let imgSrc = $(this).children(1).attr('src');
    let $modalImage = $('.modalImage');
    $modalImage.attr('src', imgSrc);
})

function moreProductImgModalBtn(src) {
    let $modalImage = $('.modalImage');
    $modalImage.attr('src', src);
}

$(function setProductType() {
    let val = $('#productType').val();

    if (val !== "") {
        $('select option[value=' + val + ']').attr('selected', true);
    }
})
$(function () {
    $('.carousel-inner > div:nth-child(1)').addClass('active');
});

$('input[name=productImg]').change(function (event) {
    $('.addImg').remove();
    let from = Array.from(event.target.files);
    $.each(from, function (idx, el) {
        const carouselHtml = `<div class="carousel-item addImg" id="addImg${idx}"></div>`;
        $('#thumbnailImg').append(carouselHtml);

        var reader = new FileReader();
        reader.onload = function (event) {
            let aTag = document.createElement('a');
            aTag.setAttribute('class', 'btn btn-link productImgModalBtn');
            aTag.setAttribute('data-bs-toggle', 'modal');
            aTag.setAttribute('data-bs-target', '#productImgModal');

            var img = document.createElement("img");
            img.setAttribute("src", event.target.result);
            img.setAttribute('onclick', 'moreProductImgModalBtn(this.src)');
            img.setAttribute('style', 'max-width: 100%; height: auto; max-height: 30rem')

            aTag.append(img);
            document.querySelector("#addImg" + idx).appendChild(aTag);
        };
        reader.readAsDataURL(el);
    })
})

$('.deleteProductImg').click(function () {
    $(this).parent().parent().remove();
    $('.carousel-inner > div:nth-child(1)').addClass('active');
})

async function updateProduct() {
    let storeFileNameArr = new Array();
    let uploadFileNameArr = new Array();
    let myProductImg = $('.productImgModalBtn .myProductImg').length;
    for (var i = 1; i <= myProductImg; i++) {
        let src = $('.carousel-inner > div:nth-child(' + i + ') a img').attr('src');
        let uploadFileName = $('.carousel-inner > div:nth-child(' + i + ') a img').attr('alt');
        let pos = src.lastIndexOf("/");
        let storeFileName = src.substring(pos + 1);
        storeFileNameArr.push(storeFileName);
        uploadFileNameArr.push(uploadFileName);
    }
    let formData = new FormData(document.getElementById("submitForm"));
    for (var o = 0; o < storeFileNameArr.length; o++) {
        formData.append('storeFileName', storeFileNameArr[o])
        formData.append('uploadFileName', uploadFileNameArr[o])
    }

    fetch('/product/update?productId=' + productId, {
        method: 'POST',
        body: formData,
    })
        .then(response => {
            location.href = response.url
        })
        .then(data => {
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
