$(document).ready(function() {
    moment.locale('pt-br');
    var table = $("#table-server").DataTable({
        processing: true,
        serverSide: true,
        responsive: true,
        lengthMenu: [10, 15, 20, 25],
        ajax: {
            url: "/promocao/datatables/server",
            data: "data"
        },
        columns: [
            {data: 'id'},
            {data: 'titulo'},
            {data: 'site'},
            {data: 'linkPromocao'},
            {data: 'descricao'},
            {data: 'linkImagem'},
            {data: 'preco'},
            {data: 'likes'},
            {data: 'dataCadastro', render:
                    function(dataCadastro) {
                        return moment(dataCadastro).format('LLL');
                    }
            },
            {data: 'categoria.titulo'}
        ],
        layout: {
            topStart: {
                buttons: [
                    {
                        text: 'Editar',
                        attr: {
                            id: 'btn-editar',
                            type: 'button'
                        },
                        enabled: false
                    },
                    {
                        text: 'Excluir',
                        attr: {
                            id: 'btn-excluir',
                            type: 'button'
                        },
                        enabled: false
                    }
                ]
            }
        }
    });

    // desabilitar botões quando clicar no cabeçalho
    $("#table-server thead").on('click', 'tr', function() {
        table.buttons().disable();
    });

    // habilitar e desabilitar botões a partir de seleção de linha da tabela
    $("#table-server tbody").on('click', 'tr', function() {
        if($(this).hasClass("selected")) {
            $(this).removeClass("selected");
            table.buttons().disable();
        } else {
            $("tr.selected").removeClass("selected");
            $(this).addClass("selected");
            table.buttons().enable();
        }
    });

    // coordenar evento de click para botão de editar
    $('#btn-editar').on('click', function() {
        if( isRowSelected() ) {
            var id = getPromoId();
            $.ajax({
                method: "GET",
                url: "/promocao/edit/" + id,
                beforeSend: function() {
                    // remove error messages
                    $("span").closest(".error-span").remove();
                    // remove bootstrap error style borders from fields
                    $(".is-invalid").removeClass("is-invalid");
                    // open edit modal
                    $('#modal-form').modal('show');
                },
                success: function( data ) {
                    $('#edt_id').val(data.id);
                    $('#edt_site').text(data.site);
                    $('#edt_titulo').val(data.titulo);
                    $('#edt_preco').val(data.preco.toLocaleString('pt-BR', {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2
                    }));
                    $('#edt_categoria').val(data.categoria.id);
                    $('#edt_descricao').val(data.descricao);
                    $('#edt_linkImagem').val(data.linkImagem);
                    $('#edt_imagem').attr('src', data.linkImagem);
                },
                error: function() {
                    alert('Ops... Ocorreu um erro, tente novamente mais tarde.');
                }
            })

        }
    });

    // confirmar edição de departamento
    $('#btn-edit-modal').on('click', function() {
        var promocao = {};
        promocao.descricao = $("#edt_descricao").val();
        promocao.preco = $("#edt_preco").val();
        promocao.titulo = $("#edt_titulo").val();
        promocao.categoria = $("#edt_categoria").val();
        promocao.linkImagem = $("#edt_linkImagem").val();
        promocao.id = $("#edt_id").val();

        $.ajax({
            method: "POST",
            url: "/promocao/edit",
            data: promocao,
            beforeSend: function() {
                // remove error messages
                $("span").closest(".error-span").remove();
                // remove bootstrap error style borders from fields
                $(".is-invalid").removeClass("is-invalid");
            },
            success: function() {
                $("#modal-form").modal('hide');
                table.ajax.reload();
            },
            statusCode: {
                422: function(xhr) {
                    console.log("status error: ", xhr.status);
                    var errors = $.parseJSON(xhr.responseText);
                    $.each(errors, function(key, value) {
                       $("#edt_" + key).addClass("is-invalid");
                       $("#error-" + key)
                            .addClass("invalid-feedback")
                            .append("<span class='error-span'>" + value + "</span>");
                    });
                }
            }
        });
    });

    // alterar imagem do componente <img> do modal de edição
    $('#edt_linkImagem').on('change', function() {
        var link = $(this).val();
        $('#edt_imagem').attr('src', link);
    });

    // coordernar evento de click para botão de excluir
    $('#btn-excluir').on('click', function() {
        if( isRowSelected() ) {
            $('#modal-delete').modal('show');
        }
    })

    // excluir item da tabela
    $('#btn-del-modal').on('click', function() {
        var id = getPromoId();
        $.ajax({
            method: "GET",
            url: "/promocao/delete/" + id,
            success: function() {
                $('#modal-delete').modal('hide');
                table.ajax.reload();
            },
            error: function() {
                alert('Ops... Ocorreu um erro, tente novamente mais tarde.')
            }
        })
    })

    function getPromoId() {
        return table.row(table.$('tr.selected')).data().id;
    }

    function isRowSelected() {
        var trow = table.row(table.$('tr.selected'));
        return trow.data() !== undefined;
    }
});