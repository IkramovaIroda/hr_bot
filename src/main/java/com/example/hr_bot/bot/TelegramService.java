package com.example.hr_bot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


public interface TelegramService {
    SendMessage start(Update update, String chatId);

    SendMessage chooseCompany(Update update, String chatId);
}
