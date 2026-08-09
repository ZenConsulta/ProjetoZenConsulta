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

---

# Atualização — Entrega P1 (CRUD completo + regras de negócio)

Esta seção documenta o que foi adicionado para atender ao **Roteiro de Entrega
da P1**: CRUD completo (Create/Read/Update/Delete) para todas as entidades do
domínio, com as operações de negócio que as interligam, seguindo a arquitetura
**Controller → Service → Repository (em memória)**.

## 1. Novas camadas do projeto

```
src/main/java/br/edu/iff/ccc/webproject/
├── model/          <- Entidades de domínio (Paciente, Profissional, Operador,
│                       Administrador, SlotAgenda, Agendamento, StatusAgendamento)
├── dto/             <- Um DTO por entidade, usado nos formulários (th:object)
├── repository/      <- Repositorio<T> (interface) + RepositorioMemoria<T>
│                       (base genérica em memória) + 6 repositórios concretos
├── service/         <- Regras de negócio e orquestração de CRUD
├── exception/       <- RegraDeNegocioException (mensagens claras, RNF07)
└── controller/view/ <- 1 controller por entidade + o controller de negócio
                        (AgendamentoViewController)
```

## 2. Por que `RepositorioMemoria<T>` é genérico?

Em vez de escrever "salvar / buscar / listar / remover" do zero em cada uma
das 6 entidades, existe uma única classe abstrata `RepositorioMemoria<T>` que
guarda os dados em um `Map<Long, T>` e sabe gerar IDs automaticamente. Cada
repositório concreto (ex: `PacienteRepository`) só precisa dizer **como ler e
escrever o id** da sua entidade:

```java
public class PacienteRepository extends RepositorioMemoria<Paciente> {
    public PacienteRepository() {
        super(Paciente::getId, Paciente::setId);
    }
}
```

Isso é o princípio DRY (Don't Repeat Yourself) aplicado com Java Generics +
Method References — um assunto ótimo pra revisar depois.

## 3. Onde estão as regras de negócio (RN01–RN05)?

Todas em `AgendamentoService`, a classe mais importante do projeto:

| Regra | Onde é aplicada |
|---|---|
| RN02 — slot só reserva se `disponivel = true` | Método `solicitar(...)` |
| RN04 — paciente só solicita, não confirma | `solicitar(...)` cria sempre com status `PENDENTE` |
| RN01/RF10 — revalida conflito antes de confirmar | Método `confirmar(...)` |
| RN03 — só Operador confirma | `confirmar(...)` exige um `operadorId` válido |
| RF12 — registra quem confirmou | `confirmar(...)` grava `operadorResponsavelId` |
| RF15 — slot fica indisponível após confirmar | `confirmar(...)` chama `slotAgendaService.marcarComoIndisponivel(...)` |
| RN05 — cancelado não reativa | `cancelar(...)` lança `RegraDeNegocioException` se já estiver `CANCELADO` |
| HU04 — cancelar libera o horário | `cancelar(...)` chama `slotAgendaService.liberar(...)` se estava `CONFIRMADO` |

## 4. Rotas criadas

| Entidade | Listar | Novo | Salvar | Editar | Atualizar | Excluir |
|---|---|---|---|---|---|---|
| Paciente | `GET /pacientes` | `GET /pacientes/novo` | `POST /pacientes` | `GET /pacientes/{id}/editar` | `POST /pacientes/{id}/editar` | `POST /pacientes/{id}/excluir` |
| Profissional | `GET /profissionais` | `GET /profissionais/novo` | `POST /profissionais` | `GET /profissionais/{id}/editar` | `POST /profissionais/{id}/editar` | `POST /profissionais/{id}/excluir` |
| Operador | `GET /operadores` | `GET /operadores/novo` | `POST /operadores` | `GET /operadores/{id}/editar` | `POST /operadores/{id}/editar` | `POST /operadores/{id}/excluir` |
| Administrador | `GET /administradores` | `GET /administradores/novo` | `POST /administradores` | `GET /administradores/{id}/editar` | `POST /administradores/{id}/editar` | `POST /administradores/{id}/excluir` |
| SlotAgenda | `GET /slots` | `GET /slots/novo` | `POST /slots` | — (não se edita, só cria/remove) | — | `POST /slots/{id}/excluir` |
| Agendamento | `GET /agendamentos` | `GET /agendamentos/novo` | `POST /agendamentos` (solicitar) | — | `POST /agendamentos/{id}/confirmar` e `POST /agendamentos/{id}/cancelar` | — |

A área toda fica acessível a partir de `/agendamentos`, `/pacientes` etc., ou
pelo link **"Área Administrativa (CRUD)"** dentro do Painel do Paciente
(`/painel`).

## 5. Diagrama de classes

Veja `docs/diagrama-classes.md` (formato Mermaid, renderiza direto no GitHub).

## 6. Como testar o fluxo completo (ponta a ponta)

1. Cadastre um Profissional em `/profissionais/novo`.
2. Cadastre um Paciente em `/pacientes/novo`.
3. Cadastre um Operador em `/operadores/novo`.
4. Cadastre um horário (slot) em `/slots/novo`, escolhendo o profissional criado.
5. Vá em `/agendamentos/novo` e solicite um agendamento (paciente + profissional + horário).
6. Volte para `/agendamentos`: o status estará **Pendente**.
7. Escolha o operador no seletor da linha e clique em **Confirmar**: o status
   muda para **Confirmado** e o slot correspondente (em `/slots`) passa a
   aparecer como **Ocupado**.
8. Clique em **Cancelar**: o status vira **Cancelado** e o slot volta a
   aparecer como **Disponível** em `/slots`.
9. Tente confirmar o mesmo agendamento de novo: o sistema deve recusar
   ("Só é possível confirmar agendamentos com status PENDENTE").

## 7. Próximo passo — seguir o roteiro de entrega da P1

Depois de validar o fluxo acima:

```bash
git checkout -b P1-entrega-final
# copiar/colar os arquivos novos e atualizados
git add .
git commit -m "P1: CRUD completo de todas as entidades + regras de negocio"
git push -u origin P1-entrega-final

git tag -a v1.0.0 -m "Entrega P1 - CRUD Completo de Todas Entidades e Regras de Negocio"
git push origin v1.0.0
```

Depois é só criar a Release no GitHub (tag `v1.0.0`, target = branch
`P1-entrega-final`) seguindo o template que o professor passou, e enviar o
link da Release no formulário.
