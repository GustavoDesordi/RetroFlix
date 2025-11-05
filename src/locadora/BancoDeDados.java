package locadora;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import java.util.List;

public class BancoDeDados {
    private static Nitrite db;
    private static ObjectRepository<Cliente> clientesRepo;
    private static ObjectRepository<Midia> midiasRepo;
    private static ObjectRepository<Locacao> locacoesRepo;

    public static void inicializar() {
        if (db == null || db.isClosed()) {
            db = Nitrite.builder()
                .filePath("retroflix.db")
                .openOrCreate("admin", "1234");

            clientesRepo = db.getRepository(Cliente.class);
            midiasRepo = db.getRepository(Midia.class);
            locacoesRepo = db.getRepository(Locacao.class);
        }
    }

    public static ObjectRepository<Cliente> getClientesRepo() {
        return clientesRepo;
    }

    public static ObjectRepository<Midia> getMidiasRepo() {
        return midiasRepo;
    }

    public static ObjectRepository<Locacao> getLocacoesRepo() {
        return locacoesRepo;
    }

    public static void fechar() {
        if (db != null && !db.isClosed()) {
            db.close();
        }
    }
}
