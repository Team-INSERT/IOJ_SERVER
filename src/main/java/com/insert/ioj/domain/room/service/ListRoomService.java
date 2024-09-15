package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.room.domain.repository.RoomRepository;
import com.insert.ioj.domain.room.presentation.dto.res.ListRoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListRoomService {
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public List<ListRoomResponse> execute() {
        return roomRepository.findAll().stream()
            .map(ListRoomResponse::new)
            .toList();
    }
}
