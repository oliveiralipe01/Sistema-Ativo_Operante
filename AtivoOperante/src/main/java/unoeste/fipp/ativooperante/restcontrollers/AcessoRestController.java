package unoeste.fipp.ativooperante.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unoeste.fipp.ativooperante.entities.Usuario;
import unoeste.fipp.ativooperante.security.JWTTokenProvider;
import unoeste.fipp.ativooperante.services.UsuarioService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/acesso")
public class AcessoRestController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Usuario user) {

        Usuario usuario = usuarioService.login(user.getEmail(), user.getSenha());

        if(usuario != null) {

            String token = JWTTokenProvider.createToken(usuario.getEmail(), String.valueOf(usuario.getNivel()));

            Map<String, Object> resposta = new HashMap<>();

            resposta.put("token", token);
            resposta.put("nivel", usuario.getNivel());
            resposta.put("email", usuario.getEmail());

            return resposta;
        }

        return null;
    }
}