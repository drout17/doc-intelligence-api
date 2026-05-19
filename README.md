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

---

## Date: 19 May 2026 (Session 2)

### What we built today:

**Goal:** Document ingestion pipeline — upload PDF, extract text, save to database.

#### 1. Added PDFBox Dependency
- Added `org.apache.pdfbox:pdfbox:3.0.1` to `pom.xml`
- Used for extracting text from uploaded PDF files
- **Important:** PDFBox 3.x changed API — `PDDocument.load()` replaced with `Loader.loadPDF()`

#### 2. Created Project Structure
Following standard Spring Boot layered architecture:
```
com.docintelligence.docapi
├── model/
│   └── Document.java        ← Database entity
├── repository/
│   └── DocumentRepository   ← DB operations (JpaRepository)
├── service/
│   └── DocumentService      ← Business logic
└── controller/
    └── DocumentController   ← REST API endpoints
```

#### 3. Document Entity (`Document.java`)
- Fields: `id`, `fileName`, `fileType`, `content`, `uploadedAt`
- `@PrePersist` auto sets `uploadedAt` timestamp before saving
- Hibernate auto creates `documents` table in PostgreSQL

#### 4. DocumentRepository
- Extends `JpaRepository<Document, Long>`
- Gets `save()`, `findById()`, `findAll()`, `deleteById()` for free
- Zero boilerplate — Spring Data JPA handles everything

#### 5. DocumentService
- `uploadDocument()` — accepts MultipartFile, extracts text, saves to DB
- `extractText()` — handles PDF (via PDFBox) and plain text files
- `getAllDocuments()` — returns all documents from DB

#### 6. DocumentController
- `POST /api/documents/upload` — upload PDF or text file
- `GET /api/documents` — get all uploaded documents
- Uses `@RequestPart` for proper multipart file handling in Swagger

#### 7. Swagger UI Integration
- Added `springdoc-openapi-starter-webmvc-ui:2.5.0`
- Swagger UI available at: `http://localhost:8080/swagger-ui/index.html`
- `@Operation` annotation adds descriptions to endpoints
- `consumes = MULTIPART_FORM_DATA_VALUE` shows file upload button in Swagger

#### 8. Tested Successfully
- Uploaded `sample-ai-policy.pdf` via Swagger UI
- PDF text extracted correctly and saved to PostgreSQL
- Response returned with `id: 1` and full extracted content ✅

### Key concepts learned today:
- **Layered Architecture** — Controller → Service → Repository → DB
- **MultipartFile** — Spring's way of handling file uploads
- **JpaRepository** — Zero boilerplate DB operations
- **@PrePersist** — Auto execute logic before saving to DB
- **PDFBox 3.x API change** — `Loader.loadPDF()` replaces `PDDocument.load()`
- **Swagger/OpenAPI** — Professional API documentation and testing UI
- **@RequestPart vs @RequestParam** — Use `@RequestPart` for multipart in Swagger

### Why current state is NOT enough (yet):
Right now we are just saving raw text to Postgres — no different from any basic CRUD app.

```
CURRENT:  PDF → extract text → save as blob → dumb storage ❌
NEXT:     PDF → extract text → CHUNK → EMBED → store vectors → smart search ✅
```

### What's next — Session 3:
- Split document content into meaningful chunks
- Generate embeddings for each chunk using AI embedding model
- Store embeddings as vectors in pgvector
- This is where the actual AI intelligence gets added!

-------------------  End ----------------