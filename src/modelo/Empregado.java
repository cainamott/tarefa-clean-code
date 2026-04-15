package modelo;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;

public class Empregado {
    private String cpf;
    private String nome;
    private String endereco;

    public Empregado(String cpf, String nome, String endereco) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
    }

    public Empregado() {
    }

    public String getCpf()                  { return cpf; }
    public void   setCpf(String cpf)        { this.cpf = cpf; }
    public String getNome()                 { return nome; }
    public void   setNome(String nome)      { this.nome = nome; }
    public String getEndereco()             { return endereco; }
    public void   setEndereco(String end)   { this.endereco = end; }

    public List<String> getCamposVazios() {
        List<String> campos = new LinkedList<>();
        if (cpf.isEmpty())       campos.add("[CPF]");
        if (nome.isEmpty())      campos.add("[Nome]");
        if (endereco.isEmpty())  campos.add("[Endereço]");
        return campos;
    }

    // Método público APENAS para chamada de métodos com lógica complexa
    public boolean isCPF(String cpf) {
        if (cpf.length() != 11 || temSequenciaRepetida(cpf)) {
            return false;
        }
        try {
            char primeiroDigito = calcularDigitoVerificador(cpf, 10, 9);
            char segundoDigito  = calcularDigitoVerificador(cpf, 11, 10);
            return primeiroDigito == cpf.charAt(9) && segundoDigito == cpf.charAt(10);
        } catch (InputMismatchException e) {
            return false;
        }
    }

    // extração da responsabilidade de detectar sequências inválidas
    private boolean temSequenciaRepetida(String cpf) {
        for (int digito = 0; digito <= 9; digito++) {
            if (cpf.equals(String.valueOf(digito).repeat(11))) {
                return true;
            }
        }
        return false;
    }

    // Extração e encapsulamento da lógica do digito verificador,
    // para remoção da complexidade em apenas um meotdo
    private char calcularDigitoVerificador(String cpf, int pesoInicial, int limite) {
        int soma = 0;
        int peso = pesoInicial;
        for (int i = 0; i < limite; i++) {
            soma += (cpf.charAt(i) - 48) * peso;
            peso--;
        }
        int resto = 11 - soma % 11;
        return (resto != 10 && resto != 11) ? (char)(resto + 48) : '0';
    }

    @Override
    public String toString() {
        return cpf + "#" + nome + "#" + endereco;
    }
}