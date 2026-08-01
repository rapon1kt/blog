# Plano de Reconstrucao do Blog

Este documento guia a reconstrucao do projeto do zero, com foco em regras de negocio claras, escopo controlado e endpoints que existam porque sustentam jornadas reais do produto.

## 1. Principios do Novo Projeto

- O projeto deve ser um blog social simples, nao uma rede social completa logo no inicio.
- Toda regra de negocio deve nascer de uma jornada real do usuario.
- Endpoints devem representar acoes do produto, nao possibilidades tecnicas.
- O backend deve separar transporte HTTP, casos de uso, dominio e infraestrutura.
- `accountId` deve ser a identidade interna do usuario autenticado.
- `username` deve ser apenas o identificador publico de perfil.
- Controllers nao devem decidir regras de negocio.
- Use cases devem orquestrar regras, repositorios e efeitos colaterais.
- O dominio nao deve depender de Spring, HTTP, MongoDB, JWT ou DTOs.

## 2. Escopo do MVP

O MVP deve provar a experiencia principal: uma pessoa cria conta, personaliza o perfil, publica textos, interage com comentarios e controla minimamente sua privacidade.

### 2.1 Auth

Objetivo: permitir entrada segura no sistema.

Funcionalidades:

- Criar conta.
- Fazer login.
- Gerar JWT.
- Validar JWT nas rotas privadas.
- Retornar dados basicos do usuario autenticado.

Regras:

- Email deve ser unico.
- Username deve ser unico.
- Senha deve ser armazenada com hash.
- Token deve carregar `accountId` como subject.
- Login nao deve revelar se email/username ou senha esta errado.

Endpoints sugeridos:

- `POST /auth/sign-up`
- `POST /auth/sign-in`

### 2.2 Account

Objetivo: permitir que o usuario gerencie sua propria identidade no blog.

Funcionalidades:

- Buscar minha conta.
- Atualizar username, bio e avatar.
- Alterar senha.
- Excluir conta.
- Ver perfil publico por username.

Regras:

- Usuario so pode editar a propria conta.
- Perfil publico nao deve expor email, senha, role ou flags internas.
- Username deve continuar unico apos atualizacao.
- Exclusao de conta deve exigir senha atual.

Endpoints sugeridos:

- `GET /accounts/me`
- `PATCH /accounts/me`
- `PATCH /accounts/me/password`
- `DELETE /accounts/me`
- `GET /accounts/{username}`

### 2.3 User Preferences

Objetivo: manter configuracoes do usuario sem misturar preferencias com regras centrais de conta.

Funcionalidades:

- Definir tema preferido.
- Definir idioma preferido.
- Controlar se o perfil exibe email ou nao, caso essa feature exista.
- Controlar notificacoes futuras, mesmo que notificacoes nao sejam MVP.

Regras:

- Preferencias pertencem a uma conta.
- Preferencias devem ter valores padrao na criacao da conta.
- Preferencias nao devem alterar permissao ou seguranca.

Modelo inicial sugerido:

- `accountId`
- `theme`: `SYSTEM`, `LIGHT`, `DARK`
- `language`: `PT_BR`, `EN_US`
- `emailVisible`: `false`
- `notifyOnComments`: `true`
- `notifyOnReplies`: `true`

Endpoints sugeridos:

- `GET /accounts/me/preferences`
- `PATCH /accounts/me/preferences`

### 2.4 Posts

Objetivo: permitir publicacao e leitura de textos.

Funcionalidades:

- Criar post.
- Atualizar post.
- Excluir post.
- Buscar post por ID.
- Listar posts publicos recentes.
- Listar posts publicos de um usuario.
- Fixar post no perfil.
- Alterar visibilidade.

Regras:

- Apenas o autor pode editar ou excluir seu post.
- MVP deve ter apenas `PUBLIC` e `PRIVATE`.
- Post privado so pode ser visto pelo autor.
- Admin/moderador nao deve ser necessario no MVP.
- Um usuario pode fixar no maximo 3 posts publicos.
- Se um post fixado virar privado, ele deve ser desafixado.

Endpoints sugeridos:

- `POST /posts`
- `GET /posts`
- `GET /posts/{postId}`
- `PATCH /posts/{postId}`
- `PATCH /posts/{postId}/visibility`
- `PATCH /posts/{postId}/pin`
- `DELETE /posts/{postId}`
- `GET /accounts/{username}/posts`

### 2.5 Comments

Objetivo: permitir conversa em torno de posts.

Funcionalidades:

- Comentar em post.
- Responder comentario.
- Listar comentarios de post.
- Listar respostas de comentario.
- Excluir comentario.

Regras:

- Usuario autenticado pode comentar em post publico.
- Autor do comentario pode excluir o proprio comentario.
- Autor do post pode excluir comentarios feitos no proprio post.
- Resposta pertence ao mesmo post do comentario original.
- Comentario em post privado so deve ser permitido ao autor do post.

Endpoints sugeridos:

- `POST /posts/{postId}/comments`
- `GET /posts/{postId}/comments`
- `POST /comments/{commentId}/replies`
- `GET /comments/{commentId}/replies`
- `DELETE /comments/{commentId}`

### 2.6 Likes

Objetivo: permitir uma interacao simples sem inflar o dominio.

Funcionalidades:

- Curtir post.
- Descurtir post.
- Curtir comentario.
- Descurtir comentario.

Regras:

- MVP deve ter apenas `LIKE`.
- Um usuario pode curtir cada alvo uma vez.
- Repetir a acao alterna entre curtir e descurtir.
- O alvo pode ser `POST` ou `COMMENT`.
- Contagem de likes deve ser atualizada junto com a acao.

Endpoints sugeridos:

- `POST /posts/{postId}/like`
- `DELETE /posts/{postId}/like`
- `POST /comments/{commentId}/like`
- `DELETE /comments/{commentId}/like`

## 3. Fora do MVP

Estas features devem ficar fora da primeira versao para evitar complexidade prematura:

- Follow/followers/following.
- Block/unblock.
- Ban/unban.
- Admin panel.
- Notifications.
- Reports com painel de moderacao.
- Reactions multiplas como `LOVE`, `LAUGH`, `SAD`, `ANGRY`.
- Visibilidade `FOLLOWERS`.
- Visibilidade `FRIENDS`.
- Feed personalizado.
- Upload complexo de imagens.

## 4. Segunda Fase

Depois do MVP estar estavel, implementar features sociais com regras bem definidas.

### 4.1 Follow

Funcionalidades:

- Seguir usuario.
- Deixar de seguir usuario.
- Listar seguidores.
- Listar seguindo.

Regras:

- Usuario nao pode seguir a si mesmo.
- Follow deve ser unico por par `followerId` e `followingId`.
- A existencia de follow pode liberar a visibilidade `FOLLOWERS`.

Endpoints sugeridos:

- `POST /accounts/{username}/follow`
- `DELETE /accounts/{username}/follow`
- `GET /accounts/{username}/followers`
- `GET /accounts/{username}/following`

### 4.2 Reports

Funcionalidades:

- Denunciar post.
- Denunciar comentario.

Regras:

- Usuario nao pode denunciar proprio conteudo.
- Usuario nao pode denunciar o mesmo alvo mais de uma vez.
- Report deve ficar com status `OPEN`.

Endpoints sugeridos:

- `POST /posts/{postId}/reports`
- `POST /comments/{commentId}/reports`

### 4.3 Notifications

Funcionalidades:

- Notificar comentario em post.
- Notificar resposta em comentario.
- Notificar novo follower.
- Listar notificacoes.
- Marcar notificacao como lida.

Regras:

- Usuario nao deve receber notificacao de acao feita por ele mesmo.
- Notificacoes devem respeitar preferencias do usuario.

Endpoints sugeridos:

- `GET /notifications`
- `PATCH /notifications/{notificationId}/read`
- `PATCH /notifications/read-all`

## 5. Terceira Fase

Fase dedicada a moderacao e seguranca social.

### 5.1 Block

Funcionalidades:

- Bloquear usuario.
- Desbloquear usuario.

Regras:

- Usuario nao pode bloquear a si mesmo.
- Usuario bloqueado nao pode comentar, responder ou curtir conteudo do bloqueador.
- Bloqueio deve remover follow entre as contas.

Endpoints sugeridos:

- `POST /accounts/{username}/block`
- `DELETE /accounts/{username}/block`

### 5.2 Moderacao

Funcionalidades:

- Listar reports abertos.
- Resolver report.
- Aplicar ban temporario.
- Remover ban.

Regras:

- Apenas usuario com role `ADMIN` ou `MODERATOR` pode moderar.
- Ban deve ter motivo, descricao, moderador, data inicial e data final.
- Ban ativo deve impedir login ou acoes privadas.
- Unban deve mudar status do ban para `REVOKED`, nao para `ACTIVE`.

Endpoints sugeridos:

- `GET /admin/reports`
- `PATCH /admin/reports/{reportId}/resolve`
- `POST /admin/accounts/{username}/ban`
- `DELETE /admin/accounts/{username}/ban`

## 6. Arquitetura Recomendada

Estrutura sugerida:

```text
src/main/java/com/raponi/blog
  domain
    model
    policy
    exception
    repository
    port
  application
    usecase
    service
    command
    result
  infrastructure
    persistence
    security
    storage
    config
  presentation
    controller
    dto
    mapper
    errors
```

Regras de dependencia:

- `presentation` pode chamar `application`.
- `application` pode chamar `domain`.
- `infrastructure` implementa portas e repositorios do `domain`.
- `domain` nao conhece nenhuma outra camada.
- DTOs nao devem entrar no dominio.
- Entidades Mongo nao devem sair da infraestrutura.

## 7. Policies de Negocio

Criar policies pequenas e explicitas:

- `AccountAccessPolicy`: decide se usuario pode ver, editar ou excluir uma conta.
- `PostVisibilityPolicy`: decide se usuario pode ver post.
- `PostOwnershipPolicy`: decide se usuario pode editar, excluir ou fixar post.
- `CommentAccessPolicy`: decide se usuario pode comentar, responder ou excluir comentario.
- `LikePolicy`: decide se usuario pode curtir determinado alvo.
- `ModerationPolicy`: decide se usuario pode moderar.

Essas policies devem receber dados simples do dominio, nao `Authentication`, `HttpServletRequest` ou DTO.

## 8. Modelo Inicial de Dominio

Entidades do MVP:

- `Account`
- `UserPreferences`
- `Post`
- `Comment`
- `Like`

Entidades futuras:

- `Follow`
- `Report`
- `Notification`
- `Block`
- `Ban`

Enums do MVP:

- `AccountRole`: `USER`
- `PostVisibility`: `PUBLIC`, `PRIVATE`
- `ThemePreference`: `SYSTEM`, `LIGHT`, `DARK`
- `LanguagePreference`: `PT_BR`, `EN_US`
- `LikeTargetType`: `POST`, `COMMENT`

Enums futuros:

- `AccountRole`: adicionar `ADMIN`, `MODERATOR`
- `PostVisibility`: adicionar `FOLLOWERS`
- `ReportStatus`: `OPEN`, `RESOLVED`, `DISMISSED`
- `BanStatus`: `ACTIVE`, `EXPIRED`, `REVOKED`

## 9. Ordem de Implementacao

1. Criar projeto limpo Spring Boot.
2. Configurar MongoDB, validation, security e JWT.
3. Criar estrutura de pacotes.
4. Criar exceptions e handler global.
5. Implementar `Account` e `UserPreferences`.
6. Implementar auth.
7. Implementar conta autenticada e perfil publico.
8. Implementar posts.
9. Implementar comentarios.
10. Implementar likes.
11. Adicionar testes unitarios dos use cases.
12. Adicionar testes de controller para rotas principais.
13. Revisar contratos de API.
14. Documentar endpoints.
15. So entao iniciar features da segunda fase.

## 10. Checklist de Qualidade

Antes de considerar uma etapa pronta:

- O projeto compila.
- O endpoint tem DTO de request e response.
- O use case nao depende de HTTP.
- A regra principal tem teste unitario.
- O controller tem pelo menos um teste de sucesso e um de erro.
- O erro retorna status HTTP coerente.
- Nao ha `return null` para representar falha de regra.
- Nao ha `Optional.get()` sem tratamento anterior.
- Nao ha `ResponseEntity<?>` em controller finalizado.
- Nao ha entidade de dominio sendo exposta sem DTO.

## 11. Contratos Importantes

Identidade:

- JWT subject = `accountId`.
- Username = identificador publico.
- Services recebem `accountId` quando a acao depende do usuario autenticado.
- Services recebem `username` apenas quando a acao e sobre perfil publico.

Autorizacao:

- Autor pode editar e excluir seu proprio conteudo.
- Autor do post pode moderar comentarios do proprio post.
- Usuario comum nao acessa rotas admin.
- Admin/moderator so entram depois da terceira fase.

Visibilidade:

- `PUBLIC`: qualquer usuario autenticado pode ver.
- `PRIVATE`: apenas autor pode ver.
- `FOLLOWERS`: implementar apenas quando follow existir.
- `FRIENDS`: nao implementar ate existir conceito real de amizade.

## 12. Primeiro Sprint Recomendado

Meta: autenticar usuario e entregar perfil basico com preferencias.

Entregas:

- Projeto limpo compilando.
- `POST /auth/sign-up`.
- `POST /auth/sign-in`.
- `GET /accounts/me`.
- `PATCH /accounts/me`.
- `GET /accounts/me/preferences`.
- `PATCH /accounts/me/preferences`.
- Testes unitarios de auth, conta e preferencias.
- Teste de controller para login e `GET /accounts/me`.

Ao final desse sprint, o projeto deve ter uma fundacao confiavel antes de posts e comentarios entrarem.
