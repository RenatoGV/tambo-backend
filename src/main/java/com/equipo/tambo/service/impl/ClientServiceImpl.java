package com.equipo.tambo.service.impl;

import com.equipo.tambo.dto.ClientRequest;
import com.equipo.tambo.dto.ClientResponse;
import com.equipo.tambo.entity.Client;
import com.equipo.tambo.repository.ClientRepository;
import com.equipo.tambo.repository.SaleRepository;
import com.equipo.tambo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final SaleRepository saleRepository;

    @Override
    public List<ClientResponse> listClients() {
        return clientRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponse getById(Long id) {
        return toResponse(getClient(id));
    }

    private Client getClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el cliente con ID " + id
                ));
    }

    @Override
    @Transactional
    public ClientResponse createClient(ClientRequest request) {
        if (clientRepository.existsByDni(request.dni())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI " + request.dni() + " ya está registrado.");
        }

        Client client = new Client();
        client.setName(request.name());
        client.setLastname(request.lastname());
        client.setEmail(request.email());
        client.setAddress(request.address());
        client.setDni(request.dni());

        return toResponse(clientRepository.save(client));
    }

    @Override
    @Transactional
    public ClientResponse updateClient(Long id, ClientRequest request) {
        Client client = getClient(id);

        if (clientRepository.existsByDniAndIdNot(request.dni(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI " + request.dni() + " ya pertenece a otro cliente.");
        }

        client.setName(request.name());
        client.setLastname(request.lastname());
        client.setEmail(request.email());
        client.setAddress(request.address());
        client.setDni(request.dni());

        return toResponse(clientRepository.save(client));
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        Client client = getClient(id);

        if(saleRepository.existsByClientId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el cliente porque tiene ventas asociadas"
            );
        }

        clientRepository.delete(client);
    }

    private ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getLastname(),
                client.getEmail(),
                client.getAddress(),
                client.getDni()
        );
    }
}