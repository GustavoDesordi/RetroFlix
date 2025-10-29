package locadora;

public class Locacao { 
    
    private Cliente cliente; 
    private Midia midia;    
    private int diasLocacao;
    private double valorTotal;
    private boolean statusAtiva;

    public Locacao(Cliente cliente, Midia midia, int diasLocacao) {
        this.cliente = cliente;
        this.midia = midia;
        this.diasLocacao = diasLocacao;
        
        this.valorTotal = midia.calcularPreco() * diasLocacao; 
        this.statusAtiva = true; 
        midia.setDisponivel(false); 
    }
    
    
    public void finalizarLocacao() {
        this.statusAtiva = false;
        this.midia.setDisponivel(true);
    }

    
    public Cliente getCliente() {
        return cliente;
    }

    public Midia getMidia() {
        return midia;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public boolean isStatusAtiva() {
        return statusAtiva;
    }
    
    public void exibirDetalhes() {
        System.out.println("--- Detalhes da Locação ---");
        System.out.println("Cliente: " + cliente.getNome() + " (ID: " + cliente.getId() + ")");
        System.out.println("Mídia: " + midia.getTituloFilme());
        System.out.println("Valor por Dia: R$" + String.format("%.2f", midia.calcularPreco()));
        System.out.println("Dias de Locação: " + diasLocacao);
        System.out.println("Valor Total: R$" + String.format("%.2f", valorTotal));
        System.out.println("Status: " + (statusAtiva ? "ATIVA" : "FINALIZADA"));
    }
}