package com.alianza.clients.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.alianza.clients.model.dto.ClientRequestDTO;
import com.alianza.clients.model.dto.ClientResponseDTO;
import com.alianza.clients.model.entity.Client;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClientMapper {
    private final ModelMapper modelMapper;

    public Client convertToEntity(ClientRequestDTO clientRequestDTO){
        return modelMapper.map(clientRequestDTO, Client.class);
    }

    public ClientResponseDTO convertToDTO(Client client){
        return modelMapper.map(client, ClientResponseDTO.class);
    }

    public List<ClientResponseDTO> convertToListDTO(List<Client> clients){
        return clients.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
