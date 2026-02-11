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
                    messageService.sendPhoto(chatId, "Генеральный директор Общества \"Россети Волга\": <b><i>Гаврилов Александр Ильич</i></b>","src/main/java/bot/img/gavrilov.jpg");
                    messageService.sendText(chatId, "<u><b>Руководство Общества</b></u>\n\nИ. о. заместителя Генерального директора - главного инженера: <b><i>Глубокий Алексей Юрьевич</i></b>" +
                            "\n\nЗаместитель Генерального директора по экономике и финансам: <b><i>Аджиев Мухаммат Срафилович</i></b>\n\n" +
                            "Заместитель Генерального директора по инвестиционной деятельности и капитальному строительству: <b><i>Голов Андрей Валерьевич</i></b>\n\n" +
                            "Заместитель Генерального директора по правовому и корпоративному управлению: <b><i>Коневец Кирилл Сергеевич</i></b>\n\n" +
                            "Заместитель Генерального директора по безопасности: <b><i>Скачков Артём Юрьевич</i></b>\n\n" +
                            "Заместитель Генерального директора по реализации и развитию услуг: <b><i>Хватов Алексей Михайлович</i></b>\n\n" +
                            "Заместитель Генерального директора - руководитель Аппарата: <b><i>Каштанова Екатерина Сергеевна</i></b>\n\n" +
                            "<u><b>Подразделение прямого подчинения Генеральному директору</b></u>\n\n" +
                            "Главный бухгалтер – начальник Департамента бухгалтерского и налогового учета и отчетности: <b><i>Митрофанова Елена Евгеньевна</i></b>\n\n" +
                            "Начальник Департамента цифровой трансформации и информационных технологий: <b><i>Дружинин Дмитрий Владимирович</i></b>\n\n" +
                            "Директор по аудиту – руководитель дирекции внутреннего аудита: <b><i>Кузнецов Сергей Анатольевич</i></b>\n\n" +
                            "Начальник управления внутреннего контроля и управления рисками: <b><i>Иванов Сергей Владимирович</i></b>");
                    break;
                case "Режим работы":
                    messageService.sendText(chatId, "⏰ Режим работы: \n" +
                            "<b>понедельник - четверг</b>: 08.00 - 17.00\n" +
                            "<b>пятница</b>: 08.00 - 16.00\n" +
                            "<b>суббота - воскресенье</b>: выходные");
                    break;
                case "Вакансии":
                    messageService.sendText(chatId, "<b>Наши преимущества</b>:\n" +
                            "➡ ДМС со стоматологией\n" +
                            "➡ Компенсация затрат на оздоровление работников и их детей\n" +
                            "➡ Доплаты и надбавки за условия труда, отклоняющихся от допустимых\n" +
                            "➡ Выплата единовременной матпомощи при уходе работника в ежегодный основной оплачиваемый отпуск\n" +
                            "➡ Выплата единовременного пособия при рождении ребенка и ежемесячного пособия по уходу за ребенком до 3-х лет\n" +
                            "➡ Компенсация расходов за содержание детей в дошкольных учреждениях\n" +
                            "➡ Яркая спортивная и общественная жизнь и многое другое\n\n" +
                            "Список актуальных вакансий. Присоединяйся в нашу дружную команду!\n<a href='https://www.rossetivolga.ru/ru/o_kompanii/kadrovaya_politika/vakansii/'>Наши Вакансии</a>");
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
