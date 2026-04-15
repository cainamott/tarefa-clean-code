package modelo;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;

public class Empregado {

    private static final int    CPF_TAMANHO          = 11;
    private static final int    CPF_TOTAL_DIGITOS    = 9;
    private static final int    OFFSET_ASCII_ZERO    = 48;  // '0' em ASCII
    private static final int    PESO_INICIAL_PRIMEIRO = 10;
    private static final int    PESO_INICIAL_SEGUNDO  = 11;
    private static final char   SEPARADOR_CAMPOS     = '#';

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

    // Metodo público APENAS para chamada de métodos com lógica complexa
    public boolean isCPF(String cpf) {
        if (cpf.length() != CPF_TAMANHO || temSequenciaRepetida(cpf)) {
            return false;
        }
        try {
            char primeiroDigito = calcularDigitoVerificador(cpf, PESO_INICIAL_PRIMEIRO, CPF_TOTAL_DIGITOS);
            char segundoDigito  = calcularDigitoVerificador(cpf, PESO_INICIAL_SEGUNDO,  CPF_TAMANHO - 1);
            return primeiroDigito == cpf.charAt(CPF_TAMANHO - 2)
                    && segundoDigito  == cpf.charAt(CPF_TAMANHO - 1);
        } catch (InputMismatchException e) {
            return false;
        }
    }

    // extração da responsabilidade de detectar sequências inválidas
    private boolean temSequenciaRepetida(String cpf) {
        for (int digito = 0; digito <= 9; digito++) {
            if (cpf.equals(String.valueOf(digito).repeat(CPF_TAMANHO))) {
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
            soma += (cpf.charAt(i) - OFFSET_ASCII_ZERO) * peso;
            peso--;
        }
        int resto = PESO_INICIAL_SEGUNDO - soma % PESO_INICIAL_SEGUNDO;
        return (resto != PESO_INICIAL_PRIMEIRO && resto != PESO_INICIAL_SEGUNDO)
                ? (char)(resto + OFFSET_ASCII_ZERO) : '0';
    }

    @Override
    public String toString() {
        return cpf + "#" + nome + "#" + endereco;
    }
}