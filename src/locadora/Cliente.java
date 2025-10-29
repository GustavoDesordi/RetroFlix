package locadora;

public class Cliente {
    private static int proximoId = 1; 
    
    private String nome; 
    private String email;
    private int id; 

    public Cliente(String nome, String email){
        this.nome = nome;
        this.email = email;
        this.id = proximoId++;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEmail(String email){
        this.email = email;
    }
    
    public int getId() {
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public String getEmail(){
        return this.email;
    }

    public void exibirDetalhes(){
        System.out.println("--- Detalhes do Cliente ---");
        System.out.println("ID: "+id);
        System.out.println("Nome: "+nome);
        System.out.println("Email: "+email);
    }
}