package com.ufape.poo.negocio;

import com.ufape.poo.dados.IRepositorioEmprestimo;
import java.time.LocalDate;

public class EmprestimoService {

    private IRepositorioEmprestimo repositorio;
    private UsuarioService usuarioService;
    private AcervoService acervoService;

    public EmprestimoService(IRepositorioEmprestimo repositorio, UsuarioService usuarioService, AcervoService acervoService) {
        this.repositorio = repositorio;
        this.usuarioService = usuarioService;
        this.acervoService = acervoService;
    }

    public void realizarEmprestimo(String matricula, String codigoItem) throws UsuarioNaoEncontradoException, ItemNaoEncontradoException, OperacaoNaoPermitidaException {
        Usuario usuario = usuarioService.buscar(matricula);
        Item item = acervoService.buscarItem(codigoItem);

        if (usuario.getStatus() == StatusUsuario.BLOQUEADO) {
            throw new OperacaoNaoPermitidaException();
        }

        if (item.getStatus() != StatusItem.DISPONIVEL) {
            throw new OperacaoNaoPermitidaException();
        }

        Emprestimo emprestimo = new Emprestimo(usuario, item, LocalDate.now());
        item.setStatus(StatusItem.EMPRESTADO);
        
        repositorio.salvar(emprestimo);
    }

    public void devolverItem(String idEmprestimo) throws EmprestimoNaoEncontradoException {
        Emprestimo emprestimo = repositorio.buscar(idEmprestimo);
        if (emprestimo == null) {
            throw new EmprestimoNaoEncontradoException();
        }

        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.getItem().setStatus(StatusItem.DISPONIVEL);
        
        repositorio.atualizar(emprestimo);
    }
}
