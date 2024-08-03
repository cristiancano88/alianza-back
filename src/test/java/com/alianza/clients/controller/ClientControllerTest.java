package com.alianza.clients.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.alianza.clients.service.ClientService;
import com.alianza.clients.model.dto.ClientRequestDTO;
import com.alianza.clients.model.dto.ClientResponseDTO;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @Test
    void testGetAllClients() {
        List<ClientResponseDTO> clients = Arrays.asList(
                new ClientResponseDTO(1L, "John Doe", "123456", "john@example.com", "123456", LocalDate.now()),
                new ClientResponseDTO(2L, "Jane Smith", "789012", "jane@example.com", "123456", LocalDate.now()));
        when(clientService.getAllClients()).thenReturn(clients);

        ResponseEntity<List<ClientResponseDTO>> response = clientController.getAllClients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clients, response.getBody());
        verify(clientService, times(1)).getAllClients();
    }

    @Test
    void testGetAllClientsEmptyList() {
        when(clientService.getAllClients()).thenReturn(List.of());

        ResponseEntity<List<ClientResponseDTO>> response = clientController.getAllClients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(clientService, times(1)).getAllClients();
    }

    @Test
    void testCreateClient() {
        ClientRequestDTO newClient = new ClientRequestDTO("New User", "new@example.com", "987654");
        ClientResponseDTO createdClient = new ClientResponseDTO(1L, "John Doe", "123456", "john@example.com", "123456",
                LocalDate.now());
        when(clientService.saveClient(newClient)).thenReturn(createdClient);

        ResponseEntity<ClientResponseDTO> response = clientController.saveClient(newClient);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdClient, response.getBody());
        verify(clientService, times(1)).saveClient(newClient);
    }

    @Test
    void testSearhClientsByText() {
        List<ClientResponseDTO> clients = Arrays.asList(
                new ClientResponseDTO(1L, "John Doe", "123456", "john@example.com", "123456", LocalDate.now()),
                new ClientResponseDTO(2L, "Jane Smith", "789012", "jane@example.com", "123456", LocalDate.now()));
        String searchTerm = "Carl";
        when(clientService.searchClients(searchTerm)).thenReturn(clients);

        ResponseEntity<List<ClientResponseDTO>> response = clientController.searchClients(searchTerm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clients, response.getBody());
        verify(clientService, times(1)).searchClients(searchTerm);
    }

}
