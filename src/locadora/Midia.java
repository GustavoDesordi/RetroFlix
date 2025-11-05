package locadora;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.dizitart.no2.objects.Id;

import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipoMidia"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DVD.class, name = "DVD"),
    @JsonSubTypes.Type(value = FitaVHS.class, name = "FitaVHS"),
    @JsonSubTypes.Type(value = Streaming.class, name = "Streaming")
})
public abstract class Midia {

    @Id
    private String tituloFilme;
    protected double precoBase;
    private boolean disponivel;

    // construtor padrão necessário pro Jackson kkkkk
    protected Midia() {
        // usado apenas para desserialização
    }

    public Midia(String tituloFilme, double precoBase) {
        this.tituloFilme = tituloFilme;
        this.precoBase = precoBase;
        this.disponivel = true;
    }

    public abstract double calcularPreco();

    public void setTituloFilme(String tituloFilme) {
        this.tituloFilme = tituloFilme;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String getTituloFilme() {
        return this.tituloFilme;
    }

    public double getPrecoBase() {
        return this.precoBase;
    }

    public boolean isDisponivel() {
        return this.disponivel;
    }

    public void exibirDetalhes() {
        System.out.println("Título do Filme: " + tituloFilme);
        System.out.println("Preço base: R$" + precoBase);
        System.out.println("Disponível: " + (isDisponivel() ? "Sim" : "Não"));
    }
}
