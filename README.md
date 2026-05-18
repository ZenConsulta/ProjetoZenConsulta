🏥 Agendamento de Consultório

Sistema de agendamento para clínicas e consultórios médicos, desenvolvido para facilitar o gerenciamento de horários entre pacientes e profissionais da saúde de forma prática, rápida e organizada.

Projeto desenvolvido por Patryck Rangel e Caio Cardoso.

📌 Sobre o Projeto

O sistema foi criado com o objetivo de simplificar o processo de marcação de consultas, permitindo que profissionais organizem suas agendas e que pacientes reservem horários disponíveis com segurança e praticidade.

A aplicação realiza validações automáticas para evitar conflitos de horários e garantir uma melhor organização dos atendimentos.

🚀 Funcionalidades
👨‍⚕️ Área do Profissional
Cadastro de profissionais
Cadastro de especialidades
Definição de horários disponíveis
Gerenciamento da agenda
Visualização de consultas agendadas
👤 Área do Paciente
Visualização de profissionais disponíveis
Consulta de especialidades
Busca de horários livres
Agendamento de consultas
Confirmação imediata da reserva
⚙️ Regras de Negócio
Um profissional não pode possuir dois agendamentos no mesmo horário
Um paciente não pode reservar um horário já ocupado
Apenas horários disponíveis podem ser reservados
Profissionais só aparecem disponíveis quando possuem horários livres
Cancelamentos seguem regras de antecedência definidas pelo sistema
🧠 Lógica Principal

O sistema possui como entidade central o Agendamento, responsável por conectar:

Paciente + Profissional + Horário = Consulta Agendada

Antes da confirmação de uma consulta, o sistema verifica automaticamente:

✅ Disponibilidade do horário
✅ Conflitos de agenda
✅ Existência do profissional e paciente

🗂️ Estrutura das Entidades
Profissional
id
nome
especialidade
Paciente
id
nome
Slot de Agenda
id
profissional_id
data_hora
disponivel
Agendamento
id
paciente_id
profissional_id
slot_id
status
🛠️ Tecnologias Utilizadas

Atualize conforme a stack utilizada no projeto.

Front-end
HTML
CSS
JavaScript
Back-end
Node.js
Banco de Dados
MySQL
Ferramentas
Git
GitHub
📂 Estrutura do Projeto
📦 agendamento-consultorio
 ┣ 📂 src
 ┣ 📂 public
 ┣ 📂 components
 ┣ 📂 routes
 ┣ 📂 database
 ┣ 📜 package.json
 ┗ 📜 README.md
🚀 Como Executar o Projeto
Clone o repositório
git clone https://github.com/seu-usuario/agendamento-consultorio.git
Acesse a pasta do projeto
cd agendamento-consultorio
Instale as dependências
npm install
Configure as variáveis de ambiente
cp .env.example .env
Execute o projeto
npm run dev
📸 Preview do Sistema

Adicione aqui imagens ou GIFs da aplicação.

![Preview do Sistema](./preview.png)
🎯 Objetivo Acadêmico

Este projeto foi desenvolvido para fins de estudo e prática de desenvolvimento de software, aplicando conceitos de:

CRUD
Relacionamento entre entidades
Regras de negócio
Organização de agendas
Validações de sistema
Desenvolvimento full stack
📈 Melhorias Futuras
Sistema de login e autenticação
Dashboard administrativo
Histórico de consultas
Notificações por e-mail
Integração com WhatsApp
Responsividade mobile
Painel do administrador
🤝 Contribuição

Contribuições são bem-vindas.

Sinta-se à vontade para abrir issues, sugerir melhorias ou enviar pull requests.

📄 Licença

Este projeto está sob a licença MIT. Consulte o arquivo LICENSE para mais informações.
