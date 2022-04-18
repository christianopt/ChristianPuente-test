package com.cpuente.clientemicroservicio.service;
import com.cpuente.clientemicroservicio.entity.Client;
import com.cpuente.clientemicroservicio.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {


    private final ClientRepository clientRepository;

    @Override
    public List<Client> listAllClient() {

        return clientRepository.findAll();
    }

    @Override
    public List<Client> findByEstado(String estado) {
        return clientRepository.findByEstado(estado);
    }

    @Override
    public Client getClient(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client createClient(Client client) {
        client.setEstado("True");
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Client client) {
        Client clientDB = getClient(client.getPersonaid());
        if(null== clientDB){
            return null;
        }
        clientDB.setNombre(client.getNombre());
        clientDB.setGenero(client.getGenero());
        clientDB.setEdad(client.getEdad());
        clientDB.setIdentificacion(client.getIdentificacion());
        clientDB.setDireccion(client.getDireccion());
        clientDB.setTelefono(client.getTelefono());
        clientDB.setContrasena(client.getContrasena());
        clientDB.setEstado(client.getEstado());
        return clientRepository.save(clientDB);
    }

    @Override
    public Client deleteClient(Long id) {
        Client clientDB = getClient(id);
        if(null== clientDB){
            return null;
        }
        clientDB.setEstado("False");
        return clientRepository.save(clientDB);
    }
}
