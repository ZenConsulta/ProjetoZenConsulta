# 🏥 Agendamento de Consultório

Sistema de agendamento para clínicas e consultórios médicos, desenvolvido para facilitar o gerenciamento de horários entre pacientes e profissionais da saúde de forma prática, rápida e organizada.

Projeto desenvolvido por **Patryck Rangel** e **Caio Cardoso**.

---

# 📌 Sobre o Projeto

O sistema foi criado com o objetivo de simplificar o processo de marcação de consultas, permitindo que profissionais organizem suas agendas e que pacientes reservem horários disponíveis com segurança e praticidade.

Além disso, o sistema também oferece funcionalidades para operadores e administradores, permitindo melhor controle da clínica, organização dos atendimentos e gerenciamento das informações do sistema.

A aplicação realiza validações automáticas para evitar conflitos de horários e garantir uma melhor organização dos atendimentos.

A proposta do sistema é ser leve, intuitiva e eficiente, sendo ideal para clínicas e consultórios com poucos profissionais.

---

# 👥 Perfis de Usuário

| Perfil | Descrição |
|---|---|
| **Administrador** | Responsável pelo gerenciamento geral do sistema, controle de usuários e administração da clínica |
| **Operador** | Atua como intermediador entre pacientes e profissionais, realizando agendamentos e organizando atendimentos |
| **Profissional** | Define horários disponíveis, gerencia sua agenda e visualiza consultas agendadas |
| **Paciente** | Consulta horários livres e realiza agendamentos de consultas |

---

# 🚀 Funcionalidades

## 👨‍💼 Área do Administrador

- Gerenciamento geral do sistema
- Controle de usuários
- Cadastro e gerenciamento de profissionais
- Visualização de relatórios e atendimentos
- Controle administrativo da clínica

## 🧑‍💻 Área do Operador

- Cadastro de pacientes
- Realização de agendamentos
- Organização da agenda dos profissionais
- Cancelamento e remarcação de consultas
- Atendimento e suporte aos pacientes

## 👨‍⚕️ Área do Profissional

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
- Operadores podem gerenciar consultas em nome dos pacientes
- Administradores possuem acesso total ao sistema
- Cancelamentos seguem regras de antecedência definidas pelo sistema

---

# 🧠 Lógica Principal

O sistema possui como entidade central o **Agendamento**, responsável por conectar:

```text
Paciente + Profissional + Horário = Consulta Agendada
```

Antes da confirmação de uma consulta, o sistema verifica automaticamente:

- ✅ Disponibilidade do horário
- ✅ Conflitos de agenda
- ✅ Existência do profissional
- ✅ Existência do paciente

---

# 🗂️ Estrutura das Entidades

## Administrador

```text
id
nome
email
senha
```

## Operador

```text
id
nome
email
senha
```

## Profissional

```text
id
nome
especialidade
```

## Paciente

```text
id
nome
```

## Slot de Agenda

Representa os horários disponíveis para atendimento.

```text
id
profissional_id
data_hora
disponivel
```

## Agendamento

```text
id
paciente_id
profissional_id
slot_id
status
```

---

# 🔄 Relação Entre as Entidades

```text
Administrador
   └── gerencia → Sistema

Operador
   └── organiza → Agendamentos

Profissional
   └── possui → Slots de Agenda

Paciente
   └── realiza → Agendamentos

Agendamento
   └── conecta → Paciente + Profissional + Slot
```

---

# 🛠️ Tecnologias Utilizadas

> Atualize conforme a stack utilizada no projeto.

## Front-end

- HTML
- CSS
- JavaScript

## Back-end

- Node.js

## Banco de Dados

- MySQL

## Ferramentas

- Git
- GitHub

---

# 📂 Estrutura do Projeto

```bash
📦 agendamento-consultorio
 ┣ 📂 src
 ┣ 📂 public
 ┣ 📂 components
 ┣ 📂 routes
 ┣ 📂 database
 ┣ 📜 package.json
 ┗ 📜 README.md
```

---

# 🚀 Como Executar o Projeto

## Clone o repositório

```bash
git clone https://github.com/seu-usuario/agendamento-consultorio.git
```

## Acesse a pasta do projeto

```bash
cd agendamento-consultorio
```

## Instale as dependências

```bash
npm install
```

## Configure as variáveis de ambiente

```bash
cp .env.example .env
```

## Execute o projeto

```bash
npm run dev
```

---

# 📸 Preview do Sistema

> Adicione aqui imagens ou GIFs da aplicação.

```md
![Preview do Sistema](./preview.png)
```

---

# 🎯 Objetivo Acadêmico

Este projeto foi desenvolvido para fins de estudo e prática de desenvolvimento de software, aplicando conceitos de:

- CRUD
- Relacionamento entre entidades
- Regras de negócio
- Organização de agendas
- Validações de sistema
- Controle de permissões
- Desenvolvimento full stack

---

# 📈 Melhorias Futuras

- Sistema de login e autenticação
- Dashboard administrativo
- Histórico de consultas
- Notificações por e-mail
- Integração com WhatsApp
- Responsividade mobile
- Painel do administrador
- Controle de permissões por perfil

---

# 🤝 Contribuição

Contribuições são bem-vindas.

Sinta-se à vontade para abrir issues, sugerir melhorias ou enviar pull requests.

---

# 📄 Licença

Este projeto está sob a licença MIT. Consulte o arquivo `LICENSE` para mais informações.
