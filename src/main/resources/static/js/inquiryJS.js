$(document).ready(function () {
    $(document).on("click", ".productInquiry", function() {
        $("#modalTargetProductId").val($("#productId").val());

    })
})