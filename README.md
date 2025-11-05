# RetroFlix 🎬

RetroFlix é um sistema de locadora de filmes desenvolvido em Java, utilizando o banco de dados embarcado **Nitrite** para persistência dos dados.

## 🧠 Sobre o projeto
O sistema permite:
- Cadastrar clientes e mídias (DVD, Fita VHS e Streaming);
- Realizar e devolver locações;
- Listar mídias disponíveis e histórico de locações;
- Calcular o total arrecadado.

## ⚙️ Requisitos
Antes de rodar o projeto, é necessário ter instalado:
- **Java JDK 17+**
- **Git**

E os seguintes arquivos `.jar` devem estar dentro da pasta `lib/`:
- `nitrite-4.0.0.jar`
- `jackson-annotations-2.13.0.jar`
- `jackson-core-2.13.0.jar`
- `jackson-databind-2.13.0.jar`

## 🚀 Como executar
Abra o terminal na raiz do projeto e execute:

```bash
javac -cp ".;lib/*" src/Main.java src/locadora/*.java
java -cp "src;lib/*" Main
```

O programa será iniciado no terminal e exibirá o menu principal da locadora.

## 🧾 Estrutura básica
```
RetroFlix/
├── lib/                 # JARs necessários para rodar
├── src/
│   ├── Main.java        # Classe principal
│   └── locadora/        # Classes do sistema (Cliente, Mídia, Locação etc.)
└── README.md
```

## 🛠️ Autor
Desenvolvido por **Gustavo Desordi** como projeto da disciplina *Algoritmos e Programação*.

---

<p align="center">
  <img src="https://i.pinimg.com/originals/41/36/2b/41362b7f052b1c34e9774e2c278397e6.gif" alt="Cute cat" />
</p>
