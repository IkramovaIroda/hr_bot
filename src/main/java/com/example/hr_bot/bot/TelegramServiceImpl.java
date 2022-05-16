package com.example.hr_bot.bot;

import com.example.hr_bot.entity.Company;
import com.example.hr_bot.entity.User;
import com.example.hr_bot.repository.CompanyRepository;
import com.example.hr_bot.repository.RoleRepository;
import com.example.hr_bot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {
    final RoleRepository roleRepository;
    final UserRepository userRepository;
    final CompanyRepository companyRepository;


    @Override
    public SendMessage start(Update update, String chatId) {
//        if (userRepository.findByChatId(chatId).isEmpty()) {
//            userRepository.save(new User(chatId, State.START));
            return SendMessage.builder().chatId(chatId).text("Salom HR botga xush kelibsiz!").build();

//        else{
//            return SendMessage.builder().chatId(chatId).text("Zo'r").build();
//        }
    }

    public SendMessage shareContact(Update update, String chatId) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton();
        button.setRequestContact(true);
        button.setText("Go go");
        row.add(button);
        rows.add(row);

        replyKeyboardMarkup.setKeyboard(rows);

        return SendMessage.builder().chatId(chatId).text("Raqam jo'natish").replyMarkup(replyKeyboardMarkup).build();
    }

    @Override
    public SendMessage chooseCompany(Update update, String chatId) {
        StringBuilder result=new StringBuilder("Choose company:\n");
        List<Company> companies = companyRepository.findAll();
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setInputFieldPlaceholder("Qandaydir input");
        if(companies.size()==0){
            return SendMessage.builder()
                    .chatId(chatId)
                    .text("Companiya yoq ekan keyinroq kel")
                    .build();
        }
        KeyboardRow row = new KeyboardRow();
        for (int i = 0; i < companies.size(); i++) {
            row.add(KeyboardButton.builder()
                            .text(companies.get(i).getName())
                    .build());
            result.append(i+1).append(". ")
                    .append(companies.get(i).getName()).append("\n");
        }
        replyKeyboardMarkup.setKeyboard(List.of(row));

        return SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(replyKeyboardMarkup)
                .text(result.toString())
                .build();
    }
}
