package Utils;

import com.github.javafaker.Faker;

import java.util.Random;

public class PayloadUtil {
    public static String getPetPayload(int id, String name, String status) {
        return "{\\n\" +\n" +
                "                \"  \\\"id\\\": 0,\\n\" +\n" +
                "                \"  \\\"category\\\": {\\n\" +\n" +
                "                \"    \\\"id\\\": " + id + ",\\n\" +\n" +
                "                \"    \\\"name\\\": \\\"string\\\"\\n\" +\n" +
                "                \"  },\\n\" +\n" +
                "                \"  \\\"name\\\": \\\"" + name + "\\\",\\n\" +\n" +
                "                \"  \\\"photoUrls\\\": [\\n\" +\n" +
                "                \"    \\\"string\\\"\\n\" +\n" +
                "                \"  ],\\n\" +\n" +
                "                \"  \\\"tags\\\": [\\n\" +\n" +
                "                \"    {\\n\" +\n" +
                "                \"      \\\"id\\\": 0,\\n\" +\n" +
                "                \"      \\\"name\\\": \\\"string\\\"\\n\" +\n" +
                "                \"    }\\n\" +\n" +
                "                \"  ],\\n\" +\n" +
                "                \"  \\\"status\\\": \\\"" + status + "\\\"\\n\" +\n" +
                "                \"}";
    }

    //{
    //    "id": 3939,
    //    "category": {
    //        "id": 0,
    //        "name": "string"
    //    },
    //    "name": "shapal",
    //    "photoUrls": [
    //        "string"
    //    ],
    //    "tags": [
    //        {
    //            "id": 0,
    //            "name": "string"
    //        }
    //    ],
    //    "status": "adapt me"

    public static String getPostPayload(String channel, String message) {
        return "{\n" +
                "  \"channel\": \""+channel+"\",\n" +
                "  \"text\": \""+message+"\"\n" +
                "}";
    }









    //generate a random message
    public static String[] beginning = {"Take", "Adapt", "Don't leave"};
    public static String[] end = {"me", "him", "her"};
    public static Random rand = new Random();

    public static String generateStatus() {
        return beginning[rand.nextInt(beginning.length)] +
                end[rand.nextInt(end.length)];
    }
    //generate a random number
    public static int generateId() {
        Random numGen = new Random();
        int id = Math.abs((100) + numGen.nextInt(100));
        return id;
    }
    // generate a name
    public static String generateName() {
        Faker faker = new Faker();
        String name = faker.name().firstName();
        return name;
    }
}
