package locadora;

public class FitaVHS extends Midia{ 

    private boolean rebobinada;

    public FitaVHS(String tituloFilme, double precoBase){
        super(tituloFilme, precoBase);
        this.rebobinada = true;
    }

    @Override
    public double calcularPreco(){
        double precoFinal = this.precoBase; 

        if (!isRebobinada()){ 
             precoFinal += 5.00; 
        }

        return precoFinal;
    }

    public void setRebobinada(boolean rebobinada){
        this.rebobinada = rebobinada;
    }

    public boolean isRebobinada(){
        return this.rebobinada;
    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Tipo de Mídia: Fita VHS");
        System.out.println("Preço de Aluguel: R$"+String.format("%.2f", calcularPreco()));
        System.out.println("Rebobinada (Status atual): "+ (isRebobinada() ? "Sim" : "Não")); 
    }
}