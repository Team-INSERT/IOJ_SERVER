package com.insert.ioj.domain.item.domain.repository;

import com.insert.ioj.domain.item.domain.UserItem;
import com.insert.ioj.domain.item.domain.type.Item;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    List<UserItem> findByUserAndRoomAndUsedIsFalse(User user, Room room);
    Optional<UserItem> findByUserAndItemAndUsedIsFalse(User user, Item item);
}
