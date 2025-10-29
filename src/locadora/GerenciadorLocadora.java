package locadora;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorLocadora {

    private List<Midia> acervo;
    private List<Locacao> historicoLocacoes;
    private List<Cliente> clientes;

    public GerenciadorLocadora() {
        this.acervo = new ArrayList<>();
        this.historicoLocacoes = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public void cadastrarMidia(Midia midia) {
        this.acervo.add(midia);
        System.out.println("Mídia '" + midia.getTituloFilme() + "' cadastrada com sucesso!");
    }
    
    public void cadastrarCliente(Cliente cliente) {
        this.clientes.add(cliente);
        System.out.println("Cliente '" + cliente.getNome() + "' cadastrado com ID: " + cliente.getId());
    }

    public void listarAcervo() {
        if (acervo.isEmpty()) {
            System.out.println("O acervo está vazio.");
            return;
        }
        System.out.println("\n--- ACERVO COMPLETO (" + acervo.size() + " Mídias) ---");
        for (int i = 0; i < acervo.size(); i++) {
            System.out.print((i + 1) + ". ");
            acervo.get(i).exibirDetalhes();
            System.out.println("-------------------------");
        }
    }

    public void realizarLocacao(int midiaIndex, int clienteId, int diasLocacao) { // Realizar o aluguel [cite: 52]
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
        historicoLocacoes.add(novaLocacao);
        
        System.out.println("\nLocação realizada com sucesso!");
        novaLocacao.exibirDetalhes();
    }
    
    public void devolverMidia(int midiaIndex) {
        if (midiaIndex < 1 || midiaIndex > acervo.size()) {
            System.out.println("Índice de mídia inválido.");
            return;
        }
        Midia midia = acervo.get(midiaIndex - 1);
        
        Locacao locacaoAtiva = historicoLocacoes.stream()
            .filter(l -> l.getMidia() == midia && l.isStatusAtiva())
            .findFirst()
            .orElse(null);

        if (locacaoAtiva == null) {
            System.out.println("A mídia '" + midia.getTituloFilme() + "' não está em locação ativa.");
            return;
        }
        
        if (midia instanceof FitaVHS) {
            FitaVHS fita = (FitaVHS) midia;
            if (!fita.isRebobinada()) {
                System.out.printf("Fita devolvida SEM rebobinar! Taxa extra de R$%.2f será aplicada.\n", (fita.calcularPreco() - fita.getPrecoBase()));
            } else {
                System.out.println("Fita devolvida rebobinada.");
            }
        }
        
        locacaoAtiva.finalizarLocacao();
        System.out.println("Devolução de '" + midia.getTituloFilme() + "' finalizada.");
    }
    
    public void listarLocacoes() {
        if (historicoLocacoes.isEmpty()) {
            System.out.println("Não há histórico de locações.");
            return;
        }
        System.out.println("\n--- HISTÓRICO DE LOCAÇÕES ---");
        for (Locacao locacao : historicoLocacoes) {
            locacao.exibirDetalhes();
            System.out.println("-----------------------------");
        }
    }

    public double calcularTotalArrecadado() {
        double total = 0;
        for (Locacao locacao : historicoLocacoes) {
            total += locacao.getValorTotal();
        }
        return total;
    }

    public Cliente buscarClientePorId(int id) {
        return clientes.stream()
            .filter(c -> c.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    public List<Midia> getAcervoCompleto() {
        return acervo;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}