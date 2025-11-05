package locadora;

import org.dizitart.no2.objects.Id;

public class Cliente {
    private static int proximoId = 1; 
    
    private String nome; 
    private String email;
    @Id
    private int id; 

    // construtor padrão pro jackson kkk
    public Cliente() {}

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