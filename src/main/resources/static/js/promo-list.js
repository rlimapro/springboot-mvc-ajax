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
    var site = $("#autocomplete-input").val();
    $.ajax({
        method: "GET",
        url: "/promocao/list/ajax",
        data: {
            page: pageNumber,
            site: site
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

// autocomplete
$("#autocomplete-input").autocomplete({
    source: function(request, response) {
        $.ajax({
            method: "GET",
            url: "/promocao/site",
            data: {
                term: request.term
            },
            success: function(result) {
                response(result);
            }
        });
    }
});

// filtered search
$("#autocomplete-submit").on("click", function() {
    var site = $("#autocomplete-input").val();
    $.ajax({
        method: "GET",
        url: "/promocao/site/list",
        data: {
            site: site
        },
        beforeSend: function() {
            pageNumber = 0;
            $("#fim-btn").hide();
            $(".row").fadeOut(400, function() {
                $(this).empty();
            });
        },
        success: function(response) {
            $(".row").fadeIn(250, function() {
                $(this).append(response);
            });
        },
        error: function(xhr) {
            alert("Ops, algo deu errado: " + xhr.status + ", " + xhr.statusText);
        }
    })
});