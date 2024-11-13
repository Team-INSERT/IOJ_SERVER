package com.insert.ioj.domain.room.domain.repository;

import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.domain.type.RoomStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.insert.ioj.domain.room.domain.QRoom.room;

@Repository
@RequiredArgsConstructor
public class CustomRoomRepositoryImpl implements CustomRoomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Room> findAllDesc() {
        return queryFactory
            .selectFrom(room)
            .where(room.status.eq(RoomStatus.RECRUITING))
            .orderBy(room.createdAt.desc())
            .fetch();
    }
}
