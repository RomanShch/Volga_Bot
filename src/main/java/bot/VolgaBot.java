package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VolgaBot extends TelegramLongPollingBot {

    private final MessageService messageService;
    private final MenuService menuService;
    private Map<Long, Boolean> waitingForContact = new HashMap<>();
    private Map<Long, String> tempUserData = new HashMap<>(); //для хранения текста

    public VolgaBot(){
        this.messageService = new MessageService(this);
        this.menuService = new MenuService(this);
    }

    @Override
    public String getBotUsername(){
        return "rossetiVolga_bot";
    }

    @Override
    public String getBotToken(){
        return System.getenv("TELEGRAM_BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();

            // Проверяем ждем ли мы контакт от пользователя
            if(waitingForContact.getOrDefault(chatId, false)){
                switch (text){
                    case "Готово":
                        //пользователь закончил ввод
                        String userData = tempUserData.getOrDefault(chatId, "");
                        messageService.sendText(chatId, "Спасибо! Ваши данные получены:\n" + userData);

                        //Можно здесь отправить например на почту
                        System.out.println("Новые контакты: " + userData);

                        waitingForContact.put(chatId, false);
                        tempUserData.remove(chatId);
                        menuService.sendMainMenu(chatId);//возвращаем в главное меню
                        break;
                    case "Отмена":
                        messageService.sendText(chatId, "Ввод контактов отменен.");
                        waitingForContact.put(chatId, false);
                        tempUserData.remove(chatId);
                        menuService.sendMainMenu(chatId);
                        break;
                    default:
                        //пользователь вводит текст
                        tempUserData.put(chatId, text);
                        messageService.sendText(chatId, "Вы написали: " + text + "\nКогда закончите, нажмите \"Готово\" или \"Отмена\".");
                }
                return; // не идем дальше
            }

            switch (text) {
                case "/start":
                    menuService.sendMainMenu(chatId);
                    break;
                case "О предприятии":
                    menuService.sendCompanyMenu(chatId);
                    break;
                case "Общая информация":
                    menuService.sendCompanyInfoMenu(chatId);
                    break;
                case "Руководство филиала":
                    messageService.sendPhoto(chatId, "Здесь будет информация о руководстве филиала.","src/main/java/bot/img/AtomicHeart_sample.jpg");
                    break;
                case "Режим работы":
                    messageService.sendText(chatId, "Здесь будет информация о режиме работы");
                    break;
                case "Вакансии":
                    messageService.sendText(chatId, "Здесь будет информация о Вакансиях");
                    break;
                case "Предложить новость":
                    messageService.sendText(chatId, "Здесь будет информация о Новостях");
                    break;
                case "Совет Молодежи":
                    messageService.sendText(chatId, "Здесь будет информация о совете молодежи");
                    break;
                case "Контакты":
                    messageService.sendText(chatId, "Здесь будет информация о Контактах");
                    break;
                case "Задать вопрос специалисту":
                    menuService.sendContactMenu(chatId);
                    waitingForContact.put(chatId, true);
                    break;
                case "Профсоюз":
                    messageService.sendText(chatId, "Здесь будет информация о профсоюзе");
                    break;
                case "Назад":
                    menuService.sendMainMenu(chatId);
                    break;
            }
        }
    }
}
