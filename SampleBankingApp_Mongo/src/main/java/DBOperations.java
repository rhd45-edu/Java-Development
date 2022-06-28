import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DBOperations {
    static CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    static CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(),
            fromProviders(pojoCodecProvider));
    static String uri = "mongodb://localhost:27017";
    static ConnectionString connectionString =
            new ConnectionString("mongodb+srv://testUser:1234@cluster0.brzqz.mongodb.net/?retryWrites=true&w=majority");

    static MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build())
            .build();
    public static BankAccount getAccountHolder(String username, String password) {
        try (MongoClient client = MongoClients.create(settings)) {
            MongoDatabase customerDocument = client.getDatabase("sample_bank")
                    .withCodecRegistry(pojoCodecRegistry);

            MongoCollection<BankAccount> bankAccounts = customerDocument
                    .getCollection("Customers", BankAccount.class);

            BankAccount foundAccount = new BankAccount();
            foundAccount = bankAccounts.find(getUsernameAndPasswordFilters(username,password)).first();
            client.close();
            return foundAccount;

        } catch (MongoException mongoException) {
            System.out.println("Error when connecting to database: " + mongoException);
            return null;
        }
    }

    public static void updateAccountHolder(BankAccount account, String username, String password){
        try(MongoClient client = MongoClients.create(settings)) {
            MongoDatabase customerDocument = client.getDatabase("sample_bank")
                    .withCodecRegistry(pojoCodecRegistry);

            MongoCollection<BankAccount> bankAccounts = customerDocument
                    .getCollection("Customers", BankAccount.class);

            bankAccounts.updateOne(getUsernameAndPasswordFilters(username,password),
                    combine(set("balance", account.getBalance()),set("transactions", account.getTransactions())));
        }catch(MongoException ex){
            ex.printStackTrace();
        }
    }

    private static Bson getUsernameAndPasswordFilters(String username, String password){
        Bson usernameFilter = Filters.eq("username", username);
        Bson passwordFilter = Filters.eq("password", password);
        Bson comboFilter = Filters.and(usernameFilter,passwordFilter);
        return comboFilter;
    }

}
