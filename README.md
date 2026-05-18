# 🏥 Agendamento de Consultório

Sistema de agendamento para clínicas e consultórios médicos, desenvolvido para facilitar o gerenciamento de horários entre pacientes e profissionais da saúde de forma prática, rápida e organizada.

Projeto desenvolvido por **Patryck Rangel** e **Caio Cardoso**.

---

# 📌 Sobre o Projeto

O sistema foi criado com o objetivo de simplificar o processo de marcação de consultas, permitindo que profissionais organizem suas agendas e que pacientes reservem horários disponíveis com segurança e praticidade.

A aplicação realiza validações automáticas para evitar conflitos de horários e garantir uma melhor organização dos atendimentos.

---

# 🚀 Funcionalidades

## 👨‍⚕️ Área do Profissional

- Cadastro de profissionais
- Cadastro de especialidades
- Definição de horários disponíveis
- Gerenciamento da agenda
- Visualização de consultas agendadas

## 👤 Área do Paciente

- Visualização de profissionais disponíveis
- Consulta de especialidades
- Busca de horários livres
- Agendamento de consultas
- Confirmação imediata da reserva

---

# ⚙️ Regras de Negócio

- Um profissional não pode possuir dois agendamentos no mesmo horário
- Um paciente não pode reservar um horário já ocupado
- Apenas horários disponíveis podem ser reservados
- Profissionais só aparecem disponíveis quando possuem horários livres
- Cancelamentos seguem regras de antecedência definidas pelo sistema

---

# 🧠 Lógica Principal

O sistema possui como entidade central o **Agendamento**, responsável por conectar:

```text
Paciente + Profissional + Horário = Consulta Agendada
