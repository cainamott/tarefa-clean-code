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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    // Troca de "retorna" por "get", verbo era redundante, get seguindo padrões Java
    public List<String> getCamposVazios() {
        List<String> campos = new LinkedList<>();  // tipo declarado como interface
        if (cpf.isEmpty())       campos.add("[CPF]");
        if (nome.isEmpty())      campos.add("[Nome]");
        if (endereco.isEmpty())  campos.add("[Endereço]");
        return campos;
    }


    // Mudança de "CPF" para padrão camelCase
    // Também feitas mudanças em nomenclatura de variáveis
    // para seguir o padrão do Clean Code
    public boolean isCPF(String cpf) {
        if (cpf.length() != 11 || temSequenciaRepetida(cpf)) {
            return false;
        }
        try {
            int soma = 0;
            int peso = 10;

            for (int i = 0; i < 9; i++) {
                int numero = cpf.charAt(i) - 48;
                soma += numero * peso;
                peso--;
            }

            int resto = 11 - soma % 11;
            char primeiroDigito = (resto != 10 && resto != 11)
                    ? (char)(resto + 48) : '0';

            soma = 0;
            peso = 11;

            for (int i = 0; i < 10; i++) {
                int numero = cpf.charAt(i) - 48;
                soma += numero * peso;
                peso--;
            }

            resto = 11 - soma % 11;
            char segundoDigito = (resto != 10 && resto != 11)
                    ? (char)(resto + 48) : '0';

            return primeiroDigito == cpf.charAt(9) && segundoDigito == cpf.charAt(10);
        } catch (InputMismatchException e) {
            return false;
        }
    }

    private boolean temSequenciaRepetida(String cpf) {
        String[] sequenciasInvalidas = {
                "00000000000","11111111111","22222222222","33333333333",
                "44444444444","55555555555","66666666666","77777777777",
                "88888888888","99999999999"
        };
        for (String sequencia : sequenciasInvalidas) {
            if (cpf.equals(sequencia)) return true;
        }
        return false;
    }

    @Override  //Anotation padrão do Java para o método toString
    public String toString() {
        return cpf + "#" + nome + "#" + endereco;
    }
}