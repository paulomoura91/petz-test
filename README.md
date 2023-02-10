# petz-test
Petz Android Test - Hearthstone App

Resumo do que foi implementado:

- Foi utilizado MVVM como padrão arquitetural
- Repository pattern para gerenciar o acesso aos dados (neste caso, remoto, porém escalável para acesso local se necessário)
- Hilt como framework de injeção de dependências
- Coroutines e Retrofit para realizar as operações de rede
- ViewModel usado como "ponte" entre as camadas de visualização e dados, mas também para salvar o estado da aplicação nos eventos de configuration changes
- Livedata responsável por atualizar a view após requisições e também para manter o estado
- UI simples, porém responsiva e com suporte para Night Mode
- Optei por iniciar o App com uma lista de Sets de cartas pois o endpoint que retorna todas cartas tem muitos registros
- Ao selecionar um Set, outra lista é exibida com suas respectivas cartas
- Ao selecionar uma carta, uma tela com detalhes da mesma é exibida com os dados descritos nas instruções do desafio
- Foram criados testes para todas as camadas
- Testes unitários para as classes de dados, estado e para o Repository
- Foram criados testes de validação dos modelos no ViewModel
- Testes de Interface foram criados para todas as telas, rodando localmente com Robolectric. A ideia principal foi testar o estado da tela e os User Events
- Os testes de UI foram relativamente simples de serem escritos pois estruturei bem as camadas com baixo acoplamento
- Mocks foram usados quando necessário
