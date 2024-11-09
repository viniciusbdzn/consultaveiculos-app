package br.com.alura.consultaveiculoapp.principal;

import br.com.alura.consultaveiculoapp.modelo.Dados;
import br.com.alura.consultaveiculoapp.modelo.Modelos;
import br.com.alura.consultaveiculoapp.modelo.Veiculo;
import br.com.alura.consultaveiculoapp.servico.ConsumoApi;
import br.com.alura.consultaveiculoapp.servico.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String URL_API = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        System.out.println("""
                ****OPÇÕES****
                
                Carro
                
                Moto 
                
                Caminhão
                
                Digite uma das opções para consultar valores: 
                """);
        var opcaoVeiculo = leitura.nextLine();
        String endereco = "";

        if(opcaoVeiculo.toLowerCase().contains("carr")) {
            endereco = URL_API + "carros/marcas";
        } else if(opcaoVeiculo.toLowerCase().contains("mot")) {
            endereco = URL_API + "motos/marcas";
        } else {
            endereco = URL_API + "caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);
        System.out.println(json);

        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Digite o código da marca para a busca: ");
        var opcaoMarca = leitura.nextLine();
        var enderecoMarca = endereco + "/" + opcaoMarca + "/modelos";
        json = consumo.obterDados(enderecoMarca);
        System.out.println(json);

        var modelosLista = conversor.obterDados(json, Modelos.class);
        System.out.println("\nLista de modelos dessa marca: ");
        modelosLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do carrro para a busca: ");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modelosLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos filtrados:");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nDigite o código do modelo para buscar os valores de avaliação: ");
        var codigoModelo = leitura.nextLine();

        var enderecoAnosAvaliacoes = enderecoMarca + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(enderecoAnosAvaliacoes);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        System.out.println(json);

        List<Veiculo> veiculos = new ArrayList<>();
        for (int i = 0; i < anos.size() ; i++) {
            var enderecoAnos = enderecoAnosAvaliacoes + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);
    }
}
