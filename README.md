# LiterAlura  

## Descrição  

O **LiterAlura** é uma aplicação desenvolvida em Java que consome dados da API externa Gutemberbex para facilitar a busca e organização de informações sobre livros e autores. A aplicação permite que os usuários pesquisem livros por título e idioma, além de gerenciar dados locais sobre livros e autores.  

## Funcionalidades  

- Buscar livros na API externa pelo título e idioma.  
- Listar livros e autores registrados no banco de dados.  
- Pesquisar autores vivos em um determinado ano.  
- Filtrar livros por idioma.  
- Menu interativo para navegação entre as funcionalidades.  

## Tecnologias Utilizadas  

- **Linguagem**: Java  
- **Framework**: Spring Boot  
- **ORM**: JPA (Jakarta Persistence API) com Hibernate  
- **Banco de Dados**: H2 (em memória)  
- **Cliente HTTP**: `java.net.http.HttpClient`  
- **Processamento de JSON**: Jackson (`ObjectMapper`)  

## Estrutura do Projeto  

- **`service.model`**: Contém a classe `ConsumoApi` para comunicação com a API externa.  
- **`service.model.entities`**: Inclui as entidades JPA, como `Livro`.  
- **`menu`**: Gerencia o fluxo da aplicação e a interação com o usuário.  

## Pré-requisitos  

1. JDK 17 ou superior instalado.  
2. Maven instalado (ou use a wrapper do Maven).  

## Configuração  

1. Clone o repositório:  
   ```bash  
   git clone https://github.com/usuario/literalura.git  
   ```  

2. Navegue até o diretório do projeto:  
   ```bash  
   cd literalura  
   ```  

3. Configure o arquivo `application.properties` em `src/main/resources/` com as informações do banco de dados H2:  
   ```properties  
   # Configuração do H2  
   spring.datasource.url=jdbc:h2:mem:literAlura;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE  
   spring.datasource.username=sa  
   spring.datasource.password=sa  
   spring.datasource.driver-class-name=org.h2.Driver  

   # Configurações JPA/Hibernate  
   spring.jpa.hibernate.ddl-auto=update  
   spring.jpa.show-sql=true  
   spring.jpa.properties.hibernate.format_sql=true  

   # Console do H2 (opcional)  
   spring.h2.console.enabled=true  
   spring.h2.console.path=/h2-console  
   ```  

4. Compile e execute a aplicação:  
   ```bash  
   mvn spring-boot:run  
   ```  

## Como Usar  

Após iniciar a aplicação, o menu principal será exibido. Escolha uma das opções para acessar as funcionalidades:  

1. **Buscar livro pelo título**: Insira o título do livro e o idioma desejado.  
2. **Listar livros registrados**: Exibe todos os livros salvos no banco de dados.  
3. **Listar autores registrados**: Exibe todos os autores salvos.  
4. **Listar autores vivos em determinado ano**: Filtra autores vivos em um ano específico.  
5. **Listar livros em um determinado idioma**: Filtra os livros pelo idioma especificado.  

## Estrutura de Classes  

- **`ConsumoApi`**: Realiza a comunicação com a API Gutemberbex e processa os dados recebidos.  
- **`Livro`**: Entidade que representa um livro no banco de dados.  
- **`Menu`**: Gerencia as interações com o usuário.  

## Contribuição  

Contribuições são bem-vindas! Siga as etapas abaixo para contribuir:  

1. Faça um fork do repositório.  
2. Crie uma branch para suas alterações:  
   ```bash  
   git checkout -b minha-feature  
   ```  
3. Commit suas alterações:  
   ```bash  
   git commit -m "Descrição da alteração"  
   ```  
4. Envie suas alterações:  
   ```bash  
   git push origin minha-feature  
   ```  
5. Abra um Pull Request.  

## Licença  

Este projeto está licenciado sob a [Licença MIT](LICENSE).  
