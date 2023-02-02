//게시글 페이지네이션 태그
showPage = function (data) {
    let str = '';
    if (data.totalPages > 0) {
        str += `<div aria-label="Page navigation"><ul class="pagination justify-content-center">`
        if (data.first) {
            str += `<li id = "prevPage" class = "page-item disabled" >
                            <a class = "page-link pe-none" href = "javascript:">
                            <span aria-hidden = "true"> <i class = "bi bi-chevron-left"></i></span>
                            </a>
                            </li>`
        } else {
            str += `<li id = "prevPage" class = "page-item">
                            <a class = "page-link" href = "javascript:">
                            <span aria-hidden = "true"><i class = "bi bi-chevron-left"></i></span>
                            </a>
                            </li>`
        }
        if (data.totalPages <= 5) {
            for (var i = 1; i <= data.totalPages; i++) {
                str += `<li class = "page-item`
                if (data.number + 1 === i) {
                    str += ` active`
                }
                str += `">
                <a class="page-link selectPage" href="javascript:"><span>${i}</a>
                </li>`
            }
        }
        if (data.totalPages > 5 && data.totalPages <= 10) {
            if (data.number + 1 <= 5) {
                for (var i = 1; i <= 5; i++) {
                    str += `<li class="page-item`
                    if (data.number + 1 === i) {
                        str += ` active`
                    }
                    str += `">
                <a class="page-link selectPage" href="javascript:"><span>${i}</a>
                </li>`
                }
                str += `<li class="page-item disabled"><a class="page-link">...</a></li>
                <li class="page-item"><a class="page-link selectPage" href="javascript:">${data.totalPages}</a></li>`

            }
            if (data.number + 1 > 5) {
                str += `<li class="page-item"><a class="page-link selectPage" href="javascript:">1</a></li>
                <li class="page-item disabled"><a class="page-link">...</a></li>`
                for (var i = 6; i <= data.totalPages; i++) {
                    str += `<li class="page-item`
                    if (data.number + 1 === i) {
                        str += ` active`
                    }
                    str += `">
                <a class="page-link selectPage" href="javascript:"><span>${i}</a>
                </li>`
                }
            }
        }
        if (data.totalPages > 10) {
            if (data.number + 1 <= 5) {
                for (var i = 1; i <= 5; i++) {
                    str += `<li class="page-item`
                    if (data.number + 1 === i) {
                        str += ` active`
                    }
                    str += `">
                <a class="page-link selectPage" href="javascript:"><span>${i}</a>
                </li>`
                }
                str += `<li class="page-item disabled"><a class="page-link">...</a></li>
                <li class="page-item"><a class="page-link selectPage" href="javascript:">${data.totalPages}</a></li>`
            }
            if (data.number + 1 > 5 && data.number + 1 < data.totalPages - 4) {
                str += `<li class="page-item"><a class="page-link selectPage" href="javascript:">1</a></li>
                <li class="page-item disabled"><a class="page-link">...</a></li>`
                for (var i = data.number + 1 - 2; i <= data.number + 1 + 2; i++) {
                    str += `<li class="page-item`
                    if (data.number + 1 === i) {
                        str += ` active`
                    }
                    str += `">
                <a class="page-link selectPage" href="javascript:"><span>${i}</a>
                </li>`
                }
                str += `<li class="page-item disabled"><a class="page-link">...</a></li>
                <li class="page-item"><a class="page-link selectPage" href="javascript:">${data.totalPages}</a></li>`
            }
            if (data.number + 1 >= data.totalPages - 4) {
                str += `<li class="page-item"><a class="page-link selectPage" href="javascript:">1</a></li>
                <li class="page-item disabled"><a class="page-link">...</a></li>`

                for (var i = data.totalPages - 4; i <= data.totalPages; i++) {
                    str += `<li class="page-item`
                    if (data.number + 1 === i) {
                        str += ` active`
                    }
                    str += `">
                <a class="page-link selectPage" href="javascript:"><span>${i}</a>
                </li>`
                }
            }
        }

        if (data.last) {
            str += `<li id="nextPage" class="page-item disabled">
                <a class="page-link pe-none" href="javascript:">
                <span aria-hidden="true"><i class="bi bi-chevron-right"></i></span>
                </a>
                </li>`
        } else {
            str += `<li id="nextPage" class="page-item">
                <a class="page-link" href="javascript:">
                <span aria-hidden="true"><i class="bi bi-chevron-right"></i></span>
                </a>
                </li>`
        }
        str += `</ul></div>`
    }
    $("#page").html(str);
}