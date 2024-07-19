// function to capture the meta tags
$("#linkPromocao").on('change', function() {
    var url = $(this).val();

    if(url.length > 7) {
        $.ajax({
            method: "POST",
            url: "/meta/info?url=" + url,
            cache: false,
            success: function(data) {
                console.log(data);
                $("#titulo").val(data.title);
                $("#site").text(data.site.replace("@", "")); /* removing the '@' from twitter:site */
                $("#linkImagem").attr("src", data.image)
            },
            statusCode: {
                404: function() {
                    $("#alert").addClass("alert alert-danger").text("Não foi possível recuperar informações a partir desta URL.");
                }
            }
        })
    }
})