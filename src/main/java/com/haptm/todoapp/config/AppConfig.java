package com.haptm.todoapp.config;

import com.haptm.todoapp.dto.CategoryDto;
import com.haptm.todoapp.dto.ReminderDto;
import com.haptm.todoapp.dto.TaskDto;
import com.haptm.todoapp.model.Category;
import com.haptm.todoapp.model.Reminder;
import com.haptm.todoapp.model.Task;
import com.haptm.todoapp.model.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<User, Long> userToUserId = new Converter<User, Long>() {
            public Long convert(MappingContext<User, Long> context) {
                return context.getSource() != null ? context.getSource().getUserId() : null;
            }
        };

        Converter<Category, String> categoryToCategoryName = new Converter<Category, String>() {
            public String convert(MappingContext<Category, String> context) {
                return context.getSource() != null ? context.getSource().getCategoryName() : null;
            }
        };

        Converter<Task, Long> taskToTaskId = new Converter<Task, Long>() {
            public Long convert(MappingContext<Task, Long> context) {
                return context.getSource() != null ? context.getSource().getTaskId() : null;
            }
        };

        PropertyMap<Category, CategoryDto> categoryToCategoryDto = new PropertyMap<Category, CategoryDto>() {
            protected void configure() {
                map().setCategoryId(source.getCategoryId());
                map().setCategoryName(source.getCategoryName());
                using(userToUserId).map(source.getUser()).setUserId(null);
            }
        };

        PropertyMap<CategoryDto, Category> categoryDtoToCategory = new PropertyMap<CategoryDto, Category>() {
            @Override
            protected void configure() {
                map().setCategoryId(source.getCategoryId());
                map().setCategoryName(source.getCategoryName());
                skip(destination.getUser());
            }
        };

        PropertyMap<Task, TaskDto> taskToTaskDto = new PropertyMap<Task, TaskDto>() {
            @Override
            protected void configure() {
                map().setTaskId(source.getTaskId());
                map().setTitle(source.getTitle());
                map().setDescription(source.getDescription());
                map().setDueDate(source.getDueDate());
                map().setCreatedAt(source.getCreatedAt());
                map().setUpdatedAt(source.getUpdatedAt());
                map().setStatus(source.isStatus());
                map().setPriority(source.isPriority());
                using(categoryToCategoryName).map(source.getCategory()).setCategoryName(null);
            }
        };

        PropertyMap<TaskDto, Task> taskDtoToTask = new PropertyMap<TaskDto, Task>() {
            @Override
            protected void configure() {
                map().setTaskId(source.getTaskId());
                map().setTitle(source.getTitle());
                map().setDescription(source.getDescription());
                map().setDueDate(source.getDueDate());
                map().setCreatedAt(source.getCreatedAt());
                map().setUpdatedAt(source.getUpdatedAt());
                map().setStatus(source.isStatus());
                map().setPriority(source.isPriority());
                skip(destination.getCategory());
            }
        };

        PropertyMap<Reminder, ReminderDto> reminderToReminderDto = new PropertyMap<Reminder, ReminderDto>() {
            @Override
            protected void configure() {
                map().setReminderId(source.getReminderId());
                map().setTime(source.getTime());
                using(taskToTaskId).map(source.getTask()).setTaskId(null);
            }
        };

        PropertyMap<ReminderDto, Reminder> reminderDtoToReminder = new PropertyMap<ReminderDto, Reminder>() {
            @Override
            protected void configure() {
                map().setReminderId(source.getReminderId());
                map().setTime(source.getTime());
                skip(destination.getTask());
            }
        };


        modelMapper.addMappings(categoryToCategoryDto);
        modelMapper.addMappings(categoryDtoToCategory);
        modelMapper.addMappings(taskToTaskDto);
        modelMapper.addMappings(taskDtoToTask);
        modelMapper.addMappings(reminderToReminderDto);
        modelMapper.addMappings(reminderDtoToReminder);

        return modelMapper;
    }
}
