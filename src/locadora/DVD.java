package locadora;

public class DVD extends Midia {
    
    private boolean possuiExtras;

    public DVD(String tituloFilme, double precoBase, boolean possuiExtras){
        super(tituloFilme, precoBase); 
        this.possuiExtras = possuiExtras; 
    }

    @Override
    public double calcularPreco(){ 
        double precoFinal = this.precoBase; 
        
        if (this.possuiExtras){
            precoFinal += 15.00; 
        }
        return precoFinal;
    }

    public void setPossuiExtras(boolean possuiExtras){
        this.possuiExtras = possuiExtras;
    }

    public boolean isPossuiExtras(){ 
        return possuiExtras;
    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Tipo de Mídia: DVD");
        System.out.println("Preço de Aluguel: R$"+String.format("%.2f", calcularPreco()));
        System.out.println("Possui extras: "+ (isPossuiExtras() ? "Sim" : "Não"));
    }
}