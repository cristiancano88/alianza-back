package com.alianza.clients.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.alianza.clients.mapper.ClientMapper;
import com.alianza.clients.model.dto.ClientRequestDTO;
import com.alianza.clients.model.dto.ClientResponseDTO;
import com.alianza.clients.model.entity.Client;
import com.alianza.clients.repository.ClientRepository;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveClient() {
        ClientRequestDTO clientToSave = getClientRequestDTOMock();
        Client savedClient = getClientMock();
        ClientResponseDTO savedClientResponse = getClientResponseDTOMock();

        when(clientMapper.convertToEntity(clientToSave)).thenReturn(savedClient);
        when(clientMapper.convertToDTO(savedClient)).thenReturn(savedClientResponse);
        when(clientRepository.save(new Client())).thenReturn(savedClient);

        ClientResponseDTO result = clientService.saveClient(clientToSave);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(clientRepository, times(1)).save(Mockito.any(Client.class));
    }

    @Test
    void testThrowExceptionWhenUserNameHasOneWordSaveClient() {
        ClientRequestDTO clientToSave = getClientRequestDTOMock();
        Client savedClient = new Client();
        savedClient.setId(1L);
        savedClient.setName("Carlos");
        savedClient.setEmail("cariza@gmail.com");
        savedClient.setPhone("3216549874");

        when(clientMapper.convertToEntity(clientToSave)).thenReturn(savedClient);

        try {
            clientService.saveClient(clientToSave);

            fail("Should throw an exception when the client name has only one word.");
        } catch (IllegalArgumentException e) {
            assertEquals("El nombre debe contener al menos dos palabras", e.getMessage());
        }
    }

    @Test
    void testGetAllClients() {
        List<Client> mockClients = new ArrayList<>();
        mockClients.add(new Client());
        mockClients.add(new Client());
        List<ClientResponseDTO> mockClientsResponse = new ArrayList<>();
        mockClientsResponse.add(getClientResponseDTOMock());
        mockClientsResponse.add(getClientResponseDTOMock());

        when(clientRepository.findAll()).thenReturn(mockClients);
        when(clientMapper.convertToListDTO(mockClients)).thenReturn(mockClientsResponse);

        List<ClientResponseDTO> result = clientService.getAllClients();

        assertEquals(2, result.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testSearchClients() {
        List<Client> mockClients = new ArrayList<>();
        mockClients.add(getClientMock());
        List<ClientResponseDTO> mockClientsResponse = new ArrayList<>();
        mockClientsResponse.add(getClientResponseDTOMock());

        String searchTerm = "Carl";

        when(clientRepository.searchByTerm(searchTerm)).thenReturn(mockClients);
        when(clientMapper.convertToListDTO(mockClients)).thenReturn(mockClientsResponse);

        List<ClientResponseDTO> result = clientService.searchClients(searchTerm);

        assertEquals(1, result.size());
        assertEquals("Carlos Ariza", result.get(0).getName());
        verify(clientRepository, times(1)).searchByTerm(searchTerm);
    }

    private ClientRequestDTO getClientRequestDTOMock() {
        ClientRequestDTO clientToSave = new ClientRequestDTO();
        clientToSave.setName("Carlos Ariza");
        clientToSave.setEmail("cariza@gmail.com");
        clientToSave.setPhone("3216549874");
        return clientToSave;
    }

    private Client getClientMock() {
        Client savedClient = new Client();
        savedClient.setId(1L);
        savedClient.setName("Carlos Ariza");
        savedClient.setEmail("cariza@gmail.com");
        savedClient.setPhone("3216549874");
        return savedClient;
    }

    private ClientResponseDTO getClientResponseDTOMock() {
        ClientResponseDTO savedClientResponse = new ClientResponseDTO();
        savedClientResponse.setId(1L);
        savedClientResponse.setName("Carlos Ariza");
        savedClientResponse.setEmail("cariza@gmail.com");
        savedClientResponse.setPhone("3216549874");
        return savedClientResponse;
    }
}