package com.ufape.poo.negocio;

public class ControlerBiblioteca {

    private UsuarioService usuarioService;
    private static ControlerBiblioteca instance;

    private ControlerBiblioteca() {
        this.usuarioService = new UsuarioService(); 
    }

    public static ControlerBiblioteca getInstance() {
        if (instance == null) {
            instance = new ControlerBiblioteca();
        }
        return instance;
    }

    public void cadastrarUsuario(Usuario u) throws UsuarioJaExisteException {
        usuarioService.cadastrar(u);
    }

    public Usuario buscarUsuario(String matricula) throws UsuarioNaoEncontradoException {
        return usuarioService.buscar(matricula);
    }
    
    public void bloquearUsuario(String matricula) throws UsuarioNaoEncontradoException, OperacaoNaoPermitidaException {
        usuarioService.bloquear(matricula);
    }
}
