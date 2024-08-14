var pageNumber = 0;

$(document).ready(function() {
    $("#loader-img").hide();
    $("#fim-btn").hide();
});

// infinite scroll
$(window).scroll(function() {
    var scrollTop = $(this).scrollTop();
    var content = $(document).height() - $(window).height();

    //console.log('scrollTop: ', scrollTop, " | content: ", content);

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
        beforeSend: function() {
            $("#loader-img").show();
        },
        success: function(response) {
            //console.log('resposta > ', response);

            if(response.length > 150) {
                $(".row").fadeIn(250, function() {
                    $(this).append(response);
                });
            }
            else {
                $("#fim-btn").show();
                $("#loader-img").removeClass("loader");
            }

        },
        error: function(xhr) {
            alert("Ops, ocorreu um error: " + xhr.status + " - " + xhr.statusText);
        },
        complete: function() {
            $("#loader-img").hide();
        }
    });
}

// likes
$(document).on("click", "button[id*='likes-btn-']", function() {
    var id = $(this).attr("id").split("-")[2];
    console.log("id: ", id);

    $.ajax({
        method: "POST",
        url: "/promocao/like/" + id,
        success: function(response) {
            $("#likes-count-" + id).text(response);
        },
        error: function(xhr) {
            alert("Ops, ocorreu um erro: " + xhr.status + ", " + xhr.statusText);
        }
    });
});