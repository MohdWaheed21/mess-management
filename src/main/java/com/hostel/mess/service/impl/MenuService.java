package com.hostel.mess.service.impl;

import com.hostel.mess.dto.request.MenuItemRequest;
import com.hostel.mess.dto.response.MenuItemResponse;
import com.hostel.mess.entity.MenuItem;
import com.hostel.mess.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemResponse addMenuItem(MenuItemRequest req) {
        MenuItem item = MenuItem.builder()
            .dayOfWeek(req.getDayOfWeek())
            .mealType(req.getMealType())
            .itemName(req.getItemName())
            .description(req.getDescription())
            .vegetarian(req.isVegetarian())
            .active(true)
            .build();
        return toResponse(menuItemRepository.save(item));
    }

    public MenuItemResponse updateMenuItem(Long id, MenuItemRequest req) {
        MenuItem item = menuItemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Menu item not found"));
        item.setDayOfWeek(req.getDayOfWeek());
        item.setMealType(req.getMealType());
        item.setItemName(req.getItemName());
        item.setDescription(req.getDescription());
        item.setVegetarian(req.isVegetarian());
        return toResponse(menuItemRepository.save(item));
    }

    public void deleteMenuItem(Long id) {
        MenuItem item = menuItemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Menu item not found"));
        item.setActive(false);
        menuItemRepository.save(item);
    }

    public List<MenuItemResponse> getAllMenu() {
        return menuItemRepository.findByActiveTrue()
            .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public Map<String, List<MenuItemResponse>> getWeeklyMenu() {
        return menuItemRepository.findByActiveTrue().stream()
            .map(this::toResponse)
            .collect(Collectors.groupingBy(i -> i.getDayOfWeek().name()));
    }

    public List<MenuItemResponse> getMenuByDay(DayOfWeek day) {
        return menuItemRepository.findByDayOfWeekAndActiveTrue(day)
            .stream().map(this::toResponse).collect(Collectors.toList());
    }

    private MenuItemResponse toResponse(MenuItem m) {
        return MenuItemResponse.builder()
            .id(m.getId()).dayOfWeek(m.getDayOfWeek())
            .mealType(m.getMealType()).itemName(m.getItemName())
            .description(m.getDescription()).vegetarian(m.isVegetarian())
            .build();
    }
}
