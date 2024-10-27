public abstract class PessoaFisica {
    private int idade;
    private String cpf;
    private String nome;

    // Construtor
    public PessoaFisica(int idade, String cpf, String nome) {
        this.idade = idade;
        this.cpf = cpf;
        this.nome = nome;
    }

    public PessoaFisica(String nome, String cpf) {
        this.cpf = cpf;
        this.nome = nome;
    }

    // Métodos getters e setters
    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Método genérico para calcular o ano de nascimento
    public int calcularAnoNascimento() {
        int anoAtual = java.time.Year.now().getValue();
        return anoAtual - this.idade;
    }

    // Método abstrato que deve ser implementado pelas subclasses
    public abstract String getIdentificacao();

    @Override
    public String toString() {
        return "PessoaFisica{" +
               "idade=" + idade +
               ", cpf='" + cpf + '\'' +
               ", nome='" + nome + '\'' +
               '}';
    }
}
