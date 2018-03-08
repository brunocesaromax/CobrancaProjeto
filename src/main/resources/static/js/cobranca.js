$('#confirmacaoExclusaoModal').on('show.bs.modal', function (event) {

    var button = $(event.relatedTarget); //pega o botao que dispara o evento

    var id = button.data('codigo');
    var descricao = button.data('descricao');

    var modal = $(this);
    var form = modal.find('form');
    var action = form.data('url-base'); //Mudança de action para data 'url-base' para não duplicar o id na chamada do excluir

    if (!strEndsWith(action, '/')) {// se nao terminar com '/' concatena com a mesma
        action += '/';
    }

    form.attr('action', action + id);// Alterando o link com o codigo do titulo

    modal.find('.modal-body span').html('Tem certeza que deseja excluir o título <strong>' + descricao + '<strong>?');

});//Seguindo o modal de exemplo do Bootstrap

function strEndsWith(str, suffix) {
    return str.match(suffix + "$") == suffix;
}

$(function () { // Usando Jquery para aparecer descrição dos botões de excluir e editar
    $('[rel="tooltip"]').tooltip();
});