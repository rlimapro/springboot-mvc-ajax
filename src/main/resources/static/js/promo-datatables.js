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
            $('#modal-form').modal('show');
        }
    })

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