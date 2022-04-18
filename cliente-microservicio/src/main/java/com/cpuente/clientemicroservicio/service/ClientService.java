package com.cpuente.clientemicroservicio.service;

import com.cpuente.clientemicroservicio.entity.Client;

import java.util.List;

public interface ClientService {

    public List<Client> listAllClient();
    public Client getClient(Long id);

    public Client createClient(Client client);
    public Client updateClient(Client client);
    public Client deleteClient(Long id);
    public List<Client> findByEstado(String estado);


}
