package locadora;

public class Streaming extends Midia{
    
    private String plataforma;

    public Streaming(String tituloFilme, double precoBase, String plataforma){
        super(tituloFilme, precoBase);
        this.plataforma = plataforma;
    }

    @Override
    public double calcularPreco(){ 
        
        return this.precoBase * 0.85; 
    }

    public void setPlataforma(String plataforma){
        this.plataforma = plataforma;
    }

    public String getPlataforma(){
        return this.plataforma;
    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Tipo de Mídia: Streaming");
        System.out.println("Preço de Aluguel: R$"+String.format("%.2f", calcularPreco()));
        System.out.println("Plataforma: "+plataforma);
    }
}