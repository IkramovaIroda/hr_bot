package com.example.hr_bot.bot;

import com.example.hr_bot.entity.Company;
import com.example.hr_bot.entity.User;
import com.example.hr_bot.entity.enums.Usertype;
import com.example.hr_bot.repository.CompanyRepository;
import com.example.hr_bot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HRBot extends TelegramLongPollingBot {
    @Value("${bot.token}")
    String token;

    @Value("${bot.username}")
    String username;

    final TelegramServiceImpl telegramService;
    final UserRepository userRepository;
    final CompanyRepository companyRepository;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        //code
        if (update.hasMessage()) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            Message message = update.getMessage();
            Optional<User> optionalUser = userRepository.findByChatId(String.valueOf(update.getMessage().getChatId()));
            User user;
            if(optionalUser.isEmpty()){
                User save = userRepository.save(new User(chatId, State.START));
                user=save;
            }else {
                user = optionalUser.get();
            }
            switch (user.getState()){
                case State.START-> {
                    execute(telegramService.start(update, chatId));
                    execute(telegramService.shareContact(update, chatId));
                    user.setState(State.SHARE_CONTACT);
                    userRepository.save(user);
                }
                case State.SHARE_CONTACT -> {
                    if(update.getMessage().hasContact()){
                        user.setNumber(update.getMessage().getContact().getPhoneNumber());
                        user.setState(State.COMPANY_CHOOSE);
                        userRepository.save(user);
                        execute(telegramService.chooseCompany(update, chatId));
                    }
                }
                case State.COMPANY_CHOOSE -> {
                    Optional<Company> company = companyRepository.findByName(message.getText());
                    if(company.isEmpty()){
                        execute(SendMessage.builder()
                                .text("Bunaqa companiya topilmadiku")
                                .chatId(chatId)
                                .build());
                        return;
                    }
                    List<User> employeeList = userRepository.findAllByUsertype(Usertype.EMPLOYEE);
                    List<User> managerList = userRepository.findAllByUsertype(Usertype.MANAGER);
                    StringBuilder result=new StringBuilder("Employees\n");
                    employeeList.addAll(managerList);
                    List<KeyboardRow> rows=new ArrayList<>();
                    for (int i = 0; i < employeeList.size(); i+=5) {
                        KeyboardRow row=new KeyboardRow();
                        for (int j = 0; j < i+5; j++) {
                            if(i+j>= employeeList.size()){
                                break;
                            }
                            User employee = employeeList.get(i+j);
                            result.append(i+j+1).append(". ")
                                    .append(employee.getUsername()+" - "+employee.getUsertype().name()).append("\n");
                            row.add(i+j+1+"");
                        }
                        rows.add(row);
                    }
                    ReplyKeyboardMarkup markup=new ReplyKeyboardMarkup();
                    markup.setResizeKeyboard(true);
                    markup.setKeyboard(rows);

                    execute(SendMessage.builder()
                            .chatId(chatId)
                            .replyMarkup(markup)
                            .text(result.toString())
                            .build());
                    user.setState(State.EMPLOYEE_CHOOSE);
                    userRepository.save(user);
                }
                case State.EMPLOYEE_CHOOSE -> {

                }

            }
        }



    }
}
