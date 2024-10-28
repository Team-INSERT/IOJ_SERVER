package com.insert.ioj.domain.item.service;

import com.insert.ioj.domain.entry.domain.Entry;
import com.insert.ioj.domain.item.domain.UserItem;
import com.insert.ioj.domain.item.domain.repository.UserItemRepository;
import com.insert.ioj.domain.item.domain.type.Item;
import com.insert.ioj.domain.room.domain.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@RequiredArgsConstructor
@Service
@Slf4j
public class ItemGiveService {
    private final UserItemRepository userItemRepository;
    private final Map<UUID, ScheduledFuture<?>> itemGiveFutureTasks = new ConcurrentHashMap<>();
    private final TaskScheduler taskScheduler;

    @Value("${item.delay}")
    private Integer delay;

    @Transactional
    public void execute(Room room, List<Entry> entry) {
        log.info("into");
        ScheduledFuture<?> task = taskScheduler.scheduleWithFixedDelay(() -> {
            log.info("방 : {}", room.getId());
            if (room.getEndTime().isBefore(LocalDateTime.now())) {
                stopItemGive(room.getId());
            } else {
                giveItem(room, entry);
            }
        }, delay);
        itemGiveFutureTasks.put(room.getId(), task);
    }

    private void giveItem(Room room, List<Entry> entryList) {
        for (Entry entry : entryList) {
            userItemRepository.save(
                new UserItem(randomItem(), room, entry.getUser())
            );
        }
    }

    private void stopItemGive(UUID roomId) {
        ScheduledFuture<?> task = itemGiveFutureTasks.remove(roomId);
        if (task != null) {
            task.cancel(false);
        }
    }

    private Item randomItem() {
        Random random = new Random();
        Item[] items = Item.values();
        return items[random.nextInt(items.length)];
    }
}