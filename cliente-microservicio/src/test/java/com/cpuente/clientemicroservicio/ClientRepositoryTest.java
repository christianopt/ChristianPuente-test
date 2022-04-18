package com.cpuente.clientemicroservicio;

import com.cpuente.clientemicroservicio.entity.Client;
import com.cpuente.clientemicroservicio.repository.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void prueba() {
        Client client01 = Client.builder()
                .identificacion("1723089080")
                .nombre("Christian Puente")
                .direccion("El Calzado")
                .edad(33)
                .genero("M")
                .telefono("0995660244")
                .contrasena("asd")
                .estado("Created")
                .build();
        clientRepository.save(client01);

        List<Client> founds = clientRepository.findAll();

        Assertions.assertThat(founds.size()).isEqualTo(1);
    }
}
