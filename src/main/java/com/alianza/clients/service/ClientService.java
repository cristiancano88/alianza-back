package com.alianza.clients.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alianza.clients.mapper.ClientMapper;
import com.alianza.clients.model.dto.ClientRequestDTO;
import com.alianza.clients.model.dto.ClientResponseDTO;
import com.alianza.clients.model.entity.Client;
import com.alianza.clients.repository.ClientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    @Transactional
    public ClientResponseDTO saveClient(ClientRequestDTO clientRequestDTO) {
        LOGGER.info("Saving new client: {}", clientRequestDTO);
        Client client = clientMapper.convertToEntity(clientRequestDTO);
        client.setSharedKey(getShareKey(client.getName()));
        client.setDataAdded(LocalDate.now());
        clientRepository.save(client);
        LOGGER.info("Client saved successfully with ID: {}", client.getId());
        return clientMapper.convertToDTO(client);
    }

    private String getShareKey(String name) {
        String[] words = name.split(" ");

        if (words.length < 2) {
            LOGGER.error("El nombre debe contener al menos dos palabras");
            throw new IllegalArgumentException("El nombre debe contener al menos dos palabras");
        }

        String firstLetter = words[0].substring(0, 1);
        String secondWord = words[1];

        return (firstLetter + secondWord).toLowerCase();
    }

    @Transactional(readOnly = true)
    public List<ClientResponseDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        LOGGER.info("Number of clients found: {}", clients.size());
        return clientMapper.convertToListDTO(clients);
    }

    @Transactional(readOnly = true)
    public List<ClientResponseDTO> searchClients(String searchTerm) {
        LOGGER.info("filter criteria: searchTerm: {}", searchTerm);
        List<Client> clients = clientRepository.searchByTerm(searchTerm);
        return clientMapper.convertToListDTO(clients);
    }

    @Transactional(readOnly = true)
    public List<ClientResponseDTO> searchClients(String name, String phone, String email) {
        LOGGER.info("Advance filter criteria: phone: {}", phone);
        LOGGER.info("Advance filter criteria: email: {}", email);
        if (name != null && name.trim().isEmpty()) {
            name = null;
        }
        if (phone != null && phone.trim().isEmpty()) {
            phone = null;
        }
        if (email != null && email.trim().isEmpty()) {
            email = null;
        }
        List<Client> clients = clientRepository.findByFilters(name, phone, email);
        return clientMapper.convertToListDTO(clients);
    }

}
