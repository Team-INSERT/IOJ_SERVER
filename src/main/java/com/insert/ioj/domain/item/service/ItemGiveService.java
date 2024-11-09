package com.insert.ioj.domain.item.service;

import com.insert.ioj.domain.entry.domain.Entry;
import com.insert.ioj.domain.item.domain.UserItem;
import com.insert.ioj.domain.item.domain.repository.UserItemRepository;
import com.insert.ioj.domain.item.domain.type.Item;
import com.insert.ioj.domain.item.presentation.dto.res.GiveItemResponse;
import com.insert.ioj.domain.room.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
public class ItemGiveService {
    private final UserItemRepository userItemRepository;
    private final Map<UUID, ScheduledFuture<?>> itemGiveFutureTasks = new ConcurrentHashMap<>();
    private final TaskScheduler taskScheduler;
    private final SimpMessagingTemplate messagingTemplate;

    @Value("${item.delay}")
    private Integer delay;

    @Transactional
    public void execute(Room room, List<Entry> entry) {
        ScheduledFuture<?> task = taskScheduler.scheduleWithFixedDelay(() -> {
            if (room.getEndTime().isBefore(LocalDateTime.now())) {
                stopItemGive(room.getId());
            } else {
                giveItem(room, entry);
                messagingTemplate.convertAndSend("/topic/room/" + room.getId(), new GiveItemResponse());
            }
        }, delay);
        itemGiveFutureTasks.put(room.getId(), task);
    }

    private void giveItem(Room room, List<Entry> entryList) {
        for (Entry entry : entryList) {
            Item item = randomItem();
            userItemRepository.save(
                new UserItem(item, room, entry.getUser())
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
