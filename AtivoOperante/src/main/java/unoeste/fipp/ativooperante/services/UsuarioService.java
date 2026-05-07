package unoeste.fipp.ativooperante.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unoeste.fipp.ativooperante.entities.Usuario;
import unoeste.fipp.ativooperante.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario cadastrar(Usuario usuario){
        usuario.setNivel(2);
        return usuarioRepository.save(usuario);
    }

    public Usuario login(String email, String senha){
        return usuarioRepository.findByEmailAndSenha(email,senha);
    }

    public Usuario buscarporEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

}
