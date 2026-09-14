# Diagrama de Classes — Sistema de Clínica (ZenConsulta)

> Este diagrama foi reconstruído a partir dos documentos de requisitos (RF/RN),
> Épicos e Histórias de Usuário do projeto, já que o diagrama original não foi
> localizado. Ele reflete exatamente as entidades e campos implementados no
> código desta entrega (P1).

Cole o bloco abaixo em https://mermaid.live para visualizar/editar, ou deixe
aqui mesmo no repositório (o GitHub renderiza blocos ```mermaid automaticamente
em arquivos .md).

```mermaid
classDiagram
  class Paciente {
    +Long id
    +String nome
    +String email
    +String telefone
  }
  class Profissional {
    +Long id
    +String nome
    +String especialidade
    +String email
  }
  class Operador {
    +Long id
    +String nome
    +String email
  }
  class Administrador {
    +Long id
    +String nome
    +String email
  }
  class SlotAgenda {
    +Long id
    +Long profissionalId
    +String data
    +String horario
    +boolean disponivel
  }
  class Agendamento {
    +Long id
    +Long pacienteId
    +Long profissionalId
    +Long slotAgendaId
    +Long operadorResponsavelId
    +StatusAgendamento status
  }
  class StatusAgendamento {
    <<enumeration>>
    PENDENTE
    CONFIRMADO
    CANCELADO
  }

  Profissional "1" --> "many" SlotAgenda : define
  Paciente "1" --> "many" Agendamento : solicita
  Profissional "1" --> "many" Agendamento : atende
  SlotAgenda "1" --> "0..1" Agendamento : reservado em
  Operador "1" --> "many" Agendamento : confirma
  Agendamento --> StatusAgendamento : possui
```

## Justificativa das relações

| Relação | Motivo (RF/RN) |
|---|---|
| `Profissional -> SlotAgenda` | RF01: profissional define seus horários |
| `Paciente -> Agendamento` | RF04/RN04: paciente solicita agendamentos |
| `Profissional -> Agendamento` | RF09: todo agendamento é vinculado a um profissional |
| `SlotAgenda -> Agendamento` (0..1) | RF09/RN02: um slot vira "ocupado" por no máximo 1 agendamento confirmado por vez |
| `Operador -> Agendamento` | RF06/RF12/RN03: operador confirma e fica registrado como responsável |
