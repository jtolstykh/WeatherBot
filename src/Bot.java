import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ
    public static void Conn() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:TEST1.s3db");


    }


    public class Bot extends TelegramLongPollingBot {

        public void onUpdateReceived(Update update) {

            if (update.hasMessage()) {
                String command = update.getMessage().getText();
                Message message = update.getMessage();

                long chat_id = update.getMessage().getChatId();


                if (message.hasLocation()) {
                    Float latitude = message.getLocation().getLatitude();
                    Float longitude = message.getLocation().getLongitude();


                    SendMessage sendMessage = new SendMessage();
                    String weatherResponse = "";
                    //текущая погода
                    try {
                        weatherResponse = Weather.getWeather(latitude, longitude);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    sendMessage.setText(weatherResponse);
                    sendMessage.setChatId(chat_id);

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }


                } else if (command.equals("/subscribe")) { // подписка на прогноз погоды

//                try {
//                    conn.WriteDB(message.getChatId(),
//                            message.getChat().getUserName(),
//                            message.getChat().getFirstName(),
//                            message.getChat().getLastName(),
//                            message.getLocation().getLatitude(),
//                            message.getLocation().getLongitude());
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
                    SendMessage sendMsg = new SendMessage();
                    sendMsg.setChatId(chat_id).setText("Thanks for subscribe!!!");
                    try {
                        execute(sendMsg);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                    try {
                        execute(sendMsg);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                } else if (command.equals("/unsubscribe")) { // отписка от прогноза погоды


                }
            }
        }

        public String getBotUsername() {
            return "@TolstykhWeather_Bot";
        }

        public String getBotToken() {
            return "716421519:AAHqqtqdF9s-XJybQUjlHOy2u9flL6_SQFs";
        }

    }












