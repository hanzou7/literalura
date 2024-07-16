package br.com.alura.literalura.main;

import br.com.alura.literalura.dto.LivroDTO;
import br.com.alura.literalura.dto.ResultsDTO;
import br.com.alura.literalura.model.*;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsultaAPI;
import br.com.alura.literalura.service.ConverteDados;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsultaAPI consultaAPI = new ConsultaAPI();
    private final ConverteDados converteDados = new ConverteDados();

    public LivroRepository livroRepository;
    public AutorRepository autorRepository;

    public Main(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibirMenu() {

        int userChoice = -1;
        while (userChoice != 0) {
            String menu = """
                    \n****** LITERALURA ******

                    1 - Buscar livros pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma

                    0 - Sair

                    Escolha uma das opções:
                    """;

            try {
                System.out.println(menu);
                userChoice = scanner.nextInt();
                scanner.nextLine();

                switch (userChoice) {
                    case 0:
                        break;
                    case 1:
                        buscarLivro();
                        break;
                    case 2:
                        listarLivros();
                        break;
                    case 3:
                        listarAutores();
                        break;
                    case 4:
                        listarAutoresPorAno();
                        break;
                    case 5:
                        listarLivrosPorIdioma();
                        break;
                    default:
                        System.out.println("Opção inválida. Escolha outra opção.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido. Tente novamente com uma das opções disponíveis no menu.");
                scanner.nextLine();
            }
        }
    }

    private void buscarLivro() {
        ResultsDTO dados = getDados();
        List<LivroDTO> livroDTO;
        livroDTO = dados.results();

        if (!livroDTO.isEmpty()) {
            Livro livro = new Livro(livroDTO.getFirst());

            Autor autor = autorRepository.findAutorByName(livro.getAutor().getName());
            if (autor != null) {
                livro.setAutor(null);
                livro.setAutor(autor);
            }
            livroRepository.save(livro);
            System.out.println(livro);
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    private ResultsDTO getDados() {
        System.out.println("Digite o nome do livro que deseja pesquisar: ");
        var nomeLivro = scanner.nextLine();

        String address = "http://gutendex.com/books";
        var json = consultaAPI.apiCall(address
                + "/?search="
                + nomeLivro.replace(" ", "+").toLowerCase());
        return converteDados.getData(json, ResultsDTO.class);
    }

    private void listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        livros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void listarAutoresPorAno() {
        try {
            System.out.println("Digite o ano: ");
            Integer ano = scanner.nextInt();
            scanner.nextLine();

            List<Autor> listaAutoresPorAno = autorRepository.searchAutorYear(ano);
            if (listaAutoresPorAno.isEmpty()) {
                System.out.println("Nenhum autor foi encontrado no ano especificado.");
            } else {
                listaAutoresPorAno.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Valor inválido. Digite um valor de ano válido.");
            scanner.nextLine();
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Escolha um idioma para filtrar:
                pt - Português
                en - Inglês
                fr - Francês
                es - Espanhol
                """);
        String chosenLanguage = scanner.nextLine();

        List<Livro> livrosLanguage = livroRepository.findByLanguage(chosenLanguage);

        if (livrosLanguage.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma escolhido.");
        } else {
            livrosLanguage.forEach(System.out::println);
        }
    }
}
