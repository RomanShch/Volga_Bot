package bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class MessageService {
    private final TelegramLongPollingBot bot;
    private  static  final  Logger logger = Logger.getLogger(MessageService.class.getName());

    public MessageService(TelegramLongPollingBot bot){
        this.bot = bot;
    }

    public void sendText(Long chatId, String text){
        SendMessage message = new SendMessage(chatId.toString(), text);
        message.setParseMode("HTML");
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            logger.severe(sw.toString());
        }
    }
    public void sendPhoto(Long chatId, String text, String filePath){
        SendPhoto photo = new SendPhoto();
        photo.setChatId(chatId.toString());
        photo.setParseMode("HTML");
        photo.setCaption(text);
        photo.setPhoto(new InputFile(new File(filePath)));

        try {
            bot.execute(photo);
        } catch (TelegramApiException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            logger.severe(sw.toString());
        }
    }

}
