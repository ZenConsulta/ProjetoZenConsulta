# 📋 Requisitos do Sistema — Agendamento de Consultório

> Documento de requisitos funcionais e não funcionais do sistema de agendamento de consultas médicas.

---

## ✅ Requisitos Funcionais

### 👨‍⚕️ Profissional
- **RF01** — O profissional deve poder definir seus horários de atendimento (SlotAgenda)
- **RF02** — O profissional deve poder visualizar todas as consultas agendadas para o dia

### 🧑‍💼 Paciente
- **RF03** — O paciente deve poder visualizar os profissionais disponíveis e suas especialidades
- **RF04** — O paciente deve poder solicitar um agendamento em um horário disponível

### 🖥️ Operador (Recepcionista)
- **RF05** — O operador deve poder cadastrar novos pacientes no sistema
- **RF06** — O operador deve poder confirmar agendamentos solicitados pelos pacientes

### 🔑 Administrador
- **RF07** — O administrador deve poder gerenciar todos os agendamentos (visualizar, cancelar e supervisionar)
- **RF08** — O administrador deve poder gerenciar os usuários do sistema (profissionais, operadores e pacientes)

### 📅 Agendamento
- **RF09** — O sistema deve vincular Paciente, Profissional e SlotAgenda em um agendamento
- **RF10** — O sistema deve verificar conflito de horário antes de confirmar qualquer agendamento
- **RF11** — O sistema deve permitir confirmar ou cancelar um agendamento
- **RF12** — O sistema deve registrar qual operador foi responsável pela confirmação do agendamento
- **RF13** — O sistema deve controlar o status do agendamento seguindo o fluxo: `PENDENTE → CONFIRMADO → CANCELADO`

### 🕐 SlotAgenda
- **RF14** — O sistema deve verificar a disponibilidade do slot antes de permitir uma reserva
- **RF15** — O sistema deve marcar o slot como indisponível após a confirmação de um agendamento

---

## 🔒 Requisitos Não Funcionais

### Segurança
- **RNF01** — Cada perfil (Profissional, Paciente, Operador, Administrador) deve ter acesso restrito apenas às suas funcionalidades
- **RNF02** — As senhas dos usuários devem ser armazenadas com criptografia 

### Desempenho
- **RNF03** — A verificação de conflito de horário deve ser realizada de forma síncrona antes de qualquer confirmação de agendamento

### Manutenibilidade
- **RNF04** — O código deve ser organizado em camadas (Controller, Service, Repository), seguindo as boas práticas do Spring Boot

### Confiabilidade
- **RNF05** — O sistema não deve permitir que dois agendamentos ocupem o mesmo slot do mesmo profissional simultaneamente
- **RNF06** — O status do agendamento deve sempre refletir o estado real da consulta

### Usabilidade
- **RNF07** — A API deve retornar mensagens de erro claras e descritivas quando uma regra de negócio for violada (ex: `"Horário já ocupado"`, `"Paciente não encontrado"`)

---

## 📌 Regras de Negócio

| # | Regra |
|---|---|
| RN01 | Um profissional não pode ter dois agendamentos confirmados no mesmo horário |
| RN02 | Um slot só pode ser reservado se estiver com `disponivel = true` |
| RN03 | Somente o Operador ou Administrador pode confirmar um agendamento |
| RN04 | O Paciente apenas solicita — quem confirma é o Operador |
| RN05 | Um agendamento cancelado não pode ser reativado, apenas um novo deve ser criado |
