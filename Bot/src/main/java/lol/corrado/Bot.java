package lol.corrado;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lol.corrado.model.TelegramMessage;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Bot {

    private static final String OWM_URI = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
    private static final String OWM_KEY = "20cc19e1825251eb43d041fd902a63dc";

    private static final String TELEGRAM_URI = "https://api.telegram.org/bot%s/sendMessage";
    private static final String BOT_ID = "259537421:AAEL9HrsBPSXMGRqfoAdu8ric99KwhbQERQ";

    private static final ObjectMapper mapper = new ObjectMapper();

    public void run(TelegramMessage message, Context context) throws Exception {

        LambdaLogger logger = context.getLogger();

        String text = message.getMessage().getText();

        logger.log("Got message: " + text);
        if (!text.startsWith("/meteo"))
            return;

        HttpClient client = HttpClients.createDefault();

        HttpGet get = new HttpGet(String.format(OWM_URI, text.split("\\s")[1], OWM_KEY));
        HttpEntity entity = client.execute(get).getEntity();
        String response = EntityUtils.toString(entity, UTF_8);

        JsonNode json = mapper.readTree(response);
        String weather = json.get("weather").get(0).get("main").asText();

        get = new HttpGet(
                new URIBuilder(String.format(TELEGRAM_URI, BOT_ID))
                        .addParameter("chat_id", message.getMessage().getChat().getId())
                        .addParameter("text", weather).build()
        );

        EntityUtils.consumeQuietly(client.execute(get).getEntity());

    }

}