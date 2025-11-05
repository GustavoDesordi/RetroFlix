package locadora;

import java.util.List;
import java.util.stream.Collectors;
import org.dizitart.no2.objects.ObjectRepository;

public class GerenciadorLocadora {

    private ObjectRepository<Midia> midiasRepo;
    private ObjectRepository<Locacao> locacoesRepo;
    private ObjectRepository<Cliente> clientesRepo;

    public GerenciadorLocadora() {
        BancoDeDados.inicializar();
        this.midiasRepo = BancoDeDados.getMidiasRepo();
        this.locacoesRepo = BancoDeDados.getLocacoesRepo();
        this.clientesRepo = BancoDeDados.getClientesRepo();
    }

    public void cadastrarMidia(Midia midia) {
        midiasRepo.insert(midia);
        System.out.println("Mídia '" + midia.getTituloFilme() + "' cadastrada com sucesso!");
    }

    public void cadastrarCliente(Cliente cliente) {
        clientesRepo.insert(cliente);
        System.out.println("Cliente '" + cliente.getNome() + "' cadastrado com ID: " + cliente.getId());
    }

    public List<Midia> getAcervoCompleto() {
        return midiasRepo.find().toList();
    }

    public List<Cliente> getClientes() {
        return clientesRepo.find().toList();
    }

    public void listarAcervo() {
        List<Midia> acervo = getAcervoCompleto();
        if (acervo.isEmpty()) {
            System.out.println("O acervo está vazio.");
            return;
        }
        System.out.println("\n--- ACERVO COMPLETO (" + acervo.size() + " Mídias) ---");
        for (Midia midia : acervo) {
            midia.exibirDetalhes();
            System.out.println("-------------------------");
        }
    }

    public Cliente buscarClientePorId(int id) {
        return clientesRepo.find().toList().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void realizarLocacao(int midiaIndex, int clienteId, int diasLocacao) {
        List<Midia> acervo = getAcervoCompleto();
        if (midiaIndex < 1 || midiaIndex > acervo.size()) {
            System.out.println("Índice de mídia inválido.");
            return;
        }

        Midia midia = acervo.get(midiaIndex - 1);
        if (!midia.isDisponivel()) {
            System.out.println("Mídia '" + midia.getTituloFilme() + "' já está alugada.");
            return;
        }

        Cliente cliente = buscarClientePorId(clienteId);
        if (cliente == null) {
            System.out.println("Cliente com ID " + clienteId + " não encontrado.");
            return;
        }

        Locacao novaLocacao = new Locacao(cliente, midia, diasLocacao);
        locacoesRepo.insert(novaLocacao);
        midiasRepo.update(midia);

        System.out.println("\nLocação realizada com sucesso!");
        novaLocacao.exibirDetalhes();
    }

    public void devolverMidia(int midiaIndex) {
    List<Midia> acervo = getAcervoCompleto();
    if (midiaIndex < 1 || midiaIndex > acervo.size()) {
        System.out.println("Índice de mídia inválido.");
        return;
    }

    Midia midia = acervo.get(midiaIndex - 1);
    Locacao locacaoAtiva = locacoesRepo.find().toList().stream()
            .filter(l -> l.getMidia().getTituloFilme().equals(midia.getTituloFilme()) && l.isStatusAtiva())
            .findFirst()
            .orElse(null);

    if (locacaoAtiva == null) {
        System.out.println("A mídia '" + midia.getTituloFilme() + "' não está em locação ativa.");
        return;
    }

    if (midia instanceof FitaVHS) {
        FitaVHS fita = (FitaVHS) midia;
        if (!fita.isRebobinada()) {
            System.out.printf("Fita devolvida SEM rebobinar! Taxa extra de R$%.2f será aplicada.\n",
                    (fita.calcularPreco() - fita.getPrecoBase()));
        } else {
            System.out.println("Fita devolvida rebobinada.");
        }
    }

    // Finaliza e salva corretamente
    locacaoAtiva.finalizarLocacao();
    locacoesRepo.update(locacaoAtiva);
    midiasRepo.update(locacaoAtiva.getMidia());

    System.out.println("Devolução de '" + midia.getTituloFilme() + "' finalizada.");
}


    public void listarLocacoes() {
        List<Locacao> locacoes = locacoesRepo.find().toList();
        if (locacoes.isEmpty()) {
            System.out.println("Não há histórico de locações.");
            return;
        }

        System.out.println("\n--- HISTÓRICO DE LOCAÇÕES ---");
        for (Locacao locacao : locacoes) {
            locacao.exibirDetalhes();
            System.out.println("-----------------------------");
        }
    }

    public double calcularTotalArrecadado() {
        return locacoesRepo.find().toList().stream()
                .mapToDouble(Locacao::getValorTotal)
                .sum();
    }
}
