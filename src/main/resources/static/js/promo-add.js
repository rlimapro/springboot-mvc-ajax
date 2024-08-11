// submit from form to controller
$("#form-add-promo").submit(function(event) {
    // block default behavior from submit input
    event.preventDefault();

    var promocao = {};

    promocao.linkPromocao = $("#linkPromocao").val();
    promocao.descricao = $("#descricao").val();
    promocao.preco = $("#preco").val();
    promocao.titulo = $("#titulo").val();
    promocao.categoria = $("#categoria").val();
    promocao.linkImagem = $("#linkImagem").attr("src");
    promocao.site = $("#site").text();

    console.log("promocao > ", promocao);

    $.ajax({
        method: "POST",
        url: "/promocao/save",
        data: promocao,
        beforeSend: function() {
            // remove error messages
            $("span").closest(".error-span").remove();

            // remove bootstrap error style borders from fields
            $("#preco").removeClass("is-invalid");
            $("#categoria").removeClass("is-invalid");
            $("#linkPromocao").removeClass("is-invalid");
            $("#titulo").removeClass("is-invalid");

            // enable loading animation while backend works
            $("#form-add-promo").hide();
            $("#loader-form").addClass("loader").show();
        },
        success: function() {
            $("#form-add-promo").each(function() {
                this.reset(); // clears all data input
            })
            $("#linkImagem").attr("src", "/images/promo-dark.png");
            $("#site").text("");
            $("#alert")
                .removeClass("alert alert-danger")
                .addClass("alert alert-success")
                .text("Ok! Promoção cadastrada com sucesso.");
        },
        statusCode: {
            422: function(xhr) {
                console.log("status error: ", xhr.status);
                var errors = $.parseJSON(xhr.responseText);
                $.each(errors, function(key, value) {
                   $("#" + key).addClass("is-invalid");
                   $("#error-" + key)
                        .addClass("invalid-feedback")
                        .append("<span class='error-span'>" + value + "</span>");
                });
            }
        },
        error: function(xhr) {
            console.log("> error: ", xhr.responseText);
            $("#alert").addClass("alert alert-danger").text("Não foi possível salvar esta promoção.");
        },
        complete: function() {
            $("#loader-form").fadeOut(800, function() {
                $("#form-add-promo").fadeIn(250);
                $("#loader-form").removeClass("loader");
            });
        }
    });
});

// function to capture the meta tags
$("#linkPromocao").on('change', function() {
    var url = $(this).val();

    if(url.length > 7) {
        $.ajax({
            method: "POST",
            url: "/meta/info?url=" + url,
            cache: false,
            beforeSend: function() {
                $("#alert").removeClass("alert alert-danger alert-success").text("");
                $("#titulo").val("");
                $("#site").text("");
                $("#linkImagem").attr("src", "");
                $("#loader-img").addClass("loader");
            },
            success: function(data) {
                console.log(data);
                $("#titulo").val(data.title);
                $("#site").text(data.site.replace("@", "")); /* removing the '@' from twitter:site */
                $("#linkImagem").attr("src", data.image);
            },
            statusCode: {
                404: function() {
                    $("#alert").addClass("alert alert-danger").text("Não foi possível recuperar informações a partir desta URL.");
                    $("#linkImagem").attr("src", "/images/promo-dark.png");
                }
            },
            error: function() {
                $("#alert").addClass("alert alert-danger").text("Ops... Algo deu errado, tente novamente mais tarde.");
                $("#linkImagem").attr("src", "/images/promo-dark.png");
            },
            complete: function() {
                $("#loader-img").removeClass("loader");
            }
        });
    }
});