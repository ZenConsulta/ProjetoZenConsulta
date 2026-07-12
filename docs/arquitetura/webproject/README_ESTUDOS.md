# ZenConsulta — Portal do Paciente (projeto de estudo Spring Boot + Thymeleaf)

Este projeto reorganiza as 4 telas do design ZenConsulta (Dashboard do Paciente,
Busca de Médicos, Perfil do Médico e Confirmação de Agendamento) em uma aplicação
Spring Boot + Thymeleaf com **um único controller**, seguindo o mesmo padrão de
fragmentos (`baseLayout.html`) que já aparecia no seu projeto original
(`inicial.html` + `baseLayout.html` com `th:replace`/`th:fragment`).

## 1. O que foi pedido x o que foi feito

| Pedido | Onde foi resolvido |
|---|---|
| Organizar o código de forma parecida com as imagens anexadas | 4 templates Thymeleaf (`painel`, `busca-medicos`, `perfil-medico`, `confirmacao`) recriando o visual das 4 páginas HTML enviadas (mesma paleta de cores, mesmo menu lateral, mesma identidade "ZenConsulta") |
| Apenas **1 controller** | `MainViewController.java` — todas as 5 rotas (painel, busca, perfil, confirmação e raiz) estão nele |
| Explicar para estudo futuro | Comentários em bloco (`/* ... */`) em cada classe/arquivo + este README |

## 2. Estrutura de pastas

```
webproject/
├── pom.xml
├── src/main/java/br/edu/iff/ccc/webproject/
│   ├── WebprojectApplication.java          <- classe main (@SpringBootApplication)
│   └── controller/view/
│       └── MainViewController.java         <- ÚNICO controller da aplicação
└── src/main/resources/
    ├── application.properties              <- configs gerais (porta, cache, i18n)
    ├── messages.properties                 <- textos reaproveitados (#{...})
    ├── static/css/estilo.css                <- CSS global (fora do Tailwind CDN)
    └── templates/
        ├── baseLayout.html                 <- "biblioteca" de fragmentos (head, sidebar, topbar, footer)
        ├── painel.html                     <- GET /painel
        ├── busca-medicos.html              <- GET /buscar-medico
        ├── perfil-medico.html              <- GET /medico/{id}
        ├── confirmacao.html                <- GET /confirmar-agendamento
        ├── consultas.html                  <- GET /consultas
        ├── prontuarios.html                <- GET /prontuarios
        └── configuracoes.html              <- GET /configuracoes
```

> **Atualização (milestone "Estruturação da Camada de Controllers View"):**
> as rotas `/consultas`, `/prontuarios` e `/configuracoes` foram adicionadas para
> completar o mapeamento de TODOS os itens do menu lateral. Elas ainda usam
> dados mock (sem banco/regra de negócio) — isso fica para a próxima milestone.

## 3. Por que só 1 controller resolve as 4 páginas?

No Spring MVC, um `@Controller` é só uma classe Java com métodos anotados com
`@GetMapping` (ou `@PostMapping`, etc). Não existe regra de "1 controller = 1
página" — o mesmo controller pode ter quantos métodos (rotas) forem necessários.
Aqui, cada método devolve o nome de um arquivo `.html` diferente dentro de
`templates/`, e o Thymeleaf resolve automaticamente o caminho completo.

```java
@Controller
@RequestMapping("/")
public class MainViewController {

    @GetMapping("painel")                 // GET /painel
    public String getPainel(Model model) { ... return "painel"; }

    @GetMapping("buscar-medico")          // GET /buscar-medico
    public String getBuscarMedico(Model model) { ... return "busca-medicos"; }

    @GetMapping("medico/{id}")            // GET /medico/3
    public String getPerfilMedico(@PathVariable String id, Model model) { ... return "perfil-medico"; }

    @GetMapping("confirmar-agendamento")  // GET /confirmar-agendamento?medico=...&horario=...
    public String getConfirmarAgendamento(@RequestParam String medico, ..., Model model) { ... return "confirmacao"; }
}
```

## 4. Conceitos usados (para revisar depois)

- **`@Controller`** — indica que os métodos retornam *nomes de páginas* (não JSON).
  Isso é diferente de `@RestController`, que devolve dados direto (usado em APIs).
- **`@RequestMapping("/")`** na classe — define um prefixo comum; cada `@GetMapping`
  só precisa declarar o restante do caminho.
- **`@PathVariable`** — captura um pedaço da própria URL como variável Java.
  Exemplo: `/medico/{id}` → acessando `/medico/3`, `id` vale `"3"`.
- **`@RequestParam`** — captura parâmetros depois do `?` na URL (query string),
  com `defaultValue` para quando o parâmetro não é enviado.
  Exemplo: `/confirmar-agendamento?horario=14:30`.
- **`Model`** — é a "ponte" de dados entre o Java e o HTML. Tudo que entra com
  `model.addAttribute("chave", valor)` fica disponível no template como `${chave}`.
- **Fragmentos Thymeleaf (`th:fragment` / `th:replace`)** — permitem escrever uma
  vez (em `baseLayout.html`) pedaços de HTML repetidos em várias páginas
  (cabeçalho `<head>`, menu lateral, rodapé) e apenas "importá-los" nas outras.
  Isso é a evolução direta do padrão que já existia no seu `inicial.html`
  (`th:replace="baseLayout :: head"` e `th:replace="~{baseLayout :: footer}"`).
- **Fragmentos com parâmetro** — `sidebar(activePage)` e `topbar(titulo)` recebem
  um valor na hora da chamada (`sidebar('painel')`), permitindo que o mesmo HTML
  do menu destaque um item diferente dependendo da página atual.
- **`th:each`** — repete um trecho de HTML para cada item de uma lista vinda do
  Model (usado na tabela de histórico do painel e na listagem de médicos).
- **`th:text`** — substitui o texto interno da tag pelo valor de uma expressão
  Thymeleaf (`${variavel}` para dados do Model, `#{chave}` para textos do
  `messages.properties`).
- **`th:href="@{/rota(param=${valor})}"`** — monta URLs relativas ao contexto da
  aplicação e injeta parâmetros dinamicamente (usado para linkar da lista de
  médicos para o perfil, e do perfil para a confirmação).
- **`messages.properties` + `spring.messages.basename`** — centraliza textos que
  se repetem (título do app, nomes dos itens de menu, rodapé), preparando o
  projeto para internacionalização (i18n) no futuro, se for necessário.
- **`spring.thymeleaf.cache=false`** — em desenvolvimento, evita ter que reiniciar
  a aplicação a cada alteração de HTML.

## 5. Como rodar

```bash
./mvnw spring-boot:run
```

Depois acesse:
- `http://localhost:8080/` → redireciona para `/painel`
- `http://localhost:8080/painel`
- `http://localhost:8080/buscar-medico`
- `http://localhost:8080/medico/1` (experimente trocar o `1` por `2` ou `3`)
- `http://localhost:8080/confirmar-agendamento?medico=Dra.%20Ana&horario=10:00`

## 6. Próximos passos sugeridos (para continuar estudando)

1. Trocar o `Map` mock (`medicosMock`) por um `Service` + `Repository` reais
   (ex: Spring Data JPA + banco H2/PostgreSQL).
2. Transformar o formulário de filtros da busca em um `<form>` real que envia
   `@RequestParam` de volta para `GET /buscar-medico`, filtrando a lista.
3. Adicionar tratamento de erro (ex: médico com `id` inexistente → página 404
   customizada) usando `@ExceptionHandler` ou `ErrorController`.
4. Separar o CSS/Tailwind do CDN para uma build local (Tailwind CLI) quando o
   projeto crescer, evitando depender de internet em produção.
