package bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MenuService {
    private final TelegramLongPollingBot bot;
    private static final Logger logger = Logger.getLogger(MenuService.class.getName());

    public MenuService(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    public void sendMainMenu(Long chatId) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);

        KeyboardRow row1 = new KeyboardRow();
        row1.add("О предприятии");
        row1.add("Контакты");


        KeyboardRow row2 = new KeyboardRow();
        row2.add("Предложить новость");
        row2.add("Задать вопрос специалисту");

        KeyboardRow row3 = new KeyboardRow();
        row3.add("Совет Молодежи");
        row3.add("Профсоюз");

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        keyboard.setKeyboard(rows);

        sendMessage(chatId, "Выберите действие", keyboard);
    }

    public void sendCompanyMenu(Long chatId){
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);

        KeyboardRow row1 = new KeyboardRow();
        row1.add("Общая информация");
        row1.add("Режим работы");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Вакансии");
        row2.add("Назад");

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        keyboard.setKeyboard(rows);

        sendMessage(chatId, "О предприятии:", keyboard);
    }

    public void sendCompanyInfoMenu(Long chatId){
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);

        KeyboardRow row1 = new KeyboardRow();
        row1.add("Руководство филиала");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Назад");

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        keyboard.setKeyboard(rows);

        sendMessage(chatId, "<b>ПАО «Россети Волга»</b>\n" +
                "ПАО «Россети Волга» (входит в Группу «Россети») - одна из крупнейших распределительных электросетевых организаций в Приволжском федеральном округе.\n" +
                "\n" +
                "Компания осуществляет передачу электроэнергии и технологическое присоединение потребителей на территории Саратовской, Самарской, Оренбургской, Пензенской, Ульяновской областей, республик Мордовия и Чувашия.\n" +
                "\n" +
                "Под управлением находятся более 236 тыс. километров линий электропередачи и 52,7 тыс. подстанций мощностью 37,7 тыс. МВА. Общая численность персонала составляет порядка 20 тыс. человек.\n" +
                "<u><b>Филиалы компании</b></u>:\n➡«Самарские распределительные сети», " +
                "\n➡«Саратовские распределительные сети», " +
                "\n➡«Ульяновские распределительные сети», " +
                "\n➡«Мордовэнерго», " +
                "\n➡«Оренбургэнерго», " +
                "\n➡«Пензаэнерго», " +
                "\n➡«Чувашэнерго» " +
                "\nявляются системообразующими территориальными сетевыми организациями в регионах присутствия.", keyboard);
    }

    public void sendContactMenu(Long chatId){
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);

        KeyboardRow row1 = new KeyboardRow();
        row1.add("Готово");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Отмена");

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);

        keyboard.setKeyboard(rows);

        sendMessage(chatId, "Напишите своё ФИО, откуда вы и номер телефона.\n" +
                "Когда закончите, нажмите «Готов», или «Отмена», если передумали.", keyboard);
    }

    private void sendMessage(Long chatId, String text, ReplyKeyboardMarkup keyboard){
        SendMessage message = new SendMessage(chatId.toString(), text);
        message.setParseMode("HTML");
        message.setReplyMarkup(keyboard);
        try {
            bot.execute(message);
        }catch (TelegramApiException e){
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            logger.severe(sw.toString());
        }
    }
}