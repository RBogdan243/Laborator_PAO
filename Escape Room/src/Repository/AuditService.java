package Repository;

import Repository.impl.MyDatabase;
import org.postgresql.util.PSQLException;



public class AuditService {
    private static AuditService instance;
    private DatabaseService DataBase = MyDatabase.getInstance();

    private AuditService() {
    }

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void logAction(String actionName) {
        try {
            if(this.DataBase.insert("INSERT INTO public.\"Audit\"(\"action\", \"time\") VALUES (?, CURRENT_TIMESTAMP);", actionName) == 0) {
                System.err.println("Nu s-a putut insera nimic în baza de date!");
                System.exit(0);
            }
        } catch (PSQLException e) {
            e.printStackTrace();
        }
    }
}