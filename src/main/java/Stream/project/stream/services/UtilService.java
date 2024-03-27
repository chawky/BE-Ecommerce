package Stream.project.stream.services;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UtilService {
    private static final EntityManagerFactory emFactory;
    static {
        emFactory = Persistence.createEntityManagerFactory("Stream/project/stream/services");
    }
    public static EntityManager getEntityManager(){
        return emFactory.createEntityManager();
    }
    public static void close(){
        emFactory.close();
    }
}
