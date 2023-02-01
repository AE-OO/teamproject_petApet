
// $('div.sc-twmo0k-0.AeXFW > a').click(function(){
//     var tab_id = $(this).attr('data-tab');
//
//     if($(this).hasClass("hOBEAG") === true){
//         $(this).removeClass('hOBEAG');
//         $(this).addClass('jjwJpl');
//       }
//     $(this).removeClass('jjwJpl');
//     $(this).parents().eq(3).children().find('.sc-1pgb0b5-1').removeClass('hJRjhk');
//
//     $(this).addClass('hOBEAG');
//     $(this).parents().eq(3).children().find("#"+tab_id).addClass('hJRjhk');
//   })

$('div.sc-twmo0k-0.AeXFW a').click(function(){
    var tab_id = $(this).attr('data-tab');

    $('div.sc-twmo0k-0.AeXFW a').removeClass('current-tab');
    $('.sc-1pgb0b5-1').removeClass('hJRjhk');

    $(this).addClass('current-tab');
    $("#"+tab_id).addClass('hJRjhk');
})

$('div.sc-1bmxn32-1 button').click(function(){
    var btn_id = $(this).attr('data-btn');

    $('div.sc-1bmxn32-1 button').removeClass('current');
    $('.sc-4clfj5-0.hTYEBQ').removeClass('current');

    $(this).addClass('current');
    $("#"+btn_id).addClass('current');
})



$('ul.sc-nly1np-0.belqZG > li').on("click", function(e){
    if($('ul.sc-nly1np-0.belqZG > li').hasClass('ghiGVh')){
        $(this).removeClass('ghiGVh');
        // $(this).addClass('dSWQUC');
    }else{
        // $(this).addClass('dSWQUC');
        $(this).addClass('ghiGVh');
        // $(this).removeClass('dSWQUC');
    }
})

// $('ul.sc-nly1np-0.belqZG > li').click(function(e){
//     if($(this).hasClass('ghiGVh')){
//         $(this).removeClass('ghiGVh');
//         // $(this).addClass('dSWQUC');
//     }else{
//         // $(this).addClass('dSWQUC');
//         $(this).addClass('ghiGVh');
//         // $(this).removeClass('dSWQUC');
//     }
// })
$(document).ready(function(){

    $('.sc-12647rk-0.kRuDMc').click(function(){
        // $(this).children().find('.yiRAk').css('display', 'block');
        $(this).children().find('.sc-to1spv-0.yiRAk').add().attr({style:"display:flex;"});

        // $(this).children().find('.sc-to1spv-0.yiRAk').attr({display: 'flex'});

    });

    $('.cancle-faq').click(function(){
        console.log("닫기 버튼 클릭!");
        $(this).parents().eq(2).children().find('.sc-to1spv-0.yiRAk').remove().attr();
        // $(this).parents().eq(2).children().find('.yiRAk').css('display','none');
    });
})

// $(this).children().find('.sc-to1spv-0.yiRAk').attr({display: 'flex'});

let uploadButton = document.getElementById("upload-button");
let chosenImage = document.getElementById("chosen-image");
let fileName = document.getElementById("file-name");
let container = document.querySelector(".container");
let error = document.getElementById("error");
let imageDisplay = document.getElementById("image-display");

const fileHandler = (file, name, type) => {
    if (type.split("/")[0] !== "image") {
        //File Type Error
        error.innerText = "Please upload an image file";
        return false;
    }
    error.innerText = "";
    let reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = () => {
        //image and file name
        let imageContainer = document.createElement("figure");
        let img = document.createElement("img");
        img.src = reader.result;
        imageContainer.appendChild(img);
        imageContainer.innerHTML += `<figcaption>${name}</figcaption>`;
        imageDisplay.appendChild(imageContainer);
    };
};

//Upload Button
uploadButton.addEventListener("change", () => {
    imageDisplay.innerHTML = "";
    Array.from(uploadButton.files).forEach((file) => {
        fileHandler(file, file.name, file.type);
    });
});

container.addEventListener(
    "dragenter",
    (e) => {
        e.preventDefault();
        e.stopPropagation();
        container.classList.add("active");
    },
    false
);

container.addEventListener(
    "dragleave",
    (e) => {
        e.preventDefault();
        e.stopPropagation();
        container.classList.remove("active");
    },
    false
);

container.addEventListener(
    "dragover",
    (e) => {
        e.preventDefault();
        e.stopPropagation();
        container.classList.add("active");
    },
    false
);

container.addEventListener(
    "drop",
    (e) => {
        e.preventDefault();
        e.stopPropagation();
        container.classList.remove("active");
        let draggedData = e.dataTransfer;
        let files = draggedData.files;
        imageDisplay.innerHTML = "";
        Array.from(files).forEach((file) => {
            fileHandler(file, file.name, file.type);
        });
    },
    false
);

window.onload = () => {
    error.innerText = "";
};

const inquiryBox = $(".sc-r1vows-1.hQgEoq > div")
const inquiryBlank = $(".bXaaqE");
const inquiryMy = $(".myInquiry");
const inquiryMyBtn = $("div.myInquiry > button.mySubmitInQuiry");

if ($(inquiryBox).hasClass(inquiryMy)){

    $(inquiryBlank).css('display','block');
}else{
    $(inquiryBlank).css('display','none');
}

$(inquiryMyBtn).click(function(){
    $(this).parent().remove();
    // if(confirm("문의내용을 삭제하시겠습니까?")== true) {
    //     $(this).parent().remove();
    // }
})

$(inquiryMy).click(function(){
    $(this).children().find(inquiryMyBtn).text('>');
    // if(confirm("문의내용을 삭제하시겠습니까?")== true) {
    //     $(this).parent().remove();
    // }
})

const setValue = (target) => {
    let val = $('#inputGroupSelect01').val();
    $('input[name=carType]').attr('value',val);

}

function fn_delete(){
    confirm("삭제하시겠습니까")
}

const putValue = (target) => {
    let value = $('#companyId').val();
    $('input[name=companyId]').attr('value',value);}