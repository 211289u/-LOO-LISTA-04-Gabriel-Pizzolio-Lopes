public class App {
    public static void main(String[] args) {
        SistemaEscola sistemaEscola = new SistemaEscola();
        try {
            sistemaEscola.executar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
