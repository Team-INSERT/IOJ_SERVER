package com.insert.ioj.domain.item.domain.repository;

import com.insert.ioj.domain.item.domain.UserItem;
import com.insert.ioj.domain.item.domain.type.Item;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface CustomUserItemRepository {
    Optional<UserItem> findNotUseUserItem(Room room, User user, Item item);
    Optional<UserItem> findAttackUserItem(Item item, User user, User targetUser, Room room);
    Integer countByUseItem(Room room, User user);
    List<UserItem> findAllByNotUseItem(Room room, User user);
}
