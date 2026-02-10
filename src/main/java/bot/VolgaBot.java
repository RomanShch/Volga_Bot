package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;



public class VolgaBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername(){
        return "rossetiVolga_bot";
    }

    @Override
    public String getBotToken(){
        return System.getenv("TELEGRAM_BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update){

        if(update.hasMessage() && update.getMessage().hasText()){

            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();

            SendMessage message = new SendMessage();
            message.setChatId(chatId.toString());
            message.setText("Привет! Ты написал: " + text);

            try {
                execute(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
