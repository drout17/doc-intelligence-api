# Dev Journal — Doc Intelligence API
 
---

## Date: 19 May 2026

### What we built today:

**Goal:** Project setup and local development environment ready.

#### 1. Created Spring Boot Project
- Spring Boot `3.5.x`
- Java `21`
- Maven
- Dependencies added:
    - Spring Web
    - Spring Data JPA
    - PostgreSQL Driver
    - Lombok
- Package name: `com.docintelligence.docapi`
#### 2. Fixed Java/Maven mismatch
- `java -version` and `mvn -version` were pointing to different Java versions
- Fixed by setting `JAVA_HOME` to Java 21 permanently in `~/.zshrc`
#### 3. Setup pgvector using Docker
- Ran `ankane/pgvector` Docker image locally
- Database config:
    - Host: `localhost`
    - Port: `5432`
    - Database: `docdb`
    - Username: `docuser`
    - Password: `docpass`
- Verified container running via `docker ps`
#### 4. Connected Spring Boot to pgvector
- Configured `application.yml` with datasource and JPA settings
- `ddl-auto: update` — Hibernate auto manages schema
- Application started successfully with DB connection ✅
#### 5. Pushed to Personal GitHub
- Resolved office vs personal GitHub account conflict on office laptop
- Used Personal Access Token (PAT) embedded in remote URL
- Chrome → Office GitHub | Safari → Personal GitHub
- Repo live at: `https://github.com/drout17/doc-intelligence-api`
### Key concepts learned today:
- **pgvector** — PostgreSQL extension to store and search vectors (embeddings)
- **RAG (Retrieval Augmented Generation)** — The architecture we are building
- **Vectors/Embeddings** — How AI converts text into numbers to understand meaning
- **T-shaped skills** — Go deep in 1-2 areas, stay aware across all
- **chmod 755** — Fix folder permission issues on Mac
- **Personal Access Token** — GitHub's way of authenticating via terminal
### What we are building:
A **Smart Document Intelligence API** — upload any document, ask questions in plain English, get back cited answers powered by AI.

```
PDF uploaded → chunked → embedded → stored in pgvector
Question asked → embedded → similarity search → LLM → cited answer
```

-------------------  End ----------------
 