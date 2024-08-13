var pageNumber = 0;

// infinite scroll
$(window).scroll(function() {
    var scrollTop = $(this).scrollTop();
    var content = $(document).height() - $(window).height();

    console.log('scrollTop: ', scrollTop, " | content: ", content);

    if(scrollTop >= content) {
        pageNumber++;
        setTimeout(function() {
            loadByScrollBar(pageNumber);
        }, 200);
    }
});

// request new objects with ajax
function loadByScrollBar(pageNumber) {
    $.ajax({
        method: "GET",
        url: "/promocao/list/ajax",
        data: {
            page: pageNumber
        },
        success: function(response) {
            console.log('resposta > ', response);
        }
    })
}