package com.ufape.poo.negocio;

import com.ufape.poo.dados.IRepositorioItem;

public class AcervoService {

    private IRepositorioItem repositorio;

    public AcervoService(IRepositorioItem repositorio) {
        this.repositorio = repositorio;
    }

    public void cadastrarItem(Item item) throws ItemJaExisteException {
        if (repositorio.existe(item.getCodigo())) {
            throw new ItemJaExisteException();
        }
        repositorio.salvar(item);
    }

    public Item buscarItem(String codigo) throws ItemNaoEncontradoException {
        Item item = repositorio.buscar(codigo);
        if (item == null) {
            throw new ItemNaoEncontradoException();
        }
        return item;
    }

    public void removerItem(String codigo) throws ItemNaoEncontradoException, OperacaoNaoPermitidaException {
        Item item = buscarItem(codigo);
        if (item.getStatus() == StatusItem.EMPRESTADO) {
            throw new OperacaoNaoPermitidaException();
        }
        repositorio.remover(codigo);
    }
}
