$(document).ready(function() {
    moment.locale('pt-br');
    $("#table-server").DataTable({
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
                        }
                    },
                    {
                        text: 'Excluir',
                        attr: {
                            id: 'btn-excluir',
                            type: 'button'
                        }
                    }
                ]
            }
        }
    });

    $("#table-server tbody").on('click', 'tr', function() {
        if($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            $("tr.selected").removeClass("selected");
            $(this).addClass("selected");
        }
    });

    $("#btn-editar").on('click', function() {
        alert('Botão para editar está funcionando!');
    });

    $("#btn-excluir").on('click', function() {
        alert('Botão para excluir está funcionando!');
    });
});