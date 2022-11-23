(function ($) {

    $(window).resize(function() {
        if($(window).width() > 991) {
            $("#dropdownA").attr("disabled",true);
            $("#dropdownMenuDiv").css("right",0);
        }else{
            $("#dropdownA").attr("disabled",false);
            $("#dropdownMenuDiv").css("right",0);

        }
    });

    if($(window).width() > 991) {
        $("#dropdownA").attr("disabled",true);
        $("#dropdownMenuDiv").css("right",0);

    }else{
        $("#dropdownA").attr("disabled",false);
        $("#dropdownMenuDiv").css("right",0);

    }

})(jQuery);

