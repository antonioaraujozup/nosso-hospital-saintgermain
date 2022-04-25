package br.com.zup.edu.saintgermain.leito;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@RestController
public class ReservarLeitoController {

    private final LeitoRepository repository;

    public ReservarLeitoController(LeitoRepository repository) {
        this.repository = repository;
    }

    @PatchMapping("/leitos/{id}")
    @Transactional
    public ResponseEntity<?> reservar(@PathVariable Long id) {
        Leito leito = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leito não encontrado"));

        leito.reservar();

        repository.save(leito);

        return ResponseEntity.noContent().build();
    }
}
