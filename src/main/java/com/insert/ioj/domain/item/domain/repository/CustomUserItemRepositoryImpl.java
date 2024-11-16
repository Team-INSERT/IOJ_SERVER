package com.insert.ioj.domain.item.domain.repository;

import com.insert.ioj.domain.item.domain.UserItem;
import com.insert.ioj.domain.item.domain.type.Item;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.insert.ioj.domain.item.domain.QUserItem.userItem;

@Repository
@RequiredArgsConstructor
public class CustomUserItemRepositoryImpl implements CustomUserItemRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserItem> findNotUseUserItem(Room room, User user, Item item) {
        return Optional.ofNullable(
            queryFactory
                .selectFrom(userItem)
                .where(userItem.room.eq(room)
                    .and(userItem.user.eq(user)
                    .and(userItem.item.eq(item))
                    .and(userItem.used.isFalse())))
                .fetchFirst());
    }

    @Override
    public Integer countByUseItem(Room room, User user) {
        return queryFactory
            .selectFrom(userItem)
            .where(
                userItem.room.eq(room),
                userItem.user.eq(user),
                userItem.used.isTrue()
            )
            .fetch().size();
    }

    @Override
    public List<UserItem> findAllByNotUseItem(Room room, User user) {
        return queryFactory
            .selectFrom(userItem)
            .where(userItem.user.eq(user),
                userItem.room.eq(room),
                userItem.used.isFalse())
            .orderBy(userItem.createdAt.asc())
            .limit(5)
            .fetch();
    }
}
