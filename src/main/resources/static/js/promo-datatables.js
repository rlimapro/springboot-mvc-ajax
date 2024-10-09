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

    $("#table-server thead").on('click', 'tr', function() {
        table.buttons().disable();
    });

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

    $('#btn-editar').on('click', function() {
        var id = table.row(table.$('tr.selected')).data().id;
        alert('id da linha clicada: ' + id);
    })
});