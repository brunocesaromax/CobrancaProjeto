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

//Ao carregar a pág
$(function () { // Usando Jquery para aparecer descrição dos botões de excluir e editar
    $('[rel="tooltip"]').tooltip();
    // Utilizando maskmoney do jquery para formatar campo valor no cadastro de titulo de acordo com as mascáras escolhidas
    $('.js-currency').maskMoney({prefix:'R$ ', allowNegative: true, thousands:'.', decimal:',', affixesStay: false});

    $('.js-atualizar-status').on('click',function (event){
        event.preventDefault(); // Parar o comportamento do link referenciado em Pesquisa titulos no botão de receber

        var botaoReceber = $(event.currentTarget);
        var urlReceber = botaoReceber.attr('href'); // pegando url para usar ajax

        console.log('urlReceber',urlReceber);

    });
});