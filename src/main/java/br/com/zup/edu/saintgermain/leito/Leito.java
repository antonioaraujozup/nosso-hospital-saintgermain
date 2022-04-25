package br.com.zup.edu.saintgermain.leito;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@OptimisticLocking(type = OptimisticLockType.ALL)
@DynamicUpdate
public class Leito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOcupacao status;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @Column(nullable = false)
    private LocalDateTime atualizadoEm;

    public Leito(String titulo) {
        this.titulo = titulo;
        this.status = StatusOcupacao.LIVRE;
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }

    /**
     * @deprecated construtor de uso exclusivo para o Hibernate
     */
    @Deprecated
    public Leito() {
    }

    public Long getId() {
        return id;
    }

    public boolean isDisponivel() {
        return this.status == StatusOcupacao.LIVRE;
    }

    public void reservar() {
        if (!this.isDisponivel()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "O leito não está livre");
        }

        this.status = StatusOcupacao.OCUPADO;
        this.atualizadoEm = LocalDateTime.now();
    }
}
