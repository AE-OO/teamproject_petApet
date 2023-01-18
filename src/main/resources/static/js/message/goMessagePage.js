goMessagePage = function (memberId) {
    let _width = '450';
    let _height = '600';
    let _left = Math.ceil((window.screen.width - _width) / 2);
    let _top = Math.ceil((window.screen.height - _height) / 2);
    window.open('/message/' + memberId , '_blank',
        'width=' + _width + ', height=' + _height + ', left=' + _left + ', top=' + _top + ', location=no,resizeable=no,menubar=no,scrollbars=no,status=no');
}