import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class SistemaEscola {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Aluno> alunosCadastrados = new ArrayList<>();
    private ArrayList<Turma> turmasCadastradas = new ArrayList<>();

    public void executar() throws Exception {
        int opcao;

        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Definir a idade de um aluno");
            System.out.println("3. Cadastrar Turma");
            System.out.println("4. Inserir Aluno na Turma");
            System.out.println("5. Remover Aluno da Turma");
            System.out.println("6. Listar Alunos da Turma");
            System.out.println("7. Remover todos alunos de uma turma");
            System.out.println("8. Cadastrar turmas a partir de um arquivo");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarAluno();
                    break;
                case 2:
                    definirIdadeAluno();
                    break;
                case 3:
                    cadastrarTurma();
                    break;
                case 4:
                    inserirAlunoNaTurma();
                    break;
                case 5:
                    removerAlunoDaTurma();
                    break;
                case 6:
                    listarAlunosDaTurma();
                    break;
                case 7:
                    removerTodosAlunosDaTurma();
                    break;
                case 8:
                    cadastrarTurmasPorArquivo();
                    break;
                case 9:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 9);
    }

    public <T> T buscarComando(String textoComando, List<T> lista, Function<T, String> keyExtractor) {
        System.out.print(textoComando);
        String input = scanner.nextLine();

        for (T item : lista) {
            if (keyExtractor.apply(item).equals(input)) {
                return item;
            }
        }
        return null;
    }

    public Aluno buscarAlunoCmd(String textoComando) {
        return buscarComando(textoComando, alunosCadastrados, Aluno::getCpf);
    }

    public Turma buscarTurmaCmd(String textoComando) {
        return buscarComando(textoComando, turmasCadastradas, Turma::getCodigo);
    }

    public Turma buscarTurmaPorCodigo(String codigo) {
        for (Turma turma : turmasCadastradas) {
            if (turma.getCodigo().equals(codigo)) {
                return turma;
            }
        }
        return null;
    }

    public void cadastrarAluno() {
        System.out.print("Digite o nome do aluno: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF do aluno: ");
        String cpf = scanner.nextLine();

        Aluno aluno = new Aluno(nome, cpf);
        alunosCadastrados.add(aluno);
        System.out.println("Aluno cadastrado com sucesso!");
    }

    public void definirIdadeAluno() {
        Aluno aluno = buscarAlunoCmd("Digite o CPF do aluno a ser editado: ");
        if (aluno != null) {
            System.out.print("Digite a idade do aluno: " + aluno.getNome());
            int idade = scanner.nextInt();
            scanner.nextLine();

            aluno.setIdade(idade);
            System.out.println(aluno.toString());
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    public void cadastrarTurma() {
        System.out.print("Digite o nome da turma: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o código da turma: ");
        String codigo = scanner.nextLine();

        Turma turma = new Turma(nome, codigo);
        turmasCadastradas.add(turma);
        System.out.println("Turma cadastrada com sucesso!");
    }

    public void inserirAlunoNaTurma() {
        Aluno aluno = buscarAlunoCmd("Digite o CPF do aluno a ser inserido: ");
        if (aluno != null) {
            Turma turma = buscarTurmaCmd("Digite o código da turma que deseja adicionar o aluno: ");
            turma.adicionarAluno(aluno);
        } else {
            System.out.println("Aluno não encontrado. Certifique-se de que o aluno foi cadastrado.");
        }
    }

    public void removerAlunoDaTurma() {
        Aluno aluno = buscarAlunoCmd("Digite o CPF do aluno a ser removido: ");
        if (aluno != null) {
            Turma turma = buscarTurmaCmd("Digite o código da turma que deseja remover o aluno: ");
            turma.removerAluno(aluno);
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    public void listarAlunosDaTurma() {
        Turma turma = buscarTurmaCmd("Digite o código da turma que deseja listar os alunos: ");
        turma.listarAlunos();
    }

    public void removerTodosAlunosDaTurma() {
        System.out.println("Você tem certeza que deseja remover todos alunos da turma? \n1. Sim\n2. Não");
        int confirmacaoAcao = scanner.nextInt();
        scanner.nextLine();
        if (confirmacaoAcao == 1) {
            Turma turma = buscarTurmaCmd("Digite o código da turma que deseja remover todos alunos: ");
            turma.removerTodos();
        }
    }

    public void cadastrarTurmasPorArquivo() throws FileNotFoundException {
        System.out.print("Digite o nome do arquivo (Exemplo: listaTurmas.csv): ");
        String nomeArquivo = scanner.nextLine();

        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado. Verifique o nome e tente novamente.");
            return;
        }

        try (Scanner leitorArquivo = new Scanner(arquivo)) {
            while (leitorArquivo.hasNextLine()) {
                String linha = leitorArquivo.nextLine();
                String[] dadosTurma = linha.split(",");

                if (dadosTurma.length == 2) {
                    String nome = dadosTurma[0].trim();
                    String codigo = dadosTurma[1].trim();

                    Turma turma = new Turma(nome, codigo);
                    turmasCadastradas.add(turma);
                    System.out.println("Turma " + nome + " cadastrada com sucesso.");
                } else {
                    System.out.println("Formato de linha inválido: " + linha);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
            throw e;
        }
    }
}
